open Ocaml_state_machine
open Model
open Transitions

let () = print_endline "FEATURE STATE MACHINE\n"

let initial_ctx = {
  revival_signals = 0;
  qa_rejections = 0;
  sprints_ignored = 0;
}

let initial_machine = (IdeaFog, initial_ctx)

let scenario1 = [
  ClarifySomehow;
  StartAnyway;
  SendToQA;
  Rework;
  SendToQA;
  Rework;
  SendToQA;
]

let scenario2 = [
  ClarifySomehow;
  StartAnyway;
  SendToQA;
  DiscoverDisagreement;
  Rework;
]

let scenario3 = [
  ClarifySomehow;
  StartAnyway;
  DiscoverDisagreement;
  Postpone;
  ForgetForLongTime;
  ForgetForLongTime;
  CustomerComplains;
  ExecutiveRemembers;
  DiscoverDisagreement;
  StartAnyway;
  SendToQA;
  Rework;
  SendToQA;
  RejectFundamentally;
  Postpone;
  ForgetForLongTime;
  AuditDiscovers;
  DeclareEntropyComplete;
]

let scenario4 = [
  Postpone;
  ForgetForLongTime;
  ForgetForLongTime;
  CustomerComplains;
  AuditDiscovers;
]

let res1 = run scenario1 initial_machine
let sep = print_endline("\n################################################\n\n")
let res3 = run scenario3 initial_machine
let sep = print_endline("\n################################################\n\n")
let res4 = run scenario4 initial_machine
let sep = print_endline("\n################################################\n\n")
let res2 = run scenario2 initial_machine
