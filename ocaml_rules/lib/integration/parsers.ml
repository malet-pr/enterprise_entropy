open Engine.Model
open Engine.Strings
open Types
open Yojson.Safe
open Yojson.Safe.Util

let environment_of_yojson (json: Yojson.Safe.t):environment option =
  match json with
  | `Null -> None
  | `String "Development" -> Some Development
  | `String "Testing" -> Some Testing
  | `String "UAT" -> Some UAT
  | `String s -> failwith ("Unknown environment: " ^ s)
  | _ -> failwith "environment must be string or null"

let risk_of_yojson (json: Yojson.Safe.t):risk option =
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

(****************************************************************************************)  

let action_list_of_json json =
  let outer_actions = json |> member "actions" |> to_list in
  match outer_actions with
  | [
      `Assoc [
        ("type", `String "IssueAction");
        ("actions", `List [
          `Assoc [
            ("type", `String "SetIssueStage");
            ("value", `String "Ignored")
          ]
        ])
      ]
    ] ->
      [IssueAction [SetIssueStage Ignored]]
  | _ ->
      []

let issue_action_of_json json =
  let issue_key = json |> member "type" |> to_string in
  let value = json |> member "value" |> to_string in
  match issue_key with
  | "SetIssueStage" -> Some (SetIssueStage (stage_of_string value))
  | "SetIssueRisk" -> Some (SetIssueRisk (risk_of_string value))
  | _ -> None  

let meeting_action_of_json json =
  let action_key = json |> member "type" |> to_string in
  match action_key with
  | "ExtendMeetingBy" ->
      let value = json |> member "value" |> to_int in
      Some (ExtendMeetingBy value)
  | "SetMeetingDrift" ->
      let value = json |> member "value" |> to_string in
      Some (SetMeetingDrift (meeting_drift_of_string value))
  | "SetDeepDive" ->
      let value = json |> member "value" |> to_bool in
      Some (SetDeepDive value)
  | _ -> None

let top_level_action_of_json json =
  let action_type = json |> member "type" |> to_string in
  match action_type with
  | "IssueAction" ->
      let issue_actions =
        json |> member "actions" |> to_list
      in
      let parsed_issue_actions =
        issue_actions |> List.filter_map issue_action_of_json
      in
      Some (IssueAction parsed_issue_actions)
  | "MeetingAction" ->
      let meeting_actions =
        json |> member "actions" |> to_list
      in
      let parsed_meeting_actions =
        meeting_actions |> List.filter_map meeting_action_of_json
      in
      Some (MeetingAction parsed_meeting_actions)
  | _ -> None

let action_list_prueba json =
  json
  |> member "actions"
  |> to_list
  |> List.filter_map top_level_action_of_json    
       
let condition_expr_of_json json =
  match json with
  | `Assoc fields ->
      begin
        match
          List.assoc_opt "type" fields,
          List.assoc_opt "condition" fields,
          List.assoc_opt "value" fields
        with
        | Some (`String "Participants"),
          Some (`String "ParticipantCountAtLeast"),
          Some (`Int n) ->
              Ok (Atom (Participants (ParticipantCountAtLeast n)))
        | _ ->
              Error "Unsupported condition"
      end
  | _ ->
      Error "Expected condition object"  
    
let rule_of_json json = 
  let rule_name = json |> member "rule_name" |> to_string in
  let actions = json |> action_list_prueba in
  let conditions =
    json
    |> member "conditions" 
    |> condition_expr_of_json
    |> fun c ->
      match c with
      | Ok cond -> cond
      | Error _ -> None
    in    
  {
    rule_name;
    actions;
    conditions;
  }


