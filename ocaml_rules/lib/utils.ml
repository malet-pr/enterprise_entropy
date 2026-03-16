open Model

let mark_fired (name: string)(state: simulation_state) : simulation_state =
  { state with fired_rules = name :: state.fired_rules }

let participant_has_role role participant =
  participant.role = role

let participants_with_role role participants =
  List.filter (participant_has_role role) participants  

let exists_interested_role roles participants =
  List.exists
    (fun p -> List.mem p.role roles && p.interested)
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

let role_is_curious p =
  match p.role with
  | SM | DataEngineer -> p.interested
  | _ -> false 


let meeting_is mt meeting = meeting.meeting_type = mt    

let count_participants_that_understand_the_issue (participants: participant list) : int = 
  List.fold_left (fun acc p -> 
    let x = Bool.to_int ((p.role = Developer || p.role = DataEngineer) && p.understands) in
    acc + x)  0 participants


  
(*************************** STRINGS *******************************)

let string_of_drift = function
  | Focused -> "Focused"
  | ToTheHillsOfUbeda -> "ToTheHillsOfUbeda"
  | ToHell -> "ToHell"

let string_of_issue_status = function
  | Open -> "Open"
  | Deferred -> "Deferred"
  | Ignored -> "Ignored"
  | MovedToAnotherMeeting -> "MovedToAnotherMeeting"
  | Resolved -> "Resolved"
  | Discarded -> "Discarded"
  | WillBreakProduction -> "WillBreakProduction"    

let string_of_fired_rules rules =
  match rules with
  | [] -> "None"
  | _ -> String.concat ", " rules
