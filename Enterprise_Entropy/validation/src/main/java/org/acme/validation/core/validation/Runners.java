package org.acme.validation.core.validation;

import org.acme.validation.core.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class Runners {

    private Runners() {}

    public static <T> List<ValidationError> runValidators(Iterable<Function<T, Optional<ValidationError>>> validators, T value) {
      List<ValidationError> errors = new ArrayList<>();
      validators.forEach(v -> v.apply(value).ifPresent(errors::add));
      return errors;
    }
    
    public static <T> ValidationResult<T> validateWithSummary(Iterable<Function<T, Optional<ValidationError>>> validators, T value) {
      List<ValidationError> errors = runValidators(validators, value);
      return errors.stream().filter(e-> e.severity() == Severity.ERROR).toList().isEmpty()
              ? new Valid<>(value)
              : new Invalid<>(List.copyOf(errors));
    }

    public static <T> Optional<ValidationError> firstErrorValidator(Iterable<Function<T, Optional<ValidationError>>> validators, T value) {
      for (Function<T, Optional<ValidationError>> v : validators) {
          Optional<ValidationError> error = v.apply(value);
          if (error.isPresent() && error.get().severity() == Severity.ERROR) {
              return error;
          }
      }
      return Optional.empty();
    }

    public static <T> ValidationResult<T> validateFirstError(T value, List<Function<T, Optional<ValidationError>>> validators) {
        ValidationResult<T> result = new Valid<>(value);
        for (Function<T, Optional<ValidationError>> validator : validators) {
            Optional<ValidationError> error = validator.apply(value);
            if (error.isPresent()) {
                result = new Invalid<>(List.of(error.get()));
                break;
            }
        }
        return result;
    }

    public static <T> ValidationOutcome<T> validateOutcomeSummary(Iterable<Function<T, Optional<ValidationError>>> validators, T value) { 
      List<ValidationError> errors = runValidators(validators, value);
      List<List<ValidationError>> separated = errors.stream().collect(Collectors.teeing(
                        Collectors.filtering(e -> e.severity() == Severity.ERROR, Collectors.toList()),
                        Collectors.filtering(e -> e.severity() == Severity.WARNING, Collectors.toList()),
                        (error, warning) -> List.of(error, warning)
                    ));
      return separated.get(0).isEmpty()
            ? new OK<>(value,separated.get(1))
            : new NOK<>(separated.get(0));
    }

    public static <T> Optional<ValidationError> firstErrorOutcome(Iterable<Function<T, Optional<ValidationError>>> validators, T value) {
      for (Function<T, Optional<ValidationError>> v : validators) {
          Optional<ValidationError> error = v.apply(value);
          if (error.isPresent() && error.get().severity() == Severity.ERROR) {
              return error;
          }
      }
      return Optional.empty();
    }

    public static  <T> ValidationSummary process(List<T> values, Iterable<Function<T, Optional<ValidationError>>> validators) {
        return null;
    }

  public static <T> ValidationSummary processWithSummary(List<T> values, Iterable<Function<T, Optional<ValidationError>>> validators) {
      List<ValidationError> allErrors = new ArrayList<>();
      for (T value : values) {
          allErrors.addAll(runValidators(validators, value));
      }
      return ValidationHelpers.summarize(allErrors);
  }

  public static <T> List<ValidationError> processWithFirstError(List<T> values, Iterable<Function<T, Optional<ValidationError>>> validators) {
      List<ValidationError> firstErrors = new ArrayList<>();
      for (T value : values) {
          firstErrorValidator(validators, value).ifPresent(firstErrors::add);
      }
      return firstErrors;
  }

}

