package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rules5cb894750a3943ecb6fa8c6b7ffedbb8.*;
import static org.acme.Rules5cb894750a3943ecb6fa8c6b7ffedbb8.*;

public class Rules5cb894750a3943ecb6fa8c6b7ffedbb8_rule_CollectApprovedApplication {

    /**
     * Rule name: CollectApprovedApplication
     */
    public static org.drools.model.Rule rule_CollectApprovedApplication() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadata5cb894750a3943ecb6fa8c6b7ffedbb8.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "CollectApprovedApplication")
                                      .build(D.pattern(var_$l).expr("GENERATED_28F2B4ED91B5E129B75A395950134B3E",
                                                                    org.acme.P0F.LambdaPredicate0F449EF07B06657E8E544BB1231E5F2A.INSTANCE,
                                                                    D.reactOn("approved")),
                                             D.on(var_approvedApplications,
                                                  var_$l).execute(org.acme.PC6.LambdaConsequenceC65B983F7107DC44C88C2D1F34E956EA.INSTANCE));
        return rule;
    }
}
