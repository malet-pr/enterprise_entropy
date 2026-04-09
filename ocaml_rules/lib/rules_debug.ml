open Model

(*
Rule D1 — Flag risk will break production
  Condition:
    Collective Debug in UAT
    Medium severity issue
    Issue is discarded
    At least 3 developers present
  Effect:
    risk_will_break_production
*)
let risk_will_break_production_conditions = And [
  Atom (Meeting (MeetingTypeIs (CollectiveDebuggingInEnvironment UAT)));
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



  