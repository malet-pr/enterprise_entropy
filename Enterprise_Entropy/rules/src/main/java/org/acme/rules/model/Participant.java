package org.acme.rules.model;

import lombok.Data;
import org.acme.rules.model.enums.Role;


@Data
public class Participant {
    private Role role;
    private boolean interested;
    private boolean understands;
}
