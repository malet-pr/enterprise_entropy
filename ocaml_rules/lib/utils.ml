open Domain

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
  | WillBreakProduction -> "WillBreakProduction"    