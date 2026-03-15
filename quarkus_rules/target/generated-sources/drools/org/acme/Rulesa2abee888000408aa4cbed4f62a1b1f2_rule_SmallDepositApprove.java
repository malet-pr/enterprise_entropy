package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rulesa2abee888000408aa4cbed4f62a1b1f2.*;
import static org.acme.Rulesa2abee888000408aa4cbed4f62a1b1f2.*;

public class Rulesa2abee888000408aa4cbed4f62a1b1f2_rule_SmallDepositApprove {

    /**
     * Rule name: SmallDepositApprove
     */
    public static org.drools.model.Rule rule_SmallDepositApprove() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadataa2abee888000408aa4cbed4f62a1b1f2.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        final org.drools.model.BitMask mask_$l = org.drools.model.BitMask.getPatternMask(DomainClassesMetadataa2abee888000408aa4cbed4f62a1b1f2.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                         "approved");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "SmallDepositApprove")
                                      .build(D.pattern(var_$l).expr("GENERATED_44AADF07954135603887DB373DC83E53",
                                                                    org.acme.PF3.LambdaPredicateF334076683E9873D37647DCC5712B4BA.INSTANCE,
                                                                    D.alphaIndexedBy(int.class,
                                                                                     org.drools.model.Index.ConstraintType.GREATER_OR_EQUAL,
                                                                                     -1,
                                                                                     org.acme.P50.LambdaExtractor502EE7811229B5737DD34BF88452D4D5.INSTANCE,
                                                                                     20),
                                                                    D.reactOn("applicant")).expr("GENERATED_D1E5FA012BE6678FEBDAD042B1DA2907",
                                                                                                 org.acme.P19.LambdaPredicate191AF5775357F8E94F6F0BEF4C59B8D6.INSTANCE,
                                                                                                 D.alphaIndexedBy(int.class,
                                                                                                                  org.drools.model.Index.ConstraintType.LESS_THAN,
                                                                                                                  DomainClassesMetadataa2abee888000408aa4cbed4f62a1b1f2.org_acme_LoanApplication_Metadata_INSTANCE.getPropertyIndex("deposit"),
                                                                                                                  org.acme.P30.LambdaExtractor3056F001F931A97CEA0018FE06006934.INSTANCE,
                                                                                                                  1000),
                                                                                                 D.reactOn("deposit")).expr("GENERATED_E4A247CC3D43FA3D83BCC84BB9FD95A2",
                                                                                                                            org.acme.PA1.LambdaPredicateA1202F4C05EA4F2E29923551662A6705.INSTANCE,
                                                                                                                            D.alphaIndexedBy(int.class,
                                                                                                                                             org.drools.model.Index.ConstraintType.LESS_OR_EQUAL,
                                                                                                                                             DomainClassesMetadataa2abee888000408aa4cbed4f62a1b1f2.org_acme_LoanApplication_Metadata_INSTANCE.getPropertyIndex("amount"),
                                                                                                                                             org.acme.P87.LambdaExtractor87722C3C1F9CAC3495E99AC4EA334132.INSTANCE,
                                                                                                                                             2000),
                                                                                                                            D.reactOn("amount")),
                                             D.on(var_$l).execute(org.acme.P11.LambdaConsequence11B6553E9FBE4A355DDAFA6DCC2C03E1.INSTANCE));
        return rule;
    }
}
