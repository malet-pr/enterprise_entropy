package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rules5cb894750a3943ecb6fa8c6b7ffedbb8.*;
import static org.acme.Rules5cb894750a3943ecb6fa8c6b7ffedbb8.*;

public class Rules5cb894750a3943ecb6fa8c6b7ffedbb8_rule_SmallDepositApprove {

    /**
     * Rule name: SmallDepositApprove
     */
    public static org.drools.model.Rule rule_SmallDepositApprove() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadata5cb894750a3943ecb6fa8c6b7ffedbb8.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        final org.drools.model.BitMask mask_$l = org.drools.model.BitMask.getPatternMask(DomainClassesMetadata5cb894750a3943ecb6fa8c6b7ffedbb8.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                         "approved");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "SmallDepositApprove")
                                      .build(D.pattern(var_$l).expr("GENERATED_44AADF07954135603887DB373DC83E53",
                                                                    org.acme.P6D.LambdaPredicate6DCF8A43289BE9B779CDF6EB882F7275.INSTANCE,
                                                                    D.alphaIndexedBy(int.class,
                                                                                     org.drools.model.Index.ConstraintType.GREATER_OR_EQUAL,
                                                                                     -1,
                                                                                     org.acme.PEE.LambdaExtractorEE3D31B9FC248BFC81BB0D2073017142.INSTANCE,
                                                                                     20),
                                                                    D.reactOn("applicant")).expr("GENERATED_D1E5FA012BE6678FEBDAD042B1DA2907",
                                                                                                 org.acme.P9E.LambdaPredicate9EBBFEF40449F413D85AD58142B74E25.INSTANCE,
                                                                                                 D.alphaIndexedBy(int.class,
                                                                                                                  org.drools.model.Index.ConstraintType.LESS_THAN,
                                                                                                                  DomainClassesMetadata5cb894750a3943ecb6fa8c6b7ffedbb8.org_acme_LoanApplication_Metadata_INSTANCE.getPropertyIndex("deposit"),
                                                                                                                  org.acme.PA5.LambdaExtractorA55550801FD7BE96F87B6CBE614D2897.INSTANCE,
                                                                                                                  1000),
                                                                                                 D.reactOn("deposit")).expr("GENERATED_E4A247CC3D43FA3D83BCC84BB9FD95A2",
                                                                                                                            org.acme.P1B.LambdaPredicate1B1248E5BBDC76AAADCA320FB7F5A60C.INSTANCE,
                                                                                                                            D.alphaIndexedBy(int.class,
                                                                                                                                             org.drools.model.Index.ConstraintType.LESS_OR_EQUAL,
                                                                                                                                             DomainClassesMetadata5cb894750a3943ecb6fa8c6b7ffedbb8.org_acme_LoanApplication_Metadata_INSTANCE.getPropertyIndex("amount"),
                                                                                                                                             org.acme.PB0.LambdaExtractorB09DEF8AEA905D3453DA216E6DB0E34C.INSTANCE,
                                                                                                                                             2000),
                                                                                                                            D.reactOn("amount")),
                                             D.on(var_$l).execute(org.acme.P14.LambdaConsequence1429C0C19B7D8B5B0C828E2977F5607E.INSTANCE));
        return rule;
    }
}
