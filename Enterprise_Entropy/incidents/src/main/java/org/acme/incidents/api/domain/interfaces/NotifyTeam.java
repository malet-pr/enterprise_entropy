package org.acme.incidents.api.domain.interfaces;

import org.acme.incidents.model.Incident;
import org.acme.incidents.model.Team;

//BiConsumer
@FunctionalInterface
public interface NotifyTeam {
    void accept(Incident incident, Team team);
}


