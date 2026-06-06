package org.acme.incidents.api.domain.interfaces;

import org.acme.incidents.model.Incident;

//Connsumer
@FunctionalInterface
public interface IncidentAction {
    void execute(Incident incident);
}
