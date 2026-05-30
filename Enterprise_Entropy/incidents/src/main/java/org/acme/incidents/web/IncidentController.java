package org.acme.incidents.web;

import org.acme.incidents.dto.ProcessedIncident;
import org.acme.incidents.dto.ProcessedIncidentFull;
import org.acme.incidents.engine.domain.Process;
import org.acme.incidents.engine.built.ProcessProvided;
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
    private final Process process;
    private final ProcessProvided processProvided;

    public IncidentController(Process process, ProcessProvided processProvided) {
        this.process = process;
        this.processProvided = processProvided;
    }

    @PostMapping("/report1")
    public List<ProcessedIncident> report1(@RequestBody List<Incident> incidents) {
        log.trace("IncidentController::report1a");
        return process.processOne(incidents);
    }

    @PostMapping("/report1b")
    public List<ProcessedIncidentFull> report1b(@RequestBody List<Incident> incidents) {
        log.trace("IncidentController::report1b");
        return process.processTwo(incidents);
    }

    @PostMapping("/report2")
    public List<ProcessedIncident> report2(@RequestBody List<Incident> incidents) {
        log.trace("IncidentController::report2a");
        return processProvided.processOne(incidents);
    }

    @PostMapping("/report2b")
    public List<ProcessedIncidentFull> report2b(@RequestBody List<Incident> incidents) {
        log.trace("IncidentController::report2b");
        return processProvided.processTwo(incidents);
    }

}
