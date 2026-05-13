package org.acme.incidents.api.built;

import org.acme.incidents.dto.built.NamedPredicate;
import org.acme.incidents.model.Incident;
import java.util.List;
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


   public static List<NamedPredicate<Incident>> suppressionRules = List.of(
            new NamedPredicate<>("dev healthcheck noise", devHealthcheckNoise),
            new NamedPredicate<>("test SAP transient noise", testSapTransientNoise),
            new NamedPredicate<>("legacy ghost call noise", legacyGhostCallNoise)
   );

}
