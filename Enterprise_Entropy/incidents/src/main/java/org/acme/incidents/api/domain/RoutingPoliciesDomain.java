package org.acme.incidents.api.domain;

import org.acme.incidents.api.domain.interfaces.RoutingPolicy;
import org.acme.incidents.model.Incident;
import org.acme.incidents.model.Team;

public final class RoutingPoliciesDomain {

    public static Team billingTeamRouting(Incident incident) {
        String service = incident.getService().toLowerCase();
        if (service.contains("billing")) {
            return Team.WHERE_IS_MY_MONEY;
        }
        return Team.WHAT_IS_THIS;
    }

    public static Team infraTeamRouting(Incident incident) {
        String service = incident.getService().toLowerCase();
        if (service.contains("gateway")) {
            return Team.MACHINES_AND_STUFF;
        }
        return Team.WHAT_IS_THIS;
    }

    public static Team securityTeamRouting(Incident incident) {
        String service = incident.getService().toLowerCase();
        if (service.contains("auth")) {
            return Team.THIS_GATE_IS_CLOSED;
        }
        return Team.WHAT_IS_THIS;
    }

    public static Team integrationTeamRouting(Incident incident) {
        String service = incident.getService().toLowerCase();
        if (service.contains("sap") || service.contains("kafka")) {
            return Team.TALK_AMONG_YOURSELVES;
        }
        return Team.WHAT_IS_THIS;
    }

    public static RoutingPolicy allRoutings = incident -> {
        Team route = billingTeamRouting(incident);
        if(route != Team.WHAT_IS_THIS){
            return route;
        }
        route = infraTeamRouting(incident);
        if(route != Team.WHAT_IS_THIS){
            return route;
        }
        route = securityTeamRouting(incident);
        if(route != Team.WHAT_IS_THIS){
            return route;
        }
        route = integrationTeamRouting(incident);
        if(route != Team.WHAT_IS_THIS){
            return route;
        }
        return Team.WHAT_IS_THIS;
    };


}
