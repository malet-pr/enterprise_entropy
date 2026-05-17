open Ocaml_rules
open Integration.Http.Routes
open Integration.Sources


let start_server (source : source) =
  Dream.run ~port:4000
  @@ Dream.logger
  @@ Dream.router (Ocaml_rules.Integration.Http.Routes.routes source)

let () = 
  start_server DB