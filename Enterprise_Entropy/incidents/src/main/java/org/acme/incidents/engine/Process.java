package org.acme.incidents.engine;

import org.acme.incidents.api.*;
import org.acme.incidents.dto.NamedProceedingRules;
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
import static org.acme.incidents.api.ProceedingRulesDomain.*;

@Service
public class Process {

    Logger log = LoggerFactory.getLogger(Process.class);

    public List<ProcessedIncident> processOne(List<Incident> incidents) {
        IncidentProcessor processor = new IncidentProcessor();
        log.trace("Processing Incidents in Process: {}", incidents.stream().map(Incident::getId).toList());
        return processor.processOne(
                incidents,
                suppressionRule,
                proceedingRule,
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
                firstProceedingRule,
                escalationPolicy,
                routingPolicy,
                followUp,
                action
        );
    }

    public List<ProcessedIncident> processThree(List<Incident> incidents) {
        IncidentProcessor processor = new IncidentProcessor();
        log.trace("Processing Incidents in ProcessTwo: {}", incidents.stream().map(Incident::getId).toList());
        return processor.processThree(
                incidents,
                firstSuppressionRule,
                firstProceedingRule,
                escalationPolicy,
                routingPolicy,
                nextStepResolver,
                action
        );
    }

    SuppressionRule suppressionRule = SupressionRulesDomain.devHealthcheckNoise;

    Optional<NamedSuppressionRule> findFirstSuppressionRule(Incident incident) {
        return SupressionRulesDomain.firstSuppressionRule.stream()
                .filter(rule -> rule.shouldSuppress(incident))
                .findFirst();
    }

    SuppressionRule firstSuppressionRule = incident -> {
        Optional<NamedSuppressionRule> matchedRule = findFirstSuppressionRule(incident);
        matchedRule.ifPresent(p -> log.info("Suppressed incident {} by rule '{}'",
                incident.getId(), p.name()));
        return matchedRule.isPresent();
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

    DecisionAwareFollowUp nextStepResolver = (incident, decision) -> {
        String service = incident.getService().toLowerCase();
        if (service.contains("gateway") && incident.isCustomerImpact()) {
            return NextStep.INVESTIGATE_AND_FIX;
        }
        if (decision == TriageDecision.WAKE_SOMEONE_UP) {
            return NextStep.ALL_HANDS_ON_DECK;
        }
        if (decision == TriageDecision.WE_SHOULD_TELL_SOMEONE
                && incident.isCustomerImpact()) {
            return NextStep.INVESTIGATE_AND_FIX;
        }
        if (decision == TriageDecision.WE_SHOULD_PROBABLY_LOOK_AT_THIS
                && incident.getOccurrences() >= 5) {
            return NextStep.WRITE_A_TICKET;
        }
        return NextStep.ALL_DONE;
    };

    ProceedingRule proceedingRule = wakeWhenTeamUnknown;

    Optional<NamedProceedingRules> findFirstProceedingRule(TriageDecision decision, Team team) {
        return ProceedingRulesDomain.proceedingRules.stream()
                .filter(rule -> rule.shouldBlock(decision,team))
                .findFirst();
    }

    ProceedingRule firstProceedingRule = (decision,team) -> {
        Optional<NamedProceedingRules> matchedRule = findFirstProceedingRule(decision, team);
        matchedRule.ifPresent(p -> log.info("Blocked action by rule '{}'", p.name()));
        return matchedRule.isPresent();
    };

    IncidentAction action = incident ->
            log.info("Action executed for {}", incident.getId());
}
