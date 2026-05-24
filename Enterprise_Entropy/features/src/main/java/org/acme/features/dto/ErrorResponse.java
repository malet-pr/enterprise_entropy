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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private String scenarioId;
    private int failedStep;
    private Event failedEvent;
    private String message;
    private State lastState;
    private MachineContext lastContext;
    private List<StepResult> steps;
}
