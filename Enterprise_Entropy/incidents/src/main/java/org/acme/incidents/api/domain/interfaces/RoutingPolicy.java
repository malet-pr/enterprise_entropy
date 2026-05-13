package org.acme.incidents.api.domain.interfaces;

import org.acme.incidents.model.Incident;
import org.acme.incidents.model.Team;

@FunctionalInterface
public interface RoutingPolicy {
    Team route(Incident incident);
}