package org.acme.incidents.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Incident {
    private String id;
    private String service;
    private String environment;
    private String message;
    private int severityScore;
    private int occurrences;
    private boolean customerImpact;
    private int hourOfDay;

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