open Daily
open Debug
open Planning

let daily_rules = [
  curiosity_spiral;
  important_issue_not_understood;
  insignificant_issue_consumes_time;
  move_issue_to_another_meeting;
  collective_debugging_swarm;
]

let debug_rules = [
  issue_is_discarded;
  risk_will_break_production;
]

let planning_rules = [
  issue_is_ignored;
]

let all_rules = daily_rules @ debug_rules @ planning_rules



