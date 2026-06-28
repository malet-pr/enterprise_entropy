open Yojson.Safe
open Yojson.Safe.Util

let string_or_string_list json =
  match json |> member "value" with
  | `String s ->
      [s]
  | `List values ->
      values |> List.map to_string
  | _ ->
      failwith "Expected string or string list"

let string_list_value json =
  match json |> member "value" with
  | `String s ->
      [s]
  | `List values ->
      values |> List.map to_string
  | _ ->
      failwith "Expected string or string list"      

let int_list_value json =
  match json |> member "value" with
  | `Int n ->
      [n]
  | `List values ->
      values |> List.map to_int
  | _ ->
      failwith "Expected int or int list"