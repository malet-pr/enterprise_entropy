package org.acme.P1E;


import static org.acme.Rulesc2412ab5c8134638a6a1ed88dee5ef0d.*;
import org.acme.*;
import org.acme.LoanApplication;
import org.acme.Applicant;
import org.drools.modelcompiler.dsl.pattern.D;

@org.drools.compiler.kie.builder.MaterializedLambda()
public enum LambdaPredicate1E64B9B0D98B1EC0AFE1F2F0283E8E59 implements org.drools.model.functions.Predicate1<org.acme.LoanApplication>, org.drools.model.functions.HashedExpression {

    INSTANCE;

    public static final String EXPRESSION_HASH = "AC127A8E702097744850AB0F6014420C";

    public java.lang.String getExpressionHash() {
        return EXPRESSION_HASH;
    }

    @Override()
    public boolean test(org.acme.LoanApplication _this) throws java.lang.Exception {
        return org.drools.modelcompiler.util.EvaluationUtil.greaterOrEqualNumbers(_this.getApplicant().getAge(), 20);
    }

    @Override()
    public org.drools.model.functions.PredicateInformation predicateInformation() {
        org.drools.model.functions.PredicateInformation info = new org.drools.model.functions.PredicateInformation("applicant.age >= 20");
        info.addRuleNames("LargeDepositApprove", "/home/nuria/Projects/POC-MigrateRuleEngine/enterprise_entropy/src/main/resources/org/acme/rules.drl", "LargeDepositReject", "/home/nuria/Projects/POC-MigrateRuleEngine/enterprise_entropy/src/main/resources/org/acme/rules.drl", "SmallDepositApprove", "/home/nuria/Projects/POC-MigrateRuleEngine/enterprise_entropy/src/main/resources/org/acme/rules.drl", "SmallDepositReject", "/home/nuria/Projects/POC-MigrateRuleEngine/enterprise_entropy/src/main/resources/org/acme/rules.drl");
        return info;
    }
}
