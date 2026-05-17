open Engine.Model
open Lwt.Infix

type source =
| Memory
| DB

let load_rules source = 
  match source with
  | Memory -> Sources.In_memory_rule_source.load_rules 
  | DB -> Sources.Data_base_rule_source.load_rules 

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

let run_with_rules rules request =
  Dream.body request >>= fun body ->
  let result =
    try
      let json = Yojson.Safe.from_string body in
      Runners.run_from_json json rules
    with
    | Yojson.Json_error msg ->
        InputError { run_id = "unknown"; message = "JSON error: " ^ msg }
  in
  result
  |> Runners.run_result_to_json
  |> Yojson.Safe.to_string
  |> Dream.json  

let rule_names_route path group source =
  Dream.get path (fun request ->
    let%lwt result = (load_rules source) group in
    match result with
    | Ok rules -> get_rule_names rules request
    | Error err ->
        Dream.json
          (Yojson.Safe.to_string (`Assoc [
            ("status", `String "error");
            ("message", `String err)
          ]))
  )

let rule_run_route path group source =
  Dream.post path (fun request ->
    let%lwt result = (load_rules source) group in
    match result with
    | Ok rules -> run_with_rules rules request
    | Error err ->
        Dream.json
          (Yojson.Safe.to_string (`Assoc [
            ("status", `String "error");
            ("message", `String err)
          ]))
  )  

let routes source = [
  rule_run_route "/run/daily" Daily source;
  rule_run_route "/run/planning" Planning source;
  rule_run_route "/run/debug" Debug source;
  rule_run_route "/run" All source;
  rule_names_route "/rules/daily" Daily source;
  rule_names_route "/rules/planning" Planning source;
  rule_names_route "/rules/debug" Debug source;
  rule_names_route "/rules" All source;
]
