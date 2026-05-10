package org.acme.incidents.api;

import org.acme.incidents.dto.NamedProceedingRules;
import org.acme.incidents.model.Team;
import org.acme.incidents.model.TriageDecision;
import java.util.List;

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

}
