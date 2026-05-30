package org.acme.incidents.api.domain;

import org.acme.incidents.api.domain.interfaces.SuppressionRule;
import org.acme.incidents.dto.domain.NamedSuppressionRule;
import org.acme.incidents.model.Incident;
import java.util.List;
import java.util.Optional;

public final class SupressionRulesDomain {

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

    public static Optional<NamedSuppressionRule> findFirstSuppressionRule(Incident incident) {
        return SupressionRulesDomain.firstSuppressionRule.stream()
                .filter(rule -> rule.shouldSuppress(incident))
                .findFirst();
    }

    public static class NormalizeIncidentDomain {

        String normalizeID (Incident incident) {
            return incident.getId().toUpperCase();
        }

    }
}
