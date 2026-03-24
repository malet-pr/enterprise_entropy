package org.acme.incidents.model;

public class Incident {
    private final String id;
    private final String service;
    private final String environment;
    private final String message;
    private final int severityScore;
    private final int occurrences;
    private final boolean customerImpact;
    private final int hourOfDay;

    public Incident(
            String id,
            String service,
            String environment,
            String message,
            int severityScore,
            int occurrences,
            boolean customerImpact,
            int hourOfDay) {
        this.id = id;
        this.service = service;
        this.environment = environment;
        this.message = message;
        this.severityScore = severityScore;
        this.occurrences = occurrences;
        this.customerImpact = customerImpact;
        this.hourOfDay = hourOfDay;
    }

    public String getId() {
        return id;
    }

    public String getService() {
        return service;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getMessage() {
        return message;
    }

    public int getSeverityScore() {
        return severityScore;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public boolean isCustomerImpact() {
        return customerImpact;
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    @Override
    public String toString() {
        return "Incident{" +
                "id='" + id + '\'' +
                ", service='" + service + '\'' +
                ", environment='" + environment + '\'' +
                ", severityScore=" + severityScore +
                ", occurrences=" + occurrences +
                ", customerImpact=" + customerImpact +
                ", hourOfDay=" + hourOfDay +
                ", message='" + message + '\'' +
                '}';
    }
}