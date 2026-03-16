type state =
  | IdeaFog
  | PretendPlanning
  | HeroicImplementation
  | PhilosophicalDebate
  | ThisIsAllWrong
  | TemporarilyPostponed
  | ZombieTicket
  | EntropyComplete

 type event =
  | ClarifySomehow
  | StartAnyway
  | DiscoverDisagreement
  | SendToQA
  | RejectFundamentally
  | Rework
  | Postpone
  | ForgetForLongTime
  | ExecutiveRemembers
  | CustomerComplains
  | AuditDiscovers
  | DeclareEntropyComplete
  
 type context = {
  revival_signals : int;
  qa_rejections : int;
  sprints_ignored : int;
}

let initial_context = {
  revival_signals = 0;
  qa_rejections = 0;
  sprints_ignored = 0;
}

let initial_state = IdeaFog

type machine = state * context