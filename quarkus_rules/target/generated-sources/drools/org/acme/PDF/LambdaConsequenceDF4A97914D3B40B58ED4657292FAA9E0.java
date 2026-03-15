package org.acme.PDF;


import static org.acme.Rules349656ae6d9f46e8ba238a033dc18ce9.*;
import org.acme.*;
import org.acme.LoanApplication;
import org.acme.Applicant;
import org.drools.modelcompiler.dsl.pattern.D;

@org.drools.compiler.kie.builder.MaterializedLambda()
public enum LambdaConsequenceDF4A97914D3B40B58ED4657292FAA9E0 implements org.drools.model.functions.Block2<java.util.List, org.acme.LoanApplication>, org.drools.model.functions.HashedExpression {

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
