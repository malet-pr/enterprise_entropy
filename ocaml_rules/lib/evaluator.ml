open Model
open Conditions
open Actions
open Printer

let evaluate_meeting_condition (s:simulation_state)(mc:meeting_condition): bool = 
  let m = s.meeting in
  match mc with 
  | MeetingTypeIs c -> isMeetingTypeIs m c
  | DurationGreaterThan c -> isDurationGreaterThan m c
  | MeetingDriftIs c -> isMeetingDriftIs m c
  | DeepDiveIs c -> isDeepDiveIs m c
  | DurationAtLeast c -> isDurationAtLeast m c
  | DurationAtMost c -> isDurationAtMost m c

let evaluate_issue_condition (s:simulation_state)(ic:issue_condition): bool =
  let i = s.issue in
  match ic with
  | IssuePriorityIs c -> isIssuePriorityIs i c
  | IssuePriorityIn c -> isIssuePriorityIn i c
  | IssueUnderstoodOnlyBy c -> isIssueUnderstoodOnlyBy i c
  | IssueUnderstoodByList c -> isIssueUnderstoodByList i c
  | IssueStageIs c -> isIssueStageIs i c 
  | IssueRiskIs c -> isIssueRiskIs i c


let evaluate_participant_condition (s:simulation_state)(pc:participant_condition): bool =
  let ps = s.participants in
  match pc with
  | ParticipantCountAtLeast c -> isParticipantCountAtLeast ps c
  | ParticipantCountAtMost c -> isParticipantCountAtMost ps c
  | UnderstandsCountAtLeast c -> isUnderstandsCountAtLeast ps c
  | UnderstandsCountAtMost c -> isUnderstandsCountAtMost ps c
  | ExistsParticipantWithRole rl -> isExistsParticipantWithRole ps rl
  | NotExistsParticipantWithRole rl -> isNotExistsParticipantWithRole ps rl
  | ExistsInterestedParticipantWithRole rl -> isExistsInterestedParticipantWithRole ps rl
  | NoInterestedParticipantWithRole rl -> isNotExistsInterestedParticipantWithRole ps rl
  | ExistsUnderstandingParticipantWithRole rl -> isExistsUnderstandingParticipantWithRole ps rl
  | NotExistsUnderstandingParticipantWithRole rl -> isNotExistsUnderstandingParticipantWithRole ps rl
  | AllParticipantsUnderstand -> isAllParticipantsUnderstand ps
  | NoParticipantUnderstands -> isNoParticipantUnderstands ps

let evaluate_predicate s p =
  match p with
  | Meeting mc-> evaluate_meeting_condition s mc
  | Issue ic -> evaluate_issue_condition s ic
  | Participants pc -> evaluate_participant_condition s pc

let rec evaluate_condition_expr s = function
  | Atom x -> evaluate_predicate s x
  | And xt -> List.for_all (evaluate_condition_expr s) xt
  | Or xt -> List.exists (evaluate_condition_expr s) xt

let apply_meeting_action (s: simulation_state)(ma: meeting_action): simulation_state = 
  match ma with 
  | ExtendMeetingBy a -> applyExtendMeetingBy s a
  | SetMeetingDrift a -> applySetMeetingDrift s a
  | SetDeepDive a -> applySetDeepDive s a 

let apply_issue_action (s: simulation_state)(ia: issue_action): simulation_state = 
  match ia with 
  | SetIssueStage a -> applySetIssueStage s a 
  | SetIssueRisk a -> applySetIssueRisk s a

let apply_action (s: simulation_state)(a: action): simulation_state =
  match a with
  | IssueAction xs -> List.fold_left (fun acc ia -> apply_issue_action acc ia) s xs
  | MeetingAction xs -> List.fold_left (fun acc ma -> apply_meeting_action acc ma) s xs

let apply_actions (s: simulation_state)(ac: action list): simulation_state = 
  List.fold_left (fun acc a -> apply_action acc a) s ac

let run_rule  (state : simulation_state)  (rule : rule_candidate): simulation_state  = 
  let cond_met = evaluate_condition_expr state rule.conditions in
  print_endline("conditions met for " ^ (string_of_rule rule) ^ "? " ^ string_of_bool (cond_met));
  if cond_met then
    let new_state = apply_actions state rule.actions in
    {new_state with fired_rules =  new_state.fired_rules @ [rule.rule_name]}
  else  
    state
    
let run_multiple_rules (state : simulation_state)  (rules : rule_candidate list): simulation_state = 
  List.fold_left run_rule state rules 
  
