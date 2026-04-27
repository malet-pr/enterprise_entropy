package org.acme.rules.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acme.rules.config.RuleCacheManager;
import org.acme.rules.persistence.model.RuleDefinition;
import org.acme.rules.persistence.repository.RuleDefinitionRepository;
import org.acme.rules.service.RuleRefreshScheduler;
import org.acme.rules.service.RuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/rules/admin")
@RequiredArgsConstructor
public class RuleAdminController {

    private final RuleCacheManager ruleCacheManager;
    private final RuleDefinitionRepository ruleDefinitionRepository;
    private final RuleRefreshScheduler ruleRefreshScheduler;
    private final RuleService ruleService;

    @GetMapping("/cache/stats")
    public ResponseEntity<Map<String, Object>> getCacheStats() {
        return ResponseEntity.ok(ruleCacheManager.getCacheStatistics());
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshRules(@RequestParam(required = false) String category) {
        log.info("Manual refresh requested for category: {}", category);
        ruleRefreshScheduler.manualRefresh(category);

        Map<String, String> response = new HashMap<>();
        response.put("status", "Refresh triggered");
        response.put("category", category == null ? "ALL" : category);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/definitions")
    public ResponseEntity<List<RuleDefinition>> getAllRuleDefinitions() {
        return ResponseEntity.ok(ruleDefinitionRepository.findAll());
    }

    @GetMapping("/definitions/category/{category}")
    public ResponseEntity<List<RuleDefinition>> getRulesByCategory(@PathVariable String category) {
        return ResponseEntity.ok(ruleDefinitionRepository.findByCategoryAndActiveTrueOrderByPriorityAsc(category));
    }

    @PostMapping("/definitions")
    public ResponseEntity<RuleDefinition> createRuleDefinition(@RequestBody RuleDefinition ruleDefinition) {
        RuleDefinition saved = ruleDefinitionRepository.save(ruleDefinition);
        // Refresh the affected category
        ruleRefreshScheduler.manualRefresh(ruleDefinition.getCategory().getCategoryName());
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/definitions/{id}")
    public ResponseEntity<RuleDefinition> updateRuleDefinition(@PathVariable Long id,
                                                               @RequestBody RuleDefinition ruleDefinition) {
        ruleDefinition.setId(id);
        RuleDefinition saved = ruleDefinitionRepository.save(ruleDefinition);
        // Refresh the affected category
        ruleRefreshScheduler.manualRefresh(ruleDefinition.getCategory().getCategoryName());
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/definitions/{id}")
    public ResponseEntity<Void> deleteRuleDefinition(@PathVariable Long id) {
        RuleDefinition rule = ruleDefinitionRepository.findById(id).orElse(null);
        if (rule != null) {
            String category = rule.getCategory().getCategoryName();
            ruleDefinitionRepository.deleteById(id);
            ruleRefreshScheduler.manualRefresh(category);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/health/rules")
    public ResponseEntity<Map<String, Object>> rulesHealth() {
        Map<String, Object> health = new HashMap<>();
        health.put("cacheStats", ruleCacheManager.getCacheStatistics());
        health.put("status", "healthy");
        return ResponseEntity.ok(health);
    }
}