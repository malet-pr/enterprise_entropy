package org.acme.PB2;


import static org.acme.Rules4e2a4655699a4fc092d5d2c5936b6078.*;
import org.acme.*;
import org.acme.LoanApplication;
import org.acme.Applicant;
import org.drools.modelcompiler.dsl.pattern.D;

@org.drools.compiler.kie.builder.MaterializedLambda()
public enum LambdaPredicateB23B9C474B6BA0EC4D7E52B91427A78C implements org.drools.model.functions.Predicate1<org.acme.LoanApplication>, org.drools.model.functions.HashedExpression {

    INSTANCE;

    public static final String EXPRESSION_HASH = "E2A871BB80411F842B20D14FCB36DEF7";

    public java.lang.String getExpressionHash() {
        return EXPRESSION_HASH;
    }

    @Override()
    public boolean test(org.acme.LoanApplication _this) throws java.lang.Exception {
        return org.drools.modelcompiler.util.EvaluationUtil.lessThanNumbers(_this.getDeposit(), 1000);
    }

    @Override()
    public org.drools.model.functions.PredicateInformation predicateInformation() {
        org.drools.model.functions.PredicateInformation info = new org.drools.model.functions.PredicateInformation("deposit < 1000");
        info.addRuleNames("SmallDepositApprove", "/home/nuria/Projects/POC-MigrateRuleEngine/enterprise_entropy/src/main/resources/org/acme/rules.drl", "SmallDepositReject", "/home/nuria/Projects/POC-MigrateRuleEngine/enterprise_entropy/src/main/resources/org/acme/rules.drl");
        return info;
    }
}
