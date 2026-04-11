open Model

(*************************** STRINGS *******************************)

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


(*****************************************************************)

let print_meeting_duration meeting =
  Printf.printf "Meeting duration: %d\n" meeting.duration_min

let print_meeting_deep_dive meeting =
  Printf.printf "Deep dive: %b\n" meeting.deep_dive

let print_meeting_drift meeting =
  Printf.printf "Drift: %s\n" (string_of_drift meeting.drift)

let print_issue_status status =
  Printf.printf "Issue status: %s\n" (string_of_status status)

let print_fired_rules fired_rules =
  Printf.printf "Fired rules: %s\n" (string_of_fired_rules fired_rules)

let print_final_state (result: simulation_state) : unit =
  print_meeting_duration result.meeting;
  print_meeting_deep_dive result.meeting;
  print_meeting_drift result.meeting;
  print_issue_status result.issue.status;
  print_fired_rules result.fired_rules;

(* let print_result result =
  print_meeting_duration result.meeting;
  print_meeting_deep_dive result.meeting;
  print_meeting_drift result.meeting;
  print_issue_status result.issue.status;
  print_fired_rules result.fired_rules; *)


