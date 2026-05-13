package org.acme.incidents.api;

import org.acme.incidents.dto.domain.NamedSuppressionRule;
import org.acme.incidents.utils.TestingData;
import org.acme.incidents.utils.TestingHelpers;
import org.acme.incidents.model.Incident;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.stream.Stream;
import static org.acme.incidents.api.domain.SupressionRulesDomain.findFirstSuppressionRule;

@ExtendWith(MockitoExtension.class)
class SuppressionRulesTests {

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideIncidents")
    @DisplayName("Should return the first matching suppression rule for given incidents")
    void shouldReturnTheFirstMatchingRule(String description, Incident incident, boolean matchExpected, String expectedRuleName) {
        Optional<NamedSuppressionRule> matched = findFirstSuppressionRule(incident);
        Assertions.assertEquals(matchExpected, matched.isPresent(), "Failing case: " + description);
        Assertions.assertEquals(expectedRuleName, TestingHelpers.getSupressionRuleName(matched) , "Failing case: " + description);
    }

    private static Stream<Arguments> provideIncidents() {
        return Stream.of(
                // Description, Incident, Expected Output (boolean), Expected Rule Name
                Arguments.of("shouldNotSuppressCriticalBillingFailure", TestingData.incident1, false,"none"),
                Arguments.of("shouldSuppressDevHealthcheckNoise", TestingData.incident2,true, "dev healthcheck noise"),
                Arguments.of("shouldSuppressLegacyTimeoutNoise", TestingData.incident3, true, "legacy ghost call noise")
        );
    }


}