package org.acme.incidents.api;

import org.acme.incidents.model.Incident;
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
import static org.acme.incidents.api.domain.EscalationPolicyDomain.*;

@ExtendWith(MockitoExtension.class)
class EscalationPoliciesTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideIncidents1")
    @DisplayName("Should return a triage decision if a allPolicies triggered or FORGET_IT if it didn't")
    void escalationResultTest_allPolicies(String description, Incident incident,TriageDecision decisionExpected){
        TriageDecision decision = allPolicies.decide(incident);
        Assertions.assertEquals(decisionExpected,decision, "Failing case: " + description);
    }

    private static Stream<Arguments> provideIncidents1() {
        return Stream.of(
                // Description, Incident, Expected Output
                Arguments.of("shouldWakeSomeone", TestingData.incident1, TriageDecision.WAKE_SOMEONE_UP),
                Arguments.of("shouldNotEscalate", TestingData.incident2, TriageDecision.FORGET_IT),
                Arguments.of("shouldTellSomeone", TestingData.incident4, TriageDecision.WE_SHOULD_TELL_SOMEONE),
                Arguments.of("shouldNotEscalate", TestingData.incident5, TriageDecision.WE_SHOULD_PROBABLY_LOOK_AT_THIS)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideIncidents2")
    @DisplayName("Should return a triage decision if a prodPolicies triggered or FORGET_IT if it didn't")
    void escalationResultTest_prodPolicies(String description, Incident incident,TriageDecision decisionExpected){
        TriageDecision decision = prodPolicies.decide(incident);
        Assertions.assertEquals(decisionExpected,decision, "Failing case: " + description);
    }

    private static Stream<Arguments> provideIncidents2() {
        return Stream.of(
                // Description, Incident, Expected Output
                Arguments.of("shouldWakeSomeone", TestingData.incident1, TriageDecision.WAKE_SOMEONE_UP),
                Arguments.of("shouldNotEscalate", TestingData.incident2,TriageDecision.FORGET_IT),
                Arguments.of("shouldTellSomeone", TestingData.incident4, TriageDecision.WE_SHOULD_TELL_SOMEONE),
                Arguments.of("shouldLookAtThis", TestingData.incident5, TriageDecision.FORGET_IT)
        );
    }

}




