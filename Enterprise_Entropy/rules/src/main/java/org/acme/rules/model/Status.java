package org.acme.rules.model;

import lombok.*;
import org.acme.rules.model.enums.Risk;
import org.acme.rules.model.enums.Stage;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Status {
    private Stage stage;
    private Risk risk;
}