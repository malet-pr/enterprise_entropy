package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rules349656ae6d9f46e8ba238a033dc18ce9.*;
import static org.acme.Rules349656ae6d9f46e8ba238a033dc18ce9.*;

public class Rules349656ae6d9f46e8ba238a033dc18ce9_rule_LargeDepositApprove {

    /**
     * Rule name: LargeDepositApprove
     */
    public static org.drools.model.Rule rule_LargeDepositApprove() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadata349656ae6d9f46e8ba238a033dc18ce9.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        final org.drools.model.BitMask mask_$l = org.drools.model.BitMask.getPatternMask(DomainClassesMetadata349656ae6d9f46e8ba238a033dc18ce9.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                         "approved");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "LargeDepositApprove")
                                      .build(D.pattern(var_$l).expr("GENERATED_44AADF07954135603887DB373DC83E53",
                                                                    org.acme.P41.LambdaPredicate41A016DAE4094B7DCC9F728589145E4B.INSTANCE,
                                                                    D.alphaIndexedBy(int.class,
                                                                                     org.drools.model.Index.ConstraintType.GREATER_OR_EQUAL,
                                                                                     -1,
                                                                                     org.acme.PD5.LambdaExtractorD55537B8D6BF836A04C4A932151DE278.INSTANCE,
                                                                                     20),
                                                                    D.reactOn("applicant")).expr("GENERATED_3DA7652F4D6E94EB06E6F8BE180C1227",
                                                                                                 org.acme.PCB.LambdaPredicateCB82B8B0191CFA338F568D798E66CED2.INSTANCE,
                                                                                                 D.alphaIndexedBy(int.class,
                                                                                                                  org.drools.model.Index.ConstraintType.GREATER_OR_EQUAL,
                                                                                                                  DomainClassesMetadata349656ae6d9f46e8ba238a033dc18ce9.org_acme_LoanApplication_Metadata_INSTANCE.getPropertyIndex("deposit"),
                                                                                                                  org.acme.P80.LambdaExtractor807DA493AA1AAB8965276D4589454F1B.INSTANCE,
                                                                                                                  1000),
                                                                                                 D.reactOn("deposit")).expr("GENERATED_D13B6DD435AF4394AE3CC69DB0976CD8",
                                                                                                                            var_maxAmount,
                                                                                                                            org.acme.P63.LambdaPredicate63C625919E087A9C4EED068D6A824714.INSTANCE,
                                                                                                                            D.reactOn("amount")),
                                             D.on(var_$l).execute(org.acme.P46.LambdaConsequence4625A8E3CB06C1ACD0952912D31EB2B3.INSTANCE));
        return rule;
    }
}
