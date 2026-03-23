type state =
  | IdeaFog
  | PretendPlanning
  | HeroicImplementation
  | PhilosophicalDebate
  | StressTheThing
  | TemporarilyPostponed
  | ZombieFeature
  | EntropyReduction
  | EntropyComplete
  | EntropyAbandoned

 type event =
  | AuditDiscovers
  | ClarifySomehow
  | CustomerComplains
  | DeclareEntropyAbandoned
  | DeclareEntropyComplete
  | DeclareEntropyReduction
  | DiscoverDisagreement
  | ExecutiveRemembers
  | ForgetForLongTime
  | Postpone
  | RealizeWrongDirection
  | RejectFundamentally
  | Rework
  | SendToQA
  | StartAnyway
  | ThisIsAllWrong

  
type context = {
  revival_signals : int;
  qa_rejections : int;
  sprints_ignored : int;
}

type machine = state * context

let initial_context = {
  revival_signals = 0;
  qa_rejections = 0;
  sprints_ignored = 0;
}

let initial_state = IdeaFog

let is_terminal = function
  | (EntropyComplete, _) -> true
  | (EntropyReduction, _) -> true
  | (EntropyAbandoned, _) -> true
  | _ -> false


