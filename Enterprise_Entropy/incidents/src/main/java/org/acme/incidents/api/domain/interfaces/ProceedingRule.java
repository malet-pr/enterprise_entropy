package org.acme.incidents.api.domain.interfaces;

import org.acme.incidents.model.Team;
import org.acme.incidents.model.TriageDecision;

//BiPredicate
@FunctionalInterface
public interface ProceedingRule {
    boolean shouldBlock(TriageDecision decision, Team team);

    default ProceedingRule or(ProceedingRule other) {
        return (decision, team) ->
                this.shouldBlock(decision, team) || other.shouldBlock(decision, team);
    }

    default ProceedingRule and(ProceedingRule other) {
        return (decision, team) ->
                this.shouldBlock(decision, team) && other.shouldBlock(decision, team);
    }

    default ProceedingRule negate() {
        return (decision,team) ->
                !this.shouldBlock(decision, team);
    }
}
