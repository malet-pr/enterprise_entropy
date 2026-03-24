package org.acme.incidents.api;

import org.acme.incidents.model.Incident;

@FunctionalInterface
public interface SuppressionRule {
    boolean shouldSuppress(Incident incident);
}