package org.acme.incidents;

import org.acme.incidents.api.EscalationPolicy;
import org.acme.incidents.api.IncidentAction;
import org.acme.incidents.api.RoutingPolicy;
import org.acme.incidents.api.SuppressionRule;
import org.acme.incidents.demo.SampleIncidents;
import org.acme.incidents.engine.IncidentProcessor;
import org.acme.incidents.model.Incident;
import org.acme.incidents.model.Team;
import org.acme.incidents.model.TriageDecision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.function.Supplier;

@SpringBootApplication
public class IncidentsApplication {

    public static void main(String[] args) {
       // SpringApplication.run(IncidentsApplication.class, args);

        Logger log = LoggerFactory.getLogger(IncidentsApplication.class);

        Supplier<List<Incident>> incidentSupplier = SampleIncidents.sampleData();

        SuppressionRule suppressionRule = incident ->
                "dev".equalsIgnoreCase(incident.getEnvironment())
                        && incident.getMessage().toLowerCase().contains("healthcheck failed")
                        && incident.getOccurrences() < 3;

        EscalationPolicy escalationPolicy = incident -> {
            boolean prod = "prod".equalsIgnoreCase(incident.getEnvironment());

            if (prod && incident.getSeverityScore() >= 9 && incident.isCustomerImpact()) {
                return TriageDecision.PAGE_NOW;
            }

            if (prod && incident.getSeverityScore() >= 8) {
                return TriageDecision.ESCALATE;
            }

            if (incident.getSeverityScore() >= 5) {
                return TriageDecision.INVESTIGATE;
            }

            return TriageDecision.LOG_ONLY;
        };

        RoutingPolicy routingPolicy = incident -> {
            String service = incident.getService().toLowerCase();

            if (service.contains("billing")) {
                return Team.BILLING;
            }
            if (service.contains("gateway")) {
                return Team.PLATFORM;
            }
            if (service.contains("auth")) {
                return Team.SECURITY;
            }
            if (service.contains("sap") || service.contains("kafka")) {
                return Team.INTEGRATIONS;
            }
            return Team.UNKNOWN;
        };

        IncidentAction action = incident ->
                log.info("   -> action executed for {}", incident.getId());

        IncidentProcessor processor = new IncidentProcessor();
        processor.process(
                incidentSupplier.get(),
                suppressionRule,
                escalationPolicy,
                routingPolicy,
                action
        );
    }

}
