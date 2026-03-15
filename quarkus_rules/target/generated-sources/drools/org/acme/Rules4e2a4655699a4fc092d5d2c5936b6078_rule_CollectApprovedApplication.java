package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rules4e2a4655699a4fc092d5d2c5936b6078.*;
import static org.acme.Rules4e2a4655699a4fc092d5d2c5936b6078.*;

public class Rules4e2a4655699a4fc092d5d2c5936b6078_rule_CollectApprovedApplication {

    /**
     * Rule name: CollectApprovedApplication
     */
    public static org.drools.model.Rule rule_CollectApprovedApplication() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadata4e2a4655699a4fc092d5d2c5936b6078.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "CollectApprovedApplication")
                                      .build(D.pattern(var_$l).expr("GENERATED_28F2B4ED91B5E129B75A395950134B3E",
                                                                    org.acme.P28.LambdaPredicate28FEDA658D5E6F0247CF9C7BB9ADD09C.INSTANCE,
                                                                    D.reactOn("approved")),
                                             D.on(var_approvedApplications,
                                                  var_$l).execute(org.acme.PDD.LambdaConsequenceDDA01F12E9983483DBC4D1B2333430D7.INSTANCE));
        return rule;
    }
}
