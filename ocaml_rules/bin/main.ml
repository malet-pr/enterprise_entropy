open Ocaml_rules
open Engine
open Rules.Daily
open Rules.Planning
open Rules.Debug
open Integration.Formatters.Printer
open Integration.Runners
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
  let result = run_from_file "test_input.json" all_rules in
  print_endline(Yojson.Safe.pretty_to_string (run_result_to_json result));


(*   try
    let json = Yojson.Safe.from_file "test_input.json" in
    let input = input_of_yojson json in
    let state = {
      meeting = input.meeting_input;
      participants = input.participants_input;
      issue = input.issue_input;
      fired_rules = [];
    } in
    let final_state = run_multiple_rules state all_rules in
    print_endline(Yojson.Safe.pretty_to_string (run_success_to_yojson input.run_id final_state))
  with
  | Failure msg -> print_endline ("Failure: " ^ msg)
  | Yojson.Json_error msg -> print_endline ("JSON error: " ^ msg)
  | Yojson.Safe.Util.Type_error (msg, _) -> print_endline ("Type error: " ^ msg) *)

