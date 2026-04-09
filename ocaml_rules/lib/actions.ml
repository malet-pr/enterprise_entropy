open Model

(******************* ISSUE **********************)

let applySetIssueStage (state: simulation_state) (st: stage): simulation_state =
  {state with issue = {state.issue with status = { stage = st ; risk = state.issue.status.risk}}}





(******************* MEETING **********************)