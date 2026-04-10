package org.acme.rules.model;

import lombok.Data;
import org.acme.rules.model.enums.Environment;
import org.acme.rules.model.enums.MeetingDrift;
import org.acme.rules.model.enums.MeetingType;

@Data
public class Meeting {
    private MeetingType meetingType;
    private int durationMin;
    private boolean deepDive;
    private MeetingDrift drift;
    private Environment environment;
}
