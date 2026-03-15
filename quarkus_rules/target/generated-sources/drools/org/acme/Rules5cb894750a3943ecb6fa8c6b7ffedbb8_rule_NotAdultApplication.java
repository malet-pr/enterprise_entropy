package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rules5cb894750a3943ecb6fa8c6b7ffedbb8.*;
import static org.acme.Rules5cb894750a3943ecb6fa8c6b7ffedbb8.*;

public class Rules5cb894750a3943ecb6fa8c6b7ffedbb8_rule_NotAdultApplication {

    /**
     * Rule name: NotAdultApplication
     */
    public static org.drools.model.Rule rule_NotAdultApplication() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadata5cb894750a3943ecb6fa8c6b7ffedbb8.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        final org.drools.model.BitMask mask_$l = org.drools.model.BitMask.getPatternMask(DomainClassesMetadata5cb894750a3943ecb6fa8c6b7ffedbb8.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                         "approved");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "NotAdultApplication")
                                      .build(D.pattern(var_$l).expr("GENERATED_6CFE70A51ED7693D87115863353D65B9",
                                                                    org.acme.PC3.LambdaPredicateC35A99C652D69B341AF4C130E02BA3BA.INSTANCE,
                                                                    D.alphaIndexedBy(int.class,
                                                                                     org.drools.model.Index.ConstraintType.LESS_THAN,
                                                                                     -1,
                                                                                     org.acme.PEE.LambdaExtractorEE3D31B9FC248BFC81BB0D2073017142.INSTANCE,
                                                                                     20),
                                                                    D.reactOn("applicant")),
                                             D.on(var_$l).execute(org.acme.PC5.LambdaConsequenceC58457A9AB9C7AA0B338F029DB09D351.INSTANCE));
        return rule;
    }
}
