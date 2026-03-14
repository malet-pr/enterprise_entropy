open Ocaml_rules
open Domain
open Rules
open Utils
open Simulation

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

  let (m, i) = simulate meeting participants issue in
  Printf.printf "Meeting duration: %d\n" m.duration_min;
  Printf.printf "Deep dive: %b\n" m.deep_dive;
  Printf.printf "Drift: %s\n" (string_of_drift m.drift);
  Printf.printf "Issue status: %s\n" (string_of_issue_status i.status);
