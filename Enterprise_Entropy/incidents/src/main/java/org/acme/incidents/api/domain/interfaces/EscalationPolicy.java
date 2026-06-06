package org.acme.incidents.api.domain.interfaces;

import org.acme.incidents.model.Incident;
import org.acme.incidents.model.TriageDecision;

//Function
@FunctionalInterface
public interface EscalationPolicy {
    TriageDecision decide(Incident incident);
}