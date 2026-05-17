open Engine.Model

let test_to_json rules =
  `Assoc [
    ("count", `Int (List.length rules));
    ("rules", `List (List.map (fun r -> `String r.rule_name) rules));
  ]

let get_test test _request =
  test
  |> test_to_json
  |> Yojson.Safe.to_string
  |> Dream.json  

let daily_rules = Dream.get "/rules/daily" (fun request ->
  let%lwt result = Sources.Data_base_rule_source.load_rules Daily in
  match result with
  | Ok rules ->
      get_test rules request
  | Error err ->
      Dream.json
        (Yojson.Safe.to_string (`Assoc [
          ("status", `String "error");
          ("message", `String err)
        ]))
) 

let planning_rules = Dream.get "/rules/planning" (fun request ->
  let%lwt result = Sources.Data_base_rule_source.load_rules Planning in
  match result with
  | Ok rules ->
      get_test rules request
  | Error err ->
      Dream.json
        (Yojson.Safe.to_string (`Assoc [
          ("status", `String "error");
          ("message", `String err)
        ]))
) 
  
let debug_rules = Dream.get "/rules/debug" (fun request ->
  let%lwt result = Sources.Data_base_rule_source.load_rules Debug in
  match result with
  | Ok rules ->
      get_test rules request
  | Error err ->
      Dream.json
        (Yojson.Safe.to_string (`Assoc [
          ("status", `String "error");
          ("message", `String err)
        ]))
) 

let all_rules = Dream.get "/rules" (fun request ->
  let%lwt result = Sources.Data_base_rule_source.load_rules All in
  match result with
  | Ok rules ->
      get_test rules request
  | Error err ->
      Dream.json
        (Yojson.Safe.to_string (`Assoc [
          ("status", `String "error");
          ("message", `String err)
        ]))
) 