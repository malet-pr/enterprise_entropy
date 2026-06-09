package org.acme.incidents.engine.built;

import static org.acme.incidents.api.built.CombinedStringsPovided.combineBlockedLogs;
import static org.acme.incidents.api.built.NormalizeIDProvided.normalizeID;
import static org.acme.incidents.api.built.PostProcessIncidentProvided.moveToWarRoom;
import static org.acme.incidents.api.built.SupplyDefaultProvided.supplyDefaultHourOfDay;
import static org.acme.incidents.api.built.SupplyDefaultProvided.supplyDefaultOccurrences;
import static org.acme.incidents.api.built.SupplyDefaultProvided.supplyDefaultSeverityScore;
import org.acme.incidents.dto.ProcessedIncident;
import org.acme.incidents.dto.ProcessedIncidentFull;
import org.acme.incidents.engine.domain.Process;
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
            Function<Incident, TriageDecision> escalationPolicy,
            Function<Incident, Team> routingPolicy,
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
            Consumer<Incident> action,
            BiConsumer<Incident, Team>  notify){
        log.trace("IncidentProcessorProvided::process2b");
        List<ProcessedIncidentFull> processedIncidents = new ArrayList<>();
        for (Incident incident : incidents) {
            IntSupplier severity = supplyDefaultSeverityScore;
            IntSupplier occurrences = supplyDefaultOccurrences;
            Supplier<Integer> hourOfDay = supplyDefaultHourOfDay;
            incident.setSeverityScore(incident.getSeverityScore() > 0 ? incident.getSeverityScore() : severity.getAsInt());
            incident.setOccurrences(incident.getOccurrences() > 0 ? incident.getOccurrences() : occurrences.getAsInt());
            incident.setHourOfDay(incident.getHourOfDay() > 0 ? incident.getHourOfDay() : hourOfDay.get());
            normalizeID.accept(incident);
            boolean suppressed = firstSuppressionRule.test(incident);
            if (suppressed) {
                processedIncidents.add(ProcessedIncidentFull.suppressed(incident));
                continue;
            }
            TriageDecision decision = escalationPolicy.apply(incident);
            Team team = routingPolicy.apply(incident);
            NextStep nextStep = nextStepResolver.apply(incident, decision);
            boolean blockedExecution = firstProceedingRule.test(decision, team);
            BinaryOperator<String> combineLogs = combineBlockedLogs;
            if (blockedExecution) {
                processedIncidents.add(ProcessedIncidentFull.blockedExecution(incident));
                log.info(combineLogs.apply(incident.getId(), ProcessProvided.blockingRule));
                continue;
            }
            UnaryOperator<ProcessedIncidentFull> postProcess = moveToWarRoom;
            ProcessedIncidentFull processedIncident = new ProcessedIncidentFull(incident, decision, team, nextStep, false,false);
            processedIncident = postProcess.apply(processedIncident);
            notify.accept(processedIncident.incident(),processedIncident.team());
            action.accept(incident);
            if (log.isInfoEnabled()) {
                log.info("    -> {}", processedIncident.logLine());
            }
            processedIncidents.add(processedIncident);
        }
        return processedIncidents;
    }

}


