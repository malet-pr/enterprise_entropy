open Validation_types

let pass value =
  Valid value  

let fail code message =
  Invalid [{ code; message; severity = Error }]  

let step_of_validator validator value =
  match validator value with
  | Some error -> Invalid [error]
  | None -> Valid value  

let step_of_transformer transformer value =
  Valid (transformer value)  