package org.acme.incidents.api.domain;

import org.acme.incidents.dto.ProcessedIncidentFull;
import org.acme.incidents.model.NextStep;
import org.acme.incidents.model.Team;

public final class PostProcessIncidentDomain {

    public static ProcessedIncidentFull moveToWarRoom(ProcessedIncidentFull processed) {
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
    }

}
