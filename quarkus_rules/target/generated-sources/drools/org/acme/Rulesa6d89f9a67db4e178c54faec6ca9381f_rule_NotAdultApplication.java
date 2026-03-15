package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rulesa6d89f9a67db4e178c54faec6ca9381f.*;
import static org.acme.Rulesa6d89f9a67db4e178c54faec6ca9381f.*;

public class Rulesa6d89f9a67db4e178c54faec6ca9381f_rule_NotAdultApplication {

    /**
     * Rule name: NotAdultApplication
     */
    public static org.drools.model.Rule rule_NotAdultApplication() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadataa6d89f9a67db4e178c54faec6ca9381f.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        final org.drools.model.BitMask mask_$l = org.drools.model.BitMask.getPatternMask(DomainClassesMetadataa6d89f9a67db4e178c54faec6ca9381f.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                         "approved");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "NotAdultApplication")
                                      .build(D.pattern(var_$l).expr("GENERATED_6CFE70A51ED7693D87115863353D65B9",
                                                                    org.acme.P67.LambdaPredicate672E67745E9756A9EC331DB686FF78EF.INSTANCE,
                                                                    D.alphaIndexedBy(int.class,
                                                                                     org.drools.model.Index.ConstraintType.LESS_THAN,
                                                                                     -1,
                                                                                     org.acme.P86.LambdaExtractor86F3F430AAF170DB350991DF710676F9.INSTANCE,
                                                                                     20),
                                                                    D.reactOn("applicant")),
                                             D.on(var_$l).execute(org.acme.PA6.LambdaConsequenceA6700EBF4E0DB97D968672073D642231.INSTANCE));
        return rule;
    }
}
