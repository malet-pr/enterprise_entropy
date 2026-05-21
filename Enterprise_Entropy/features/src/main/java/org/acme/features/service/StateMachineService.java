package org.acme.features.service;

import lombok.RequiredArgsConstructor;
import org.acme.features.domain.Event;
import org.acme.features.domain.Machine;
import org.acme.features.domain.MachineContext;
import org.acme.features.domain.State;
import org.acme.features.exception.InvalidTransitionException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StateMachineService {

    private final ServiceUtils utils;

    public Machine step(State state, MachineContext context, Event event) {
        return switch (state) {
            case IDEA_FOG -> handleIdeaFog(context, event);
            case PRETEND_PLANNING -> utils.notImplemented(state);
            case HEROIC_IMPLEMENTATION -> utils.notImplemented(state);
            case PHILOSOPHICAL_DEBATE -> utils.notImplemented(state);
            case STRESS_THE_THING -> utils.notImplemented(state);
            case TEMPORARILY_POSTPONED -> utils.notImplemented(state);
            case ZOMBIE_FEATURE -> utils.notImplemented(state);
            case ENTROPY_REDUCTION -> utils.notImplemented(state);
            case ENTROPY_COMPLETE -> utils.notImplemented(state);
            case ENTROPY_ABANDONED -> utils.notImplemented(state);
        };
    }

    private Machine handleIdeaFog(MachineContext context, Event event) {
        return switch (event) {
            case CLARIFY_SOMEHOW
                -> Machine.builder().state(State.PRETEND_PLANNING).context(context).build();
            case DECLARE_ENTROPY_ABANDONED
                -> Machine.builder().state(State.ENTROPY_ABANDONED).context(context).build();
            case POSTPONE
                -> Machine.builder().state(State.TEMPORARILY_POSTPONED)
                    .context(utils.resetSprintsIgnored(context)).build();
            case START_ANYWAY
                -> Machine.builder().state(State.HEROIC_IMPLEMENTATION).context(context).build();
            default
                -> throw new InvalidTransitionException(utils.exMsg(State.IDEA_FOG,event ));
        };
    }
    
    

}
