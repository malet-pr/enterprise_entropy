package org.acme.rules.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.acme.rules.persistence.model.RuleDefinition;
import org.acme.rules.persistence.model.RuleCategory;
import org.acme.rules.persistence.repository.RuleCategoryRepository;
import org.acme.rules.persistence.repository.RuleDefinitionRepository;
import org.kie.api.KieServices;
import org.drools.model.codegen.ExecutableModelProject;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RuleCacheManager {

    private final RuleDefinitionRepository ruleDefinitionRepository;
    private final RuleCategoryRepository ruleCategoryRepository;

    @Value("${drools.rules.cache.maximum-size:100}")
    private int maximumSize;

    @Value("${drools.rules.cache.expire-after-access:24h}")
    private String expireAfterAccess;

    @Value("${drools.rules.cache.expire-after-write:24h}")
    private String expireAfterWrite;

    // Cache for KieContainers by category
    private final Map<String, KieContainer> kieContainerCache = new ConcurrentHashMap<>();

    // Cache for rule content versions (to detect changes)
    private final Map<String, String> ruleContentHashCache = new ConcurrentHashMap<>();

    // Caffeine cache for rule definitions
    private Cache<String, List<RuleDefinition>> ruleDefinitionCache;

    private final KieContainer mainKieContainer;

    public RuleCacheManager(RuleDefinitionRepository ruleDefinitionRepository,
                            RuleCategoryRepository ruleCategoryRepository, KieContainer mainKieContainer) {
        this.ruleDefinitionRepository = ruleDefinitionRepository;
        this.ruleCategoryRepository = ruleCategoryRepository;
        this.mainKieContainer = mainKieContainer;
    }

    @PostConstruct
    public void init() {
        // Initialize Caffeine cache
        ruleDefinitionCache = Caffeine.newBuilder()
                .maximumSize(maximumSize)
                .expireAfterAccess(24, TimeUnit.HOURS)
                .expireAfterWrite(24, TimeUnit.HOURS)
                .recordStats()
                .build();

        log.info("RuleCacheManager initialized with max size: {}", maximumSize);

        // Load all active categories on startup
        loadAllCategories();
    }

    /**
     * Load all rule categories and build KieContainers
     */
    public void loadAllCategories() {
        List<RuleCategory> categories = ruleCategoryRepository.findByActiveTrue();
        log.info("Loading {} rule categories", categories.size());

        for (RuleCategory category : categories) {
            loadCategory(category.getCategoryName());
        }
    }

    /**
     * Load a specific rule category
     */
    @Transactional
    public KieContainer loadCategory(String category) {
        log.info("Loading rule category: {}", category);

        try {
            // Get active rules for this category
            List<RuleDefinition> rules = ruleDefinitionRepository.findByCategoryNameWithCategory(category);
            if (rules.isEmpty()) {
                log.warn("No active rules found for category: {}", category);
                return null;
            }

            // Build DRL content from all rules
            String drlContent = buildDrlFromRules(category, rules);

            // Calculate content hash for change detection
            String contentHash = String.valueOf(drlContent.hashCode());
            String cachedHash = ruleContentHashCache.get(category);

            // If content hasn't changed and we have a cached KieContainer, reuse it
            if (contentHash.equals(cachedHash) && kieContainerCache.containsKey(category)) {
                log.info("Rule content unchanged for category: {}, reusing cached KieContainer", category);
                return kieContainerCache.get(category);
            }

            // Build new KieContainer
            KieContainer kieContainer = buildKieContainer(category, drlContent);

            // Update caches
            kieContainerCache.put(category, kieContainer);
            ruleContentHashCache.put(category, contentHash);

            // Also cache the rule definitions
            ruleDefinitionCache.put(category, rules);

            log.info("Successfully loaded {} rules for category: {}", rules.size(), category);
            return kieContainer;

        } catch (Exception e) {
            log.error("Failed to load rule category: {}", category, e);
            throw new RuntimeException("Failed to load rule category: " + category, e);
        }
    }

    /**
     * Build DRL content from multiple rule definitions
     */
    private String buildDrlFromRules(String category, List<RuleDefinition> rules) {
        StringBuilder drlBuilder = new StringBuilder();

        // Add package declaration
        drlBuilder.append("package rules.").append(category).append(";\n\n");

        // Add imports
        drlBuilder.append("import org.acme.rules.model.*;\n");
        drlBuilder.append("import java.util.*;\n\n");

        // Add globals if needed
        drlBuilder.append("global java.util.Map<String, Object> results;\n\n");

        // Add each rule
        for (RuleDefinition rule : rules) {
            drlBuilder.append("// Rule: ").append(rule.getRuleName()).append("\n");
            drlBuilder.append("// Category: ").append(rule.getCategory()).append("\n");
            drlBuilder.append("// Priority: ").append(rule.getPriority()).append("\n");
            drlBuilder.append(rule.getRuleContent()).append("\n\n");
        }

        return drlBuilder.toString();
    }

    /**
     * Build KieContainer from DRL content
     */
    private KieContainer buildKieContainer(String category, String drlContent) {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        // Log the DRL content being built (for debugging)
        log.info("Building KieContainer for category: {} with DRL length: {}", category, drlContent.length());
        log.debug("DRL Content:\n{}", drlContent);  // Change to debug or remove after debugging

        String drlPath = String.format("src/main/resources/rules/%s/rules.drl", category);
        kieFileSystem.write(ResourceFactory.newByteArrayResource(drlContent.getBytes(StandardCharsets.UTF_8))
                .setSourcePath(drlPath));

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll(ExecutableModelProject.class);

        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            log.error("=== DRL COMPILATION ERRORS for category: {} ===", category);
            for (Message msg : results.getMessages(Message.Level.ERROR)) {
                log.error("Error: {}", msg.getText());
                log.error("  Path: {}", msg.getPath());
                if (msg.getLine() > 0) {
                    log.error("  Line: {}, Column: {}", msg.getLine(), msg.getColumn());
                }
            }
            log.error("=== Full DRL Content ===");
            log.error(drlContent);
            throw new RuntimeException("Failed to build KieContainer for category: " + category);
        }

        // Return the KieContainer
        KieContainer kieContainer = kieServices.newKieContainer(
                kieServices.getRepository().getDefaultReleaseId());

        log.info("Built KieContainer for category: {}", category);
        return kieContainer;
    }

    /**
     * Get KieContainer for a specific category
     */
    public KieContainer getKieContainer(String category) {
        KieContainer kieContainer = kieContainerCache.get(category);
        if (kieContainer == null) {
            log.info("KieContainer not found in cache for category: {}, loading...", category);
            kieContainer = loadCategory(category);
        }
        if (kieContainer == null) {
            log.warn("No database rules for category: {}, using classpath KieContainer", category);
            kieContainer = getClasspathKieContainer();
        }
        return kieContainer;
    }

    private KieContainer getClasspathKieContainer() {
        // Return the main KieContainer that loads from classpath
        return mainKieContainer;
    }

    /**
     * Refresh a specific category (reload from database)
     */
    public void refreshCategory(String category) {
        log.info("Refreshing rule category: {}", category);
        // Remove from cache to force reload
        kieContainerCache.remove(category);
        ruleContentHashCache.remove(category);
        ruleDefinitionCache.invalidate(category);
        // Reload
        loadCategory(category);
    }

    /**
     * Refresh all categories
     */
    public void refreshAllCategories() {
        log.info("Refreshing all rule categories");
        List<RuleCategory> categories = ruleCategoryRepository.findByActiveTrue();
        for (RuleCategory category : categories) {
            refreshCategory(category.getCategoryName());
        }
    }

    /**
     * Reload rules that have changed since a given timestamp
     */
    @Transactional
    public void reloadChangedRules(LocalDateTime since) {
        List<RuleDefinition> changedRules = ruleDefinitionRepository.findByUpdatedAtAfter(since);

        if (changedRules.isEmpty()) {
            log.debug("No rules changed since: {}", since);
            return;
        }

        log.info("Found {} changed rules since: {}", changedRules.size(), since);

        // Group changed rules by category
        Map<String, List<RuleDefinition>> changedByCategory = new ConcurrentHashMap<>();
        for (RuleDefinition rule : changedRules) {
            changedByCategory.computeIfAbsent(rule.getCategory().getCategoryName(), k -> new java.util.ArrayList<>())
                    .add(rule);
        }

        // Refresh each affected category
        for (String category : changedByCategory.keySet()) {
            refreshCategory(category);
        }
    }

    /**
     * Get cache statistics
     */
    public Map<String, Object> getCacheStatistics() {
        Map<String, Object> stats = new java.util.HashMap<>();
        stats.put("caffeine_stats", ruleDefinitionCache.stats());
        stats.put("kie_container_count", kieContainerCache.size());
        stats.put("cached_categories", kieContainerCache.keySet());
        return stats;
    }

    /**
     * Get rule definitions from cache or database
     */
    public List<RuleDefinition> getRuleDefinitions(String category) {
        List<RuleDefinition> rules = ruleDefinitionCache.getIfPresent(category);
        if (rules == null) {
            rules = ruleDefinitionRepository.findByCategoryAndActiveTrueOrderByPriorityAsc(category);
            ruleDefinitionCache.put(category, rules);
        }
        return rules;
    }
}