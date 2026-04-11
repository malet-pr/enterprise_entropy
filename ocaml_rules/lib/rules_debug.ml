open Model

(*
  Rule D1 — Discarded Issue
  Condition:
    Collective Debug in Testing
    Medium severity issue
    SM not interested 
  Effect:
    issue is discarded
*)
let issue_is_discarded_conditions =
  And [
    Atom (Meeting (MeetingTypeIs (CollectiveDebuggingInEnvironment Testing)));
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

(*
Rule D2 — Flag risk will break production
  Condition:
    Collective Debug in Testing
    Medium severity issue
    Issue is discarded
    At least 3 participants
  Effect:
    risk_will_break_production
*)
let risk_will_break_production_conditions = And [
  Atom (Meeting (MeetingTypeIs (CollectiveDebuggingInEnvironment Testing)));
  Atom (Issue (IssuePriorityIs Medium));
  Atom (Issue (IssueStageIs Discarded));
  Atom (Participants (ParticipantCountAtLeast 3))
]
let risk_will_break_production_actions = [IssueAction ([SetIssueRisk (Some WillBreakProduction)])]
let risk_will_break_production = {
  rule_name = "risk_will_break_production";
  conditions = risk_will_break_production_conditions;
  actions = risk_will_break_production_actions;
} 



  