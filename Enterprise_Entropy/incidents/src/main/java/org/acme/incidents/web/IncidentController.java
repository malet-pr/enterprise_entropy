package org.acme.incidents.web;

import org.acme.incidents.dto.ProcessedIncident;
import org.acme.incidents.engine.ProcessOne;
import org.acme.incidents.model.Incident;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/incidents")
public class IncidentController {

    private final ProcessOne processOne;

    public IncidentController(ProcessOne processOne) {
        this.processOne = processOne;
    }

    @PostMapping("/report")
    public List<ProcessedIncident> report(@RequestBody List<Incident> incidents) {
        return processOne.process(incidents);
    }

}
