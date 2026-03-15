package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rulesc2412ab5c8134638a6a1ed88dee5ef0d.*;
import static org.acme.Rulesc2412ab5c8134638a6a1ed88dee5ef0d.*;

public class Rulesc2412ab5c8134638a6a1ed88dee5ef0d_rule_NotAdultApplication {

    /**
     * Rule name: NotAdultApplication
     */
    public static org.drools.model.Rule rule_NotAdultApplication() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadatac2412ab5c8134638a6a1ed88dee5ef0d.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        final org.drools.model.BitMask mask_$l = org.drools.model.BitMask.getPatternMask(DomainClassesMetadatac2412ab5c8134638a6a1ed88dee5ef0d.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                         "approved");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "NotAdultApplication")
                                      .build(D.pattern(var_$l).expr("GENERATED_6CFE70A51ED7693D87115863353D65B9",
                                                                    org.acme.P58.LambdaPredicate5856403396DA8BB15FAEEDB91A4384FE.INSTANCE,
                                                                    D.alphaIndexedBy(int.class,
                                                                                     org.drools.model.Index.ConstraintType.LESS_THAN,
                                                                                     -1,
                                                                                     org.acme.P78.LambdaExtractor7847E7846072EAB4C9C61ADC44B900D7.INSTANCE,
                                                                                     20),
                                                                    D.reactOn("applicant")),
                                             D.on(var_$l).execute(org.acme.PB2.LambdaConsequenceB20E75F9AC04007E5ECFA80A5D49A71E.INSTANCE));
        return rule;
    }
}
