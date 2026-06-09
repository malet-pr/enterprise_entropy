package org.acme.incidents.engine.built;


import org.acme.incidents.api.domain.interfaces.*;
import org.acme.incidents.dto.ProcessedIncidentFull;
import org.acme.incidents.dto.built.NamedBiPredicate;
import org.acme.incidents.dto.ProcessedIncident;
import org.acme.incidents.dto.built.NamedPredicate;
import org.acme.incidents.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.function.*;
import static org.acme.incidents.api.built.EscalationPolicyProvided.allPolicies;
import static org.acme.incidents.api.built.FollowUpProvided.allFollowUps;
import static org.acme.incidents.api.built.ProceedingRulesProvided.*;
import static org.acme.incidents.api.built.RoutingPolicyProvided.allRoutings;
import static org.acme.incidents.api.built.SuppressionRulesProvided.*;


@Service
public class ProcessProvided {

    Logger log = LoggerFactory.getLogger(ProcessProvided.class);

    public static String blockingRule = "";

    public List<ProcessedIncident> processProvidedOne(List<Incident> incidents) {
        IncidentProcessorProvided processor = new IncidentProcessorProvided();
        log.trace("Processing Incidents in ProcessProvidedOne: {}", incidents.stream().map(Incident::getId).toList());
        return processor.processOne(
                incidents,
                suppressionRule,
                escalationPolicy,
                routingPolicy,
                action
        );
    }

    public List<ProcessedIncidentFull> processProvidedTwo(List<Incident> incidents) {
        IncidentProcessorProvided processor = new IncidentProcessorProvided();
        log.trace("Processing Incidents in ProcessProvidedTwo: {}", incidents.stream().map(Incident::getId).toList());
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
    Predicate<Incident> suppressionRule = devHealthcheckNoise;
    BiPredicate<TriageDecision, Team> proceedingRule = wakeWhenTeamUnknown;
    //////////////////////////////////////////


    public Predicate<Incident> firstSuppressionRule = incident -> {
        Optional<NamedPredicate<Incident>> matchedRule = findFirstSuppressionRule(incident);
        matchedRule.ifPresent(p -> log.info("Suppressed incident {} by rule '{}'",
                incident.getId(), p.name()));
        return matchedRule.isPresent();
    };

    public BiPredicate<TriageDecision, Team> firstProceedingRule = (decision, team) -> {
        Optional<NamedBiPredicate<TriageDecision, Team>> matchedRule = findFirstProceedingRule(decision, team);
        matchedRule.ifPresent(p -> blockingRule = "'" + p.name() + "'");
        return matchedRule.isPresent();
    };

    Function<Incident,TriageDecision> escalationPolicy = allPolicies;

    Function<Incident,Team> routingPolicy = allRoutings;

    BiFunction<Incident,TriageDecision,NextStep> nextStepResolver = allFollowUps;

    Consumer<Incident> action = incident ->
            log.info("Action executed for {}", incident.getId());

    BiConsumer<Incident, Team>  notify = (incident, team) ->
            log.info("Notify team {} that they where assigned incident {} with severity {}.",
                    team.name(), incident.getId(), incident.getSeverityScore());


}
