type meeting_type =
  | Daily
  | Planning
  | CollectiveDebuggingInEnvironment

type role =
  | SM
  | PO
  | TechLead
  | ActingLead
  | Developer
  | Tester
  | Analyst
  | DataEngineer
  | Operations

type issue_priority =
  | Insignificant
  | Low
  | Medium
  | High
  | Critical

type risk =
  | WillBreakProduction
  | UsersWillRaiseHell
  | AuditorsWillNotBeHappy

type stage =
  | Open
  | Ignored
  | Discarded
  | Deferred
  | MovedToAnotherMeeting

type understanding =
  | Functional
  | Testing
  | Technical  

type meeting_drift =
  | Focused
  | ToTheHillsOfUbeda
  | ToHell  

type environment =
  | Development
  | Testing
  | UAT  

type meeting = {
  meeting_type : meeting_type;
  duration_min : int;
  deep_dive : bool;
  drift : meeting_drift;
  environment : environment option;
}

type participant = {
  role : role;
  interested : bool;
  understands : bool;
}

type status = {
  stage : stage;
  risk : risk option;
}

type issue = {
  priority : issue_priority;
  status : status;
  understood_by : understanding list;
}

type simulation_state = {
  meeting : meeting;
  participants : participant list;
  issue : issue;
  fired_rules : string list;
}

type simulation_result = {
  meeting : meeting;
  issue : issue;
  fired_rules : string list;
}

type rule = simulation_state -> simulation_state

let make_meeting meeting_type duration_min =
  {
    meeting_type;
    duration_min;
    deep_dive = false;
    drift = Focused;
    environment = None;
  }

let make_participant role =
  {role; interested = false; understands= false}  

let make_issue priority =
  {priority; status = {stage = Open; risk = None}; understood_by = []}  

(********************************************************************************************)  
 
type predicate =
  | Meeting of meeting_condition
  | Issue of issue_condition
  | Participants of participant_condition

and meeting_condition =
  | MeetingTypeIs of meeting_type
  | MeetingDriftIs of meeting_drift
  | DeepDiveIs of bool
  | EnvironmentIs of environment
  | DurationGreaterThan of int
  | DurationAtLeast of int
  | DurationAtMost of int

and issue_condition =
  | IssueStageIs of stage
  | IssueRiskIs of risk
  | IssuePriorityIs of issue_priority
  | IssuePriorityIn of issue_priority list
  | IssueUnderstoodOnlyBy of understanding
  | IssueUnderstoodByList of understanding list

and participant_condition =
  | ParticipantCountAtLeast of int
  | ParticipantCountAtMost of int
  | UnderstandsCountAtLeast of int
  | UnderstandsCountAtMost of int
  | ExistsParticipantWithRole of role list
  | ExistsInterestedParticipantWithRole of role list
  | NoInterestedParticipantWithRole of role list
  | ExistsUnderstandingParticipantWithRole of role list
  | AllParticipantsUnderstand
  | NoParticipantUnderstands

type condition_expr =
  | Atom of predicate
  | And of condition_expr list
  | Or of condition_expr list

type action =
  | MeetingAction of meeting_action list
  | IssueAction of issue_action list

and meeting_action =
  | ExtendMeetingBy of int
  | SetMeetingDrift of meeting_drift
  | SetDeepDive of bool

and issue_action =
  | SetIssueStage of stage
  | SetIssueRisk of risk option

type rule_candidate = {
  rule_name : string;
  conditions : condition_expr;
  actions : action list;
}


