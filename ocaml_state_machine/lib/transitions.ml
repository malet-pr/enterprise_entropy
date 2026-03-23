open Model 
open Utils

let invalid_transition state event =
  failwith
    (Printf.sprintf
       "Invalid transition: state=%s, event=%s"
       (string_of_state state)
       (string_of_event event))     

let step ((state,ctx) : machine) event : machine  =
  match state with
  | EntropyComplete | EntropyAbandoned | EntropyReduction -> failwith "Invalid transition"
  | _ ->
    match state, event with
    | IdeaFog, ClarifySomehow ->  PretendPlanning, ctx
    | IdeaFog, StartAnyway ->  HeroicImplementation, ctx
    | IdeaFog, Postpone ->  TemporarilyPostponed, reset_sprints_ignored ctx
    | IdeaFog, DeclareEntropyAbandoned ->  EntropyAbandoned, ctx
    | PretendPlanning, StartAnyway ->  HeroicImplementation, ctx
    | PretendPlanning, DiscoverDisagreement ->  PhilosophicalDebate, ctx
    | PretendPlanning, Postpone ->  TemporarilyPostponed, reset_sprints_ignored ctx
    | PretendPlanning, DeclareEntropyComplete ->  EntropyComplete, ctx
    | PretendPlanning, DeclareEntropyReduction ->  EntropyReduction, ctx
    | PretendPlanning, DeclareEntropyAbandoned ->  EntropyAbandoned, ctx
    | HeroicImplementation, SendToQA -> StressTheThing, ctx
    | HeroicImplementation, DiscoverDisagreement ->  PhilosophicalDebate, ctx
    | HeroicImplementation, Postpone ->  TemporarilyPostponed, reset_sprints_ignored ctx
    | HeroicImplementation, RealizeWrongDirection ->  PretendPlanning, reset_qa_rejections ctx
    | StressTheThing, Rework when ctx.qa_rejections < 3 -> HeroicImplementation, increment_qa_rejections ctx
    | StressTheThing, ThisIsAllWrong when ctx.qa_rejections >= 3 ->  PretendPlanning, reset_qa_rejections ctx
    | StressTheThing, Postpone ->  TemporarilyPostponed, reset_sprints_ignored ctx
    | StressTheThing, RealizeWrongDirection ->  PretendPlanning, reset_qa_rejections ctx
    | StressTheThing, RejectFundamentally ->  PhilosophicalDebate, ctx
    | StressTheThing, DeclareEntropyComplete ->  EntropyComplete, ctx  
    | PhilosophicalDebate, StartAnyway ->  HeroicImplementation, ctx
    | PhilosophicalDebate, Postpone ->  TemporarilyPostponed, reset_sprints_ignored ctx
    | PhilosophicalDebate, DeclareEntropyReduction ->  EntropyReduction, ctx
    | PhilosophicalDebate, DeclareEntropyAbandoned ->  EntropyAbandoned, ctx 
    | TemporarilyPostponed, ForgetForLongTime ->
        let new_ctx = increment_sprints_ignored ctx in
        if new_ctx.sprints_ignored >= 2 then
             ZombieFeature, new_ctx
        else
             TemporarilyPostponed, new_ctx
    | TemporarilyPostponed, StartAnyway ->  HeroicImplementation, reset_sprints_ignored ctx
    | TemporarilyPostponed, ClarifySomehow ->  PretendPlanning, reset_sprints_ignored ctx
    | ZombieFeature, ExecutiveRemembers ->
        let new_ctx = increment_revival_signals ctx in
        if new_ctx.revival_signals >= 2 then
             HeroicImplementation, reset_revival_signals new_ctx
        else
             ZombieFeature, new_ctx
    | ZombieFeature, CustomerComplains ->
        let new_ctx = increment_revival_signals ctx in
        if new_ctx.revival_signals >= 2 then
             HeroicImplementation, reset_revival_signals new_ctx
        else
             ZombieFeature, new_ctx
    | ZombieFeature, AuditDiscovers ->
        let new_ctx = increment_revival_signals ctx in
        if new_ctx.revival_signals >= 2 then
             HeroicImplementation, reset_revival_signals new_ctx
        else
             ZombieFeature, new_ctx
    | ZombieFeature, DeclareEntropyAbandoned -> EntropyAbandoned, ctx         
    | _ -> failwith "Invalid transition"
 

let run events state =
  Printf.printf "Start: %s\n\n" (string_of_machine state);   
  List.fold_left
    (fun (m, step_num) event ->
        Printf.printf "[%d] Event: %s\n" step_num (string_of_event event);
        try
            let res = step m event in
            Printf.printf "Next -> %s\n\n" (string_of_machine res);
            (res, step_num + 1)
        with Failure msg ->
            Printf.printf " !! %s\n\n" msg;
            raise (Failure msg)) 
    (state , 1)
    events
       