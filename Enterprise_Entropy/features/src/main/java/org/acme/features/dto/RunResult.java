package org.acme.features.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.features.domain.Event;
import org.acme.features.domain.MachineContext;
import org.acme.features.domain.State;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RunResult {
    private String scenarioId;
    private RunStatus status;
    private State finalState;
    private MachineContext finalContext;
    private int failedStep;
    private Event failedEvent;
    private String errorMessage;
    private State lastState;
    private MachineContext lastContext;
    private List<StepResult> steps;
}
