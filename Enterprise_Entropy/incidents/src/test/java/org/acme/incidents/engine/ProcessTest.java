package org.acme.incidents.engine;

import org.acme.incidents.dto.NamedProceedingRules;
import org.acme.incidents.dto.NamedSuppressionRule;
import org.acme.incidents.model.Incident;
import org.acme.incidents.model.Team;
import org.acme.incidents.model.TriageDecision;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.stream.Stream;


@ExtendWith(MockitoExtension.class)
public class ProcessTest {

    @Spy
    Process spyProcess;

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideIncidents")
    @DisplayName("Should return the first matching suppression rule for given incidents")
    void shouldReturnTheFirstMatchingRule(String description, Incident incident, boolean matchExpected, String expectedRuleName) {
        Optional<NamedSuppressionRule> matched = spyProcess.findFirstSuppressionRule(incident);
        Assertions.assertEquals(matchExpected, matched.isPresent(), "Failing case: " + description);
        Assertions.assertEquals(expectedRuleName, TestingHelpers.getSupressionRuleName(matched) , "Failing case: " + description);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideIncidents")
    @DisplayName("Should return if an incident is suppressed based on the first matching suppression rule")
    void shouldReturnIfAnIncidentIsSuppressed(String description, Incident incident, boolean matchExpected, String expectedRuleName) {
        boolean isSuppresed = spyProcess.firstSuppressionRule.shouldSuppress(incident);
        Assertions.assertEquals(matchExpected, isSuppresed, "Failing case: " + description);
    }

    private static Stream<Arguments> provideIncidents() {
        return Stream.of(
                // Description, Incident, Expected Output (boolean), Expected Rule Name
                Arguments.of("shouldNotSuppressCriticalBillingFailure", TestingData.incident1, false,"none"),
                Arguments.of("shouldSuppressDevHealthcheckNoise", TestingData.incident2,true, "dev healthcheck noise"),
                Arguments.of("shouldSuppressLegacyTimeoutNoise", TestingData.incident3, true, "legacy ghost call noise")
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideTriageDecisionsAndTeams")
    @DisplayName("Should return the first matching blocking rule for given triage decision and team")
    void shouldReturnTheFirstBlockingRule(String description, TriageDecision decision, Team team, boolean matchExpected, String expectedRuleName) {
        Optional<NamedProceedingRules> matched = spyProcess.findFirstProceedingRule(decision, team);
        Assertions.assertEquals(matchExpected, matched.isPresent(), "Failing case: " + description);
        Assertions.assertEquals(expectedRuleName, TestingHelpers.getProceedingRuleName(matched) , "Failing case: " + description);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideTriageDecisionsAndTeams")
    @DisplayName("Should return the first matching blocking rule for given triage decision and team")
    void shouldReturnIfAnActionIsBlocked(String description, TriageDecision decision, Team team, boolean matchExpected, String expectedRuleName) {
        boolean isBlocked = spyProcess.firstProceedingRule.shouldBlock(decision, team);
        Assertions.assertEquals(matchExpected, isBlocked, "Failing case: " + description);
    }

    private static Stream<Arguments> provideTriageDecisionsAndTeams() {
        return Stream.of(
                // Description, TriageDecision, Team, Expected Output (boolean), Expected Rule Name
                Arguments.of("shouldNotBlockJustWriteItDown",TriageDecision.JUST_WRITE_IT_DOWN,Team.WHAT_IS_THIS , false,"none"),
                Arguments.of("shouldBlockWakeWhenTeamUnknown",TriageDecision.WAKE_SOMEONE_UP,Team.WHAT_IS_THIS ,true, "wake when team unknown"),
                Arguments.of("shouldNotBlockWakeWhenTeamKnown",TriageDecision.WAKE_SOMEONE_UP,Team.WHERE_IS_MY_MONEY , false,"none")
        );
    }

}
