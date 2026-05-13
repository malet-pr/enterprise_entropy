package org.acme.incidents.dto.domain;

import org.acme.incidents.api.domain.interfaces.ProceedingRule;
import org.acme.incidents.model.Team;
import org.acme.incidents.model.TriageDecision;

public record NamedProceedingRules(
        String name,
        ProceedingRule proceedingRules
) {
    public boolean shouldBlock(TriageDecision decision, Team team) {
        return proceedingRules.shouldBlock(decision, team);
    }
}
