package org.acme.validation.core.model;

public record ValidationError(
        String code,
        String message,
        Severity severity
) {
}
