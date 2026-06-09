package org.acme.incidents.api.built;

import org.acme.incidents.model.Incident;
import org.acme.incidents.model.NextStep;
import org.acme.incidents.model.TriageDecision;

import java.util.function.BiFunction;

public final class FollowUpProvided {

    public static NextStep wakeUpWithCustImpact(Incident incident, TriageDecision decision) {
        if (decision == TriageDecision.WAKE_SOMEONE_UP
                && incident.isCustomerImpact()) {
            return NextStep.ALL_HANDS_ON_DECK;
        }
        return NextStep.ALL_DONE;
    }

    public static NextStep shouldTellWithCustImpact(Incident incident, TriageDecision decision) {
        if (decision == TriageDecision.WE_SHOULD_TELL_SOMEONE
                && incident.isCustomerImpact()) {
            return NextStep.INVESTIGATE_AND_FIX;
        }
        return NextStep.ALL_DONE;
    }

    public static NextStep lookAndMediumOcurrencies(Incident incident, TriageDecision decision) {
        if (decision == TriageDecision.WE_SHOULD_PROBABLY_LOOK_AT_THIS
                && incident.getOccurrences() >= 5) {
            return NextStep.WRITE_A_TICKET;
        }
        return NextStep.ALL_DONE;
    }

    public static final BiFunction<Incident,TriageDecision,NextStep> allFollowUps = (incident, decision) -> {
        NextStep next = wakeUpWithCustImpact(incident,decision);
        if (next != NextStep.ALL_DONE) {
            return next;
        }
        next = shouldTellWithCustImpact(incident,decision);
        if (next != NextStep.ALL_DONE) {
            return next;
        }
        next = lookAndMediumOcurrencies(incident,decision);
        if (next != NextStep.ALL_DONE) {
            return next;
        }
        return NextStep.ALL_DONE;
    };

}

