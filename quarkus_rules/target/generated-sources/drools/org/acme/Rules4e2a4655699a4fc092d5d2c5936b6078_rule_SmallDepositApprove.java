package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rules4e2a4655699a4fc092d5d2c5936b6078.*;
import static org.acme.Rules4e2a4655699a4fc092d5d2c5936b6078.*;

public class Rules4e2a4655699a4fc092d5d2c5936b6078_rule_SmallDepositApprove {

    /**
     * Rule name: SmallDepositApprove
     */
    public static org.drools.model.Rule rule_SmallDepositApprove() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadata4e2a4655699a4fc092d5d2c5936b6078.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        final org.drools.model.BitMask mask_$l = org.drools.model.BitMask.getPatternMask(DomainClassesMetadata4e2a4655699a4fc092d5d2c5936b6078.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                         "approved");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "SmallDepositApprove")
                                      .build(D.pattern(var_$l).expr("GENERATED_44AADF07954135603887DB373DC83E53",
                                                                    org.acme.P70.LambdaPredicate7089A899DC0402828D11E28FB5BEB23B.INSTANCE,
                                                                    D.alphaIndexedBy(int.class,
                                                                                     org.drools.model.Index.ConstraintType.GREATER_OR_EQUAL,
                                                                                     -1,
                                                                                     org.acme.PD9.LambdaExtractorD95DBEA98F917209EE17D6753B2450A8.INSTANCE,
                                                                                     20),
                                                                    D.reactOn("applicant")).expr("GENERATED_D1E5FA012BE6678FEBDAD042B1DA2907",
                                                                                                 org.acme.PB2.LambdaPredicateB23B9C474B6BA0EC4D7E52B91427A78C.INSTANCE,
                                                                                                 D.alphaIndexedBy(int.class,
                                                                                                                  org.drools.model.Index.ConstraintType.LESS_THAN,
                                                                                                                  DomainClassesMetadata4e2a4655699a4fc092d5d2c5936b6078.org_acme_LoanApplication_Metadata_INSTANCE.getPropertyIndex("deposit"),
                                                                                                                  org.acme.P0F.LambdaExtractor0FEAA8C1DCD8A26142DF90C9D0D775BF.INSTANCE,
                                                                                                                  1000),
                                                                                                 D.reactOn("deposit")).expr("GENERATED_E4A247CC3D43FA3D83BCC84BB9FD95A2",
                                                                                                                            org.acme.P4C.LambdaPredicate4C86C28B222000B1D280C80B579359CE.INSTANCE,
                                                                                                                            D.alphaIndexedBy(int.class,
                                                                                                                                             org.drools.model.Index.ConstraintType.LESS_OR_EQUAL,
                                                                                                                                             DomainClassesMetadata4e2a4655699a4fc092d5d2c5936b6078.org_acme_LoanApplication_Metadata_INSTANCE.getPropertyIndex("amount"),
                                                                                                                                             org.acme.P95.LambdaExtractor95743BD1A9FB188A7898B4C970F0C40A.INSTANCE,
                                                                                                                                             2000),
                                                                                                                            D.reactOn("amount")),
                                             D.on(var_$l).execute(org.acme.PD4.LambdaConsequenceD48A8FF83E5BAEAA96F284FC3FCE6DC7.INSTANCE));
        return rule;
    }
}
