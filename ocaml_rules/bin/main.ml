open Ocaml_rules
open Model
open Utils 
open Rules_daily
open Printer
open Simulation
open Rules_planning
open Rules_debug

let all_rules = [
  curiosity_spiral;
  move_issue_to_another_meeting; 
]

let () =
  let meeting = make_meeting Daily 15 in
    let participants = [
    {(make_participant SM) with interested = false};
    {(make_participant Developer) with interested = true; understands = true};
    {(make_participant Developer) with understands = true};
    (make_participant Developer);
    (make_participant Developer);
    {(make_participant DataEngineer) with interested = true; understands = true};  
  ] in
  let issue = {(make_issue Medium) with understood_by = [Technical] } in
  let state = {
    meeting = meeting;
    participants = participants;
    issue = issue;
    fired_rules = [] ;
  } in
  let final_state = run_rule state curiosity_spiral in
  print_fired_rules final_state.fired_rules;







(* let () =
  let meeting1 = make_meeting Planning 105 in
  let meeting2 = {(make_meeting CollectiveDebuggingInEnvironment 120) with environment = Some UAT}in
  let participants = [
    {(make_participant SM) with interested = false};
    {(make_participant Developer) with interested = true; understands = true};
    {(make_participant Developer) with understands = true};
    (make_participant Developer);
    (make_participant Developer);
    {(make_participant DataEngineer) with interested = true; understands = true};  
  ] in
  let issue = {(make_issue Medium) with understood_by = [Technical] } in
  let result1 = simulate [auditors_will_not_like_it;issue_is_discarded] meeting1 participants issue in
  print_result result1;
  print_endline("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
  let result2 = simulate [risk_will_break_production] meeting2 participants result1.issue in
  print_result result2; 
  print_endline("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")

let () =
  let meeting = make_meeting Daily 15 in
  let participants = [
    (make_participant Developer);
    {(make_participant SM) with interested = true};
  ] in
  let issue = {(make_issue Critical) with understood_by = [Technical] } in
  let result = simulate all_rules meeting participants issue in
  print_result result;
  print_endline("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")

let () =
  let meeting = make_meeting Daily 15  in
  let participants = [
    {(make_participant SM) with interested = true};
    {(make_participant Developer) with understands = true};
  ] in
  let issue = {(make_issue Insignificant) with understood_by = [Technical;Testing] } in
  let result = simulate all_rules meeting participants issue in
  print_result result;
  print_endline("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")

let () =
  let meeting = make_meeting Daily 15 in
  let participants = [
    {(make_participant SM) with interested = true};
    {(make_participant Developer) with interested = true; understands = true};
    {(make_participant Developer) with understands = true};
    (make_participant Developer);
    (make_participant Developer);
    {(make_participant DataEngineer) with interested = true; understands = true};  
  ] in
  let issue = {(make_issue High) with understood_by = [Technical] } in
  let result = simulate all_rules meeting participants issue in
  print_result result;
  print_endline("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")

let () =
  let meeting = make_meeting Daily 5 in
  let participants = [
    (make_participant SM);
    {(make_participant ActingLead) with interested = true; understands = true};] in
  let issue = {(make_issue High) with understood_by = [Technical] } in
  let result = simulate all_rules meeting participants issue in
  print_result result;
  print_endline("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")

  
let () =
  let meeting = make_meeting Daily 35 in
  let participants = [{(make_participant ActingLead) with interested = true; understands = true};] in
  let issue = {(make_issue High) with understood_by = [Technical] } in
  let result = simulate all_rules meeting participants issue in
  print_result result;

 *)