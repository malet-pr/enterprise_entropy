package org.acme.PFB;


import static org.acme.Rulesd43a8da3760548af90c623e3d84c718b.*;
import org.acme.*;
import org.acme.LoanApplication;
import org.acme.Applicant;
import org.drools.modelcompiler.dsl.pattern.D;

@org.drools.compiler.kie.builder.MaterializedLambda()
public enum LambdaConsequenceFB24C9B79E09FAC93895BC346A250691 implements org.drools.model.functions.Block2<java.util.List, org.acme.LoanApplication>, org.drools.model.functions.HashedExpression {

    INSTANCE;

    public static final String EXPRESSION_HASH = "D5E76964A19B881B3E138905D1817AA4";

    public java.lang.String getExpressionHash() {
        return EXPRESSION_HASH;
    }

    @Override()
    public void execute(java.util.List approvedApplications, org.acme.LoanApplication $l) throws java.lang.Exception {
        approvedApplications.add($l);
    }
}
