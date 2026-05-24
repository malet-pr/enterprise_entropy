package org.acme.features.service;

import org.acme.features.domain.Event;
import org.acme.features.domain.Machine;
import org.acme.features.domain.MachineContext;
import org.acme.features.domain.State;
import org.springframework.stereotype.Service;

@Service
public class ServiceUtils {

    protected Machine notImplemented(State state) {
        throw new UnsupportedOperationException("State not implemented yet: " + state);
    }

    protected String exMsg(State state, Event event){
        return "Event " + event + " is not valid for " + state + ".";
    }

    protected String exMsgCtx(State state, Event event){
        return "Event " + event + " is not valid for " + state + " and this context.";
    }

    public String finalMsg(State state) {
        return "State " + state + " is final, no event can be applied to it.";
    }

    protected MachineContext resetSprintsIgnored(MachineContext ctx) {
        return MachineContext.builder()
                .qaRejections(ctx.getQaRejections())
                .revivalSignal(ctx.getRevivalSignal())
                .sprintsIgnored(0)
                .build();
    }

    protected MachineContext resetQaRejections(MachineContext ctx) {
        return MachineContext.builder()
                .qaRejections(0)
                .revivalSignal(ctx.getRevivalSignal())
                .sprintsIgnored(ctx.getSprintsIgnored())
                .build();
    }

    protected MachineContext resetRevivalSignals(MachineContext ctx) {
        return MachineContext.builder()
                .qaRejections(ctx.getQaRejections())
                .revivalSignal(0)
                .sprintsIgnored(ctx.getSprintsIgnored())
                .build();
    }

    protected MachineContext incrementQaRejections(MachineContext ctx) {
        return MachineContext.builder()
                .qaRejections(ctx.getQaRejections() + 1)
                .revivalSignal(ctx.getRevivalSignal())
                .sprintsIgnored(ctx.getSprintsIgnored())
                .build();
    }

    protected MachineContext incrementSprintsIgnored(MachineContext ctx) {
        return MachineContext.builder()
                .qaRejections(ctx.getQaRejections())
                .revivalSignal(ctx.getRevivalSignal())
                .sprintsIgnored(ctx.getSprintsIgnored() + 1)
                .build();
    }

    protected MachineContext incrementRevivalSignals(MachineContext ctx) {
        return MachineContext.builder()
                .qaRejections(ctx.getQaRejections())
                .revivalSignal(ctx.getRevivalSignal() + 1)
                .sprintsIgnored(ctx.getSprintsIgnored())
                .build();
    }

}
