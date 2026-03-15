package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rulesd43a8da3760548af90c623e3d84c718b.*;
import static org.acme.Rulesd43a8da3760548af90c623e3d84c718b.*;

public class Rulesd43a8da3760548af90c623e3d84c718b_rule_SmallDepositReject {

    /**
     * Rule name: SmallDepositReject
     */
    public static org.drools.model.Rule rule_SmallDepositReject() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadatad43a8da3760548af90c623e3d84c718b.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        final org.drools.model.BitMask mask_$l = org.drools.model.BitMask.getPatternMask(DomainClassesMetadatad43a8da3760548af90c623e3d84c718b.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                         "approved");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "SmallDepositReject")
                                      .build(D.pattern(var_$l).expr("GENERATED_44AADF07954135603887DB373DC83E53",
                                                                    org.acme.PFB.LambdaPredicateFB78ECFAC0A7FF828575B167F810006E.INSTANCE,
                                                                    D.alphaIndexedBy(int.class,
                                                                                     org.drools.model.Index.ConstraintType.GREATER_OR_EQUAL,
                                                                                     -1,
                                                                                     org.acme.P5D.LambdaExtractor5DD2496B6B8AF31374ABE7EE6D028E21.INSTANCE,
                                                                                     20),
                                                                    D.reactOn("applicant")).expr("GENERATED_D1E5FA012BE6678FEBDAD042B1DA2907",
                                                                                                 org.acme.P81.LambdaPredicate81D254A93BB134D177DE056D88650FB9.INSTANCE,
                                                                                                 D.alphaIndexedBy(int.class,
                                                                                                                  org.drools.model.Index.ConstraintType.LESS_THAN,
                                                                                                                  DomainClassesMetadatad43a8da3760548af90c623e3d84c718b.org_acme_LoanApplication_Metadata_INSTANCE.getPropertyIndex("deposit"),
                                                                                                                  org.acme.P5A.LambdaExtractor5A0542087888C5A6D08DE1CF2E68BDB2.INSTANCE,
                                                                                                                  1000),
                                                                                                 D.reactOn("deposit")).expr("GENERATED_B563256BD82E5753E92868E2AEAC0F74",
                                                                                                                            org.acme.P13.LambdaPredicate13767C1239B25D5EC681B8AC3A8384C9.INSTANCE,
                                                                                                                            D.alphaIndexedBy(int.class,
                                                                                                                                             org.drools.model.Index.ConstraintType.GREATER_THAN,
                                                                                                                                             DomainClassesMetadatad43a8da3760548af90c623e3d84c718b.org_acme_LoanApplication_Metadata_INSTANCE.getPropertyIndex("amount"),
                                                                                                                                             org.acme.PD0.LambdaExtractorD02E8502D90B427DF81DAE1C8BDC554B.INSTANCE,
                                                                                                                                             2000),
                                                                                                                            D.reactOn("amount")),
                                             D.on(var_$l).execute(org.acme.P06.LambdaConsequence06A8BB041F6C657E079A84A9A4C5C42A.INSTANCE));
        return rule;
    }
}
