package org.acme.P5E;


import static org.acme.Rulesc2412ab5c8134638a6a1ed88dee5ef0d.*;
import org.acme.*;
import org.acme.LoanApplication;
import org.acme.Applicant;
import org.drools.modelcompiler.dsl.pattern.D;

@org.drools.compiler.kie.builder.MaterializedLambda()
public enum LambdaExtractor5E02FFEC863A149EFD3FC230E71427B6 implements org.drools.model.functions.Function1<org.acme.LoanApplication, Integer>, org.drools.model.functions.HashedExpression {

    INSTANCE;

    public static final String EXPRESSION_HASH = "D5FF062194EDEAD48654227E5392DF7B";

    public java.lang.String getExpressionHash() {
        return EXPRESSION_HASH;
    }

    @Override()
    public Integer apply(org.acme.LoanApplication _this) {
        return _this.getDeposit();
    }
}
