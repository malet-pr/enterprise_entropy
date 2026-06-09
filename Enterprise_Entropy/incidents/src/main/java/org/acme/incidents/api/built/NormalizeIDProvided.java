package org.acme.incidents.api.built;

import org.acme.incidents.model.Incident;
import java.util.function.Consumer;

public final class NormalizeIDProvided {

    Consumer<Incident> nomralizeID = incident ->
            incident.setId(incident.getId().toUpperCase());


}
