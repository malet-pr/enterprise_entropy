package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rules349656ae6d9f46e8ba238a033dc18ce9.*;
import static org.acme.Rules349656ae6d9f46e8ba238a033dc18ce9.*;

public class Rules349656ae6d9f46e8ba238a033dc18ce9_rule_SmallDepositApprove {

    /**
     * Rule name: SmallDepositApprove
     */
    public static org.drools.model.Rule rule_SmallDepositApprove() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadata349656ae6d9f46e8ba238a033dc18ce9.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        final org.drools.model.BitMask mask_$l = org.drools.model.BitMask.getPatternMask(DomainClassesMetadata349656ae6d9f46e8ba238a033dc18ce9.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                         "approved");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "SmallDepositApprove")
                                      .build(D.pattern(var_$l).expr("GENERATED_44AADF07954135603887DB373DC83E53",
                                                                    org.acme.P41.LambdaPredicate41A016DAE4094B7DCC9F728589145E4B.INSTANCE,
                                                                    D.alphaIndexedBy(int.class,
                                                                                     org.drools.model.Index.ConstraintType.GREATER_OR_EQUAL,
                                                                                     -1,
                                                                                     org.acme.PD5.LambdaExtractorD55537B8D6BF836A04C4A932151DE278.INSTANCE,
                                                                                     20),
                                                                    D.reactOn("applicant")).expr("GENERATED_D1E5FA012BE6678FEBDAD042B1DA2907",
                                                                                                 org.acme.P34.LambdaPredicate34B09C83C477276D323375ADA0F75C5A.INSTANCE,
                                                                                                 D.alphaIndexedBy(int.class,
                                                                                                                  org.drools.model.Index.ConstraintType.LESS_THAN,
                                                                                                                  DomainClassesMetadata349656ae6d9f46e8ba238a033dc18ce9.org_acme_LoanApplication_Metadata_INSTANCE.getPropertyIndex("deposit"),
                                                                                                                  org.acme.P80.LambdaExtractor807DA493AA1AAB8965276D4589454F1B.INSTANCE,
                                                                                                                  1000),
                                                                                                 D.reactOn("deposit")).expr("GENERATED_E4A247CC3D43FA3D83BCC84BB9FD95A2",
                                                                                                                            org.acme.P9B.LambdaPredicate9BB1ED7EE853C6F460A1DE49504924CA.INSTANCE,
                                                                                                                            D.alphaIndexedBy(int.class,
                                                                                                                                             org.drools.model.Index.ConstraintType.LESS_OR_EQUAL,
                                                                                                                                             DomainClassesMetadata349656ae6d9f46e8ba238a033dc18ce9.org_acme_LoanApplication_Metadata_INSTANCE.getPropertyIndex("amount"),
                                                                                                                                             org.acme.P49.LambdaExtractor49E2777F54F15118E3B88409E81963C8.INSTANCE,
                                                                                                                                             2000),
                                                                                                                            D.reactOn("amount")),
                                             D.on(var_$l).execute(org.acme.P46.LambdaConsequence4625A8E3CB06C1ACD0952912D31EB2B3.INSTANCE));
        return rule;
    }
}
