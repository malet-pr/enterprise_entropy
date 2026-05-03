open Ocaml_rules
open Integration.Http.Routes


let () =
  Dream.run ~port:4000
  @@ Dream.logger
  @@ Dream.router Ocaml_rules.Integration.Http.Routes.routes
