package org.acme.rules.model.enums;

import lombok.Data;

@Data
public class Status {
    private Stage stage;
    private Risk risk;
}
