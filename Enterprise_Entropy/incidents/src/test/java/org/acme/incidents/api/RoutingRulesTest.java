package org.acme.incidents.api;

import org.acme.incidents.model.Incident;
import org.acme.incidents.model.Team;
import org.acme.incidents.utils.TestingData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.stream.Stream;
import static org.acme.incidents.api.domain.RoutingPoliciesDomain.*;

@ExtendWith(MockitoExtension.class)
public class RoutingRulesTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideIncidents1")
    @DisplayName("Should return a triage decision if a allPolicies triggered or FORGET_IT if it didn't")
    void routingPolicies_allRoutings (String description, Incident incident, Team expectedTeam){
        Team team = allRoutings.route(incident);
        Assertions.assertEquals(expectedTeam,team, "Failing case: " + description);
    }

    private static Stream<Arguments> provideIncidents1() {
        return Stream.of(
                // Description, Incident, Expected Output
                Arguments.of("shouldRouteToWhereIsMyMoney", TestingData.incident4, Team.WHERE_IS_MY_MONEY),
                Arguments.of("shouldRouteToMachinesAndStuff", TestingData.incident6, Team.MACHINES_AND_STUFF),
                Arguments.of("shouldRouteToThisGateIsClose", TestingData.incident3, Team.THIS_GATE_IS_CLOSED),
                Arguments.of("shouldRouteToTalkAmongYourselves", TestingData.incident5, Team.TALK_AMONG_YOURSELVES),
                Arguments.of("shouldNotApplyRouting", TestingData.incident2, Team.WHAT_IS_THIS)
        );
    }
}
