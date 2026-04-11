open Model
open Utils


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


