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
  let test = [{
    rule_name =  "test rule";
    conditions = Atom (Meeting (MeetingTypeIs (CollectiveDebuggingInEnvironment Testing))); 
    actions =  [IssueAction ([SetIssueStage Ignored])];
  }]
  let load_rules group =
    let rules =
      match group with
      | Daily -> test
      | Planning -> test
      | Debug -> test
      | All -> test
    in
    Lwt.return (Ok rules)
end

let memory_source = {
  load_rules = In_memory_rule_source.load_rules;
}

let db_source = {
  load_rules = Data_base_rule_source.load_rules;
}