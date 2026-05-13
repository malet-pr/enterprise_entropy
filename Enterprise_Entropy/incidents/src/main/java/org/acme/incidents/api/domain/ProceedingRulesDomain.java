package org.acme.incidents.api.domain;

import org.acme.incidents.api.domain.interfaces.ProceedingRule;
import org.acme.incidents.dto.domain.NamedProceedingRules;
import org.acme.incidents.model.Team;
import org.acme.incidents.model.TriageDecision;
import java.util.List;
import java.util.Optional;

public class ProceedingRulesDomain {

    public static final ProceedingRule wakeWhenTeamUnknown = (decision, team) -> {
        if(decision == TriageDecision.WAKE_SOMEONE_UP && team == Team.WHAT_IS_THIS) {
            return true;
        }
        return false;
    };

    public static final List<NamedProceedingRules> proceedingRules = List.of(
            new NamedProceedingRules("wake when team unknown", wakeWhenTeamUnknown)
    );

    public static final Optional<NamedProceedingRules> findFirstProceedingRule(TriageDecision decision, Team team) {
        return ProceedingRulesDomain.proceedingRules.stream()
                .filter(rule -> rule.shouldBlock(decision,team))
                .findFirst();
    }


}
