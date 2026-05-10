package org.acme.incidents.dto;

import java.util.function.BiPredicate;

public record NamedBiPredicate<T, U>(
        String name,
        BiPredicate<T, U> predicate
) {
    public boolean test(T t, U u) {
        return predicate.test(t, u);
    }

}

