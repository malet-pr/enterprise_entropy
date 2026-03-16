open Model 
open Utils

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

let string_of_machine2 (state, ctx) =
  Printf.sprintf "%s %s"
    (string_of_state state)
    (string_of_context ctx)  



