open Model
open Utils
open Transitions
open Yojson.Safe
open Yojson.Safe.Util

(************** TYPES *************)

type scenario_input = {
  scenario_id : string;
  initial_state : state;
  initial_context : context;
  events : event list;
}

type trace_step = {
  step : int;
  event : event;
  resulting_state : state;
  resulting_context : context;
}

type scenario_success = {
  scenario_id : string;
  final_state : state;
  final_context : context;
  steps : trace_step list;
}

type scenario_error = {
  scenario_id : string;
  failed_step : int;
  failed_event : event;
  message : string;
  last_state : state;
  last_context : context;
  steps : trace_step list;
}

let scenario_id_or_unknown json =
  try Yojson.Safe.Util.(json |> member "scenario_id" |> to_string)
  with _ -> "unknown"

type run_result =
  | Success of scenario_success
  | Error of scenario_error


(************** PARSER *************)

let context_of_yojson json =
  {
    revival_signals = json |> member "revival_signals" |> to_int;
    qa_rejections = json |> member "qa_rejections" |> to_int;
    sprints_ignored = json |> member "sprints_ignored" |> to_int;
  }

let scenario_input_of_yojson json =
  let scenario_id = json |> member "scenario_id" |> to_string in
  let initial_state =
    json |> member "initial_state" |> to_string |> state_of_string
  in
  let initial_context =
    json |> member "initial_context" |> context_of_yojson
  in
  let events =
    json |> member "events" |> to_list |> List.map (fun j ->
      j |> to_string |> event_of_string)
  in
  {
    scenario_id;
    initial_state;
    initial_context;
    events;
  }

(************** WRITING *************)  

let yojson_of_context ctx =
  `Assoc [
    ("revival_signals", `Int ctx.revival_signals);
    ("qa_rejections", `Int ctx.qa_rejections);
    ("sprints_ignored", `Int ctx.sprints_ignored);
  ]

let yojson_of_trace_step t =
  `Assoc [
    ("step", `Int t.step);
    ("event", `String (string_of_event t.event));
    ("resulting_state", `String (string_of_state t.resulting_state));
    ("resulting_context", yojson_of_context t.resulting_context);
  ]
 
  let yojson_of_success (s: scenario_success) =
  `Assoc [
    ("scenario_id", `String s.scenario_id);
    ("status", `String "ok");
    ("final_state", `String (string_of_state s.final_state));
    ("final_context", yojson_of_context s.final_context);
    ("steps", `List (List.map yojson_of_trace_step s.steps));
  ]

  let yojson_of_error (e:scenario_error) =
  `Assoc [
    ("scenario_id", `String e.scenario_id);
    ("status", `String "error");
    ("error", `Assoc [
      ("step", `Int e.failed_step);
      ("event", `String (string_of_event e.failed_event));
      ("message", `String e.message);
    ]);
    ("last_state", `String (string_of_state e.last_state));
    ("last_context", yojson_of_context e.last_context);
    ("steps", `List (List.map yojson_of_trace_step e.steps));
  ]

  let yojson_of_run_result = function
    | Error e -> yojson_of_error e
    | Success s -> yojson_of_success s


(************** RUNNER *************)


let run_with_trace (input : scenario_input) : run_result =
  let rec loop step_num machine events acc =
    match events with
    | [] ->
        let (final_state, final_context) = machine in
        Success {
          scenario_id = input.scenario_id;
          final_state;
          final_context;
          steps = List.rev acc;
        }
    | ev :: rest ->
        let (state, ctx) = machine in
        try
          let (next_state, next_ctx) = step machine ev in
          let trace = {
            step = step_num;
            event = ev;
            resulting_state = next_state;
            resulting_context = next_ctx;
          } in
          loop (step_num + 1) (next_state, next_ctx) rest (trace :: acc)
        with Failure msg ->
          Error {
            scenario_id = input.scenario_id;
            failed_step = step_num;
            failed_event = ev;
            message = msg;
            last_state = state;
            last_context = ctx;
            steps = List.rev acc;
          }
  in
  loop 1 (input.initial_state, input.initial_context) input.events []

