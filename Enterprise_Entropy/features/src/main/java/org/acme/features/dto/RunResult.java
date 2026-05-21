package org.acme.features.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.features.domain.MachineContext;
import org.acme.features.domain.State;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RunResult {
    private String scenarioId;
    private RunStatus status;
    private State finalState;
    private MachineContext finalContext;
    private List<StepResult> steps;
    private ErrorResponse error;
    private State lastState;
    private MachineContext lastContext;
}
