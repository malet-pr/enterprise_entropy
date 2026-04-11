open Ocaml_rules
open Model
open Rules_daily
open Rules_planning
open Rules_debug
open Evaluator
open Printer
open Integration
open Yojson.Safe
open Yojson.Safe.Util


let all_rules = [
  curiosity_spiral;
  important_issue_not_understood;
  insignificant_issue_consumes_time;
  move_issue_to_another_meeting;
  collective_debugging_swarm;
  issue_is_discarded;
  risk_will_break_production;
  issue_is_ignored;
]  


let () =
  try
    let json = Yojson.Safe.from_file "test_input.json" in
    let input = input_of_yojson json in
    print_endline ("Parsed run_id: " ^ input.run_id);
    let state = {
      meeting = input.meeting_input;
      participants = input.participants_input;
      issue = input.issue_input;
      fired_rules = [];
    } in
    let result = run_multiple_rules state all_rules in
    print_final_state result
  with
  | Failure msg -> print_endline ("Failure: " ^ msg)
  | Yojson.Json_error msg -> print_endline ("JSON error: " ^ msg)
  | Yojson.Safe.Util.Type_error (msg, _) -> print_endline ("Type error: " ^ msg)


(* let () = 
  let meeting1 = make_meeting Daily 15 in 
  let meeting2 = make_meeting (CollectiveDebuggingInEnvironment Testing) 45 in
  let participants = [ 
    {(make_participant SM) with interested = false}; 
    {(make_participant ActingLead) with interested = true; understands = true}; 
    {(make_participant Developer) with understands = true}; 
    (make_participant Developer); 
    (make_participant Developer); 
    {(make_participant DataEngineer) with interested = true; understands = true}; 
  ] in 
  let issue1 = {(make_issue High) with understood_by = [Technical] } in 
  let issue2 = (make_issue Medium) in 
  let state1 = { meeting = meeting1; participants = participants; issue = issue1; fired_rules = [] ; } in 
  let state2 = { meeting = meeting2; participants = participants; issue = issue2; fired_rules = [] ; } in 
  let final_state1 = run_multiple_rules state1 all_rules in 
  print_final_state(final_state1);
  let final_state2 = run_multiple_rules state2 all_rules in 
  print_final_state(final_state2);
 *)





   