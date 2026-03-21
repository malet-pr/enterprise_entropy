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
  | EntropyComplete -> failwith "Invalid transition"
  | _ ->
    match state, event with
    | IdeaFog, ClarifySomehow ->  PretendPlanning, ctx
    | IdeaFog, StartAnyway ->  HeroicImplementation, ctx
    | IdeaFog, Postpone ->  TemporarilyPostponed, ctx
    | IdeaFog, DeclareEntropyComplete ->  EntropyComplete, ctx
    | PretendPlanning, StartAnyway ->  HeroicImplementation, ctx
    | PretendPlanning, DiscoverDisagreement ->  PhilosophicalDebate, ctx
    | PretendPlanning, Postpone ->  TemporarilyPostponed, ctx
    | HeroicImplementation, SendToQA ->
        let new_ctx = increment_qa_rejections ctx in
        if new_ctx.qa_rejections >= 3 then
             PhilosophicalDebate, new_ctx
        else
             ThisIsAllWrong, new_ctx
    | HeroicImplementation, DiscoverDisagreement ->  PhilosophicalDebate, ctx
    | HeroicImplementation, Postpone ->  TemporarilyPostponed, ctx
    | ThisIsAllWrong, Rework ->  HeroicImplementation, ctx
    | ThisIsAllWrong, RejectFundamentally ->  PhilosophicalDebate, ctx
    | ThisIsAllWrong, Postpone ->  TemporarilyPostponed, ctx
    | PhilosophicalDebate, ClarifySomehow ->  PretendPlanning, ctx
    | PhilosophicalDebate, StartAnyway ->  HeroicImplementation, ctx
    | PhilosophicalDebate, Postpone ->  TemporarilyPostponed, ctx
    | TemporarilyPostponed, ForgetForLongTime ->
        let new_ctx = increment_sprints_ignored ctx in
        if new_ctx.sprints_ignored >= 2 then
             ZombieTicket, new_ctx
        else
             TemporarilyPostponed, new_ctx
    | TemporarilyPostponed, StartAnyway ->  HeroicImplementation, ctx
    | ZombieTicket, ExecutiveRemembers ->
        let new_ctx = increment_revival_signals ctx in
        if new_ctx.revival_signals >= 2 then
             HeroicImplementation, reset_revival_signals new_ctx
        else
             ZombieTicket, new_ctx
    | ZombieTicket, CustomerComplains ->
        let new_ctx = increment_revival_signals ctx in
        if new_ctx.revival_signals >= 2 then
             HeroicImplementation, reset_revival_signals new_ctx
        else
             ZombieTicket, new_ctx
    | ZombieTicket, AuditDiscovers ->
        let new_ctx = increment_revival_signals ctx in
        if new_ctx.revival_signals >= 2 then
             HeroicImplementation, reset_revival_signals new_ctx
        else
             ZombieTicket, new_ctx
    | _, DeclareEntropyComplete ->  EntropyComplete, ctx
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
       