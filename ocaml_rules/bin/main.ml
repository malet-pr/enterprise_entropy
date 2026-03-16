open Ocaml_rules
open Model
open Utils 
open Rules_daily
open Printer
open Simulation

let all_rules = [
  curiosity_spiral;
  important_issue_not_understood;
  insignificant_issue_consumes_time;
  move_issue_to_another_meeting;
  collective_debugging_swarm;
]


let () =
  let meeting = make_meeting Daily 15 in
  let participants = [
    (make_participant Developer);
    {(make_participant SM) with interested = true};
] in
  let issue = {(make_issue Critical) with understood_by = [Technical] } in
  let result = simulate all_rules meeting participants issue in
  print_result result;

print_endline("###########################################################")

let () =
  let meeting = make_meeting Daily 15  in
  let participants = [
    {(make_participant SM) with interested = true};
    {(make_participant Developer) with understands = true};
  ] in
  let issue = {(make_issue Insignificant) with understood_by = [Technical;Testing] } in
  let result = simulate all_rules meeting participants issue in
  print_result result;

  print_endline("###########################################################")

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
  
  
