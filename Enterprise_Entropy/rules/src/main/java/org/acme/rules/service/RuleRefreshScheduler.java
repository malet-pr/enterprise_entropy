package org.acme.rules.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acme.rules.config.RuleCacheManager;
import org.acme.rules.persistence.model.RuleCategory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class RuleRefreshScheduler {

    private final RuleCacheManager ruleCacheManager;
    private final RuleService ruleService;

    @Value("${drools.rules.cache.refresh-interval:3600000}")
    private long refreshInterval;

    // Track last refresh time for incremental updates
    private final AtomicReference<LocalDateTime> lastFullRefresh = new AtomicReference<>(LocalDateTime.now().minusHours(1));

    /**
     * Full refresh of all rules - runs every hour
     */
    @Scheduled(fixedDelayString = "${drools.rules.cache.refresh-interval:3600000}")
    @Transactional
    public void refreshAllRules() {
        log.info("Starting scheduled full refresh of all rule categories");
        long startTime = System.currentTimeMillis();

        try {
            ruleCacheManager.refreshAllCategories();
            lastFullRefresh.set(LocalDateTime.now());

            long duration = System.currentTimeMillis() - startTime;
            log.info("Completed full refresh in {} ms", duration);

        } catch (Exception e) {
            log.error("Failed to refresh rules", e);
        }
    }

    /**
     * Incremental refresh for recently changed rules - runs every 5 minutes
     */
    @Scheduled(fixedDelay = 300000) // 5 minutes
    @Transactional
    public void refreshChangedRules() {
        log.debug("Checking for recently changed rules");
        long startTime = System.currentTimeMillis();

        try {
            // Check for rules changed in the last 5 minutes
            LocalDateTime since = LocalDateTime.now().minusMinutes(5);
            ruleCacheManager.reloadChangedRules(since);

            long duration = System.currentTimeMillis() - startTime;
            if (duration > 100) {
                log.info("Completed incremental refresh in {} ms", duration);
            }

        } catch (Exception e) {
            log.error("Failed to refresh changed rules", e);
        }
    }

    /**
     * Manual refresh endpoint trigger
     */
    @Transactional
    public void manualRefresh(String category) {
        log.info("Manual refresh requested for category: {}", category);
        if (category == null ) {
            refreshAllRules();
        } else {
            ruleCacheManager.refreshCategory(category);
        }
    }
}