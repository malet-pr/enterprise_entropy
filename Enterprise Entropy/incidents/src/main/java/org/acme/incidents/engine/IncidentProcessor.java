package org.acme.incidents.engine;

import lombok.extern.slf4j.XSlf4j;
import org.acme.incidents.api.EscalationPolicy;
import org.acme.incidents.api.IncidentAction;
import org.acme.incidents.api.RoutingPolicy;
import org.acme.incidents.api.SuppressionRule;
import org.acme.incidents.model.Incident;
import org.acme.incidents.model.Team;
import org.acme.incidents.model.TriageDecision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;


public class IncidentProcessor {

    Logger log = LoggerFactory.getLogger(IncidentProcessor.class);

    public void process(
            List<Incident> incidents,
            SuppressionRule suppressionRule,
            EscalationPolicy escalationPolicy,
            RoutingPolicy routingPolicy,
            IncidentAction action) {

        for (Incident incident : incidents) {
            if (suppressionRule.shouldSuppress(incident)) {
                log.info("[SUPPRESSED] {} -> {}", incident.getId(), incident.getMessage());
                continue;
            }
            TriageDecision decision = escalationPolicy.decide(incident);
            Team team = routingPolicy.route(incident);
            log.info("[TRIAGE] {} | service={} | decision={} | team={}",
                    incident.getId(), incident.getService(), decision, team);
            action.execute(incident);
        }
    }
}