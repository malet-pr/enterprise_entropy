open Engine.Model

(*
Rule 1 — Curiosity Spiral
  Condition:
    Daily meeting
    High or Critical issue
    SM or DataEngineer interested
  Effect:
    meeting extended by 30 min.
    deep dive: true
    drift: ToTheHillsOfUbeda
*)
let curiosity_spiral_conditions = 
  And [
    Atom (Meeting (MeetingTypeIs Daily));
    Atom (Issue (IssuePriorityIn [High; Critical]));
    Atom (Participants (ExistsInterestedParticipantWithRole [SM; DataEngineer]));
  ]
let curiosity_spiral_actions = 
  [MeetingAction ([ExtendMeetingBy 30; SetMeetingDrift ToTheHillsOfUbeda; SetDeepDive true ])]
let curiosity_spiral = {
  rule_name = "curiosity_spiral";
  conditions = curiosity_spiral_conditions;
  actions = curiosity_spiral_actions;
}


(*
Rule 2 — Important Issue Not Understood
  Condition:
    Issue requires Technical understanding
    Nobody in the meeting understands it
  Effect:
    issue status: Ignored
*)
let important_issue_not_understood_conditions =
  And [
    Atom (Issue (IssueUnderstoodByList [Technical]));
    Atom (Participants (NoParticipantUnderstands));
  ]
let important_issue_not_understood_actions =
  [IssueAction ([SetIssueStage Ignored])]

let important_issue_not_understood = {
  rule_name = "important_issue_not_understood";
  conditions = important_issue_not_understood_conditions;
  actions = important_issue_not_understood_actions;
}  

(*
Rule 3 - Insignificat issue consumes time
  Condition: 
    Daily meeting
    Issue priority Insignificant
  Effect:
    meeting extended by 10 min.
    drift: ToHell  
*)
let insignificant_issue_consumes_time_conditions =
  And [
    Atom (Issue (IssuePriorityIs Insignificant));
    Atom (Meeting (MeetingTypeIs Daily));
  ]
let insignificant_issue_consumes_time_actions =
  [MeetingAction ([ExtendMeetingBy 10; SetMeetingDrift ToHell ])]
let insignificant_issue_consumes_time = {
  rule_name = "insignificant_issue_consumes_time";
  conditions = insignificant_issue_consumes_time_conditions;
  actions = insignificant_issue_consumes_time_actions;
}  

(*
Rule 4 - Move issue to another meeting
  Condition
    meeting is Daily
    issue priority is Medium or High
    issue understood by Technical only
    and:
      SM not interested
      or
      ActingLead interested and meeting lenght > 30 min
  Effect
    issue status: MovedToAnotherMeeting
*)
let move_issue_to_another_meeting_conditions = And [
  Atom (Meeting (MeetingTypeIs Daily)); 
  Atom (Issue (IssuePriorityIn [Medium;High]));
  Atom (Issue (IssueUnderstoodOnlyBy Technical));
  Or [
    Atom (Participants (NoInterestedParticipantWithRole [SM]));
    And [
      Atom (Participants (ExistsInterestedParticipantWithRole [ActingLead]));
      Atom (Meeting (DurationGreaterThan 30))
    ]
  ]
]
let move_issue_to_another_meeting_actions = [IssueAction [SetIssueStage MovedToAnotherMeeting]]
let move_issue_to_another_meeting = {
  rule_name = "move_issue_to_another_meeting";
  conditions = move_issue_to_another_meeting_conditions;
  actions = move_issue_to_another_meeting_actions;
}


(*
Rule 5 - Collective Debugging Swarm
  Condition:
    meeting is Daily
    issue priority is High OR Critical
    issue is understood only by Technical
    at least TWO participants understand the issue
    AND
    at least one of them is Developer OR DataEngineer
  Effect:
    meeting extended by 20 min.
    deep dive: true
    drift: Focused
    status remains Open
*)
let collective_debugging_swarm_conditions = And [
  Atom (Meeting (MeetingTypeIs Daily)); 
  Atom (Issue (IssuePriorityIn [Critical;High]));
  Atom (Issue (IssueUnderstoodOnlyBy Technical));
  Atom (Participants (UnderstandsCountAtLeast 2));
  Atom (Participants (ExistsUnderstandingParticipantWithRole [Developer;DataEngineer]))
]
let collective_debugging_swarm_actions = [
  IssueAction ([SetIssueStage Open]);
  MeetingAction ([ExtendMeetingBy 20; SetMeetingDrift Focused; SetDeepDive true ])
]
let collective_debugging_swarm = {
  rule_name = "collective_debugging_swarm";
  conditions = collective_debugging_swarm_conditions;
  actions = collective_debugging_swarm_actions;
}  

