package org.acme.incidents.api.built;

import org.acme.incidents.dto.built.NamedBiPredicate;
import org.acme.incidents.model.Team;
import org.acme.incidents.model.TriageDecision;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;

public final class ProceedingRulesProvided {

    public static final BiPredicate<TriageDecision, Team> wakeWhenTeamUnknown = (decision, team) -> {
        if(decision == TriageDecision.WAKE_SOMEONE_UP && team == Team.WHAT_IS_THIS) {
            return true;
        }
        return false;
    };


    public static final List<NamedBiPredicate<TriageDecision, Team>> proceedingRules = List.of(
        new NamedBiPredicate<TriageDecision, Team>("wake when team unknown", wakeWhenTeamUnknown)
    );

    public static Optional<NamedBiPredicate<TriageDecision, Team>> findFirstProceedingRule(TriageDecision decision, Team team) {
        return proceedingRules.stream()
                .filter(rule -> rule.test(decision, team))
                .findFirst();
    }
}

