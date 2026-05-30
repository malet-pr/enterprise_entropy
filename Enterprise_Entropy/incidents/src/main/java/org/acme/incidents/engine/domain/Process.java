package org.acme.incidents.engine.domain;

import org.acme.incidents.api.domain.interfaces.*;
import org.acme.incidents.dto.ProcessedIncidentFull;
import org.acme.incidents.dto.domain.NamedProceedingRules;
import org.acme.incidents.dto.domain.NamedSuppressionRule;
import org.acme.incidents.dto.ProcessedIncident;
import org.acme.incidents.model.Incident;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import static org.acme.incidents.api.domain.EscalationPolicyDomain.*;
import static org.acme.incidents.api.domain.FollowUpDomain.allFollowUps;
import static org.acme.incidents.api.domain.ProceedingRulesDomain.*;
import static org.acme.incidents.api.domain.RoutingPoliciesDomain.*;
import static org.acme.incidents.api.domain.SupressionRulesDomain.*;


@Service
public class Process {

    Logger log = LoggerFactory.getLogger(Process.class);

    public List<ProcessedIncident> processOne(List<Incident> incidents) {
        IncidentProcessor processor = new IncidentProcessor();
        log.trace("Processing Incidents in Process: {}", incidents.stream().map(Incident::getId).toList());
        return processor.processOne(
                incidents,
                suppressionRule,
                escalationPolicy,
                routingPolicy,
                action
        );
    }

    public List<ProcessedIncidentFull> processTwo(List<Incident> incidents) {
        IncidentProcessor processor = new IncidentProcessor();
        log.trace("Processing Incidents in ProcessTwo: {}", incidents.stream().map(Incident::getId).toList());
        return processor.processTwo(
                incidents,
                firstSuppressionRule,
                firstProceedingRule,
                escalationPolicy,
                routingPolicy,
                nextStepResolver,
                action,
                notify
        );
    }

    /////////////////////////////////////////
    SuppressionRule suppressionRule = devHealthcheckNoise;
    ProceedingRule proceedingRule = wakeWhenTeamUnknown;
    //////////////////////////////////////////

    public SuppressionRule firstSuppressionRule = incident -> {
        Optional<NamedSuppressionRule> matchedRule = findFirstSuppressionRule(incident);
        matchedRule.ifPresent(p -> log.info("Suppressed incident {} by rule '{}'",
                incident.getId(), p.name()));
        return matchedRule.isPresent();
    };

    public ProceedingRule firstProceedingRule = (decision,team) -> {
        Optional<NamedProceedingRules> matchedRule = findFirstProceedingRule(decision, team);
        matchedRule.ifPresent(p -> log.info("Blocked action by rule '{}'", p.name()));
        return matchedRule.isPresent();
    };

    EscalationPolicy escalationPolicy = allPolicies;

    RoutingPolicy routingPolicy = allRoutings;

    FollowUp nextStepResolver = allFollowUps;

    IncidentAction action = incident ->
            log.info("Action executed for {}", incident.getId());

    NotifyTeam notify = (incident, team) ->
            log.info("Notify team {} that they where assigned incident {} with severity {}.",
                    team.name(), incident.getId(), incident.getSeverityScore());

}
