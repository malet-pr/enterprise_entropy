package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rulesa2abee888000408aa4cbed4f62a1b1f2.*;
import static org.acme.Rulesa2abee888000408aa4cbed4f62a1b1f2.*;

public class Rulesa2abee888000408aa4cbed4f62a1b1f2_rule_CollectApprovedApplication {

    /**
     * Rule name: CollectApprovedApplication
     */
    public static org.drools.model.Rule rule_CollectApprovedApplication() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadataa2abee888000408aa4cbed4f62a1b1f2.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "CollectApprovedApplication")
                                      .build(D.pattern(var_$l).expr("GENERATED_28F2B4ED91B5E129B75A395950134B3E",
                                                                    org.acme.PE0.LambdaPredicateE06E72C441F7D4AF67D69EEF59F605BF.INSTANCE,
                                                                    D.reactOn("approved")),
                                             D.on(var_approvedApplications,
                                                  var_$l).execute(org.acme.PC2.LambdaConsequenceC21107F471B20B3F7E44CF68E8CCA735.INSTANCE));
        return rule;
    }
}
