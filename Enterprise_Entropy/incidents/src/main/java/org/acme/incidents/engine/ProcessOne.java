package org.acme.incidents.engine;

import org.acme.incidents.api.*;
import org.acme.incidents.dto.ProcessedIncident;
import org.acme.incidents.model.Incident;
import org.acme.incidents.model.NextStep;
import org.acme.incidents.model.Team;
import org.acme.incidents.model.TriageDecision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProcessOne {

    Logger log = LoggerFactory.getLogger(ProcessOne.class);

    public List<ProcessedIncident> process(List<Incident> incidents) {
        IncidentProcessor processor = new IncidentProcessor();
        return processor.process(
                incidents,
                suppressionRule,
                escalationPolicy,
                routingPolicy,
                followUp,
                action
        );
    }

    SuppressionRule suppressionRule = incident ->
            "dev".equalsIgnoreCase(incident.getEnvironment())
                    && incident.getMessage().toLowerCase().contains("healthcheck failed")
                    && incident.getOccurrences() < 3;

    EscalationPolicy escalationPolicy = incident -> {
        boolean prod = "prod".equalsIgnoreCase(incident.getEnvironment());
        if (prod && incident.getSeverityScore() >= 9 && incident.isCustomerImpact()) {
            return TriageDecision.WAKE_SOMEONE_UP;
        }
        if (prod && incident.getSeverityScore() >= 8) {
            return TriageDecision.WE_SHOULD_TELL_SOMEONE;
        }
        if (incident.getSeverityScore() >= 5) {
            return TriageDecision.WE_SHOULD_PROBABLY_LOOK_AT_THIS;
        }
        return TriageDecision.FORGET_IT;
    };

    RoutingPolicy routingPolicy = incident -> {
        String service = incident.getService().toLowerCase();
        if (service.contains("billing")) {
            return Team.WHERE_IS_MY_MONEY;
        }
        if (service.contains("gateway")) {
            return Team.MACHINES_AND_STUFF;
        }
        if (service.contains("auth")) {
            return Team.THIS_GATE_IS_CLOSED;
        }
        if (service.contains("sap") || service.contains("kafka")) {
            return Team.TALK_AMONG_YOURSELVES;
        }
        return Team.WHAT_IS_THIS;
    };

    FollowUp followUp = incident -> {
        String service = incident.getService().toLowerCase();
        if (service.contains("gateway") && incident.isCustomerImpact()) {
            return NextStep.INVESTIGATE_AND_FIX;
        }
        return NextStep.ALL_DONE;
    };

    IncidentAction action = incident ->
            log.info("Action executed for {}", incident.getId());
}
