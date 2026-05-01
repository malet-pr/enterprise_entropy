package org.acme.incidents.dto;

import org.acme.incidents.api.SuppressionRule;
import org.acme.incidents.model.Incident;

public record NamedSuppressionRule(
        String name,
        SuppressionRule rule
) {
    public boolean shouldSuppress(Incident incident) {
        return rule.shouldSuppress(incident);
    }
}