package org.acme.incidents.api;

import org.acme.incidents.model.Incident;
import java.util.function.Predicate;

public final class SuppressionRulesProvided {

   public static Predicate<Incident> devHealthcheckNoise = incident ->
            "dev".equalsIgnoreCase(incident.getEnvironment())
                    && incident.getMessage().toLowerCase().contains("healthcheck failed")
                    && incident.getOccurrences() < 3;

   public static Predicate<Incident> testSapTransientNoise = incident ->
            "test".equalsIgnoreCase(incident.getEnvironment())
                    && incident.getMessage().toLowerCase().contains("connection refused")
                    && incident.getOccurrences() < 3;

   public static Predicate<Incident> legacyGhostCallNoise = incident ->
            "prod".equalsIgnoreCase(incident.getEnvironment())
                    && incident.getSeverityScore() < 6
                    && !incident.isCustomerImpact();

}
