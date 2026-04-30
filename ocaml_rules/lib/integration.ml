open Model
open Evaluator
open Utils
open Yojson.Safe
open Yojson.Safe.Util


(************** TYPES *************)

type single_run = {
  run_id: string;
  meeting_input: meeting;
  issue_input: issue;
  participants_input: participant list;
}

type run_success = {
  run_id : string;
  final_state : simulation_state;
}

type input_error = {
  run_id : string;
  message : string;
}

let run_id_or_unknown json =
  try Yojson.Safe.Util.(json |> member "run_id" |> to_string)
  with _ -> "unknown"

type run_result =
  | Success of run_success
  | InputError of input_error

(************** PARSERS *************)

let environment_of_yojson json =
  match json with
  | `Null -> None
  | `String "Development" -> Some Development
  | `String "Testing" -> Some Testing
  | `String "UAT" -> Some UAT
  | `String s -> failwith ("Unknown environment: " ^ s)
  | _ -> failwith "environment must be string or null"

let risk_of_yojson json =
  match json with
  | `Null -> None
  | `String "WillBreakProduction" -> Some WillBreakProduction
  | `String "UsersWillRaiseHell" -> Some UsersWillRaiseHell
  | `String "AuditorsWillNotBeHappy" -> Some AuditorsWillNotBeHappy
  | `String s -> failwith ("Unknown risk: " ^ s)
  | _ -> failwith "risk must be a string or null"

let meeting_of_yojson json = 
  let meeting_type_str = json |> member "meeting_type" |> to_string in
  let environment = json |> member "environment" |> environment_of_yojson in
  let meeting_type =
    match meeting_type_str with
    | "CollectiveDebuggingInEnvironment" ->
        (match environment with
         | Some env -> CollectiveDebuggingInEnvironment env
         | None -> failwith "CollectiveDebuggingInEnvironment requires environment")
    | _ -> meeting_type_of_string meeting_type_str
  in
  {
    meeting_type;
    duration_min = json |> member "duration_min" |> to_int;
    deep_dive = json |> member "deep_dive" |> to_bool;
    drift = json |> member "drift" |> to_string |> meeting_drift_of_string;
    environment = environment;
  }

let participant_of_yojson json = {
  role = json |> member "role" |> to_string |> role_of_string;
  interested = json |> member "interested" |> to_bool;
  understands = json |> member "understands" |> to_bool;
}

let status_of_yojson json = {
  stage = json |> member "stage" |> to_string |> stage_of_string;
  risk = json |> member "risk" |> risk_of_yojson;
}

let issue_of_yojson json = {
  priority = json |> member "priority" |> to_string |> issue_priority_of_string;
  status = json |> member "status" |> status_of_yojson;
  understood_by = json |> member "understood_by" |> to_list |> List.map (fun j ->
      j |> to_string |> understanding_of_string)
}

let simulation_state_to_yojson state =
  `Assoc [
    ("meeting", string_of_meeting state.meeting);
    ("issue", string_of_issue state.issue);
  ]

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

