open Validation_types

let string_of_message_list (es: validation_error list) : string =
  let messages = List.map (fun e -> "\"" ^ e.message ^ "\"") es in
  "[" ^ String.concat ", " messages ^ "]"

