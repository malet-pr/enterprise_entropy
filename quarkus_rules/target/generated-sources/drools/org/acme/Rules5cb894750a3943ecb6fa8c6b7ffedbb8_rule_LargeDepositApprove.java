package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rules5cb894750a3943ecb6fa8c6b7ffedbb8.*;
import static org.acme.Rules5cb894750a3943ecb6fa8c6b7ffedbb8.*;

public class Rules5cb894750a3943ecb6fa8c6b7ffedbb8_rule_LargeDepositApprove {

    /**
     * Rule name: LargeDepositApprove
     */
    public static org.drools.model.Rule rule_LargeDepositApprove() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadata5cb894750a3943ecb6fa8c6b7ffedbb8.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        final org.drools.model.BitMask mask_$l = org.drools.model.BitMask.getPatternMask(DomainClassesMetadata5cb894750a3943ecb6fa8c6b7ffedbb8.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                         "approved");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "LargeDepositApprove")
                                      .build(D.pattern(var_$l).expr("GENERATED_44AADF07954135603887DB373DC83E53",
                                                                    org.acme.P6D.LambdaPredicate6DCF8A43289BE9B779CDF6EB882F7275.INSTANCE,
                                                                    D.alphaIndexedBy(int.class,
                                                                                     org.drools.model.Index.ConstraintType.GREATER_OR_EQUAL,
                                                                                     -1,
                                                                                     org.acme.PEE.LambdaExtractorEE3D31B9FC248BFC81BB0D2073017142.INSTANCE,
                                                                                     20),
                                                                    D.reactOn("applicant")).expr("GENERATED_3DA7652F4D6E94EB06E6F8BE180C1227",
                                                                                                 org.acme.P8A.LambdaPredicate8A06C247C8A0C03BFE5B996429FEDE86.INSTANCE,
                                                                                                 D.alphaIndexedBy(int.class,
                                                                                                                  org.drools.model.Index.ConstraintType.GREATER_OR_EQUAL,
                                                                                                                  DomainClassesMetadata5cb894750a3943ecb6fa8c6b7ffedbb8.org_acme_LoanApplication_Metadata_INSTANCE.getPropertyIndex("deposit"),
                                                                                                                  org.acme.PA5.LambdaExtractorA55550801FD7BE96F87B6CBE614D2897.INSTANCE,
                                                                                                                  1000),
                                                                                                 D.reactOn("deposit")).expr("GENERATED_D13B6DD435AF4394AE3CC69DB0976CD8",
                                                                                                                            var_maxAmount,
                                                                                                                            org.acme.P09.LambdaPredicate099486A7F69A1B4ECE697BD9A52DD2CA.INSTANCE,
                                                                                                                            D.reactOn("amount")),
                                             D.on(var_$l).execute(org.acme.P14.LambdaConsequence1429C0C19B7D8B5B0C828E2977F5607E.INSTANCE));
        return rule;
    }
}
