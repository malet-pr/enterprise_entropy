open Lwt.Syntax
open Caqti_request.Infix
open Engine.Strings

let select_rule_names =
  Caqti_type.(string ->* string)
    {|
      SELECT rule_name
      FROM ocaml.rule_definition
      WHERE category = ?
        AND active = true
      ORDER BY priority, rule_name
    |}

let load_rule_names category =
  let* conn_result = Connection.connect () in
  match conn_result with
  | Error err ->
      Lwt.return (Error (Caqti_error.show err))
  | Ok (module Db : Caqti_lwt.CONNECTION) ->
      Db.collect_list select_rule_names category
      |> Lwt.map (Result.map_error Caqti_error.show)
      
let load_rules group =
  let category = category_to_string group in
  let%lwt names_result = load_rule_names category in
  match names_result with
  | Ok names ->
      List.iter (fun name -> Printf.printf "Loaded rule: %s\n%!" name) names;
      Lwt.return (Ok "test")
  | Error err ->
      Lwt.return (Error err)      

let select_rule_jsons =
  Caqti_request.(
    Caqti_type.string ->* Caqti_type.string
  )
    {|
      SELECT rule_json::text
      FROM ocaml.rule_definition
      WHERE category = $1::text
        AND active = true
      ORDER BY priority, rule_name 
    |}

let load_rule_jsons category =
  let%lwt conn_result = Connection.connect () in
  match conn_result with
  | Error err ->
      Lwt.return (Error (Caqti_error.show err))

  | Ok (module Db : Caqti_lwt.CONNECTION) ->
      Db.collect_list select_rule_jsons category
      |> Lwt.map (Result.map_error Caqti_error.show)

