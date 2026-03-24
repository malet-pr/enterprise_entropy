package org.acme.incidents.demo;

import org.acme.incidents.model.Incident;
import java.util.List;
import java.util.function.Supplier;

public class SampleIncidents {

    public static Supplier<List<Incident>> sampleData() {
        return () -> List.of(
                new Incident(
                        "INC-001",
                        "billing-engine",
                        "prod",
                        "NullPointerException during invoice generation",
                        9,
                        12,
                        true,
                        3
                ),
                new Incident(
                        "INC-002",
                        "gateway-api",
                        "prod",
                        "Timeout calling downstream customer profile service",
                        8,
                        7,
                        true,
                        10
                ),
                new Incident(
                        "INC-003",
                        "auth-service",
                        "dev",
                        "Healthcheck failed once after redeploy",
                        2,
                        1,
                        false,
                        11
                ),
                new Incident(
                        "INC-004",
                        "sap-bridge",
                        "test",
                        "Connection refused while calling SAP",
                        6,
                        2,
                        false,
                        15
                ),
                new Incident(
                        "INC-005",
                        "kafka-consumer",
                        "prod",
                        "Consumer lag exceeded threshold for 20 minutes",
                        7,
                        5,
                        true,
                        4
                ),
                new Incident(
                        "INC-006",
                        "mysterious-legacy-module",
                        "prod",
                        "Llamada fantasma detected in deprecated path",
                        5,
                        19,
                        false,
                        2
                )
        );
    }
}