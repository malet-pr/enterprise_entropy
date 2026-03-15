package org.acme.PC2;


import static org.acme.Rulesa2abee888000408aa4cbed4f62a1b1f2.*;
import org.acme.*;
import org.acme.LoanApplication;
import org.acme.Applicant;
import org.drools.modelcompiler.dsl.pattern.D;

@org.drools.compiler.kie.builder.MaterializedLambda()
public enum LambdaConsequenceC21107F471B20B3F7E44CF68E8CCA735 implements org.drools.model.functions.Block2<java.util.List, org.acme.LoanApplication>, org.drools.model.functions.HashedExpression {

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
