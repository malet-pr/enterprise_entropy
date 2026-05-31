package org.acme.incidents.api.domain;

public final class CombineStringsDomain {

    public static String combineBlockedLogs (String log1, String log2){
        return "Blocked incident " + log1 + " by rule " + log2;
    }

    public static String joinWithSpace(String first, String second) {
        return first + " " + second;
    }

    public static String joinWithSeparator(String first, String second) {
        return first + " | " + second;
    }

}
