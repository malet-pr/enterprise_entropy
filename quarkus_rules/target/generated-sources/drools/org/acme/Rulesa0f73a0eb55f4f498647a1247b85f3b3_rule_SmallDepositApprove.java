package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rulesa0f73a0eb55f4f498647a1247b85f3b3.*;
import static org.acme.Rulesa0f73a0eb55f4f498647a1247b85f3b3.*;

public class Rulesa0f73a0eb55f4f498647a1247b85f3b3_rule_SmallDepositApprove {

    /**
     * Rule name: SmallDepositApprove
     */
    public static org.drools.model.Rule rule_SmallDepositApprove() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadataa0f73a0eb55f4f498647a1247b85f3b3.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        final org.drools.model.BitMask mask_$l = org.drools.model.BitMask.getPatternMask(DomainClassesMetadataa0f73a0eb55f4f498647a1247b85f3b3.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                         "approved");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "SmallDepositApprove")
                                      .build(D.pattern(var_$l).expr("GENERATED_44AADF07954135603887DB373DC83E53",
                                                                    org.acme.PE7.LambdaPredicateE7CE74DB6FEB1E88120650D2F7AF35D1.INSTANCE,
                                                                    D.alphaIndexedBy(int.class,
                                                                                     org.drools.model.Index.ConstraintType.GREATER_OR_EQUAL,
                                                                                     -1,
                                                                                     org.acme.P6E.LambdaExtractor6ED589EF9B4DCB7C768830A718B6B751.INSTANCE,
                                                                                     20),
                                                                    D.reactOn("applicant")).expr("GENERATED_D1E5FA012BE6678FEBDAD042B1DA2907",
                                                                                                 org.acme.P24.LambdaPredicate24401E279F22A8BD9FECE89EDB429F7A.INSTANCE,
                                                                                                 D.alphaIndexedBy(int.class,
                                                                                                                  org.drools.model.Index.ConstraintType.LESS_THAN,
                                                                                                                  DomainClassesMetadataa0f73a0eb55f4f498647a1247b85f3b3.org_acme_LoanApplication_Metadata_INSTANCE.getPropertyIndex("deposit"),
                                                                                                                  org.acme.PAD.LambdaExtractorAD8C3EF776BA056064850969B451D14C.INSTANCE,
                                                                                                                  1000),
                                                                                                 D.reactOn("deposit")).expr("GENERATED_E4A247CC3D43FA3D83BCC84BB9FD95A2",
                                                                                                                            org.acme.P16.LambdaPredicate16CFFA780B80F0E6CB599039D6ECA414.INSTANCE,
                                                                                                                            D.alphaIndexedBy(int.class,
                                                                                                                                             org.drools.model.Index.ConstraintType.LESS_OR_EQUAL,
                                                                                                                                             DomainClassesMetadataa0f73a0eb55f4f498647a1247b85f3b3.org_acme_LoanApplication_Metadata_INSTANCE.getPropertyIndex("amount"),
                                                                                                                                             org.acme.P7E.LambdaExtractor7E22D1B52735163BE73CF86E7AE8ABE5.INSTANCE,
                                                                                                                                             2000),
                                                                                                                            D.reactOn("amount")),
                                             D.on(var_$l).execute(org.acme.P1A.LambdaConsequence1A93D7EAD6ECD3C916B58684C3E7429E.INSTANCE));
        return rule;
    }
}
