package org.acme.incidents.engine.built;

import org.acme.incidents.dto.ProcessedIncident;
import org.acme.incidents.dto.ProcessedIncidentFull;
import org.acme.incidents.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

@Service
public class IncidentProcessorProvided {

    Logger log = LoggerFactory.getLogger(IncidentProcessorProvided.class);

    public List<ProcessedIncident> processOne(
            List<Incident> incidents,
            Predicate<Incident> suppressionRule,
            BiPredicate<TriageDecision, Team> proceedingRule,
            Function<Incident, TriageDecision> escalationPolicy,
            Function<Incident, Team> routingPolicy,
            Function<Incident, NextStep> followUp,
            Consumer<Incident> action){
        log.trace("IncidentProcessorProvided::process2a");
        List<ProcessedIncident> processedIncidents = new ArrayList<>();
        for (Incident incident : incidents) {
            boolean suppressed = suppressionRule.test(incident);
            if (suppressed) {
                processedIncidents.add(ProcessedIncident.suppressed(incident));
                log.info("Suppressed incident {}", incident.getId());
                continue;
            }
            TriageDecision decision = escalationPolicy.apply(incident);
            Team team = routingPolicy.apply(incident);
            NextStep nextStep = followUp.apply(incident);
            boolean blockedExecution = proceedingRule.test(decision, team);
            if (blockedExecution) {
                processedIncidents.add(ProcessedIncident.blockedExecution(incident));
                log.info("    -> Incident blocked: {}", incident.getId());
                continue;
            }
            action.accept(incident);
            ProcessedIncident processedIncident = new ProcessedIncident(incident, decision, team, false);
            if (log.isInfoEnabled()) {
                log.info("    -> {}", processedIncident.logLine());
            }
            processedIncidents.add(processedIncident);
        }
        return processedIncidents;
    }

    public List<ProcessedIncidentFull> processTwo(
            List<Incident> incidents,
            Predicate<Incident> firstSuppressionRule,
            BiPredicate<TriageDecision, Team> firstProceedingRule,
            Function<Incident, TriageDecision> escalationPolicy,
            Function<Incident, Team> routingPolicy,
            BiFunction<Incident,TriageDecision, NextStep> nextStepResolver,
            Consumer<Incident> action){
        log.trace("IncidentProcessorProvided::process2b");
        List<ProcessedIncidentFull> processedIncidents = new ArrayList<>();
        for (Incident incident : incidents) {
            boolean suppressed = firstSuppressionRule.test(incident);
            if (suppressed) {
                processedIncidents.add(ProcessedIncidentFull.suppressed(incident));
                continue;
            }
            TriageDecision decision = escalationPolicy.apply(incident);
            Team team = routingPolicy.apply(incident);
            NextStep nextStep = nextStepResolver.apply(incident, decision);
            boolean blockedExecution = firstProceedingRule.test(decision, team);
            if (blockedExecution) {
                processedIncidents.add(ProcessedIncidentFull.blockedExecution(incident));
                log.info("    -> Incident blocked: {}", incident.getId());
                continue;
            }
            action.accept(incident);
            ProcessedIncidentFull processedIncident = new ProcessedIncidentFull(incident, decision, team,nextStep,false,false);
            if (log.isInfoEnabled()) {
                log.info("    -> {}", processedIncident.logLine());
            }
            processedIncidents.add(processedIncident);
        }
        return processedIncidents;
    }

}


