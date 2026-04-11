package org.acme.rules.model;

import lombok.Data;
import org.acme.rules.model.enums.IssuePriority;
import org.acme.rules.model.enums.Understanding;
import java.util.List;

@Data
public class Issue {
    private IssuePriority priority;
    private Status status;
    private List<Understanding> understoodBy;
}
