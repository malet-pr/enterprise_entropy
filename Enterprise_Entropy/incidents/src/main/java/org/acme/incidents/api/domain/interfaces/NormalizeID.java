package org.acme.incidents.api.domain.interfaces;

import org.acme.incidents.model.Incident;

// Consumer<Incident>
@FunctionalInterface
public interface NormalizeID {
    Incident normalize(Incident incident);
}
