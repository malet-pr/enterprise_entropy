open Model
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

type execution_error = {
  run_id : string;
  last_state : simulation_state;
  failed_rule : rule_candidate;
  message : string;
}

let run_id_or_unknown json =
  try Yojson.Safe.Util.(json |> member "run_id" |> to_string)
  with _ -> "unknown"

type run_result =
  | Success of run_success
  | InputError of input_error
  | ExecutionError of execution_error

(************** PARSER *************)
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




