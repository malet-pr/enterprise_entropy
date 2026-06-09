package org.acme.incidents.api.built;

import org.acme.incidents.dto.ProcessedIncidentFull;
import org.acme.incidents.model.NextStep;
import org.acme.incidents.model.Team;

import java.util.function.UnaryOperator;

public final class PostProcessIncidentProvided {

    public static UnaryOperator<ProcessedIncidentFull> moveToWarRoom = processed -> {
        if (Team.WHAT_IS_THIS == processed.team()
                && processed.incident().isCustomerImpact()) {
            return new ProcessedIncidentFull(
                    processed.incident(),
                    processed.decision(),
                    Team.WAR_ROOM,
                    NextStep.ALL_HANDS_ON_DECK,
                    processed.suppressed(),
                    processed.blockedExecution()
            );
        }
        return processed;
    };

}

