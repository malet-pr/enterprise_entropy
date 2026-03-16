type meeting_type =
  | Daily
  | Planning
  | Retro
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

type issue_status =
  | Open
  | Deferred
  | Ignored
  | MovedToAnotherMeeting
  | Resolved
  | Discarded
  | WillBreakProduction

type understanding =
  | Functional
  | Testing
  | Technical  

type meeting_drift =
  | Focused
  | ToTheHillsOfUbeda
  | ToHell  

type action_status =
  | Proposed
  | Accepted
  | Forgotten
  | Completed
  | ReplacedByAnotherAction
  | NeverToBeSpokenAgain

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
  hour : int option;  
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

type action = {
  status : action_status;
  source_issue_priority : issue_priority;
} 

type rule = simulation_state -> simulation_state

let make_meeting meeting_type duration_min =
  {
    meeting_type;
    duration_min;
    deep_dive = false;
    drift = Focused;
    environment = None;
    hour = None;
  }

let make_participant role =
  {role; interested = false; understands= false}  

let make_issue priority =
  {priority; status = Open; understood_by = []}  