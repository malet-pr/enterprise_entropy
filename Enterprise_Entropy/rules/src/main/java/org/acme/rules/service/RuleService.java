package org.acme.rules.service;

import org.acme.rules.model.*;
import org.drools.core.event.DebugAgendaEventListener;
import org.drools.core.event.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
public class RuleService {
    private static final Logger log = LoggerFactory.getLogger(RuleService.class);
    private final KieContainer kieContainer;

    public RuleService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
        log.info("RuleService initialized with KieContainer");
    }

    public ScenarioResult processScenario(ScenarioOrigin origin) {
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
            List<String> appliedRules = new ArrayList<>();
            kieSession.setGlobal("appliedRules", appliedRules);
            // Fire rules
            kieSession.fireAllRules();
            log.info("rules applied: {}", appliedRules);
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