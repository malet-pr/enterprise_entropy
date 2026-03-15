package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rulesa0f73a0eb55f4f498647a1247b85f3b3.*;
import static org.acme.Rulesa0f73a0eb55f4f498647a1247b85f3b3.*;

public class Rulesa0f73a0eb55f4f498647a1247b85f3b3_rule_CollectApprovedApplication {

    /**
     * Rule name: CollectApprovedApplication
     */
    public static org.drools.model.Rule rule_CollectApprovedApplication() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadataa0f73a0eb55f4f498647a1247b85f3b3.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "CollectApprovedApplication")
                                      .build(D.pattern(var_$l).expr("GENERATED_28F2B4ED91B5E129B75A395950134B3E",
                                                                    org.acme.P88.LambdaPredicate882988FCEB8F99840DF1FA5B0FC83575.INSTANCE,
                                                                    D.reactOn("approved")),
                                             D.on(var_approvedApplications,
                                                  var_$l).execute(org.acme.P8E.LambdaConsequence8EE7EB6544877A86AF200BA35B46671A.INSTANCE));
        return rule;
    }
}
