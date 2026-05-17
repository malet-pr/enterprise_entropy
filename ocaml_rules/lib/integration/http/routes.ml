open Engine.Model
open Lwt.Infix

type source =
| Memory
| DB

let mem_routes = [
  Rule_names.route1;
  Rule_names.route2;
  Rule_names.route3;
  Rule_names.route4;
  Rule_runs.route1;
  Rule_runs.route2;
  Rule_runs.route3;
  Rule_runs.route4;
]  

let db_routes = [
  Db.Temp.daily_rules;
  Db.Temp.planning_rules;
  Db.Temp.debug_rules;
  Db.Temp.all_rules;
]

let get_rules_source = function
  | Memory -> mem_routes
  | DB -> db_routes

let routes source =
  get_rules_source  source