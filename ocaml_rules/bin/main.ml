open Ocaml_rules
open Model
open Rules_daily
open Rules_planning
open Rules_debug
open Evaluator
open Printer

let all_rules = [
  curiosity_spiral;
  move_issue_to_another_meeting; 
]

let () =
  let meeting = make_meeting Daily 15 in
    let participants = [
    {(make_participant SM) with interested = true};
    {(make_participant ActingLead) with interested = true; understands = false};
    {(make_participant Developer) with understands = false};
    (make_participant Developer);
    (make_participant Developer);
    {(make_participant DataEngineer) with interested = true; understands = false};  
  ] in
  let issue = {(make_issue High) with understood_by = [Technical] } in
  let state = {
    meeting = meeting;
    participants = participants;
    issue = issue;
    fired_rules = [] ;
  } in
  let final_state = run_rule state important_issue_not_understood in
  print_fired_rules final_state.fired_rules;
  print_endline(string_of_status final_state.issue.status);






   