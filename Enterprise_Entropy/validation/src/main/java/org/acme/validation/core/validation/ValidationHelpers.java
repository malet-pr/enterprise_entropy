package org.acme.validation.core.validation;

import org.acme.validation.core.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class ValidationHelpers {

    private ValidationHelpers() {}

    public static ValidationSummary summarize(Iterable<ValidationError> errors) {
        List<ValidationError> warnings = new ArrayList<>();
        List<ValidationError> actualErrors = new ArrayList<>();
        for (ValidationError error : errors) {
            if (error.severity() == Severity.WARNING) {
                warnings.add(error);
            } else {
                actualErrors.add(error);
            }
        }
        boolean isValid = actualErrors.isEmpty();
        return new ValidationSummary(warnings, actualErrors, isValid);
    }

    public static <T> Record combineResults(Iterable<ValidationResult<T>> results) {
        List<T> validValues = new ArrayList<>();
        List<ValidationError> allErrors = new ArrayList<>();
        for (ValidationResult<T> result : results) {
            if (result instanceof Valid<T>(T value)) {
                validValues.add(value);
            } else if (result instanceof Invalid<T>(List<ValidationError> errors)) {
                allErrors.addAll(errors);
            }
        }
        return allErrors.isEmpty() ? new Valid<>(validValues) : new Invalid<>(allErrors);
    }

    public static <T> ValidationResult<T> bindResult(Function<T, T> f, ValidationResult<T> result) {
        if (result instanceof Valid<T>(T value)) {
            return new Valid<>(f.apply(value));
        } else if (result instanceof Invalid<T>(List<ValidationError> errors)) {
            return new Invalid<>(errors);
        }
        throw new IllegalStateException("Unexpected ValidationResult type");
    }

    public static <T> ValidationOutcome<T> bindOutcome(Function<T,T> f, ValidationOutcome<T> outcome) {
        if (outcome instanceof OK<T>(T value, List<ValidationError> warnings)) {
            return new OK<>(f.apply(value), warnings);
        } else if (outcome instanceof NOK<T>(List<ValidationError> errors)) {
            return new NOK<>(errors);
        }
        throw new IllegalStateException("Unexpected ValidationOutcome type");
    }

}

