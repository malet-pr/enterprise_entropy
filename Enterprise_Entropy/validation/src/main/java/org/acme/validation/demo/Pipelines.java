package org.acme.validation.demo;

import org.acme.validation.core.model.ValidationError;
import org.acme.validation.core.model.ValidationResult;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import static org.acme.validation.core.validation.Mappers.mapValid;
import static org.acme.validation.core.validation.Runners.validateFirstError;
import static org.acme.validation.core.validation.Runners.validateWithSummary;

public final class Pipelines {

    private Pipelines() {}

    public static ValidationResult<Applicant> validatePipeline1(Applicant applicant, List<Function<Applicant, Optional<ValidationError>>> validators) {
        ValidationResult<Applicant> vr = validateWithSummary(validators, applicant);
        return mapValid(vr, Rules.normalizeName.andThen(Rules::withValidated));
    }

    public static ValidationResult<Applicant> validatePipeline2(Applicant applicant, List<Function<Applicant, Optional<ValidationError>>> validators) {
        ValidationResult<Applicant> vr = validateFirstError(applicant,validators);
        return mapValid(vr,Rules.normalizeName.andThen(Rules::withValidated));
    }

}


