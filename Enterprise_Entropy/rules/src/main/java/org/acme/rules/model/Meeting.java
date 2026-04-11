package org.acme.rules.model;

import lombok.Data;
import org.acme.rules.model.enums.*;

import java.util.List;

@Data
public class Meeting {
    private MeetingType meetingType;
    private int durationMin;
    private boolean deepDive;
    private MeetingDrift drift;
    private Environment environment;
    private List<Participant> participants;
}
