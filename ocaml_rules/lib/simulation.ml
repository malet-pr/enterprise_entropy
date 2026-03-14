open Model
open Rules
open Utils


let simulate (rules : rule list) meeting participants issue : simulation_result =
  let initial_state : simulation_state = {
    meeting;
    participants;
    issue;
    fired_rules = [];
  } in
  let final_state =
    List.fold_left
      (fun state rule -> rule state)
      initial_state
      rules
  in
  let result : simulation_result = {
    meeting = final_state.meeting;
    issue = final_state.issue;
    fired_rules = List.rev final_state.fired_rules;
  } in
  result
