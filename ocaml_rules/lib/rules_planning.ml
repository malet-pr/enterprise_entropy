open Model

(*
Rule P1 — Ignored Issue
  Condition:
    Planning meeting
    Medium severity issue
    SM not interested 
  Effect:
    issue is ignored
*)
let issue_is_ignored_conditions =
  And [
    Atom (Meeting (MeetingTypeIs Planning));
    Atom (Issue (IssuePriorityIs Medium));
    Atom (Participants (NoInterestedParticipantWithRole [SM]));
  ]
let issue_is_ignored_actions =
  [IssueAction ([SetIssueStage Ignored;SetIssueRisk (Some UsersWillRaiseHell)])]

let issue_is_ignored = {
  rule_name = "issue_is_ignored";
  conditions = issue_is_ignored_conditions;
  actions = issue_is_ignored_actions;
}  

