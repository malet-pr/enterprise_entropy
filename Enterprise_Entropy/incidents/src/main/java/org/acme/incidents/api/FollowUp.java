package org.acme.incidents.api;

import org.acme.incidents.model.Incident;
import org.acme.incidents.model.NextStep;


@FunctionalInterface
public interface FollowUp {
    NextStep decide(Incident incident);
}
