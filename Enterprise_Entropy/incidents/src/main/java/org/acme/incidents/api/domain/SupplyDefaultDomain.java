package org.acme.incidents.api.domain;
import java.time.LocalTime;

public final class SupplyDefaultDomain {

    public static int supplyDefaultSeverityScore() {
        return 1;
    }

    public static int supplyDefaultOccurrences() {
        return 1;
    }

     public static int supplyDefaultHourOfDay() {
        return LocalTime.now().getHour();
    }

}
