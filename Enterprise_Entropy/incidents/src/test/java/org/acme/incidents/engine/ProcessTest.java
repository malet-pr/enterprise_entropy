package org.acme.incidents.engine;

import org.acme.incidents.engine.domain.Process;
import org.acme.incidents.model.Incident;
import org.acme.incidents.model.Team;
import org.acme.incidents.model.TriageDecision;
import org.acme.incidents.utils.TestingData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.stream.Stream;


@ExtendWith(MockitoExtension.class)
class ProcessTest {

    @Spy
    Process spyProcess;

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideIncidents")
    @DisplayName("Should return if an incident is suppressed based on the first matching suppression rule")
    void shouldReturnIfAnIncidentIsSuppressed(String description, Incident incident, boolean matchExpected) {
        boolean isSuppresed = spyProcess.firstSuppressionRule.shouldSuppress(incident);
        Assertions.assertEquals(matchExpected, isSuppresed, "Failing case: " + description);
    }

    private static Stream<Arguments> provideIncidents() {
        return Stream.of(
                // Description, Incident, Expected Output (boolean)
                Arguments.of("shouldNotSuppressCriticalBillingFailure", TestingData.incident1, false),
                Arguments.of("shouldSuppressDevHealthcheckNoise", TestingData.incident2,true),
                Arguments.of("shouldSuppressLegacyTimeoutNoise", TestingData.incident3, true)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideTriageDecisionsAndTeams")
    @DisplayName("Should return the first matching blocking rule for given triage decision and team")
    void shouldReturnIfAnActionIsBlocked(String description, TriageDecision decision, Team team, boolean matchExpected) {
        boolean isBlocked = spyProcess.firstProceedingRule.shouldBlock(decision, team);
        Assertions.assertEquals(matchExpected, isBlocked, "Failing case: " + description);
    }

    private static Stream<Arguments> provideTriageDecisionsAndTeams() {
        return Stream.of(
                // Description, TriageDecision, Team, Expected Output (boolean), Expected Rule Name
                Arguments.of("shouldNotBlockJustWriteItDown",TriageDecision.JUST_WRITE_IT_DOWN,Team.WHAT_IS_THIS , false),
                Arguments.of("shouldBlockWakeWhenTeamUnknown",TriageDecision.WAKE_SOMEONE_UP,Team.WHAT_IS_THIS ,true),
                Arguments.of("shouldNotBlockWakeWhenTeamKnown",TriageDecision.WAKE_SOMEONE_UP,Team.WHERE_IS_MY_MONEY , false)
        );
    }

}
