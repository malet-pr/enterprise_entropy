package org.acme.incidents.web;

import org.acme.incidents.dto.ProcessedIncident;
import org.acme.incidents.engine.ProcessOne;
import org.acme.incidents.engine.ProcessOneProvided;
import org.acme.incidents.model.Incident;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/incidents")
public class IncidentController {

    Logger log = LoggerFactory.getLogger(IncidentController.class);
    private final ProcessOne processOne;
    private final ProcessOneProvided processOneProvided;

    public IncidentController(ProcessOne processOne, ProcessOneProvided processOneProvided) {
        this.processOne = processOne;
        this.processOneProvided = processOneProvided;
    }

    @PostMapping("/report1")
    public List<ProcessedIncident> report1(@RequestBody List<Incident> incidents) {
        log.trace("IncidentController::report1");
        return processOne.process(incidents);
    }

    @PostMapping("/report2")
    public List<ProcessedIncident> report2(@RequestBody List<Incident> incidents) {
        log.trace("IncidentController::report2");
        return processOneProvided.process(incidents);
    }

}
