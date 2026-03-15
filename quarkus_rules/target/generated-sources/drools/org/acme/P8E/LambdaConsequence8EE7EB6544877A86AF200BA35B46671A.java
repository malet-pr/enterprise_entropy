package org.acme.P8E;


import static org.acme.Rulesa0f73a0eb55f4f498647a1247b85f3b3.*;
import org.acme.*;
import org.acme.LoanApplication;
import org.acme.Applicant;
import org.drools.modelcompiler.dsl.pattern.D;

@org.drools.compiler.kie.builder.MaterializedLambda()
public enum LambdaConsequence8EE7EB6544877A86AF200BA35B46671A implements org.drools.model.functions.Block2<java.util.List, org.acme.LoanApplication>, org.drools.model.functions.HashedExpression {

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
