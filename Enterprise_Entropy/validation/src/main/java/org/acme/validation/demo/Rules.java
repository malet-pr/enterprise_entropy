package org.acme.validation.demo;

import org.acme.validation.core.model.Severity;
import org.acme.validation.core.model.ValidationError;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public final class Rules {

    private Rules() {}

    public static final Function<Applicant, Optional<ValidationError>> nameIsRequired =
            applicant -> applicant.name() == null || applicant.name().isBlank()
                    ? Optional.of(new ValidationError("NAME", "name is required", Severity.WARNING))
                    : Optional.empty();

    public static final Function<Applicant, Optional<ValidationError>> mustBeAdult =
applicant -> applicant.age() < 18
            ? Optional.of(new ValidationError("AGE", "must be adult", Severity.ERROR))
            : Optional.empty();

    public static final Function<Applicant, Optional<ValidationError>> depositPositive =
    applicant -> applicant.deposit() <= 0
                ? Optional.of(new ValidationError("AMOUNT", "deposit must be positive", Severity.ERROR))
                : Optional.empty();

    public static final Function<Applicant, Optional<ValidationError>> youngUnemployed =
        applicant -> applicant.age() >= 18 && applicant.age() < 25 && !applicant.hasIncome()
                ? Optional.of(new ValidationError("INCOME", "young applicants must have an income", Severity.ERROR))
                : Optional.empty();

    public static final UnaryOperator<Applicant> normalizeName =
        applicant -> {
            if (applicant.name() == null || applicant.name().isBlank()) {
                return new Applicant("UNKNOWN", applicant.age(), applicant.deposit(), applicant.hasIncome());
            } else {
                String normalized = applicant.name().trim().toUpperCase();
                return new Applicant(normalized, applicant.age(), applicant.deposit(), applicant.hasIncome());
            }
        };

    public static Applicant withValidated(Applicant applicant) {
        return new Applicant(applicant.name(), applicant.age(), applicant.deposit(), applicant.hasIncome(), true);
    }

}

