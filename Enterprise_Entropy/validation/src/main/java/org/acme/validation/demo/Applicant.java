package org.acme.validation.demo;

public record Applicant (
    String name,
    int age,
    int deposit,
    boolean hasIncome,
    boolean validated
) {
    public Applicant(String name) {
        this(name,0, 0,false, false);
    }

    public Applicant(String name, int age, int deposit, boolean hasIncome) {
        this(name, age, deposit, hasIncome, false);
    }

}
