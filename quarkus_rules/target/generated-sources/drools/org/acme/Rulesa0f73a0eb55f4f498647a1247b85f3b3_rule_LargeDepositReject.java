package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rulesa0f73a0eb55f4f498647a1247b85f3b3.*;
import static org.acme.Rulesa0f73a0eb55f4f498647a1247b85f3b3.*;

public class Rulesa0f73a0eb55f4f498647a1247b85f3b3_rule_LargeDepositReject {

    /**
     * Rule name: LargeDepositReject
     */
    public static org.drools.model.Rule rule_LargeDepositReject() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadataa0f73a0eb55f4f498647a1247b85f3b3.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        final org.drools.model.BitMask mask_$l = org.drools.model.BitMask.getPatternMask(DomainClassesMetadataa0f73a0eb55f4f498647a1247b85f3b3.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                         "approved");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "LargeDepositReject")
                                      .build(D.pattern(var_$l).expr("GENERATED_44AADF07954135603887DB373DC83E53",
                                                                    org.acme.PE7.LambdaPredicateE7CE74DB6FEB1E88120650D2F7AF35D1.INSTANCE,
                                                                    D.alphaIndexedBy(int.class,
                                                                                     org.drools.model.Index.ConstraintType.GREATER_OR_EQUAL,
                                                                                     -1,
                                                                                     org.acme.P6E.LambdaExtractor6ED589EF9B4DCB7C768830A718B6B751.INSTANCE,
                                                                                     20),
                                                                    D.reactOn("applicant")).expr("GENERATED_3DA7652F4D6E94EB06E6F8BE180C1227",
                                                                                                 org.acme.PE1.LambdaPredicateE1C20662DB8E8216D2912270C612D81E.INSTANCE,
                                                                                                 D.alphaIndexedBy(int.class,
                                                                                                                  org.drools.model.Index.ConstraintType.GREATER_OR_EQUAL,
                                                                                                                  DomainClassesMetadataa0f73a0eb55f4f498647a1247b85f3b3.org_acme_LoanApplication_Metadata_INSTANCE.getPropertyIndex("deposit"),
                                                                                                                  org.acme.PAD.LambdaExtractorAD8C3EF776BA056064850969B451D14C.INSTANCE,
                                                                                                                  1000),
                                                                                                 D.reactOn("deposit")).expr("GENERATED_2AC576A76D5F96F1792E6E781829E57D",
                                                                                                                            var_maxAmount,
                                                                                                                            org.acme.PC2.LambdaPredicateC2132801EA9B957DFF53F977D603694E.INSTANCE,
                                                                                                                            D.reactOn("amount")),
                                             D.on(var_$l).execute(org.acme.PBB.LambdaConsequenceBBF4A8D366612D7ACB93FE3D4AE7D573.INSTANCE));
        return rule;
    }
}
