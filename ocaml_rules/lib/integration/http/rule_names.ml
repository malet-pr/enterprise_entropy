open Engine.Model
open Lwt.Infix


let rule_names_to_json rules =
  `Assoc [
    ("count", `Int (List.length rules));
    ("rules", `List (List.map (fun r -> `String r.rule_name) rules));
  ]

let get_rule_names rules _request =
  rules
  |> rule_names_to_json
  |> Yojson.Safe.to_string
  |> Dream.json  


let route1 =
  Dream.get "/rules/daily" (fun request ->
    let%lwt result = Sources.In_memory_rule_source.load_rules Daily in
    match result with
    | Ok rules ->
        get_rule_names rules request
    | Error err ->
        Dream.json
          (Yojson.Safe.to_string (`Assoc [
            ("status", `String "error");
            ("message", `String err)
          ]))
  )  

let route2 =
  Dream.get "/rules/planning" (fun request ->
    let%lwt result = Sources.In_memory_rule_source.load_rules Planning in
    match result with
    | Ok rules ->
        get_rule_names rules request
    | Error err ->
        Dream.json
          (Yojson.Safe.to_string (`Assoc [
            ("status", `String "error");
            ("message", `String err)
          ]))
  )

let route3 =
  Dream.get "/rules/debug" (fun request ->
    let%lwt result = Sources.In_memory_rule_source.load_rules Debug in
    match result with
    | Ok rules ->
        get_rule_names rules request
    | Error err ->
        Dream.json
          (Yojson.Safe.to_string (`Assoc [
            ("status", `String "error");
            ("message", `String err)
          ]))
  )

let route4 =
  Dream.get "/rules" (fun request ->
    let%lwt result = Sources.In_memory_rule_source.load_rules All in
    match result with
    | Ok rules ->
        get_rule_names rules request
    | Error err ->
        Dream.json
          (Yojson.Safe.to_string (`Assoc [
            ("status", `String "error");
            ("message", `String err)
          ]))
  )

