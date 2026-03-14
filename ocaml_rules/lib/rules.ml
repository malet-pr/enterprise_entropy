open Domain
open Utils


let is_high_priority issue =
  match issue.priority with
  | High | Critical -> true
  | _ -> false

let role_is_curious p =
  match p.role with
  | SM | DBA -> p.interested
  | _ -> false 

(*
Rule 1 — Curiosity Spiral
  Condition:
    Daily meeting
    High or Critical issue
    SM or DBA interested
  Effect:
    duration +30
    deep_dive = true
    drift = ToTheHillsOfUbeda
*)
let curiosity_spiral meeting participants issue =
  if meeting.meeting_type = Daily
    && is_high_priority issue
    && exists_interested_role [SM; DBA] participants
  then
    { meeting with
      duration_min = meeting.duration_min + 30;
      deep_dive = true;
      drift = ToTheHillsOfUbeda }
  else
    meeting

(*
Rule 2 — Important Issue Not Understood
  Condition:
    Issue requires Technical understanding
    Nobody in the meeting understands it
  Effect:
    issue.status = Ignored
*)
let important_issue_not_understood participants issue =
  if List.mem Technical issue.understood_by then
    let someone_understands =
      List.exists (fun p -> p.understands) participants
    in
    if not someone_understands then
      { issue with status = Ignored }
    else
      issue
  else
    issue


