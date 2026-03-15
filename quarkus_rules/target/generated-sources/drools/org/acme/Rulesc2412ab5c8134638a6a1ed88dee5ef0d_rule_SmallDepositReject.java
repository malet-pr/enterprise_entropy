package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rulesc2412ab5c8134638a6a1ed88dee5ef0d.*;
import static org.acme.Rulesc2412ab5c8134638a6a1ed88dee5ef0d.*;

public class Rulesc2412ab5c8134638a6a1ed88dee5ef0d_rule_SmallDepositReject {

    /**
     * Rule name: SmallDepositReject
     */
    public static org.drools.model.Rule rule_SmallDepositReject() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadatac2412ab5c8134638a6a1ed88dee5ef0d.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        final org.drools.model.BitMask mask_$l = org.drools.model.BitMask.getPatternMask(DomainClassesMetadatac2412ab5c8134638a6a1ed88dee5ef0d.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                         "approved");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "SmallDepositReject")
                                      .build(D.pattern(var_$l).expr("GENERATED_44AADF07954135603887DB373DC83E53",
                                                                    org.acme.P1E.LambdaPredicate1E64B9B0D98B1EC0AFE1F2F0283E8E59.INSTANCE,
                                                                    D.alphaIndexedBy(int.class,
                                                                                     org.drools.model.Index.ConstraintType.GREATER_OR_EQUAL,
                                                                                     -1,
                                                                                     org.acme.P78.LambdaExtractor7847E7846072EAB4C9C61ADC44B900D7.INSTANCE,
                                                                                     20),
                                                                    D.reactOn("applicant")).expr("GENERATED_D1E5FA012BE6678FEBDAD042B1DA2907",
                                                                                                 org.acme.PB2.LambdaPredicateB22DCD029B8C316DC778EB05977875C7.INSTANCE,
                                                                                                 D.alphaIndexedBy(int.class,
                                                                                                                  org.drools.model.Index.ConstraintType.LESS_THAN,
                                                                                                                  DomainClassesMetadatac2412ab5c8134638a6a1ed88dee5ef0d.org_acme_LoanApplication_Metadata_INSTANCE.getPropertyIndex("deposit"),
                                                                                                                  org.acme.P5E.LambdaExtractor5E02FFEC863A149EFD3FC230E71427B6.INSTANCE,
                                                                                                                  1000),
                                                                                                 D.reactOn("deposit")).expr("GENERATED_B563256BD82E5753E92868E2AEAC0F74",
                                                                                                                            org.acme.P2F.LambdaPredicate2F7357A811304E814BABD14A6BB86A50.INSTANCE,
                                                                                                                            D.alphaIndexedBy(int.class,
                                                                                                                                             org.drools.model.Index.ConstraintType.GREATER_THAN,
                                                                                                                                             DomainClassesMetadatac2412ab5c8134638a6a1ed88dee5ef0d.org_acme_LoanApplication_Metadata_INSTANCE.getPropertyIndex("amount"),
                                                                                                                                             org.acme.P7F.LambdaExtractor7F6500C2B0E9B2647CB43B846BFE7F95.INSTANCE,
                                                                                                                                             2000),
                                                                                                                            D.reactOn("amount")),
                                             D.on(var_$l).execute(org.acme.PB2.LambdaConsequenceB20E75F9AC04007E5ECFA80A5D49A71E.INSTANCE));
        return rule;
    }
}
