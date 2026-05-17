open Engine.Model

type rule_group =
  | Daily
  | Planning
  | Debug
  | All

module type Rule_source = sig
  val load_rules : rule_group -> (rule_candidate list, string) result Lwt.t
end


type rule_source = {
  load_rules :
    rule_group ->
    (rule_candidate list, string) result Lwt.t;
}

module In_memory_rule_source : Rule_source = struct
  let load_rules group =
    let rules =
      match group with
      | Daily -> Rules.All.daily_rules
      | Planning -> Rules.All.planning_rules
      | Debug -> Rules.All.debug_rules
      | All -> Rules.All.all_rules
    in
    Lwt.return (Ok rules)
end

module Data_base_rule_source : Rule_source = struct
  let placeholder_rule name = {
    rule_name = name;
    conditions =
      Atom (Meeting (MeetingTypeIs (CollectiveDebuggingInEnvironment Testing)));
    actions =
      [IssueAction ([SetIssueStage Ignored])];
  }
  let category_to_string = function
    | Daily -> "DAILY"
    | Planning -> "PLANNING"
    | Debug -> "DEBUG"
    | All -> "ALL"

let load_rules group =
  let category = category_to_string group in
  let%lwt result = Db.Rule_repository.load_rule_jsons category in

  match result with
  | Ok jsons ->
      List.iter
        (fun json -> Printf.printf "Loaded JSON from DB: %s\n%!" json)
        jsons;

      let rules = List.map placeholder_rule jsons in
      Lwt.return (Ok rules)

  | Error err ->
      Lwt.return (Error err)
end

