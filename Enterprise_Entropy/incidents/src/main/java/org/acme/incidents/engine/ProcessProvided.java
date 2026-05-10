package org.acme.incidents.engine;

import org.acme.incidents.dto.NamedBiPredicate;
import org.acme.incidents.dto.NamedPredicate;
import org.acme.incidents.dto.ProcessedIncident;
import org.acme.incidents.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.function.*;
import static org.acme.incidents.api.ProceedingRulesProvided.*;
import static org.acme.incidents.api.SuppressionRulesProvided.*;


@Service
public class ProcessProvided {

    Logger log = LoggerFactory.getLogger(ProcessProvided.class);

    public List<ProcessedIncident> processOne(List<Incident> incidents) {
        log.trace("Processing Incidents in ProcessOneProvider: {}", incidents.stream().map(Incident::getId).toList());
        IncidentProcessorProvided processor = new IncidentProcessorProvided();
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
        log.trace("Processing Incidents in ProcessOneProvider with First Suppression Rule: {}", incidents.stream().map(Incident::getId).toList());
        IncidentProcessorProvided processor = new IncidentProcessorProvided();
        return processor.processTwo(
                incidents,
                firstSuppressionRule,
                proceedingRule,
                escalationPolicy,
                routingPolicy,
                followUp,
                action
        );
    }

    public List<ProcessedIncident> processThree(List<Incident> incidents) {
        log.trace("Processing Incidents in ProcessOneProvider with First Suppression Rule: {}", incidents.stream().map(Incident::getId).toList());
        IncidentProcessorProvided processor = new IncidentProcessorProvided();
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

    Predicate<Incident> suppressionRule =
            devHealthcheckNoise.or(testSapTransientNoise).or(legacyGhostCallNoise);

    Predicate<Incident> firstSuppressionRule = incident -> {
        Optional<NamedPredicate<Incident>> matchedRule =
                suppressionRules.stream()
                        .filter(rule -> rule.test(incident))
                        .findFirst();
        matchedRule.ifPresent(p -> log.info("Suppressed incident {} by rule '{}'",
                incident.getId(), p.name()));
        return matchedRule
                .map(incidentNamedPredicate -> incidentNamedPredicate.test(incident))
                .orElse(false);
    };

    Function<Incident, TriageDecision> escalationPolicy = incident -> {
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

    Function<Incident, Team> routingPolicy = incident -> {
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

    Function<Incident, NextStep> followUp = incident -> {
        String service = incident.getService().toLowerCase();
        if (service.contains("gateway") && incident.isCustomerImpact()) {
            return NextStep.INVESTIGATE_AND_FIX;
        }
        return NextStep.ALL_DONE;
    };

    BiFunction<Incident, TriageDecision, NextStep> nextStepResolver =
        (incident, decision) -> {
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

    BiPredicate<TriageDecision, Team> proceedingRule = wakeWhenTeamUnknown;

    BiPredicate<TriageDecision, Team>  firstProceedingRule = (decision,team) -> {
        Optional<NamedBiPredicate<TriageDecision, Team>> matchedRule =
                proceedingRules.stream()
                        .filter(rule -> rule.test(decision, team))
                        .findFirst();
        matchedRule.ifPresent(p -> log.info("Blocked action by rule '{}'", p.name()));
        return matchedRule
                .map(rule -> rule.test(decision, team))
                .orElse(false);
    };

    Consumer<Incident> action = incident ->
           log.info("Action executed for {}", incident.getId());

}
