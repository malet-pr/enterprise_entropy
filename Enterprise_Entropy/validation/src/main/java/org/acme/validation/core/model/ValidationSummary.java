package org.acme.validation.core.model;

import java.util.List;

public record ValidationSummary(
       List<ValidationError> warnings,
       List<ValidationError> errors,
       Boolean isValid
) {
}
