package org.acme.incidents.api.built;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

public final class SupplyDefaultProvided {

    IntSupplier supplyDefaultSeverityScore = () -> 1;
    IntSupplier supplyDefaultOccurrences = () -> 1;
    Supplier<Integer> supplyDefaultHourOfDay = () -> java.time.LocalTime.now().getHour();

}

