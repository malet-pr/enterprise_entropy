package org.acme.PD5;


import static org.acme.Rules349656ae6d9f46e8ba238a033dc18ce9.*;
import org.acme.*;
import org.acme.LoanApplication;
import org.acme.Applicant;
import org.drools.modelcompiler.dsl.pattern.D;

@org.drools.compiler.kie.builder.MaterializedLambda()
public enum LambdaExtractorD55537B8D6BF836A04C4A932151DE278 implements org.drools.model.functions.Function1<org.acme.LoanApplication, Integer>, org.drools.model.functions.HashedExpression {

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
