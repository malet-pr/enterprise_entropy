open Model
open Utils


let is_high_priority issue =
  match issue.priority with
  | High | Critical -> true
  | _ -> false

let role_is_curious p =
  match p.role with
  | SM | DataEngineer -> p.interested
  | _ -> false 

(*
Rule 1 — Curiosity Spiral
  Condition:
    Daily meeting
    High or Critical issue
    SM or DBA interested
  Effect:
    duration +30
    deep_dive = true
    drift = ToTheHillsOfUbeda
*)
let curiosity_spiral (state : simulation_state) : simulation_state =
  if meeting_is Daily state.meeting
     && is_high_priority state.issue
     && exists_interested_role [SM; DataEngineer] state.participants
  then
    {
      state with
      meeting = {
        state.meeting with
        duration_min = state.meeting.duration_min + 30;
        deep_dive = true;
        drift = ToTheHillsOfUbeda;
      };
      fired_rules = "curiosity_spiral" :: state.fired_rules;
    }
  else
    state

(*
Rule 2 — Important Issue Not Understood
  Condition:
    Issue requires Technical understanding
    Nobody in the meeting understands it
  Effect:
    issue.status = Ignored
*)
let important_issue_not_understood (state : simulation_state) : simulation_state =
  if issue_is_understandable_by Technical state.issue then
    if not (exists_understanding_participant [Developer; DataEngineer; TechLead; ActingLead] state.participants)
    then
      {
        state with
        issue = { state.issue with status = Ignored };
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
    meeting duration +10
    drift = ToHell  
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

