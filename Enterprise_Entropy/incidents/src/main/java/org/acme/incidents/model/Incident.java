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
    @Builder.Default
    private int severityScore = 1;
    @Builder.Default
    private int occurrences = 1;
    @Builder.Default
    private boolean customerImpact = false;
    @Builder.Default
    private int hourOfDay = 0;

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