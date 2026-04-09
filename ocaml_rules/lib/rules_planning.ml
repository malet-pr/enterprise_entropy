open Model

(*
Rule P1 — Discarded Issue
  Condition:
    Planning meeting
    Medium severity issue
    SM not interested 
  Effect:
    issue is discarded
*)
let issue_is_discarded_conditions =
  And [
    Atom (Meeting (MeetingTypeIs Planning));
    Atom (Issue (IssuePriorityIs Medium));
    Atom (Participants (NoInterestedParticipantWithRole [SM]));
  ]
let issue_is_discarded_actions =
  [IssueAction ([SetIssueStage Discarded;SetIssueRisk (Some AuditorsWillNotBeHappy)])]

let issue_is_discarded = {
  rule_name = "issue_is_discarded";
  conditions = issue_is_discarded_conditions;
  actions = issue_is_discarded_actions;
}  

