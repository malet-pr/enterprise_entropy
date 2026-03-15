package org.acme.P6E;


import static org.acme.Rulesa0f73a0eb55f4f498647a1247b85f3b3.*;
import org.acme.*;
import org.acme.LoanApplication;
import org.acme.Applicant;
import org.drools.modelcompiler.dsl.pattern.D;

@org.drools.compiler.kie.builder.MaterializedLambda()
public enum LambdaExtractor6ED589EF9B4DCB7C768830A718B6B751 implements org.drools.model.functions.Function1<org.acme.LoanApplication, Integer>, org.drools.model.functions.HashedExpression {

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
