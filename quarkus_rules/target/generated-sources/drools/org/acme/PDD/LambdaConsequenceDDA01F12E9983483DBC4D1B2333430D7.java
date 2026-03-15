package org.acme.PDD;


import static org.acme.Rules4e2a4655699a4fc092d5d2c5936b6078.*;
import org.acme.*;
import org.acme.LoanApplication;
import org.acme.Applicant;
import org.drools.modelcompiler.dsl.pattern.D;

@org.drools.compiler.kie.builder.MaterializedLambda()
public enum LambdaConsequenceDDA01F12E9983483DBC4D1B2333430D7 implements org.drools.model.functions.Block2<java.util.List, org.acme.LoanApplication>, org.drools.model.functions.HashedExpression {

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
