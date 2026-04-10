open Ocaml_rules
open Model
open Rules_daily
open Rules_planning
open Rules_debug
open Evaluator
open Printer


let all_rules = [
  curiosity_spiral;
  move_issue_to_another_meeting;
  collective_debugging_swarm;
]  

let () = 
  let meeting = make_meeting Daily 15 in 
  let participants = [ 
    {(make_participant SM) with interested = false}; 
    {(make_participant ActingLead) with interested = true; understands = true}; 
    {(make_participant Developer) with understands = true}; 
    (make_participant Developer); 
    (make_participant Developer); 
    {(make_participant DataEngineer) with interested = true; understands = true}; 
  ] in 
  let issue = {(make_issue High) with understood_by = [Technical] } in 
  let state = { meeting = meeting; participants = participants; issue = issue; fired_rules = [] ; } in 
  let final_state = run_rule state collective_debugging_swarm in 
  print_final_state(final_state)






   