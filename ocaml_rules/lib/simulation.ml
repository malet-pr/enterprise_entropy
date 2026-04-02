open Model
open Rules_daily
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

let evaluate_condition_expr state conditions = true

let apply_actions state actions = state

let run_rule  (state : simulation_state)  (rule : rule_candidate): simulation_state  = 
  if evaluate_condition_expr state rule.conditions 
    then
      let new_state = apply_actions state rule.actions in
      {new_state with fired_rules = "test" :: state.fired_rules}
  else  
    state



