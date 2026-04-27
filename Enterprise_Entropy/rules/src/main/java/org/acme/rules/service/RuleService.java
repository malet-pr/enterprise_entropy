package org.acme.rules.service;

import lombok.extern.slf4j.Slf4j;
import org.acme.rules.config.RuleCacheManager;
import org.acme.rules.model.*;
import org.acme.rules.persistence.repository.RuleDefinitionRepository;
import org.drools.core.event.DebugAgendaEventListener;
import org.drools.core.event.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class RuleService {

    private final RuleCacheManager ruleCacheManager;
    private final RuleDefinitionRepository ruleDefinitionRepository;

    public RuleService(RuleCacheManager ruleCacheManager, RuleDefinitionRepository ruleDefinitionRepository) {
        this.ruleCacheManager = ruleCacheManager;
        this.ruleDefinitionRepository = ruleDefinitionRepository;
    }

    public ScenarioResult processScenario(ScenarioOrigin origin) {
        KieContainer kieContainer = ruleCacheManager.getKieContainer(origin.getMeeting().getMeetingType().toString());
        if (kieContainer == null) {
            log.error("No KieContainer found for DAILY category");
            return null;
        }
        log.info("Processing scenario {}", origin.getScenarioId());
        ScenarioResult result = new ScenarioResult();
        try (KieSession kieSession = kieContainer.newKieSession()){
            //kieSession.addEventListener(new DebugAgendaEventListener());
            //kieSession.addEventListener(new DebugRuleRuntimeEventListener());
            Meeting meeting = origin.getMeeting();
            Issue issue = origin.getIssue();
            // Insert facts
            kieSession.insert(meeting);
            kieSession.insert(issue);
            // Use global to capture result - set it before firing rules
            Map<String, Object> results = new HashMap<>();
            kieSession.setGlobal("results", results);
            // Fire rules
            kieSession.fireAllRules();
            log.info("rules applied: {}", results.get("appliedRule"));
            Object appliedRuleObj = results.get("appliedRule");
            List<String> appliedRules = new ArrayList<>();
            if (appliedRuleObj instanceof String) {
                appliedRules.add((String) appliedRuleObj);
            } else if (appliedRuleObj instanceof List) {
                appliedRules = (List<String>) appliedRuleObj;
            }
            // Collect results from globals or modified facts
            result.setScenarioId(origin.getScenarioId());
            result.setIssue(issue);
            result.setMeeting(meeting);
            result.setAppliedRules(appliedRules);
        } catch (Exception e) {
            log.error("Error during scenario processing", e);
        }
        log.info("Scenario {} processed successfully.", result.getScenarioId());
        return result;
    }
}

