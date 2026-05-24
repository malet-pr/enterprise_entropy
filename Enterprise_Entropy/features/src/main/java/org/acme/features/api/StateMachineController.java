package org.acme.features.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acme.features.dto.RunResult;
import org.acme.features.dto.ScenarioInput;
import org.acme.features.service.RunnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StateMachineController {

    private final RunnerService runner;

    @PostMapping("/run-scenario")
    public ResponseEntity<RunResult> runScenario(@RequestBody ScenarioInput input){
        log.info("Received scenario for processing {}", input.getScenarioId());
        RunResult result = runner.runEvents(input.getScenarioId(),input.getInitialState()
                ,input.getInitialContext(),input.getEvents());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
