package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rulesa2abee888000408aa4cbed4f62a1b1f2.*;
import static org.acme.Rulesa2abee888000408aa4cbed4f62a1b1f2.*;

public class Rulesa2abee888000408aa4cbed4f62a1b1f2_rule_LargeDepositApprove {

    /**
     * Rule name: LargeDepositApprove
     */
    public static org.drools.model.Rule rule_LargeDepositApprove() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadataa2abee888000408aa4cbed4f62a1b1f2.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        final org.drools.model.BitMask mask_$l = org.drools.model.BitMask.getPatternMask(DomainClassesMetadataa2abee888000408aa4cbed4f62a1b1f2.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                         "approved");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "LargeDepositApprove")
                                      .build(D.pattern(var_$l).expr("GENERATED_44AADF07954135603887DB373DC83E53",
                                                                    org.acme.PF3.LambdaPredicateF334076683E9873D37647DCC5712B4BA.INSTANCE,
                                                                    D.alphaIndexedBy(int.class,
                                                                                     org.drools.model.Index.ConstraintType.GREATER_OR_EQUAL,
                                                                                     -1,
                                                                                     org.acme.P50.LambdaExtractor502EE7811229B5737DD34BF88452D4D5.INSTANCE,
                                                                                     20),
                                                                    D.reactOn("applicant")).expr("GENERATED_3DA7652F4D6E94EB06E6F8BE180C1227",
                                                                                                 org.acme.P0A.LambdaPredicate0AC8EB87BEA2939F10BB432CC7B06BCC.INSTANCE,
                                                                                                 D.alphaIndexedBy(int.class,
                                                                                                                  org.drools.model.Index.ConstraintType.GREATER_OR_EQUAL,
                                                                                                                  DomainClassesMetadataa2abee888000408aa4cbed4f62a1b1f2.org_acme_LoanApplication_Metadata_INSTANCE.getPropertyIndex("deposit"),
                                                                                                                  org.acme.P30.LambdaExtractor3056F001F931A97CEA0018FE06006934.INSTANCE,
                                                                                                                  1000),
                                                                                                 D.reactOn("deposit")).expr("GENERATED_D13B6DD435AF4394AE3CC69DB0976CD8",
                                                                                                                            var_maxAmount,
                                                                                                                            org.acme.P7E.LambdaPredicate7ED253C4C6DC028EB4CB3F521312CD18.INSTANCE,
                                                                                                                            D.reactOn("amount")),
                                             D.on(var_$l).execute(org.acme.P11.LambdaConsequence11B6553E9FBE4A355DDAFA6DCC2C03E1.INSTANCE));
        return rule;
    }
}
