package org.acme.validation.core.model;

public sealed interface ValidationOutcome<T>
        permits OK, NOK {
}
