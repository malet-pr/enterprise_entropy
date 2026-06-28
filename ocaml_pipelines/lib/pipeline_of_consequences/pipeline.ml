open Validation_types

let bind result next_step =
  match result with
  | Valid value -> next_step value
  | Invalid errors -> Invalid errors
