package org.acme.incidents.engine;

import org.acme.incidents.api.domain.*;
import org.acme.incidents.api.domain.interfaces.IncidentAction;
import org.acme.incidents.dto.ProcessedIncident;
import org.acme.incidents.engine.domain.IncidentProcessor;
import org.acme.incidents.model.Incident;
import org.acme.incidents.utils.TestingData;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class incidentProcessorTest {

    @Mock
    IncidentAction actionMock;

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
                SupressionRulesDomain.devHealthcheckNoise,
                ProceedingRulesDomain.wakeWhenTeamUnknown,
                EscalationPolicyDomain.allPolicies,
                RoutingPoliciesDomain.allRoutings,
                actionMock
        );
        // Assert
        Assertions.assertEquals(incList.size(),result.size(),"There should be a Processed Incident for each Incident");
        Mockito.verify(actionMock,times(4)).execute(any(Incident.class));
        Assertions.assertFalse(result.get(0).isSuppressed(),"This incident should not be suppressed");
        Assertions.assertTrue(result.get(1).isSuppressed(),"This incident should be suppressed by the rule tested");
        Assertions.assertTrue(result.get(0).isBlockedExecution(),"This incident should be blocked from continue with an action");
        Assertions.assertFalse(result.get(1).isBlockedExecution(),"This incident should continue with an action");
    }



}
