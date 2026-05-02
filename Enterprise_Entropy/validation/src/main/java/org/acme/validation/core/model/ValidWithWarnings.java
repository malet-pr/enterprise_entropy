package org.acme.validation.core.model;

import org.acme.validation.demo.Applicant;

import java.util.List;

public record ValidWithWarnings(
        Applicant applicant,
        List<ValidationError> warnings
) {
}
