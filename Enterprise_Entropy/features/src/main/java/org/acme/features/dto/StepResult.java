package org.acme.features.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.features.domain.Event;
import org.acme.features.domain.MachineContext;
import org.acme.features.domain.State;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StepResult {
    private int step;
    private State fromState;
    private Event event;
    private State toState;
    private MachineContext context;
}
