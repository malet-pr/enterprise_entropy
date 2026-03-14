open Domain
open Rules
open Utils

let simulate meeting participants issue =
  let meeting_after =
    curiosity_spiral meeting participants issue
  in
  let issue_after =
    important_issue_not_understood participants issue
  in
  (meeting_after, issue_after)
