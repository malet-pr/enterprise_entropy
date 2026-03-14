open Ocaml_rules
open Model
open Utils 
open Rules
open Simulation

let all_rules = [
  curiosity_spiral;
  important_issue_not_understood;
  insignificant_issue_consumes_time;
  move_issue_to_another_meeting;
  collective_debugging_swarm;
]


let () =
  let meeting = {
    meeting_type = Daily;
    duration_min = 15;
    deep_dive = false;
    drift = Focused;
  } in

   let participants = [
    { role = SM; interested = true; understands = false };
    { role = Developer; interested = false; understands = false };
  ] in

  let issue = {
    priority = Critical;
    status = Open;
    understood_by = [Technical];
  } in

  let result = simulate all_rules meeting participants issue in
  Printf.printf "Meeting duration: %d\n" result.meeting.duration_min;
  Printf.printf "Deep dive: %b\n" result.meeting.deep_dive;
  Printf.printf "Drift: %s\n" (string_of_drift result.meeting.drift);
  Printf.printf "Issue status: %s\n" (string_of_issue_status result.issue.status);
  Printf.printf "Fired rules: %s\n" (string_of_fired_rules result.fired_rules); 

print_endline("###########################################################")

let () =
  let meeting = {
    meeting_type = Daily;
    duration_min = 15;
    deep_dive = false;
    drift = Focused;
  } in

   let participants = [
    { role = SM; interested = true; understands = false };
    { role = Developer; interested = false; understands = true };
  ] in

  let issue = {
    priority = Insignificant;
    status = Open;
    understood_by = [Technical; Testing];
  } in

  let result = simulate all_rules meeting participants issue in
  Printf.printf "Meeting duration: %d\n" result.meeting.duration_min;
  Printf.printf "Deep dive: %b\n" result.meeting.deep_dive;
  Printf.printf "Drift: %s\n" (string_of_drift result.meeting.drift);
  Printf.printf "Issue status: %s\n" (string_of_issue_status result.issue.status);
  Printf.printf "Fired rules: %s\n" (string_of_fired_rules result.fired_rules);

  print_endline("###########################################################")

let () =
  let meeting = {
    meeting_type = Daily;
    duration_min = 15;
    deep_dive = false;
    drift = Focused;
  } in

   let participants = [
    { role = SM; interested = true; understands = false };
    { role = Developer; interested = true; understands = true };
    { role = Developer; interested = false; understands = true };
    { role = Developer; interested = false; understands = false };
    { role = Developer; interested = false; understands = false };
    { role = DataEngineer; interested = true; understands = true };
  ] in
  let issue = {
    priority = High;
    status = Open;
    understood_by = [Technical];
  } in

  let result = simulate all_rules meeting participants issue in
  Printf.printf "Meeting duration: %d\n" result.meeting.duration_min;
  Printf.printf "Deep dive: %b\n" result.meeting.deep_dive;
  Printf.printf "Drift: %s\n" (string_of_drift result.meeting.drift);
  Printf.printf "Issue status: %s\n" (string_of_issue_status result.issue.status);
  Printf.printf "Fired rules: %s\n" (string_of_fired_rules result.fired_rules);
  
  
