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


