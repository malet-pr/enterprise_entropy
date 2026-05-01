package org.acme.incidents.dto;

import java.util.function.Predicate;

public record NamedPredicate<T>(
        String name,
        Predicate<T> predicate
) {
    public boolean test(T value) {
        return predicate.test(value);
    }
}
