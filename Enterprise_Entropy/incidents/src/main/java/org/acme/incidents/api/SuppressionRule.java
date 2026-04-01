package org.acme.incidents.api;

import org.acme.incidents.model.Incident;

@FunctionalInterface
public interface SuppressionRule {
    boolean shouldSuppress(Incident incident);

    default SuppressionRule or(SuppressionRule other) {
        return incident ->
                this.shouldSuppress(incident) || other.shouldSuppress(incident);
    }

    default SuppressionRule and(SuppressionRule other) {
        return incident ->
                this.shouldSuppress(incident) && other.shouldSuppress(incident);
    }

    default SuppressionRule negate() {
        return incident -> !this.shouldSuppress(incident);
    }

}