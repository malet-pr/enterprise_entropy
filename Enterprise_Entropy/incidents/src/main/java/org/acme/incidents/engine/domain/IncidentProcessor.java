package org.acme.incidents.engine.domain;

import org.acme.incidents.api.domain.interfaces.*;
import org.acme.incidents.dto.ProcessedIncident;
import org.acme.incidents.model.Incident;
import org.acme.incidents.model.NextStep;
import org.acme.incidents.model.Team;
import org.acme.incidents.model.TriageDecision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class IncidentProcessor {

    Logger log = LoggerFactory.getLogger(IncidentProcessor.class);

    public List<ProcessedIncident> processOne(
            List<Incident> incidents,
            SuppressionRule suppressionRule,
            ProceedingRule proceedingRule,
            EscalationPolicy escalationPolicy,
            RoutingPolicy routingPolicy,
            IncidentAction action) {
        log.trace("IncidentProcessor::process1a");
        List<ProcessedIncident> processedIncidents = new ArrayList<>();
        for (Incident incident : incidents) {
            boolean suppressed = suppressionRule.shouldSuppress(incident);
            if (suppressed) {
                processedIncidents.add(ProcessedIncident.suppressed(incident));
                log.info("Suppressed incident {}", incident.getId());
                continue;
            }
            TriageDecision decision = escalationPolicy.decide(incident);
            Team team = routingPolicy.route(incident);
            NextStep nextStep = null;
            boolean blockedExecution = proceedingRule.shouldBlock(decision, team);
            if (blockedExecution) {
                processedIncidents.add(ProcessedIncident.blockedExecution(incident));
                log.info("    -> Incident blocked: {}", incident.getId());
                continue;
            }
            action.execute(incident);
            ProcessedIncident processedIncident = new ProcessedIncident(incident, decision, team, nextStep, false,false);
            if (log.isInfoEnabled()) {
                log.info("    -> {}", processedIncident.logLine());
            }
            processedIncidents.add(processedIncident);
        }
        return processedIncidents;
    }

    public List<ProcessedIncident> processTwo(
            List<Incident> incidents,
            SuppressionRule firstSuppressionRule,
            ProceedingRule firstProceedingRule,
            EscalationPolicy escalationPolicy,
            RoutingPolicy routingPolicy,
            IncidentAction action) {
        log.trace("IncidentProcessor::process1b");
        List<ProcessedIncident> processedIncidents = new ArrayList<>();
        for (Incident incident : incidents) {
            boolean suppressed = firstSuppressionRule.shouldSuppress(incident);
            if (suppressed) {
                processedIncidents.add(ProcessedIncident.suppressed(incident));
                continue;
            }
            TriageDecision decision = escalationPolicy.decide(incident);
            Team team = routingPolicy.route(incident);
            NextStep nextStep = null;
            boolean blockedExecution = firstProceedingRule.shouldBlock(decision, team);
            if (blockedExecution) {
                processedIncidents.add(ProcessedIncident.blockedExecution(incident));
                log.info("    -> Incident blocked: {}", incident.getId());
                continue;
            }
            action.execute(incident);
            ProcessedIncident processedIncident = new ProcessedIncident(incident, decision, team, nextStep, false,false);
            if (log.isInfoEnabled()) {
                log.info("    -> {}", processedIncident.logLine());
            }
            processedIncidents.add(processedIncident);
        }
        return processedIncidents;
    }

    public List<ProcessedIncident> processThree(
            List<Incident> incidents,
            SuppressionRule firstSuppressionRule,
            ProceedingRule firstProceedingRule,
            EscalationPolicy escalationPolicy,
            RoutingPolicy routingPolicy,
            FollowUp nextStepResolver,
            IncidentAction action) {
        log.trace("IncidentProcessor::process1c");
        List<ProcessedIncident> processedIncidents = new ArrayList<>();
        for (Incident incident : incidents) {
            boolean suppressed = firstSuppressionRule.shouldSuppress(incident);
            if (suppressed) {
                processedIncidents.add(ProcessedIncident.suppressed(incident));
                continue;
            }
            TriageDecision decision = escalationPolicy.decide(incident);
            Team team = routingPolicy.route(incident);
            NextStep nextStep = nextStepResolver.decide(incident, decision);
            boolean blockedExecution = firstProceedingRule.shouldBlock(decision, team);
            if (blockedExecution) {
                processedIncidents.add(ProcessedIncident.blockedExecution(incident));
                log.info("    -> Incident blocked: {}", incident.getId());
                continue;
            }
            action.execute(incident);
            ProcessedIncident processedIncident = new ProcessedIncident(incident, decision, team, nextStep, false,false);
            if (log.isInfoEnabled()) {
                log.info("    -> {}", processedIncident.logLine());
            }
            processedIncidents.add(processedIncident);
        }
        return processedIncidents;
    }
}