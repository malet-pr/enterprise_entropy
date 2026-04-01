package org.acme.incidents.engine;

import org.acme.incidents.dto.ProcessedIncident;
import org.acme.incidents.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
public class IncidentProcessorProvided {

    Logger log = LoggerFactory.getLogger(IncidentProcessorProvided.class);

    public List<ProcessedIncident> process(
            List<Incident> incidents,
            Predicate<Incident> suppressionRule,
            Function<Incident, TriageDecision> escalationPolicy,
            Function<Incident, Team> routingPolicy,
            Function<Incident, NextStep> followUp,
            Consumer<Incident> action){
        log.trace("IncidentProcessorProvided::process");
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
            NextStep nextStep = followUp.apply(incident);
            action.accept(incident);
            ProcessedIncident processedIncident = new ProcessedIncident(incident, decision, team, nextStep, false);
            if (log.isInfoEnabled()) {
                log.info("    -> {}", processedIncident.logLine());
            }
            processedIncidents.add(processedIncident);
        }
        return processedIncidents;
    }

}
