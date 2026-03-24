package org.acme.incidents.api;

import org.acme.incidents.model.Incident;

@FunctionalInterface
public interface IncidentAction {
    void execute(Incident incident);
}
