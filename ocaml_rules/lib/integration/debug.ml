
let enabled =
  match Sys.getenv_opt "OCAML_RULES_DEBUG" with
  | Some "true" -> true
  | Some "1" -> true
  | _ -> false

let log message =
  if enabled then
    Printf.printf "[debug] %s\n%!" message
  else
    ()

let logf fmt =
  Printf.ksprintf log fmt
