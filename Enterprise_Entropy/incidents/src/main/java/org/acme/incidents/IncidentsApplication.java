package org.acme.incidents;

import org.acme.incidents.api.*;
import org.acme.incidents.demo.SampleIncidents;
import org.acme.incidents.engine.IncidentProcessor;
import org.acme.incidents.model.Incident;
import org.acme.incidents.model.NextStep;
import org.acme.incidents.model.Team;
import org.acme.incidents.model.TriageDecision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.function.Supplier;

@SpringBootApplication
public class IncidentsApplication  implements CommandLineRunner {

    protected static Logger log = LoggerFactory.getLogger(IncidentsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(IncidentsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        log.info("\n\nStarting Incident Triage Application Runner ...\n");

        Supplier<List<Incident>> incidentSupplier = SampleIncidents.sampleData();

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

        IncidentProcessor processor = new IncidentProcessor();
        processor.process(
                incidentSupplier.get(),
                suppressionRule,
                escalationPolicy,
                routingPolicy,
                followUp,
                action
        );

        log.info("\n\nIncident Triage Application Runner Finished...\n");
    }
}
