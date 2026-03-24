open Model
open Utils

(*
Rule P1 — Discarded Issue
  Condition:
    Planning meeting
    Medium severity issue
    SM not interested 
  Effect:
    issue is discarded
*)
let issue_is_discarded (state : simulation_state) : simulation_state =
  if meeting_is Planning state.meeting
     && is_medium_priority state.issue
     && not (exists_interested_role [SM] state.participants)
  then
    let updated_state =
      {
        state with
        issue = {
          state.issue with
          status = Discarded
        };
      } 
    in
    mark_fired "issue_is_discarded" updated_state;
  else
    state

(*
Rule P2 — Auditors will not like it
  Condition:
    Planning meeting
    Medium severity issue
    SM not interested 
  Effect:
    auditors_will_not_like_it
*)
let auditors_will_not_like_it (state : simulation_state) : simulation_state =
  if meeting_is Planning state.meeting
     && is_medium_priority state.issue
     && not (exists_interested_role [SM] state.participants)
  then
    let updated_state =
      {
        state with
        issue = {
          state.issue with
          status = RiskFlagged AuditorsWillNotBeHappy
        };
      } 
    in
    mark_fired "auditors_will_not_like_it" updated_state;
  else
    state    