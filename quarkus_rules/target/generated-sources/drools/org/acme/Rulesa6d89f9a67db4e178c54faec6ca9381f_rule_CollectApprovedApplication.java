package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rulesa6d89f9a67db4e178c54faec6ca9381f.*;
import static org.acme.Rulesa6d89f9a67db4e178c54faec6ca9381f.*;

public class Rulesa6d89f9a67db4e178c54faec6ca9381f_rule_CollectApprovedApplication {

    /**
     * Rule name: CollectApprovedApplication
     */
    public static org.drools.model.Rule rule_CollectApprovedApplication() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadataa6d89f9a67db4e178c54faec6ca9381f.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "CollectApprovedApplication")
                                      .build(D.pattern(var_$l).expr("GENERATED_28F2B4ED91B5E129B75A395950134B3E",
                                                                    org.acme.P55.LambdaPredicate5519FD9A1EEA532C18646BB109FC2BAA.INSTANCE,
                                                                    D.reactOn("approved")),
                                             D.on(var_approvedApplications,
                                                  var_$l).execute(org.acme.P6F.LambdaConsequence6F4336830D69A02EA952CB7EF24D5478.INSTANCE));
        return rule;
    }
}
