package org.acme.rules.web;

import lombok.RequiredArgsConstructor;
import org.acme.rules.model.ScenarioOrigin;
import org.acme.rules.model.ScenarioResult;
import org.acme.rules.service.RuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rules")
@RequiredArgsConstructor
public class RulesController {
    private static final Logger log = LoggerFactory.getLogger(RulesController.class);
    private final RuleService ruleService;

    @PostMapping("/process")
    public ResponseEntity<?> processOrder(@RequestBody ScenarioOrigin request) {
        log.info("Sending scenario {} to process", request.getScenarioId());
        ScenarioResult result = ruleService.processScenario(request);
        return ResponseEntity.ok(result);
    }


}
