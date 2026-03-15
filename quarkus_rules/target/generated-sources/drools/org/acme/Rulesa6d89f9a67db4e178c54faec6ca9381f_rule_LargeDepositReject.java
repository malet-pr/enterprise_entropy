package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rulesa6d89f9a67db4e178c54faec6ca9381f.*;
import static org.acme.Rulesa6d89f9a67db4e178c54faec6ca9381f.*;

public class Rulesa6d89f9a67db4e178c54faec6ca9381f_rule_LargeDepositReject {

    /**
     * Rule name: LargeDepositReject
     */
    public static org.drools.model.Rule rule_LargeDepositReject() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadataa6d89f9a67db4e178c54faec6ca9381f.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        final org.drools.model.BitMask mask_$l = org.drools.model.BitMask.getPatternMask(DomainClassesMetadataa6d89f9a67db4e178c54faec6ca9381f.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                         "approved");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "LargeDepositReject")
                                      .build(D.pattern(var_$l).expr("GENERATED_44AADF07954135603887DB373DC83E53",
                                                                    org.acme.P35.LambdaPredicate351E0691CA6E7681F02C08662EFD5DD3.INSTANCE,
                                                                    D.alphaIndexedBy(int.class,
                                                                                     org.drools.model.Index.ConstraintType.GREATER_OR_EQUAL,
                                                                                     -1,
                                                                                     org.acme.P86.LambdaExtractor86F3F430AAF170DB350991DF710676F9.INSTANCE,
                                                                                     20),
                                                                    D.reactOn("applicant")).expr("GENERATED_3DA7652F4D6E94EB06E6F8BE180C1227",
                                                                                                 org.acme.P8B.LambdaPredicate8B8ED768E112032E7D7BF0049511DCB7.INSTANCE,
                                                                                                 D.alphaIndexedBy(int.class,
                                                                                                                  org.drools.model.Index.ConstraintType.GREATER_OR_EQUAL,
                                                                                                                  DomainClassesMetadataa6d89f9a67db4e178c54faec6ca9381f.org_acme_LoanApplication_Metadata_INSTANCE.getPropertyIndex("deposit"),
                                                                                                                  org.acme.P38.LambdaExtractor38E4EABFCD9C3BBAE7093823A1B43C9A.INSTANCE,
                                                                                                                  1000),
                                                                                                 D.reactOn("deposit")).expr("GENERATED_2AC576A76D5F96F1792E6E781829E57D",
                                                                                                                            var_maxAmount,
                                                                                                                            org.acme.P88.LambdaPredicate885D0FC77AAFC3320620F68DEFF37062.INSTANCE,
                                                                                                                            D.reactOn("amount")),
                                             D.on(var_$l).execute(org.acme.PA6.LambdaConsequenceA6700EBF4E0DB97D968672073D642231.INSTANCE));
        return rule;
    }
}
