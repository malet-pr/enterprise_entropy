open Engine.Model
open Lwt.Infix

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

let routes = [
  Dream.get "/rules" (get_rule_names Rules.All.all_rules);
  Dream.get "/rules/daily" (get_rule_names Rules.All.daily_rules);
  Dream.get "/rules/planning" (get_rule_names Rules.All.planning_rules);
  Dream.get "/rules/debug" (get_rule_names Rules.All.debug_rules);
  Dream.post "/run" (run_with_rules Rules.All.all_rules);
  Dream.post "/run/daily" (run_with_rules Rules.All.daily_rules);
  Dream.post "/run/planning" (run_with_rules Rules.All.planning_rules);
  Dream.post "/run/debug" (run_with_rules Rules.All.debug_rules);
]