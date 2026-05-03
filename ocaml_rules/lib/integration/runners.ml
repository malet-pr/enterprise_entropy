open Engine.Model
open Engine.Evaluator
open Engine.Strings
open Types
open Parsers
open Yojson.Safe
open Yojson.Safe.Util

(************** INPUT - OUTPUT ***************)

let input_of_yojson json =
  let run_id = json |> member "run_id" |> to_string in
  let meeting_input = json |> member "meeting_input" |> meeting_of_yojson in
  let issue_input = json |> member "issue_input" |> issue_of_yojson in
  let participants_input = json |> member "participants_input" |> to_list |> List.map (fun j ->
      j |> participant_of_yojson)in
  {
    run_id;
    meeting_input;
    issue_input;
    participants_input;
  }

let state_of_input (input: single_run) : simulation_state ={
  meeting = input.meeting_input;
  participants = input.participants_input;
  issue = input.issue_input;
  fired_rules = [];
}    

let run_success_to_yojson run_id (fs : simulation_state) =
  `Assoc [
    ("run_id", `String run_id);
    ("status", `String "success");
    ("final_meeting_state", `String (string_of_meeting fs.meeting));
    ("final_issue_state", `String (string_of_issue fs.issue));
    ("fired_rules", `List (List.map (fun r -> `String r) fs.fired_rules));
  ]

let input_error_to_yojson (e:input_error) =
  `Assoc [
    ("run_id", `String e.run_id);
    ("status", `String "error");
    ("message", `String e.message);
  ]

let run_result_to_json = function
  | Success success -> (run_success_to_yojson success.run_id success.final_state)
  | InputError error ->  (input_error_to_yojson error)


(************** FULL RUNNER ***************)  

let run_from_file filename rules =
  try
    let json = Yojson.Safe.from_file filename in
    let run_id = run_id_or_unknown json in
    try
      let input = input_of_yojson json in
      let state = state_of_input input in
      let final_state = run_multiple_rules state rules in
      Success {
        run_id;
        final_state;
      }
    with
    | Failure msg ->
        InputError { run_id; message = msg }
    | Yojson.Safe.Util.Type_error (msg, _) ->
        InputError { run_id; message = "Type error: " ^ msg }
  with
  | Yojson.Json_error msg ->
      InputError { run_id = "unknown"; message = "JSON error: " ^ msg }
  | Sys_error msg ->
      InputError { run_id = "unknown"; message = "File error: " ^ msg } 

let run_from_json json rules =
  let run_id = run_id_or_unknown json in
  try
    let input = input_of_yojson json in
    let state = state_of_input input in
    let final_state = run_multiple_rules state rules in
    Success {
      run_id;
      final_state;
    }
  with
  | Failure msg ->
      InputError { run_id; message = msg }
  | Yojson.Safe.Util.Type_error (msg, _) ->
      InputError { run_id; message = "Type error: " ^ msg }

