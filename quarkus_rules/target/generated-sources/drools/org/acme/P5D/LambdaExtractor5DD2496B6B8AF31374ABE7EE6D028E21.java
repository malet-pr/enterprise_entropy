package org.acme.P5D;


import static org.acme.Rulesd43a8da3760548af90c623e3d84c718b.*;
import org.acme.*;
import org.acme.LoanApplication;
import org.acme.Applicant;
import org.drools.modelcompiler.dsl.pattern.D;

@org.drools.compiler.kie.builder.MaterializedLambda()
public enum LambdaExtractor5DD2496B6B8AF31374ABE7EE6D028E21 implements org.drools.model.functions.Function1<org.acme.LoanApplication, Integer>, org.drools.model.functions.HashedExpression {

    INSTANCE;

    public static final String EXPRESSION_HASH = "6A8052EAC363B4917D3AAA88DA915C20";

    public java.lang.String getExpressionHash() {
        return EXPRESSION_HASH;
    }

    @Override()
    public Integer apply(org.acme.LoanApplication _this) {
        return _this.getApplicant().getAge();
    }
}
