open Ocaml_state_machine
open Model
open Transitions
open Integration


let () =
  if Array.length Sys.argv <> 3 then
    failwith "Usage: runner <input.json> <output.json>";

  let input_path = Sys.argv.(1) in
  let output_path = Sys.argv.(2) in

  let output_json =
    try
      let input_json = Yojson.Safe.from_file input_path in
      let scenario_id = scenario_id_or_unknown input_json in
      try
        let scenario = scenario_input_of_yojson input_json in
        let result = run_with_trace scenario in
        yojson_of_run_result result
      with
      | Failure msg ->
          `Assoc [
            ("scenario_id", `String scenario_id);
            ("status", `String "error");
            ("error", `Assoc [
              ("phase", `String "input");
              ("message", `String msg);
            ]);
          ]
    with
    | Failure msg ->
        `Assoc [
          ("scenario_id", `String "unknown");
          ("status", `String "error");
          ("error", `Assoc [
            ("phase", `String "input");
            ("message", `String msg);
          ]);
        ]
  in
  Yojson.Safe.to_file output_path output_json




(* 


let () = print_endline "FEATURE STATE MACHINE\n"

let initial_ctx = {
  revival_signals = 0;
  qa_rejections = 0;
  sprints_ignored = 0;
}

let initial_machine = (IdeaFog, initial_ctx)

let scenario1 = [
  ClarifySomehow;
  StartAnyway;
  SendToQA;
  Rework;
  SendToQA;
  Rework;
  SendToQA;
  Rework;
  SendToQA;
  ThisIsAllWrong;
]

let scenario2 = [
  ClarifySomehow;
  StartAnyway;
  SendToQA;
  Rework;
  SendToQA;
  DeclareEntropyComplete;
]

let scenario3 = [
  ClarifySomehow;
  StartAnyway;
  DiscoverDisagreement;
  Postpone;
  ForgetForLongTime;
  ForgetForLongTime;
  CustomerComplains;
  ExecutiveRemembers;
  DiscoverDisagreement;
  StartAnyway;
  SendToQA;
  Rework;
  SendToQA;
  RejectFundamentally;
  Postpone;
  ForgetForLongTime;
  ForgetForLongTime;
  AuditDiscovers;
  DeclareEntropyAbandoned;
]

let scenario4 = [
  Postpone;
  ForgetForLongTime;
  ForgetForLongTime;
  CustomerComplains;
  AuditDiscovers;
]

let res1 = run scenario3 initial_machine
let sep = print_endline("\n################################################\n\n")
(* let res3 = run scenario2 initial_machine
let sep = print_endline("\n################################################\n\n")
let res4 = run scenario3 initial_machine
let sep = print_endline("\n################################################\n\n")
let res2 = run scenario4 initial_machine *)
 *)