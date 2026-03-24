package org.acme.incidents.api;

import org.acme.incidents.model.Incident;
import org.acme.incidents.model.TriageDecision;

@FunctionalInterface
public interface EscalationPolicy {
    TriageDecision decide(Incident incident);
}