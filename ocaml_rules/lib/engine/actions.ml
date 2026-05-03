open Model

(******************* ISSUE **********************)

let applySetIssueStage (state: simulation_state) (st: stage): simulation_state =
  {state with issue = {state.issue with status = { stage = st ; risk = state.issue.status.risk}}}

let applySetIssueRisk (state: simulation_state) (rk: risk option): simulation_state =
  {state with issue = {state.issue with status = {
                stage = state.issue.status.stage ; 
                risk = 
                match rk with
                | Some r -> rk
                | None -> None}}}

(******************* MEETING **********************)

let applyExtendMeetingBy (state: simulation_state) (d: int): simulation_state =
  {state with meeting = {state.meeting with duration_min = state.meeting.duration_min + d}}

let applySetMeetingDrift (state: simulation_state) (d: meeting_drift): simulation_state =
  {state with meeting = {state.meeting with drift = d}}

let applySetDeepDive (state: simulation_state) (d: bool): simulation_state =
  {state with meeting = {state.meeting with deep_dive = d}}

