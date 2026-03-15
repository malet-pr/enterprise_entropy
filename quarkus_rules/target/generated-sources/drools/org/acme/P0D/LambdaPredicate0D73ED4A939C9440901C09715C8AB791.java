package org.acme.P0D;


import static org.acme.Rulesd43a8da3760548af90c623e3d84c718b.*;
import org.acme.*;
import org.acme.LoanApplication;
import org.acme.Applicant;
import org.drools.modelcompiler.dsl.pattern.D;

@org.drools.compiler.kie.builder.MaterializedLambda()
public enum LambdaPredicate0D73ED4A939C9440901C09715C8AB791 implements org.drools.model.functions.Predicate2<org.acme.LoanApplication, java.lang.Integer>, org.drools.model.functions.HashedExpression {

    INSTANCE;

    public static final String EXPRESSION_HASH = "A8819970E68E5DB54434BA522A80360B";

    public java.lang.String getExpressionHash() {
        return EXPRESSION_HASH;
    }

    @Override()
    public boolean test(org.acme.LoanApplication _this, java.lang.Integer maxAmount) throws java.lang.Exception {
        return org.drools.modelcompiler.util.EvaluationUtil.lessOrEqualNumbers(_this.getAmount(), maxAmount);
    }

    @Override()
    public org.drools.model.functions.PredicateInformation predicateInformation() {
        org.drools.model.functions.PredicateInformation info = new org.drools.model.functions.PredicateInformation("amount <= maxAmount");
        info.addRuleNames("LargeDepositApprove", "/home/nuria/Projects/POC-MigrateRuleEngine/enterprise_entropy/src/main/resources/org/acme/rules.drl");
        return info;
    }
}
