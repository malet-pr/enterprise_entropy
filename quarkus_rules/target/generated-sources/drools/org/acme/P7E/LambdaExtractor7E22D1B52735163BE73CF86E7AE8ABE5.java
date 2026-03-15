package org.acme.P7E;


import static org.acme.Rulesa0f73a0eb55f4f498647a1247b85f3b3.*;
import org.acme.*;
import org.acme.LoanApplication;
import org.acme.Applicant;
import org.drools.modelcompiler.dsl.pattern.D;

@org.drools.compiler.kie.builder.MaterializedLambda()
public enum LambdaExtractor7E22D1B52735163BE73CF86E7AE8ABE5 implements org.drools.model.functions.Function1<org.acme.LoanApplication, Integer>, org.drools.model.functions.HashedExpression {

    INSTANCE;

    public static final String EXPRESSION_HASH = "037E714FEB19C18B7AB8E7DFFB5F4397";

    public java.lang.String getExpressionHash() {
        return EXPRESSION_HASH;
    }

    @Override()
    public Integer apply(org.acme.LoanApplication _this) {
        return _this.getAmount();
    }
}
