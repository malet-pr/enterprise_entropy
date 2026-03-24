package org.acme.incidents.api;

import org.acme.incidents.model.Incident;
import org.acme.incidents.model.Team;

@FunctionalInterface
public interface RoutingPolicy {
    Team route(Incident incident);
}