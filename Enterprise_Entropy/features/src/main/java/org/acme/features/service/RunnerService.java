package org.acme.features.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acme.features.domain.Event;
import org.acme.features.domain.Machine;
import org.acme.features.domain.MachineContext;
import org.acme.features.domain.State;
import org.acme.features.dto.ErrorResponse;
import org.acme.features.dto.RunResult;
import org.acme.features.dto.RunStatus;
import org.acme.features.dto.StepResult;
import org.acme.features.exception.InvalidTransitionException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RunnerService {

    private final TransitionsService transitions;

    private ErrorResponse runRecursive(State currentState, MachineContext currentContext,
                                          List<Event> events, int index, List<StepResult> history) {
        List<State> finalState = List.of(State.ENTROPY_ABANDONED, State.ENTROPY_COMPLETE, State.ENTROPY_REDUCTION);
        if (index >= events.size()) {
            return ErrorResponse.builder()
                    .steps(history)
                    .build();
        }
        try{
            if(finalState.contains(currentState) && events.get(index) != null) {
                throw new InvalidTransitionException(currentState + " is terminal, no new events are allowed.");
            }
            Machine newMachine = transitions.step(currentState, currentContext, events.get(index));
            State newState = newMachine.getState();
            MachineContext newContext = newMachine.getContext();
            history.add(
                    StepResult.builder()
                            .step(index + 1)
                            .fromState(currentState)
                            .event(events.get(index))
                            .toState(newState)
                            .context(newContext)
                            .build()
            );
            return runRecursive(newState, newContext, events, index + 1, history);
        } catch (Exception e){
            log.error("Failed run: {}",e.getMessage(), e);
            return ErrorResponse.builder()
                    .steps(history)
                    .failedStep(index + 1)
                    .failedEvent(events.get(index))
                    .message(e.getMessage())
                    .lastState(currentState)
                    .lastContext(currentContext)
                    .build();
        }
    }

    public RunResult runEvents(String scenarioId,State initialState, MachineContext initialContext, List<Event> events){
        List<StepResult> history = new ArrayList<>();
        ErrorResponse response = runRecursive(initialState, initialContext, events, 0, history);
        List<StepResult> steps = response.getSteps();
        if(steps.size() == events.size()){
            State finalState = steps.getLast().getToState();
            MachineContext finalContext = steps.getLast().getContext();
            return RunResult.builder()
                    .scenarioId(scenarioId)
                    .status(RunStatus.OK)
                    .finalState(finalState)
                    .finalContext(finalContext)
                    .steps(steps)
                    .build();
        } else {
            return RunResult.builder()
                    .scenarioId(scenarioId)
                    .failedStep(response.getFailedStep())
                    .failedEvent(response.getFailedEvent())
                    .errorMessage(response.getMessage())
                    .lastState(response.getLastState())
                    .lastContext(response.getLastContext())
                    .steps(response.getSteps())
                    .build();
        }
    }

}

