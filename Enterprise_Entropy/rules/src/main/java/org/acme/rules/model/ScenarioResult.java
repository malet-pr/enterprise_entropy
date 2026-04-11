package org.acme.rules.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScenarioResult {
    private String scenarioId;
    private Meeting meeting;
    private Issue issue;
    private List<String> appliedRules;
}
