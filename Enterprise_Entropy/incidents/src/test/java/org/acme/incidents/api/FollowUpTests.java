package org.acme.incidents.api;

import org.acme.incidents.model.Incident;
import org.acme.incidents.model.NextStep;
import org.acme.incidents.model.TriageDecision;
import org.acme.incidents.utils.TestingData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.stream.Stream;
import static org.acme.incidents.api.domain.FollowUpDomain.*;

@ExtendWith(MockitoExtension.class)
public class FollowUpTests {

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideIncidents1")
    @DisplayName("Should return a triage decision if a allPolicies triggered or FORGET_IT if it didn't")
    void followUpResults_allPolicies(String description, Incident incident, TriageDecision decision, NextStep expectedFollowUp){
        NextStep followUp = allFollowUps.decide(incident,decision);
        Assertions.assertEquals(expectedFollowUp,followUp, "Failing case: " + description);
    }

    private static Stream<Arguments> provideIncidents1() {
        return Stream.of(
               // Description, Incident, Decision, Expected Output
                Arguments.of("shouldGoAllHandOnDesk", TestingData.incident1, TriageDecision.WAKE_SOMEONE_UP, NextStep.ALL_HANDS_ON_DECK),
                Arguments.of("shouldGoInvestigateAndFix", TestingData.incident6, TriageDecision.WE_SHOULD_TELL_SOMEONE,NextStep.INVESTIGATE_AND_FIX ),
                Arguments.of("shouldWriteATicket", TestingData.incident3,TriageDecision.WE_SHOULD_PROBABLY_LOOK_AT_THIS, NextStep.WRITE_A_TICKET),
                Arguments.of("shouldFinishHere", TestingData.incident5, TriageDecision.JUST_WRITE_IT_DOWN, NextStep.ALL_DONE)
        );
    }

}
