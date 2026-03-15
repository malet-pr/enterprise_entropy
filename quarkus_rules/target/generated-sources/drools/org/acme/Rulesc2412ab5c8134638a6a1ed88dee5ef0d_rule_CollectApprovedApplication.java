package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rulesc2412ab5c8134638a6a1ed88dee5ef0d.*;
import static org.acme.Rulesc2412ab5c8134638a6a1ed88dee5ef0d.*;

public class Rulesc2412ab5c8134638a6a1ed88dee5ef0d_rule_CollectApprovedApplication {

    /**
     * Rule name: CollectApprovedApplication
     */
    public static org.drools.model.Rule rule_CollectApprovedApplication() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadatac2412ab5c8134638a6a1ed88dee5ef0d.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "CollectApprovedApplication")
                                      .build(D.pattern(var_$l).expr("GENERATED_28F2B4ED91B5E129B75A395950134B3E",
                                                                    org.acme.PCA.LambdaPredicateCA99A80B69FF895C08E6F3668E70656B.INSTANCE,
                                                                    D.reactOn("approved")),
                                             D.on(var_approvedApplications,
                                                  var_$l).execute(org.acme.P1D.LambdaConsequence1D996781BC53A06897B03E795C85DC9B.INSTANCE));
        return rule;
    }
}
