open Engine.Model
open Engine.Strings
open Types
open Yojson.Safe
open Yojson.Safe.Util

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