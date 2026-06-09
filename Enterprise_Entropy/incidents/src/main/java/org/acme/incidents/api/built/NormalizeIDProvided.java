package org.acme.incidents.api.built;

import org.acme.incidents.model.Incident;
import java.util.function.Consumer;

public final class NormalizeIDProvided {

    public static Consumer<Incident> normalizeID = incident ->
            incident.setId(incident.getId().toUpperCase());


}
