package org.acme.incidents.engine;

import org.acme.incidents.dto.NamedProceedingRules;
import org.acme.incidents.dto.NamedSuppressionRule;
import java.util.Optional;

public class TestingHelpers {

    public static String getSupressionRuleName(Optional<NamedSuppressionRule> matched) {
        return matched.map(NamedSuppressionRule::name).orElse("none");
    }

    public static String getProceedingRuleName(Optional<NamedProceedingRules> matched) {
        return matched.map(NamedProceedingRules::name).orElse("none");
    }



}

