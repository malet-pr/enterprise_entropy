open Model

(*************************** TO_STRING *******************************)
let string_of_risk = function
  | WillBreakProduction -> "WillBreakProduction"
  | UsersWillRaiseHell -> "UsersWillRaiseHell"
  | AuditorsWillNotBeHappy -> "AuditorsWillNotBeHappy"

let string_of_stage = function
  | Open -> "Open"
  | Ignored -> "Ignored"
  | Discarded -> "Discarded"
  | Deferred -> "Deferred"
  | MovedToAnotherMeeting -> "MovedToAnotherMeeting"

let string_of_status s = 
  match s.risk with
  | Some x -> "stage: " ^ string_of_stage s.stage ^ ", risk: " ^ string_of_risk x
  | None -> "stage: " ^ string_of_stage s.stage
  
let string_of_drift = function
  | Focused -> "Focused"
  | ToTheHillsOfUbeda -> "ToTheHillsOfUbeda"
  | ToHell -> "ToHell"

let string_of_fired_rules rules =
  match rules with
  | [] -> "None"
  | _ -> String.concat ", " rules


let string_of_risk = function
  | WillBreakProduction -> "WillBreakProduction"
  | UsersWillRaiseHell -> "UsersWillRaiseHell"
  | AuditorsWillNotBeHappy -> "AuditorsWillNotBeHappy"

let string_of_stage = function
  | Open -> "Open"
  | Ignored -> "Ignored"
  | Discarded -> "Discarded"
  | Deferred -> "Deferred"
  | MovedToAnotherMeeting -> "MovedToAnotherMeeting"

let string_of_status s = 
  match s.risk with
  | Some x -> "stage: " ^ string_of_stage s.stage ^ ", risk: " ^ string_of_risk x
  | None -> "stage: " ^ string_of_stage s.stage
  
let string_of_drift = function
  | Focused -> "Focused"
  | ToTheHillsOfUbeda -> "ToTheHillsOfUbeda"
  | ToHell -> "ToHell"

let string_of_fired_rules rules =
  match rules with
  | [] -> "None"
  | _ -> String.concat ", " rules

let string_of_rule (r: rule_candidate): string = r.rule_name

let string_of_environment e =
  match e with
  | Development -> "Development"
  | Testing -> "Testing"
  | UAT -> "UAT"

let string_of_environment_option = function
  | None -> "not specified"
  | Some x -> 
      match x with
      | Development -> "Development"
      | Testing -> "Testing"
      | UAT -> "UAT"

let string_of_meeting_type mt = 
  match mt with
  | Daily -> "Daily"
  | Planning -> "Planning"
  | CollectiveDebuggingInEnvironment e -> "Collective Debugging in " ^ string_of_environment e     

let string_of_meeting (m: meeting): string =
  "meeting type = " ^ string_of_meeting_type m.meeting_type 
  ^ ", duration in min = " ^ string_of_int m.duration_min
  ^ ", deep dive = " ^ string_of_bool m.deep_dive
  ^ ", drift = " ^ string_of_drift m.drift
  ^ ", environment = " ^ string_of_environment_option m.environment

let string_of_issue_priority = function
  | Insignificant -> "Insignificant"
  | Low -> "Low"
  | Medium -> "Medium"
  | High -> "High"
  | Critical -> "Critical"

let string_of_understanding = function
  | Functional -> "Functional"
  | Testing -> "Testing"
  | Technical -> "Technical" 

let string_of_understanding_list ulist =
  (List.map (fun u -> string_of_understanding u) ulist)

let string_of_issue (i:issue):string =
  "{ priority = " ^ string_of_issue_priority i.priority 
  ^ ", status = " ^ string_of_status i.status
  ^ ", understood_by = " ^ String.concat ", "(string_of_understanding_list i.understood_by) ^ " }"

let string_of_simulation_state (s: simulation_state) = string_of_meeting s.meeting




(****************************** FROM_STRING ***********************************)

let meeting_type_of_string = function
  | "Daily" -> Daily 
  | "Planning" -> Planning
  | "CollectiveDebuggingInEnvironment" -> failwith "Use environment to construct this meeting type"
  | m -> failwith ("Unknown meeting_type: " ^ m)

let meeting_drift_of_string = function
  | "Focused" -> Focused
  | "ToTheHillsOfUbeda" -> ToTheHillsOfUbeda
  | "ToHell" -> ToHell 
  | d -> failwith ("Unknown meeting_drift: " ^ d) 

let environment_of_string = function
  | "Development" -> Some Development
  | "Testing" -> Some Testing
  | "UAT" -> Some UAT 
  | _ -> None
  
let role_of_string = function
  | "SM" -> SM
  | "PO" -> PO
  | "TechLead" -> TechLead
  | "ActingLead" -> ActingLead
  | "Developer" -> Developer
  | "Tester" -> Tester
  | "Analyst" -> Analyst
  | "DataEngineer" -> DataEngineer
  | "Operations" -> Operations  
  | r -> failwith ("Unknown role: " ^ r) 

let issue_priority_of_string = function
  | "Insignificant" -> Insignificant
  | "Low" -> Low
  | "Medium" -> Medium
  | "High" -> High
  | "Critical" -> Critical
  | p -> failwith ("Unknown priority: " ^ p) 
  
let understanding_of_string = function
  | "Functional" -> Functional
  | "Testing" -> Testing
  | "Technical" -> Technical  
  | u -> failwith ("Unknown understanding: " ^ u)   

let stage_of_string = function
  | "Open" -> Open
  | "Ignored" -> Ignored
  | "Discarded" -> Discarded
  | "Deferred" -> Deferred
  | "MovedToAnotherMeeting" -> MovedToAnotherMeeting
  | s -> failwith ("Unknown stage: " ^ s)     

let risk_of_string = function
  | "WillBreakProduction" -> Some WillBreakProduction
  | "UsersWillRaiseHell" -> Some UsersWillRaiseHell
  | "AuditorsWillNotBeHappy" -> Some AuditorsWillNotBeHappy
  | _ -> None


