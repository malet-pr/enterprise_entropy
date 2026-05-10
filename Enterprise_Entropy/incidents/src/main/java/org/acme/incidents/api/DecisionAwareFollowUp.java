package org.acme.incidents.api;

import org.acme.incidents.model.Incident;
import org.acme.incidents.model.NextStep;
import org.acme.incidents.model.TriageDecision;

@FunctionalInterface
public interface DecisionAwareFollowUp {
    NextStep decide(Incident incident, TriageDecision decision);
}
