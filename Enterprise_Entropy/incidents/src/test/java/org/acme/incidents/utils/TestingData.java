package org.acme.incidents.utils;

import org.acme.incidents.model.Incident;

public class TestingData {

    public static final Incident incident1 = new Incident(
    "INC-001", "billing-service", "prod",
    "NullPointerException while search for account", 9,
    5, true, 14
    );

    public static final Incident incident2 = new Incident(
    "INC-002", "remediation", "dev",
    "healthcheck failed", 3,
    1, false, 14
    );

    public static final Incident incident3 = new Incident(
    "INC-003", "billing-service", "prod",
    "Time out", 2,
    2, false, 14
    );

    public static final Incident incident4 = new Incident(
            "INC-003", "billing-service", "prod",
            "Time out", 8,
            2, false, 14
    );

    public static final Incident incident5 = new Incident(
            "INC-003", "billing-service", "prod",
            "Time out", 6,
            2, false, 14
    );


}
