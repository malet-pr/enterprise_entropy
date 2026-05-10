package org.acme.incidents.dto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import org.acme.incidents.model.Incident;
import org.acme.incidents.model.NextStep;
import org.acme.incidents.model.Team;
import org.acme.incidents.model.TriageDecision;

public record ProcessedIncident(
        @JsonIncludeProperties({"id"})
        Incident incident,
        TriageDecision decision,
        Team team,
        NextStep nextStep,
        boolean suppressed,
        boolean blockedExecution
) {
    public ProcessedIncident {
        if (incident == null) {
            throw new IllegalArgumentException("incident cannot be null");
        }
    }

    public boolean isSuppressed() {
        return suppressed;
    }

    public boolean isBlockedExecution() { return blockedExecution;}

    public static ProcessedIncident suppressed(Incident incident) {
        return new ProcessedIncident(
            incident,
            TriageDecision.FORGET_IT,
            Team.WHAT_IS_THIS,
            NextStep.ALL_DONE,
            true,
            false
        );
    }

    public static ProcessedIncident blockedExecution(Incident incident) {
        return new ProcessedIncident(
            incident,
            null,
            null,
            NextStep.FIND_SOMEONE_TO_HELP,
            false,
            true
        );
    }

    public String logLine() {
        return String.format(
            "service=%s | decision=%s | team=%s | nextStep=%s",
            incident.getService(),
            decision,
            team,
            nextStep
        );
    }

}