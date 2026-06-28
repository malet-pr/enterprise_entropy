open Validation_types
open Pipeline
open Step

let run_step result step =
  bind result step

let run_pipeline initial steps =
  List.fold_left
    (fun result step -> run_step result step)
    (Valid initial)
    steps  