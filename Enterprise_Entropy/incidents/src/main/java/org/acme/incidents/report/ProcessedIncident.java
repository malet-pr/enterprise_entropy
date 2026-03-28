package org.acme.incidents.report;

import org.acme.incidents.model.Incident;
import org.acme.incidents.model.NextStep;
import org.acme.incidents.model.Team;
import org.acme.incidents.model.TriageDecision;

public record ProcessedIncident(
        Incident incident,
        TriageDecision decision,
        Team team,
        NextStep nextStep,
        boolean suppressed
) {
    public ProcessedIncident {
        if (incident == null) {
            throw new IllegalArgumentException("incident cannot be null");
        }
    }

    public boolean isSuppressed() {
        return suppressed;
    }

    public static ProcessedIncident suppressed(Incident incident) {
        return new ProcessedIncident(
                incident,
                TriageDecision.FORGET_IT,
                Team.WHAT_IS_THIS,
                NextStep.ALL_DONE,
                true
        );
    }

    public String logLine() {
        return String.format(
                "service=%s | decision=%s | team=%s | nextStep=%s | suppressed=%s",
                incident.getService(),
                decision,
                team,
                nextStep,
                suppressed
        );
    }

}