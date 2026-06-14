package org.acme.incidents.engine;

import org.acme.incidents.api.domain.CombineStringsDomain;
import org.acme.incidents.api.domain.interfaces.*;
import org.acme.incidents.dto.ProcessedIncident;
import org.acme.incidents.dto.ProcessedIncidentFull;
import org.acme.incidents.engine.domain.IncidentProcessor;
import org.acme.incidents.model.Incident;
import org.acme.incidents.model.NextStep;
import org.acme.incidents.model.Team;
import org.acme.incidents.model.TriageDecision;
import org.acme.incidents.utils.TestingData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.acme.incidents.api.domain.EscalationPolicyDomain.allPolicies;
import static org.acme.incidents.api.domain.FollowUpDomain.allFollowUps;
import static org.acme.incidents.api.domain.RoutingPoliciesDomain.allRoutings;
import static org.acme.incidents.api.domain.SupressionRulesDomain.devHealthcheckNoise;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncidentProcessorTest {

    @Mock
    IncidentAction actionMock;

    @Mock
    NotifyTeam notifyTeamMock;

    @InjectMocks
    IncidentProcessor incidentProcessor = new IncidentProcessor();

    @Test
    @DisplayName("Test incident process One")
    void processOneTest(){
        // Arrange
        List<Incident> incList = TestingData.incidents;
        // Act
        List<ProcessedIncident> result = incidentProcessor.processOne(
                incList,
                devHealthcheckNoise,
                allPolicies,
                allRoutings,
                actionMock
        );
        // Assert
        assertEquals(incList.size(),result.size(),"There should be a Processed Incident for each Incident");
        Mockito.verify(actionMock,times(6)).execute(any(Incident.class));
        Assertions.assertFalse(result.get(0).isSuppressed(),"This incident should not be suppressed");
        Assertions.assertTrue(result.get(1).isSuppressed(),"This incident should be suppressed by the rule tested");
        assertEquals(TriageDecision.WE_SHOULD_TELL_SOMEONE,result.get(3).decision(),"Should match this escalation policy.");
        Assertions.assertNotEquals(TriageDecision.WE_SHOULD_PROBABLY_LOOK_AT_THIS,result.get(3).decision(),"Should not have reached this escalation policy.");
        assertEquals(Team.TALK_AMONG_YOURSELVES,result.get(4).team(),"Should match this team assignment.");
        assertEquals(Team.WHAT_IS_THIS,result.get(6).team(),"Should not have found any team to assign.");
    }

    @Test
    @DisplayName("Test incident process Two")
    void processTwoTest(){
        // Arrange
        List<Incident> incList = TestingData.incidents;
        SuppressionRule suppressionRule = incident -> switch (incident.getId()) {
            case "INC-002", "INC-004" -> true;
            default -> false;
        };
        ProceedingRule proceedingRule = (decision, team) -> switch (decision) {
            case WAKE_SOMEONE_UP -> {
                yield team == Team.WHAT_IS_THIS;
            }
            default -> false;
        };
        SupplyDefault hourOfDay = () -> 14;
        // Act
        List<ProcessedIncidentFull> result = incidentProcessor.processTwo(
                incList,
                suppressionRule,
                proceedingRule,
                allPolicies,
                allRoutings,
                allFollowUps,
                actionMock,
                notifyTeamMock
        );
        // Assert
        assertEquals(incList.size(),result.size(),"There should be a Processed Incident for each Incident");
        Assertions.assertFalse(result.get(0).isSuppressed(),"This incident should not be suppressed");
        assertEquals(NextStep.FIND_SOMEONE_TO_HELP, result.get(0).nextStep());
        Assertions.assertTrue(result.get(1).isSuppressed(),"This incident should be suppressed by the rule tested");
        assertEquals(TriageDecision.FORGET_IT,result.get(3).decision(),"Should match this escalation policy.");
        Assertions.assertNotEquals(TriageDecision.WE_SHOULD_PROBABLY_LOOK_AT_THIS,result.get(3).decision(),"Should not have reached this escalation policy.");
        assertEquals(Team.TALK_AMONG_YOURSELVES,result.get(4).team(),"Should match this team assignment.");
        assertEquals(Team.WHAT_IS_THIS,result.get(6).team(),"Should not have found any team to assign.");
        assertEquals(9,result.get(7).incident().getSeverityScore(),"Default severity score should not have been applied");
        assertEquals(1, result.get(7).incident().getOccurrences(),"Default occurrences should have been applied");
        assertEquals(14, result.get(7).incident().getHourOfDay(),"Default hour of day should have been applied");
        assertEquals("INC-008", result.get(7).incident().getId(),"ID should have been normalized");
        Mockito.verify(actionMock,times(5)).execute(any(Incident.class));
        Mockito.verify(notifyTeamMock,times(5)).accept(any(Incident.class), any(Team.class));
    }

    @Test
    void shouldCombineTwoStringsIntoUnifiedLog() {
        // Arrange
        String incidentId = "INC-007";
        String ruleName = "wake when team unknown";
        // Act
        String result = CombineStringsDomain.combineBlockedLogs(incidentId, ruleName);
        // Assert
        assertEquals("Blocked incident INC-007 by rule wake when team unknown", result);
    }

}
