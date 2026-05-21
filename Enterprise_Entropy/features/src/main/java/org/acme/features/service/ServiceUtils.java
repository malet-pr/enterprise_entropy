package org.acme.features.service;

import org.acme.features.domain.Event;
import org.acme.features.domain.Machine;
import org.acme.features.domain.MachineContext;
import org.acme.features.domain.State;
import org.springframework.stereotype.Service;

@Service
public class ServiceUtils {

    protected MachineContext resetSprintsIgnored(MachineContext ctx) {
        return MachineContext.builder()
                .qaRejections(ctx.getQaRejections())
                .revivalSignal(ctx.getRevivalSignal())
                .springsIgnored(0)
                .build();
    }

    protected String exMsg(State state, Event event){
        return "Event " + event + " is not valid for " + state + ".";
    }

    protected Machine notImplemented(State state) {
        throw new UnsupportedOperationException("State not implemented yet: " + state);
    }

}
