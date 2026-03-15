package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rulesd43a8da3760548af90c623e3d84c718b.*;
import static org.acme.Rulesd43a8da3760548af90c623e3d84c718b.*;

public class Rulesd43a8da3760548af90c623e3d84c718b_rule_CollectApprovedApplication {

    /**
     * Rule name: CollectApprovedApplication
     */
    public static org.drools.model.Rule rule_CollectApprovedApplication() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadatad43a8da3760548af90c623e3d84c718b.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "CollectApprovedApplication")
                                      .build(D.pattern(var_$l).expr("GENERATED_28F2B4ED91B5E129B75A395950134B3E",
                                                                    org.acme.P19.LambdaPredicate19AA500738D0016F66750EFC5B135787.INSTANCE,
                                                                    D.reactOn("approved")),
                                             D.on(var_approvedApplications,
                                                  var_$l).execute(org.acme.PFB.LambdaConsequenceFB24C9B79E09FAC93895BC346A250691.INSTANCE));
        return rule;
    }
}
