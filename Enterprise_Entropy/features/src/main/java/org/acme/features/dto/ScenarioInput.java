package org.acme.features.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.features.domain.Event;
import org.acme.features.domain.MachineContext;
import org.acme.features.domain.State;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScenarioInput {
    private String scenarioId;
    private State initialState;
    private MachineContext initialContext;
    private List<Event> events;
}
