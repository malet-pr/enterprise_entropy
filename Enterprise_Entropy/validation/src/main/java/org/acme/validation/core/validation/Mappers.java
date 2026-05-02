package org.acme.validation.core.validation;

import org.acme.validation.core.model.*;
import java.util.List;
import java.util.function.Function;

public final class Mappers {

    private Mappers() {}

    public static <T> ValidationOutcome<T> mapValidOutcome(ValidationOutcome<T> outcome, Function<T, T> f) {
        if (outcome instanceof OK<T>(T value, List<ValidationError> warnings)) {
            return new OK<>((f.apply(value)), warnings);
        } else {
            return outcome;
        }
    }

    public static <T> ValidationOutcome<T> mapWarningsOutcome(ValidationOutcome<T> outcome, Function<ValidationError, ValidationError> f) {
        if (outcome instanceof OK<T>(T value, List<ValidationError> warnings)) {
            return new OK<>(value, warnings.stream().map(f).toList());
        } else {
            return outcome;
        }
    }

    public static <T> ValidationOutcome<T> mapErrorOutcome(ValidationOutcome<T> outcome, Function<ValidationError, ValidationError> f) {
        if (outcome instanceof NOK<T> (List<ValidationError> errors)) {
            return new NOK<>(errors.stream().map(f).toList()); // map(f) equals map(e -> f.apply(e)) || map(f::apply)
        } else {
            return outcome;
        }
    }

    public static <T> ValidationResult<T> mapValid(ValidationResult<T> result, Function<T, T> f) {
        if (result instanceof Valid<T>(T value)) {
            return new Valid<>(f.apply(value));
        } else {
            return result;
        }
    }

    public static <T> ValidationResult<T> mapErrors(ValidationResult<T> result, Function<ValidationError, ValidationError> f) {
        if (result instanceof Invalid<T>(List<ValidationError> errors)) {
            return new Invalid<>(errors.stream().map(f).toList()); // map(f) equals map(e -> f.apply(e)) || map(f::apply)
        } else {
            return result;
        }
    }

}
