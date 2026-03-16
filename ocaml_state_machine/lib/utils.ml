open Model

let string_of_state = function
  | IdeaFog -> "IdeaFog"
  | PretendPlanning -> "PretendPlanning"
  | HeroicImplementation -> "HeroicImplementation"
  | PhilosophicalDebate -> "PhilosophicalDebate"
  | ThisIsAllWrong -> "ThisIsAllWrong"
  | TemporarilyPostponed -> "TemporarilyPostponed"
  | ZombieTicket -> "ZombieTicket"
  | EntropyComplete -> "EntropyComplete"

let string_of_event = function
  | ClarifySomehow -> "ClarifySomehow"
  | StartAnyway -> "StartAnyway"
  | DiscoverDisagreement -> "DiscoverDisagreement"
  | SendToQA -> "SendToQA"
  | RejectFundamentally -> "RejectFundamentally"
  | Rework -> "Rework"
  | Postpone -> "Postpone"
  | ForgetForLongTime -> "ForgetForLongTime"
  | ExecutiveRemembers -> "ExecutiveRemembers"
  | CustomerComplains -> "CustomerComplains"
  | AuditDiscovers -> "AuditDiscovers"
  | DeclareEntropyComplete -> "DeclareEntropyComplete"  




let increment_qa_rejections ctx =
  { ctx with qa_rejections = ctx.qa_rejections + 1 }

let increment_sprints_ignored ctx =
  { ctx with sprints_ignored = ctx.sprints_ignored + 1 }

let increment_revival_signals ctx =
  { ctx with revival_signals = ctx.revival_signals + 1 }

let reset_revival_signals ctx =
  { ctx with revival_signals = 0 }  



(* 
transition result --> (state * context) -> event -> (state * context)  

val check_invariants : state -> context -> bool

val invariant_violations : state -> context -> string list 
*)