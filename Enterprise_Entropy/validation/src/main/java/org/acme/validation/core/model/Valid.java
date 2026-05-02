package org.acme.validation.core.model;

public record Valid<T>(
        T value
) implements ValidationResult<T> {
}
