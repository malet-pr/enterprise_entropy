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

let route1 =
  Dream.post "/run/daily" (fun request ->
    let%lwt result = Sources.In_memory_rule_source.load_rules Daily in
    match result with
    | Ok rules ->
        run_with_rules rules request
    | Error err ->
        Dream.json
          (Yojson.Safe.to_string (`Assoc [
            ("status", `String "error");
            ("message", `String err)
          ]))
  )  

let route2 =
  Dream.post "/run/planning" (fun request ->
    let%lwt result = Sources.In_memory_rule_source.load_rules Planning in
    match result with
    | Ok rules ->
        run_with_rules rules request
    | Error err ->
        Dream.json
          (Yojson.Safe.to_string (`Assoc [
            ("status", `String "error");
            ("message", `String err)
          ]))
  ) 
  
let route3 =
  Dream.post "/run/debug" (fun request ->
    let%lwt result = Sources.In_memory_rule_source.load_rules Debug in
    match result with
    | Ok rules ->
        run_with_rules rules request
    | Error err ->
        Dream.json
          (Yojson.Safe.to_string (`Assoc [
            ("status", `String "error");
            ("message", `String err)
          ]))
  ) 
  
 let route4 =
  Dream.post "/run" (fun request ->
    let%lwt result = Sources.In_memory_rule_source.load_rules All in
    match result with
    | Ok rules ->
        run_with_rules rules request
    | Error err ->
        Dream.json
          (Yojson.Safe.to_string (`Assoc [
            ("status", `String "error");
            ("message", `String err)
          ]))
  ) 
  