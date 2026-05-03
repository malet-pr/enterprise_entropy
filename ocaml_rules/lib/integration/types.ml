open Engine.Model

type single_run = {
  run_id: string;
  meeting_input: meeting;
  issue_input: issue;
  participants_input: participant list;
}

type run_success = {
  run_id : string;
  final_state : simulation_state;
}

type input_error = {
  run_id : string;
  message : string;
}

let run_id_or_unknown json =
  try Yojson.Safe.Util.(json |> member "run_id" |> to_string)
  with _ -> "unknown"

type run_result =
  | Success of run_success
  | InputError of input_error

