package org.acme.incidents.utils;

import org.acme.incidents.model.Incident;
import java.util.List;

public class TestingData {

    public static final Incident incident1 = new Incident(
    "INC-001", "unknown", "prod",
    "NullPointerException while search for account", 9,
    5, true, 14
    );

    public static final Incident incident2 = new Incident(
    "INC-002", "remediation", "dev",
    "healthcheck failed", 3,
    1, false, 14
    );

    public static final Incident incident3 = new Incident(
    "INC-003", "auth-service", "prod",
    "Time out", 2,
    6, false, 14
    );

    public static final Incident incident4 = new Incident(
            "INC-004", "billing-service", "prod",
            "Time out", 8,
            2, false, 14
    );

    public static final Incident incident5 = new Incident(
            "INC-005", "kafka-service", "prod",
            "Time out", 6,
            2, false, 14
    );

    public static final Incident incident6 = new Incident(
            "INC-006", "api-gateway", "prod",
            "Time out", 6,
            2, true, 14
    );

    public static final Incident incident7 = new Incident(
            "INC-007", "unknown", "prod",
            "Time out", 3,
            5, false, 14
    );

    public static final Incident incident8 = new Incident(
            "inc-008", "billing", "prod",
            "Time out", 9,
            -1, true, 0
    );

    public static List<Incident> incidents = List.of(incident1,incident2,incident3,incident4,incident5,incident6,incident7,incident8);


}
