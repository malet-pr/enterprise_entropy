package org.acme.incidents.engine;

import org.acme.incidents.api.*;
import org.acme.incidents.model.Incident;
import org.acme.incidents.model.NextStep;
import org.acme.incidents.model.Team;
import org.acme.incidents.model.TriageDecision;
import org.acme.incidents.dto.ProcessedIncident;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IncidentProcessor {

    Logger log = LoggerFactory.getLogger(IncidentProcessor.class);

    public List<ProcessedIncident> process(
            List<Incident> incidents,
            SuppressionRule suppressionRule,
            EscalationPolicy escalationPolicy,
            RoutingPolicy routingPolicy,
            FollowUp followUp,
            IncidentAction action) {
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
            NextStep nextStep = followUp.decide(incident);
            action.execute(incident);
            ProcessedIncident processedIncident = new ProcessedIncident(incident, decision, team, nextStep, false);
            if (log.isInfoEnabled()) {
                log.info("    -> {}", processedIncident.logLine());
            }
            processedIncidents.add(processedIncident);
        }
        return processedIncidents;
    }
}