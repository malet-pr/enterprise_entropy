open Model
open Utils

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
let important_issue_not_understood (state : simulation_state) : simulation_state =
  if issue_is_understandable_by Technical state.issue then
    if not (exists_understanding_participant [Developer; DataEngineer; TechLead; ActingLead] state.participants)
    then
      {
        state with
        issue = {
          state.issue with
          status = {stage=Ignored;risk=state.issue.status.risk}
        };
        fired_rules = "important_issue_not_understood" :: state.fired_rules;
      }
    else
      state
  else
    state 

(*
Rule 3 - Insignificat issue consumes time
  Condition: 
    Daily meeting
    Issue priority Insignificant
  Effect:
    meeting extended by 10 min.
    drift: ToHell  
*)
let insignificant_issue_consumes_time (state : simulation_state) : simulation_state =
  if meeting_is Daily state.meeting
    && state.issue.priority = Insignificant
  then
    {
      state with
      meeting = {
        state.meeting with
        duration_min = state.meeting.duration_min + 10;
        deep_dive = false;
        drift = ToHell;
      };
      fired_rules = "insignificant_issue_consumes_time" :: state.fired_rules;
    }
  else
    state

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
let collective_debugging_swarm (state : simulation_state) : simulation_state =
  if meeting_is Daily state.meeting
    && (state.issue.priority = High || state.issue.priority = Critical)
    && issue_is_understandable_by Technical state.issue
    && count_participants_that_understand_the_issue state.participants >= 2
  then
    {
      state with
      issue = {
        state.issue with
        status = {stage=Open;risk= state.issue.status.risk};
      };
      meeting = {
        state.meeting with
        duration_min = state.meeting.duration_min + 20;
        deep_dive = true;
        drift = Focused;
      };
      fired_rules = "collective_debugging_swarm" :: state.fired_rules;
    }
  else  
    state

let collective_debugging_swarm_no_if (state : simulation_state) : simulation_state =
  let cond = daily_priority_technical_understand_by_two state in
  match cond with
    | false -> state
    | true ->
              {
                state with
                issue = {
                  state.issue with
                  status = {stage=Open;risk= state.issue.status.risk};
                };
                meeting = {
                  state.meeting with
                  duration_min = state.meeting.duration_min + 20;
                  deep_dive = true;
                  drift = Focused;
                };
                fired_rules = "collective_debugging_swarm_no_if" :: state.fired_rules;
              }
