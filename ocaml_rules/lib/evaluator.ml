open Model
open Conditions


let evaluate_meeting_condition (s:simulation_state)(mc:meeting_condition): bool = 
  let m = s.meeting in
  match mc with 
  | MeetingTypeIs c -> isMeetingTypeIs m c
  | DurationGreaterThan c -> isDurationGreaterThan m c
  | MeetingDriftIs _ | DeepDiveIs _ | EnvironmentIs _ | DurationAtLeast _ | DurationAtMost _ -> false

let evaluate_issue_condition (s:simulation_state)(ic:issue_condition): bool =
  let i = s.issue in
  match ic with
  | IssuePriorityIs c -> isIssuePriorityIs i c
  | IssuePriorityIn c -> isIssuePriorityIn i c
  | IssueUnderstoodOnlyBy c -> isIssueUnderstoodOnlyBy i c
  | IssueUnderstoodByList c -> isIssueUnderstoodByList i c
  | IssueStageIs _ | IssueRiskIs _ -> false

let evaluate_participant_condition (s:simulation_state)(pc:participant_condition): bool =
  let ps = s.participants in
  match pc with
  | ParticipantCountAtLeast _ -> false
  | ParticipantCountAtMost _ -> false
  | UnderstandsCountAtLeast _ -> false
  | UnderstandsCountAtMost _ -> false
  | ExistsParticipantWithRole _ -> false
  | ExistsInterestedParticipantWithRole rl -> isExistsInterestedParticipantWithRole ps rl
  | NoInterestedParticipantWithRole rl -> isNoInterestedParticipantWithRole ps rl
  | ExistsUnderstandingParticipantWithRole _ -> false
  | AllParticipantsUnderstand -> false
  | NoParticipantUnderstands -> false

let evaluate_predicate s p =
  match p with
  | Meeting mc-> evaluate_meeting_condition s mc
  | Issue ic -> evaluate_issue_condition s ic
  | Participants pc -> evaluate_participant_condition s pc

let rec evaluate_condition_expr s = function
  | Atom x -> evaluate_predicate s x
  | And xt -> List.for_all (evaluate_condition_expr s) xt
  | Or xt -> List.exists (evaluate_condition_expr s) xt

let apply_actions state actions = state

let run_rule  (state : simulation_state)  (rule : rule_candidate): simulation_state  = 
  print_endline("conditions met? " ^ string_of_bool (evaluate_condition_expr state rule.conditions));
  if evaluate_condition_expr state rule.conditions 
    then
      let new_state = apply_actions state rule.actions in
      {new_state with fired_rules = "test" :: new_state.fired_rules}
  else  
    state


