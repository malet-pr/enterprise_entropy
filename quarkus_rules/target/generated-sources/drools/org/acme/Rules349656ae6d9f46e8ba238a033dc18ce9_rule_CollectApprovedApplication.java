package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;
import static org.acme.Rules349656ae6d9f46e8ba238a033dc18ce9.*;
import static org.acme.Rules349656ae6d9f46e8ba238a033dc18ce9.*;

public class Rules349656ae6d9f46e8ba238a033dc18ce9_rule_CollectApprovedApplication {

    /**
     * Rule name: CollectApprovedApplication
     */
    public static org.drools.model.Rule rule_CollectApprovedApplication() {
        final org.drools.model.Variable<org.acme.LoanApplication> var_$l = D.declarationOf(org.acme.LoanApplication.class,
                                                                                           DomainClassesMetadata349656ae6d9f46e8ba238a033dc18ce9.org_acme_LoanApplication_Metadata_INSTANCE,
                                                                                           "$l");
        org.drools.model.Rule rule = D.rule("org.acme",
                                            "CollectApprovedApplication")
                                      .build(D.pattern(var_$l).expr("GENERATED_28F2B4ED91B5E129B75A395950134B3E",
                                                                    org.acme.P1B.LambdaPredicate1B604F5BEDF97ADE2D17CC078041B076.INSTANCE,
                                                                    D.reactOn("approved")),
                                             D.on(var_approvedApplications,
                                                  var_$l).execute(org.acme.PDF.LambdaConsequenceDF4A97914D3B40B58ED4657292FAA9E0.INSTANCE));
        return rule;
    }
}
