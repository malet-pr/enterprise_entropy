package org.acme.incidents.api;

import org.acme.incidents.dto.domain.NamedProceedingRules;
import org.acme.incidents.model.Team;
import org.acme.incidents.model.TriageDecision;
import org.acme.incidents.utils.TestingHelpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.stream.Stream;
import static org.acme.incidents.api.domain.ProceedingRulesDomain.findFirstProceedingRule;

@ExtendWith(MockitoExtension.class)
class ProceedingRulesTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideTriageDecisionsAndTeams")
    @DisplayName("Should return the first matching blocking rule for given triage decision and team")
    void shouldReturnTheFirstBlockingRule(String description, TriageDecision decision, Team team, boolean matchExpected, String expectedRuleName) {
        Optional<NamedProceedingRules> matched = findFirstProceedingRule(decision, team);
        Assertions.assertEquals(matchExpected, matched.isPresent(), "Failing case: " + description);
        Assertions.assertEquals(expectedRuleName, TestingHelpers.getProceedingRuleName(matched) , "Failing case: " + description);
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
