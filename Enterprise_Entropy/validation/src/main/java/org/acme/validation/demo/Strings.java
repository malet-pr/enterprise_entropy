package org.acme.validation.demo;

import org.acme.validation.core.model.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import static org.acme.validation.core.format.StringFormat.stringOfSummaryMessages;
import static org.acme.validation.core.validation.Runners.runValidators;
import static org.acme.validation.core.validation.ValidationHelpers.summarize;

public class Strings {

    private Strings() {}

    public static String StringProcessApplicant(Applicant applicant, Iterable<Function<Applicant, Optional<ValidationError>>> validators) {
        List<ValidationError> errors = runValidators(validators, applicant);
        ValidationSummary summary = summarize(errors);
        return stringOfSummaryMessages(summary);
    }
}
