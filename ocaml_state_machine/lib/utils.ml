open Model

let string_of_state = function
  | IdeaFog -> "IdeaFog"
  | PretendPlanning -> "PretendPlanning"
  | HeroicImplementation -> "HeroicImplementation"
  | PhilosophicalDebate -> "PhilosophicalDebate"
  | StressTheThing -> "StressTheThing"
  | TemporarilyPostponed -> "TemporarilyPostponed"
  | ZombieFeature -> "ZombieFeature"
  | EntropyReduction -> "EntropyReduction"
  | EntropyComplete -> "EntropyComplete"
  | EntropyAbandoned -> "EntropyAbandoned"


let string_of_event = function
  | AuditDiscovers -> "AuditDiscovers"
  | ClarifySomehow -> "ClarifySomehow"
  | CustomerComplains -> "CustomerComplains"
  | DeclareEntropyAbandoned -> "DeclareEntropyAbandoned"
  | DeclareEntropyComplete -> "DeclareEntropyComplete"
  | DeclareEntropyReduction -> "DeclareEntropyReduction"
  | DiscoverDisagreement -> "DiscoverDisagreement"
  | ExecutiveRemembers -> "ExecutiveRemembers"
  | ForgetForLongTime -> "ForgetForLongTime"
  | Postpone -> "Postpone"
  | RealizeWrongDirection -> "RealizeWrongDirection"
  | RejectFundamentally -> "RejectFundamentally"
  | Rework -> "Rework"
  | SendToQA -> "SendToQA"
  | StartAnyway -> "StartAnyway"
  | ThisIsAllWrong -> "ThisIsAllWrong"

let string_of_context ctx =
  Printf.sprintf
    "{revival=%d; qa_rej=%d; sprints_ignored=%d}"
    ctx.revival_signals
    ctx.qa_rejections
    ctx.sprints_ignored

let string_of_machine (state, ctx) =
  Printf.sprintf "%s %s"
    (string_of_state state)
    (string_of_context ctx)  

let increment_qa_rejections ctx =
  { ctx with qa_rejections = ctx.qa_rejections + 1 }

let reset_qa_rejections ctx =
  { ctx with qa_rejections = 0 }  

let increment_sprints_ignored ctx =
  { ctx with sprints_ignored = ctx.sprints_ignored + 1 }

let reset_sprints_ignored ctx =
  { ctx with sprints_ignored = 0 }  

let increment_revival_signals ctx =
  { ctx with revival_signals = ctx.revival_signals + 1 }

let reset_revival_signals ctx =
  { ctx with revival_signals = 0 }  



(* 
transition result --> (state * context) -> event -> (state * context)  

val check_invariants : state -> context -> bool

val invariant_violations : state -> context -> string list 
*)