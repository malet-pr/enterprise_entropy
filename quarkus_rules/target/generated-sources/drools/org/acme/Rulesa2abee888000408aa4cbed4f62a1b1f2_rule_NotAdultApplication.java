package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rulesa2abee888000408aa4cbed4f62a1b1f2.*;
import static org.acme.Rulesa2abee888000408aa4cbed4f62a1b1f2.*;

public class Rulesa2abee888000408aa4cbed4f62a1b1f2_rule_NotAdultApplication {

    /**
     * Rule name: NotAdultApplication
     */
    public static org.drools.model.Rule rule_NotAdultApplication() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadataa2abee888000408aa4cbed4f62a1b1f2.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        final org.drools.model.BitMask mask_$l = org.drools.model.BitMask.getPatternMask(DomainClassesMetadataa2abee888000408aa4cbed4f62a1b1f2.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                         "approved");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "NotAdultApplication")
                                      .build(D.pattern(var_$l).expr("GENERATED_6CFE70A51ED7693D87115863353D65B9",
                                                                    org.acme.P24.LambdaPredicate249FCA6DA0F47B861062F33EAB697685.INSTANCE,
                                                                    D.alphaIndexedBy(int.class,
                                                                                     org.drools.model.Index.ConstraintType.LESS_THAN,
                                                                                     -1,
                                                                                     org.acme.P50.LambdaExtractor502EE7811229B5737DD34BF88452D4D5.INSTANCE,
                                                                                     20),
                                                                    D.reactOn("applicant")),
                                             D.on(var_$l).execute(org.acme.P06.LambdaConsequence061552E479C3CD14DFB5F1D35B93CAA8.INSTANCE));
        return rule;
    }
}
