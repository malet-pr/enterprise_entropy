package org.acme.features.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acme.features.domain.Event;
import org.acme.features.domain.Machine;
import org.acme.features.domain.MachineContext;
import org.acme.features.domain.State;
import org.acme.features.exception.InvalidTransitionException;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class TransitionsService {

    private final ServiceUtils utils;

    public Machine step(State state, MachineContext context, Event event) {
        return switch (state) {
            case IDEA_FOG -> handleIdeaFog(context, event);
            case PRETEND_PLANNING -> handlePretendPlanning(context,event);
            case HEROIC_IMPLEMENTATION -> handleHeroicImplementation(context,event);
            case PHILOSOPHICAL_DEBATE -> handlePhilosophicalDebate(context,event);
            case STRESS_THE_THING -> handleStressTheThing(context,event);
            case TEMPORARILY_POSTPONED -> handleTemporarilyPostponed(context,event);
            case ZOMBIE_FEATURE -> handleZombieFeature(context,event);
            case ENTROPY_REDUCTION, ENTROPY_COMPLETE, ENTROPY_ABANDONED -> handleFinalState(state);
        };
    }

    private Machine handleFinalState(State state) {
        log.info(state + " is a terminal state.");
        return null;
    }

    private Machine handleIdeaFog(MachineContext ctx, Event event) {
    return switch (event) {
            case CLARIFY_SOMEHOW
                -> Machine.builder().state(State.PRETEND_PLANNING).context(ctx).build();
            case DECLARE_ENTROPY_ABANDONED
                -> Machine.builder().state(State.ENTROPY_ABANDONED).context(ctx).build();
            case POSTPONE
                -> Machine.builder().state(State.TEMPORARILY_POSTPONED)
                    .context(utils.resetSprintsIgnored(ctx)).build();
            case START_ANYWAY
                -> Machine.builder().state(State.HEROIC_IMPLEMENTATION).context(ctx).build();
            default
                -> throw new InvalidTransitionException(utils.exMsg(State.IDEA_FOG,event ));
        };
    }

    private Machine handlePretendPlanning(MachineContext ctx, Event event){
        return switch (event) {
            case START_ANYWAY
                -> Machine.builder().state(State.HEROIC_IMPLEMENTATION).context(ctx).build();
            case DISCOVER_DISAGREEMENT
                -> Machine.builder().state(State.PHILOSOPHICAL_DEBATE).context(ctx).build();
            case POSTPONE
                -> Machine.builder().state(State.TEMPORARILY_POSTPONED)
                    .context(utils.resetSprintsIgnored(ctx)).build();
            case DECLARE_ENTROPY_COMPLETE
                -> Machine.builder().state(State.ENTROPY_COMPLETE).context(ctx).build();
            case DECLARE_ENTROPY_REDUCTION
                -> Machine.builder().state(State.ENTROPY_REDUCTION).context(ctx).build();
            case DECLARE_ENTROPY_ABANDONED
                -> Machine.builder().state(State.ENTROPY_ABANDONED).context(ctx).build();
            default
                -> throw new InvalidTransitionException(utils.exMsg(State.PRETEND_PLANNING,event ));
        };
    }

    private Machine handleHeroicImplementation(MachineContext ctx, Event event) {
        return switch (event) {
            case SEND_TO_QA -> Machine.builder().state(State.STRESS_THE_THING).context(ctx).build();
            case DISCOVER_DISAGREEMENT -> Machine.builder().state(State.PHILOSOPHICAL_DEBATE).context(ctx).build();
            case POSTPONE -> Machine.builder().state(State.TEMPORARILY_POSTPONED)
                    .context(utils.resetSprintsIgnored(ctx)).build();
            case REALIZE_WRONG_DIRECTION -> Machine.builder().state(State.PRETEND_PLANNING)
                    .context(utils.resetQaRejections(ctx)).build();
            default -> throw new InvalidTransitionException(utils.exMsg(State.HEROIC_IMPLEMENTATION, event));
        };
    }

    private Machine handlePhilosophicalDebate(MachineContext ctx, Event event) {
        return switch (event) {
            case START_ANYWAY -> Machine.builder().state(State.HEROIC_IMPLEMENTATION).context(ctx).build();
            case POSTPONE -> Machine.builder().state(State.TEMPORARILY_POSTPONED)
                    .context(utils.resetSprintsIgnored(ctx)).build();
            case DECLARE_ENTROPY_REDUCTION -> Machine.builder().state(State.ENTROPY_REDUCTION).context(ctx).build();
            case DECLARE_ENTROPY_ABANDONED -> Machine.builder().state(State.ENTROPY_ABANDONED).context(ctx).build();
            default -> throw new InvalidTransitionException(utils.exMsg(State.PHILOSOPHICAL_DEBATE, event));
        };
    }

    private Machine handleStressTheThing(MachineContext ctx, Event event) {
        return switch (event) {
            case REWORK -> {
                if (ctx.getQaRejections() < 3) {
                    yield Machine.builder().state(State.HEROIC_IMPLEMENTATION)
                            .context(utils.incrementQaRejections(ctx)).build();
                } else {
                    throw new InvalidTransitionException(utils.exMsgCtx(State.STRESS_THE_THING, event));
                }
            }
            case THIS_IS_ALL_WRONG -> {
                if (ctx.getQaRejections() >= 3) {
                    yield Machine.builder().state(State.PRETEND_PLANNING)
                            .context(utils.resetQaRejections(ctx)).build();
                } else {
                    throw new InvalidTransitionException(utils.exMsgCtx(State.STRESS_THE_THING, event));
                }
            }
            case POSTPONE
                -> Machine.builder().state(State.TEMPORARILY_POSTPONED)
                    .context(utils.resetSprintsIgnored(ctx)).build();
            case REALIZE_WRONG_DIRECTION
                -> Machine.builder().state(State.PRETEND_PLANNING)
                    .context(utils.resetQaRejections(ctx)).build();
            case REJECT_FUNDAMENTALLY
                -> Machine.builder().state(State.PHILOSOPHICAL_DEBATE).context(utils.incrementQaRejections(ctx)).build();
            case DECLARE_ENTROPY_COMPLETE
                -> Machine.builder().state(State.ENTROPY_COMPLETE).context(ctx).build();
            default
                -> throw new InvalidTransitionException(utils.exMsg(State.STRESS_THE_THING, event));
        };
    }

    private Machine handleTemporarilyPostponed(MachineContext ctx, Event event) {
        return switch (event) {
            case FORGET_FOR_LONG_TIME ->
            {
                MachineContext newCtx = utils.incrementSprintsIgnored(ctx);
                State nextState = newCtx.getSprintsIgnored() >= 2
                        ? State.ZOMBIE_FEATURE
                        : State.TEMPORARILY_POSTPONED;
                yield Machine.builder()
                        .state(nextState)
                        .context(newCtx)
                        .build();
            }
            case START_ANYWAY -> Machine.builder().state(State.HEROIC_IMPLEMENTATION)
                    .context(utils.resetSprintsIgnored(ctx)).build();
            case CLARIFY_SOMEHOW -> Machine.builder().state(State.PRETEND_PLANNING)
                    .context(utils.resetSprintsIgnored(ctx)).build();
            default -> throw new InvalidTransitionException(utils.exMsg(State.TEMPORARILY_POSTPONED, event));
        };
    }

    private Machine handleZombieFeature(MachineContext ctx, Event event) {
        return switch (event) {
            case EXECUTIVE_REMEMBERS, CUSTOMER_COMPLAINS, AUDIT_DISCOVERS ->
            {
                MachineContext newCtx = utils.incrementRevivalSignals(ctx);
                if(newCtx.getRevivalSignal() >= 2){
                    yield Machine.builder().state(State.HEROIC_IMPLEMENTATION)
                            .context(utils.resetRevivalSignals(newCtx))
                            .build();
                } else {
                    yield Machine.builder().state(State.ZOMBIE_FEATURE)
                            .context(newCtx)
                            .build();
                }
            }
            case DECLARE_ENTROPY_ABANDONED -> Machine.builder().state(State.ENTROPY_ABANDONED)
                    .context(ctx).build();
            default -> throw new InvalidTransitionException(utils.exMsg(State.ZOMBIE_FEATURE, event));
        };
    }

}
