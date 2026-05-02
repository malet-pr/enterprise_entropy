package org.acme.validation.core.model;

public sealed interface ValidationResult<T> permits Valid, Invalid {
}

