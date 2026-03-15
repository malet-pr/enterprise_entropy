package org.acme.P8B;


import static org.acme.Rulesa6d89f9a67db4e178c54faec6ca9381f.*;
import org.acme.*;
import org.acme.LoanApplication;
import org.acme.Applicant;
import org.drools.modelcompiler.dsl.pattern.D;

@org.drools.compiler.kie.builder.MaterializedLambda()
public enum LambdaPredicate8B8ED768E112032E7D7BF0049511DCB7 implements org.drools.model.functions.Predicate1<org.acme.LoanApplication>, org.drools.model.functions.HashedExpression {

    INSTANCE;

    public static final String EXPRESSION_HASH = "1239135FA6FDAD14C3A468221BE98E42";

    public java.lang.String getExpressionHash() {
        return EXPRESSION_HASH;
    }

    @Override()
    public boolean test(org.acme.LoanApplication _this) throws java.lang.Exception {
        return org.drools.modelcompiler.util.EvaluationUtil.greaterOrEqualNumbers(_this.getDeposit(), 1000);
    }

    @Override()
    public org.drools.model.functions.PredicateInformation predicateInformation() {
        org.drools.model.functions.PredicateInformation info = new org.drools.model.functions.PredicateInformation("deposit >= 1000");
        info.addRuleNames("LargeDepositApprove", "/home/nuria/Projects/POC-MigrateRuleEngine/enterprise_entropy/src/main/resources/org/acme/rules.drl", "LargeDepositReject", "/home/nuria/Projects/POC-MigrateRuleEngine/enterprise_entropy/src/main/resources/org/acme/rules.drl");
        return info;
    }
}
