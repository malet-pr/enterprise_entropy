package org.acme.incidents.api.built;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

public final class SupplyDefaultProvided {

    public static IntSupplier supplyDefaultSeverityScore = () -> 1;
    public static IntSupplier supplyDefaultOccurrences = () -> 1;
    public static Supplier<Integer> supplyDefaultHourOfDay = () -> java.time.LocalTime.now().getHour();

}

