package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rulesd43a8da3760548af90c623e3d84c718b.*;
import static org.acme.Rulesd43a8da3760548af90c623e3d84c718b.*;

public class Rulesd43a8da3760548af90c623e3d84c718b_rule_NotAdultApplication {

    /**
     * Rule name: NotAdultApplication
     */
    public static org.drools.model.Rule rule_NotAdultApplication() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadatad43a8da3760548af90c623e3d84c718b.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        final org.drools.model.BitMask mask_$l = org.drools.model.BitMask.getPatternMask(DomainClassesMetadatad43a8da3760548af90c623e3d84c718b.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                         "approved");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "NotAdultApplication")
                                      .build(D.pattern(var_$l).expr("GENERATED_6CFE70A51ED7693D87115863353D65B9",
                                                                    org.acme.P7F.LambdaPredicate7F99A586BAD90A7F4955A48B2C2351CB.INSTANCE,
                                                                    D.alphaIndexedBy(int.class,
                                                                                     org.drools.model.Index.ConstraintType.LESS_THAN,
                                                                                     -1,
                                                                                     org.acme.P5D.LambdaExtractor5DD2496B6B8AF31374ABE7EE6D028E21.INSTANCE,
                                                                                     20),
                                                                    D.reactOn("applicant")),
                                             D.on(var_$l).execute(org.acme.P06.LambdaConsequence06A8BB041F6C657E079A84A9A4C5C42A.INSTANCE));
        return rule;
    }
}
