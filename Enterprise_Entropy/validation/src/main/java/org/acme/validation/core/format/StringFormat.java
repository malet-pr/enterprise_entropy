package org.acme.validation.core.format;

import org.acme.validation.core.model.ValidationError;
import org.acme.validation.core.model.ValidationSummary;
import java.util.List;

public final class StringFormat {

    private StringFormat() {}

    public static String stringOfMessageListColumn(List<ValidationError> es) {
        StringBuilder messages = new StringBuilder();
        for (ValidationError e : es) {
            messages.append("    - ").append(e.message()).append("\n");
        }
        String lines = messages.toString();
        return lines.isEmpty() ? "    (none)" : lines;
    }

    public static String stringOfSummaryMessages(ValidationSummary s) {
        return """
        Validation Summary:
            Valid: %s
            Errors:
            %s
            Warnings:
            %s
        """.formatted(s.isValid(), stringOfMessageListColumn(s.errors()), stringOfMessageListColumn(s.warnings()));
    }


}

