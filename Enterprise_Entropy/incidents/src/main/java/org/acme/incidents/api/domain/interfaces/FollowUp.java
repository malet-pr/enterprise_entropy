package org.acme.incidents.api.domain.interfaces;

import org.acme.incidents.model.Incident;
import org.acme.incidents.model.NextStep;
import org.acme.incidents.model.TriageDecision;

@FunctionalInterface
public interface FollowUp {
    NextStep decide(Incident incident, TriageDecision decision);
}
