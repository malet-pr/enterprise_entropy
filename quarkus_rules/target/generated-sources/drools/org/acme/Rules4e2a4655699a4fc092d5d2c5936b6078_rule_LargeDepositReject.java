package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rules4e2a4655699a4fc092d5d2c5936b6078.*;
import static org.acme.Rules4e2a4655699a4fc092d5d2c5936b6078.*;

public class Rules4e2a4655699a4fc092d5d2c5936b6078_rule_LargeDepositReject {

    /**
     * Rule name: LargeDepositReject
     */
    public static org.drools.model.Rule rule_LargeDepositReject() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadata4e2a4655699a4fc092d5d2c5936b6078.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        final org.drools.model.BitMask mask_$l = org.drools.model.BitMask.getPatternMask(DomainClassesMetadata4e2a4655699a4fc092d5d2c5936b6078.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                         "approved");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "LargeDepositReject")
                                      .build(D.pattern(var_$l).expr("GENERATED_44AADF07954135603887DB373DC83E53",
                                                                    org.acme.P70.LambdaPredicate7089A899DC0402828D11E28FB5BEB23B.INSTANCE,
                                                                    D.alphaIndexedBy(int.class,
                                                                                     org.drools.model.Index.ConstraintType.GREATER_OR_EQUAL,
                                                                                     -1,
                                                                                     org.acme.PD9.LambdaExtractorD95DBEA98F917209EE17D6753B2450A8.INSTANCE,
                                                                                     20),
                                                                    D.reactOn("applicant")).expr("GENERATED_3DA7652F4D6E94EB06E6F8BE180C1227",
                                                                                                 org.acme.P77.LambdaPredicate77903BE5B2E4C2C7E364E0BAD35A854D.INSTANCE,
                                                                                                 D.alphaIndexedBy(int.class,
                                                                                                                  org.drools.model.Index.ConstraintType.GREATER_OR_EQUAL,
                                                                                                                  DomainClassesMetadata4e2a4655699a4fc092d5d2c5936b6078.org_acme_LoanApplication_Metadata_INSTANCE.getPropertyIndex("deposit"),
                                                                                                                  org.acme.P0F.LambdaExtractor0FEAA8C1DCD8A26142DF90C9D0D775BF.INSTANCE,
                                                                                                                  1000),
                                                                                                 D.reactOn("deposit")).expr("GENERATED_2AC576A76D5F96F1792E6E781829E57D",
                                                                                                                            var_maxAmount,
                                                                                                                            org.acme.P7D.LambdaPredicate7D2ACCEF6E26BD1762D5AC5A139709C4.INSTANCE,
                                                                                                                            D.reactOn("amount")),
                                             D.on(var_$l).execute(org.acme.P24.LambdaConsequence244DD9B33FE96800217B382260F68147.INSTANCE));
        return rule;
    }
}
