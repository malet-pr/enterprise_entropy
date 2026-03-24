open Model

let mark_fired (name: string)(state: simulation_state) : simulation_state =
  { state with fired_rules = name :: state.fired_rules }  

let participant_has_role role participant =
  participant.role = role

let participants_with_role role participants =
  List.filter (participant_has_role role) participants  

let participants_with_role_count role participants =
  participants |> participants_with_role Developer |> List.length

let exists_interested_role roles participants =
  List.exists
    (fun p -> List.mem p.role roles && p.interested)
    participants

let roles_not_interested roles participants =
  List.exists
    (fun p -> List.mem p.role roles && p.interested = false)
    participants

let issue_is_understandable_by domain issue =
  List.mem domain issue.understood_by

let exists_understanding_participant roles participants =
  List.exists
    (fun p ->
      List.mem p.role roles
      && p.understands
    )
    participants

let is_high_priority issue =
  match issue.priority with
  | High | Critical -> true
  | _ -> false

let is_medium_priority issue = 
  issue.priority = Medium

let role_is_curious p =
  match p.role with
  | SM | DataEngineer -> p.interested
  | _ -> false 


let meeting_is mt meeting = meeting.meeting_type = mt    

let meeting_is_collective_debug_in_environment meeting environment = 
  meeting_is CollectiveDebuggingInEnvironment meeting && meeting.environment = Some environment

let count_participants_that_understand_the_issue (participants: participant list) : int = 
  List.fold_left (fun acc p -> 
    let x = Bool.to_int ((p.role = Developer || p.role = DataEngineer) && p.understands) in
    acc + x)  0 participants

 
(* let daily_priority_technical_understand_by_two (s : simulation_state): bool = 
  let part = count_participants_that_understand_the_issue s.participants >= 2 in
  let und = issue_is_understandable_by Technical s.issue in
  match s.meeting.meeting_type with
  | Retro | Planning | CollectiveDebuggingInEnvironment -> false 
  | Daily -> 
      match s.issue.priority with 
        | Insignificant | Low | Medium -> false
        | High | Critical ->
          match und with
            | false -> false
            | true ->
              match part with
                | false -> false
                | true -> true *)

let daily_priority_technical_understand_by_two (s : simulation_state): bool = 
  let part = count_participants_that_understand_the_issue s.participants >= 2 in
  let und = issue_is_understandable_by Technical s.issue in
  match s.meeting.meeting_type, s.issue.priority, part, und with
  | Daily, (High | Critical), true, true -> true
  | _ -> false
               
let is_daily_from_state (state: simulation_state) : bool =
  state.meeting.meeting_type = Daily

let is_high_priority_from_state (state: simulation_state) : bool =
  state.issue.priority = High

let is_daily_and_high_priority (state: simulation_state) : bool  =
  is_daily_from_state state && is_high_priority_from_state state
  

(*************************** STRINGS *******************************)

let string_of_risk = function
  | WillBreakProduction -> "WillBreakProduction"
  | UsersWillRaiseHell -> "UsersWillRaiseHell"
  | AuditorsWillNotBeHappy -> "AuditorsWillNotBeHappy"

let string_of_drift = function
  | Focused -> "Focused"
  | ToTheHillsOfUbeda -> "ToTheHillsOfUbeda"
  | ToHell -> "ToHell"

let string_of_issue_status = function
  | Open -> "Open"
  | Deferred -> "Deferred"
  | Ignored -> "Ignored"
  | MovedToAnotherMeeting -> "MovedToAnotherMeeting"
  | Discarded -> "Discarded"
  | RiskFlagged a -> "RiskFlagged - " ^ string_of_risk a

let string_of_fired_rules rules =
  match rules with
  | [] -> "None"
  | _ -> String.concat ", " rules

