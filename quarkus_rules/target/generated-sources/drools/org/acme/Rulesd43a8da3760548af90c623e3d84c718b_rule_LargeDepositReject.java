package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rulesd43a8da3760548af90c623e3d84c718b.*;
import static org.acme.Rulesd43a8da3760548af90c623e3d84c718b.*;

public class Rulesd43a8da3760548af90c623e3d84c718b_rule_LargeDepositReject {

    /**
     * Rule name: LargeDepositReject
     */
    public static org.drools.model.Rule rule_LargeDepositReject() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadatad43a8da3760548af90c623e3d84c718b.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        final org.drools.model.BitMask mask_$l = org.drools.model.BitMask.getPatternMask(DomainClassesMetadatad43a8da3760548af90c623e3d84c718b.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                         "approved");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "LargeDepositReject")
                                      .build(D.pattern(var_$l).expr("GENERATED_44AADF07954135603887DB373DC83E53",
                                                                    org.acme.PFB.LambdaPredicateFB78ECFAC0A7FF828575B167F810006E.INSTANCE,
                                                                    D.alphaIndexedBy(int.class,
                                                                                     org.drools.model.Index.ConstraintType.GREATER_OR_EQUAL,
                                                                                     -1,
                                                                                     org.acme.P5D.LambdaExtractor5DD2496B6B8AF31374ABE7EE6D028E21.INSTANCE,
                                                                                     20),
                                                                    D.reactOn("applicant")).expr("GENERATED_3DA7652F4D6E94EB06E6F8BE180C1227",
                                                                                                 org.acme.PFB.LambdaPredicateFBAE68D6ABE5CC597E668B355F423683.INSTANCE,
                                                                                                 D.alphaIndexedBy(int.class,
                                                                                                                  org.drools.model.Index.ConstraintType.GREATER_OR_EQUAL,
                                                                                                                  DomainClassesMetadatad43a8da3760548af90c623e3d84c718b.org_acme_LoanApplication_Metadata_INSTANCE.getPropertyIndex("deposit"),
                                                                                                                  org.acme.P5A.LambdaExtractor5A0542087888C5A6D08DE1CF2E68BDB2.INSTANCE,
                                                                                                                  1000),
                                                                                                 D.reactOn("deposit")).expr("GENERATED_2AC576A76D5F96F1792E6E781829E57D",
                                                                                                                            var_maxAmount,
                                                                                                                            org.acme.PC7.LambdaPredicateC740D8C7DF3A7E0A9BE0FD147A226330.INSTANCE,
                                                                                                                            D.reactOn("amount")),
                                             D.on(var_$l).execute(org.acme.P06.LambdaConsequence06A8BB041F6C657E079A84A9A4C5C42A.INSTANCE));
        return rule;
    }
}
