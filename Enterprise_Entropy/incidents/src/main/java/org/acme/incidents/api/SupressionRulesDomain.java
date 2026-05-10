package org.acme.incidents.api;

import org.acme.incidents.dto.NamedSuppressionRule;
import java.util.List;

public class SupressionRulesDomain {

    public static final SuppressionRule devHealthcheckNoise = incident ->
            "dev".equalsIgnoreCase(incident.getEnvironment())
                    && incident.getMessage().toLowerCase().contains("healthcheck failed")
                    && incident.getOccurrences() < 3;

    public static final SuppressionRule testSapTransientNoise = incident ->
            "test".equalsIgnoreCase(incident.getEnvironment())
                    && incident.getMessage().toLowerCase().contains("connection refused")
                    && incident.getOccurrences() < 3;

    public static final SuppressionRule legacyGhostCallNoise = incident ->
            "prod".equalsIgnoreCase(incident.getEnvironment())
                    && incident.getSeverityScore() < 6
                    && !incident.isCustomerImpact();

    public static final List<NamedSuppressionRule> firstSuppressionRule = List.of(
        new NamedSuppressionRule("dev healthcheck noise", devHealthcheckNoise),
        new NamedSuppressionRule("test SAP transient noise", testSapTransientNoise),
        new NamedSuppressionRule("legacy ghost call noise", legacyGhostCallNoise)
    );

}
