open Model
open Utils


(*
Rule D1 — Flag risk will break production
  Condition:
    Collective Debug in UAT
    Medium severity issue
    Issue is discarded
    At least 3 developers present
  Effect:
    risk_will_break_production
*)
let risk_will_break_production (state : simulation_state) : simulation_state =
  if meeting_is_collective_debug_in_environment state.meeting UAT
     && is_medium_priority state.issue
     && state.issue.status = Discarded
     && participants_with_role_count Developer state.participants >= 3
    then
    let updated_state =
      {
        state with
        issue = {
          state.issue with
          status = RiskFlagged WillBreakProduction;
        }
      }
    in
    mark_fired "risk_will_break_production" updated_state;
  else
    state
  


  