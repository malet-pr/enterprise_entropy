package org.acme.incidents.api.built;

import java.util.function.BinaryOperator;

public final class CombinedStringsPovided {

    public static BinaryOperator<String> combineBlockedLogs = (log1, log2)
            -> "Blocked incident " + log1 + " by rule " + log2;

    public static BinaryOperator<String> joinWithSpace = (str1, str2)
            -> str1 + " " + str2;

    public static BinaryOperator<String> joinWithSeparator = (str1, str2)
            -> str1 + " | " + str2;

}
