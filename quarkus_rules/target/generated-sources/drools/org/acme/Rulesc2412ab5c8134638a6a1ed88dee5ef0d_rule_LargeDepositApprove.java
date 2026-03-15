package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rulesc2412ab5c8134638a6a1ed88dee5ef0d.*;
import static org.acme.Rulesc2412ab5c8134638a6a1ed88dee5ef0d.*;

public class Rulesc2412ab5c8134638a6a1ed88dee5ef0d_rule_LargeDepositApprove {

    /**
     * Rule name: LargeDepositApprove
     */
    public static org.drools.model.Rule rule_LargeDepositApprove() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadatac2412ab5c8134638a6a1ed88dee5ef0d.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        final org.drools.model.BitMask mask_$l = org.drools.model.BitMask.getPatternMask(DomainClassesMetadatac2412ab5c8134638a6a1ed88dee5ef0d.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                         "approved");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "LargeDepositApprove")
                                      .build(D.pattern(var_$l).expr("GENERATED_44AADF07954135603887DB373DC83E53",
                                                                    org.acme.P1E.LambdaPredicate1E64B9B0D98B1EC0AFE1F2F0283E8E59.INSTANCE,
                                                                    D.alphaIndexedBy(int.class,
                                                                                     org.drools.model.Index.ConstraintType.GREATER_OR_EQUAL,
                                                                                     -1,
                                                                                     org.acme.P78.LambdaExtractor7847E7846072EAB4C9C61ADC44B900D7.INSTANCE,
                                                                                     20),
                                                                    D.reactOn("applicant")).expr("GENERATED_3DA7652F4D6E94EB06E6F8BE180C1227",
                                                                                                 org.acme.P75.LambdaPredicate75F0969CD2539D336DAD49E1DE96FACE.INSTANCE,
                                                                                                 D.alphaIndexedBy(int.class,
                                                                                                                  org.drools.model.Index.ConstraintType.GREATER_OR_EQUAL,
                                                                                                                  DomainClassesMetadatac2412ab5c8134638a6a1ed88dee5ef0d.org_acme_LoanApplication_Metadata_INSTANCE.getPropertyIndex("deposit"),
                                                                                                                  org.acme.P5E.LambdaExtractor5E02FFEC863A149EFD3FC230E71427B6.INSTANCE,
                                                                                                                  1000),
                                                                                                 D.reactOn("deposit")).expr("GENERATED_D13B6DD435AF4394AE3CC69DB0976CD8",
                                                                                                                            var_maxAmount,
                                                                                                                            org.acme.PF7.LambdaPredicateF7707EB2A59CEC7BA81C880B789A6B7F.INSTANCE,
                                                                                                                            D.reactOn("amount")),
                                             D.on(var_$l).execute(org.acme.P03.LambdaConsequence034D1F39F83D7A732744D3255432C511.INSTANCE));
        return rule;
    }
}
