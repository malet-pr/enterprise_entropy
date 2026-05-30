package org.acme.incidents.api.domain;

import org.acme.incidents.model.Incident;

public final class NormalizeIDDomain {

    public static void normalizeID(Incident incident){
        incident.setId(incident.getId().toUpperCase());
    }

}
