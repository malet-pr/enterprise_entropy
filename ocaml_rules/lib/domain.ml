type meeting_type =
  | Daily
  | Planning
  | StaffMeeting
  | CollectiveDebuggingInTestEnvironment

type role =
  | SM
  | DBA
  | Developer
  | Tester
  | PO
  | ShadowTL
  | OfficialTL
  | Analyst
  | AppOps

type issue_priority =
  | Insignificant
  | Low
  | Medium
  | High
  | Critical

type issue_status =
  | Open
  | Deferred
  | Ignored
  | MovedToAnotherMeeting
  | Resolved
  | WillBreakProduction

type understanding =
  | Functional
  | Testing
  | Technical  

type meeting_drift =
  | Focused
  | ToTheHillsOfUbeda
  | ToHell  

type meeting = {
  meeting_type : meeting_type;
  duration_min : int;
  deep_dive : bool;
  drift : meeting_drift;
}

type participant = {
  role : role;
  interested : bool;
  understands : bool;
}

type issue = {
  priority : issue_priority;
  status : issue_status;
  understood_by : understanding list;
}


