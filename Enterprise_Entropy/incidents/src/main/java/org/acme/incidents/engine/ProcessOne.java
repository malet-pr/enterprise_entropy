package org.acme.incidents.engine;

import org.acme.incidents.api.SuppressionRule;
import org.acme.incidents.api.EscalationPolicy;
import org.acme.incidents.api.RoutingPolicy;
import org.acme.incidents.api.FollowUp;
import org.acme.incidents.api.IncidentAction;
import org.acme.incidents.api.SupressionRulesDomain;
import org.acme.incidents.dto.NamedSuppressionRule;
import org.acme.incidents.dto.ProcessedIncident;
import org.acme.incidents.model.Incident;
import org.acme.incidents.model.NextStep;
import org.acme.incidents.model.Team;
import org.acme.incidents.model.TriageDecision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import static org.acme.incidents.api.SuppressionRulesProvided.suppressionRules;

@Service
public class ProcessOne {

    Logger log = LoggerFactory.getLogger(ProcessOne.class);

    public List<ProcessedIncident> processOne(List<Incident> incidents) {
        IncidentProcessor processor = new IncidentProcessor();
        log.trace("Processing Incidents in ProcessOne: {}", incidents.stream().map(Incident::getId).toList());
        return processor.processOne(
                incidents,
                suppressionRule,
                escalationPolicy,
                routingPolicy,
                followUp,
                action
        );
    }

    public List<ProcessedIncident> processTwo(List<Incident> incidents) {
        IncidentProcessor processor = new IncidentProcessor();
        log.trace("Processing Incidents in ProcessTwo: {}", incidents.stream().map(Incident::getId).toList());
        return processor.processTwo(
                incidents,
                firstSuppressionRule,
                escalationPolicy,
                routingPolicy,
                followUp,
                action
        );
    }

    SuppressionRule suppressionRule = SupressionRulesDomain.devHealthcheckNoise;

    SuppressionRule firstSuppressionRule = incident -> {
        Optional<NamedSuppressionRule> matchedRule =
                SupressionRulesDomain.firstSuppressionRule.stream()
                        .filter(rule -> rule.shouldSuppress(incident))
                        .findFirst();
        matchedRule.ifPresent(p -> log.info("Suppressed incident {} by rule '{}'",
                incident.getId(), p.name()));
        return matchedRule
                .map(incidentNamedPredicate -> incidentNamedPredicate.shouldSuppress(incident))
                .orElse(false);
    };

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
