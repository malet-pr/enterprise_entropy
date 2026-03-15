package org.acme;

import org.drools.modelcompiler.dsl.pattern.D;
import org.drools.model.Index.ConstraintType;
import org.acme.LoanApplication;
import org.acme.Applicant;

public class Rulesa0f73a0eb55f4f498647a1247b85f3b3 implements org.drools.model.Model {

    public final static java.time.format.DateTimeFormatter DATE_TIME_FORMATTER = new java.time.format.DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern(org.drools.util.DateUtils.getDateFormatMask()).toFormatter(java.util.Locale.ENGLISH);

    @Override
    public String getName() {
        return "org.acme";
    }

    @Override
    public java.util.List<org.drools.model.Global> getGlobals() {
        return globals;
    }

    @Override
    public java.util.List<org.drools.model.TypeMetaData> getTypeMetaDatas() {
        return typeMetaDatas;
    }

    public static final org.drools.model.Global<java.lang.Integer> var_maxAmount = D.globalOf(java.lang.Integer.class,
                                                                                              "org.acme",
                                                                                              "maxAmount");

    public static final org.drools.model.Global<java.util.List> var_approvedApplications = D.globalOf(java.util.List.class,
                                                                                                      "org.acme",
                                                                                                      "approvedApplications");

    protected java.util.List<org.drools.model.Global> globals = new java.util.ArrayList<>();

    java.util.List<org.drools.model.TypeMetaData> typeMetaDatas = java.util.Collections.emptyList();

    @Override
    public java.util.List<org.drools.model.Rule> getRules() {
        return rules;
    }

    public java.util.List<org.drools.model.Rule> getRulesList() {
        return java.util.Arrays.asList(Rulesa0f73a0eb55f4f498647a1247b85f3b3_rule_SmallDepositApprove.rule_SmallDepositApprove(),
                                       Rulesa0f73a0eb55f4f498647a1247b85f3b3_rule_SmallDepositReject.rule_SmallDepositReject(),
                                       Rulesa0f73a0eb55f4f498647a1247b85f3b3_rule_LargeDepositApprove.rule_LargeDepositApprove(),
                                       Rulesa0f73a0eb55f4f498647a1247b85f3b3_rule_LargeDepositReject.rule_LargeDepositReject(),
                                       Rulesa0f73a0eb55f4f498647a1247b85f3b3_rule_NotAdultApplication.rule_NotAdultApplication(),
                                       Rulesa0f73a0eb55f4f498647a1247b85f3b3_rule_CollectApprovedApplication.rule_CollectApprovedApplication());
    }

    java.util.List<org.drools.model.Rule> rules = getRulesList();

    @Override
    public java.util.List<org.drools.model.Query> getQueries() {
        return java.util.Collections.emptyList();
    }

    @Override
    public java.util.List<org.drools.model.EntryPoint> getEntryPoints() {
        return java.util.Collections.emptyList();
    }

    {
        globals.add(var_maxAmount);
        globals.add(var_approvedApplications);
    }
}
