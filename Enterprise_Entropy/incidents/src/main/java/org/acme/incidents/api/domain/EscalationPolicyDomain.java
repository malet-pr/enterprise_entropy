package org.acme.incidents.api.domain;

import org.acme.incidents.api.domain.interfaces.EscalationPolicy;
import org.acme.incidents.model.Incident;
import org.acme.incidents.model.TriageDecision;

public final class EscalationPolicyDomain {

    public static TriageDecision prodAndCustImpactAndHighSeverity(Incident incident) {
        if ("prod".equalsIgnoreCase(incident.getEnvironment())
                && incident.getSeverityScore() >= 9
                && incident.isCustomerImpact()) {
            return TriageDecision.WAKE_SOMEONE_UP;
        }
        return TriageDecision.NONE;
    }

    public static TriageDecision prodAndHighSeverity(Incident incident) {
        if ("prod".equalsIgnoreCase(incident.getEnvironment())
                && incident.getSeverityScore() >= 8) {
            return TriageDecision.WE_SHOULD_TELL_SOMEONE;
        }
        return TriageDecision.NONE;
    }

    public static TriageDecision mediumSeverity(Incident incident) {
        if (incident.getSeverityScore() >= 5) {
            return TriageDecision.WE_SHOULD_PROBABLY_LOOK_AT_THIS;
        }
        return TriageDecision.NONE;
    }

    public static final EscalationPolicy allPolicies  = incident -> {
        TriageDecision decision = prodAndCustImpactAndHighSeverity(incident);
        if (decision != TriageDecision.NONE) {
            return decision;
        }
        decision = prodAndHighSeverity(incident);
        if (decision != TriageDecision.NONE) {
            return decision;
        }
        decision = mediumSeverity(incident);
        if (decision != TriageDecision.NONE) {
            return decision;
        }
        return TriageDecision.FORGET_IT;
    };

    public static final EscalationPolicy prodPolicies  = incident -> {
        TriageDecision decision = prodAndCustImpactAndHighSeverity(incident);
        if (decision != TriageDecision.NONE) {
            return decision;
        }
        decision = prodAndHighSeverity(incident);
        if (decision != TriageDecision.NONE) {
            return decision;
        }
        return TriageDecision.FORGET_IT;
    };

}
