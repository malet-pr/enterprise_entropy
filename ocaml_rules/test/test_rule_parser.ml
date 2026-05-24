open Alcotest

let test_true () =
  check bool "true should be true" true true

let () =
  run "Rule parser tests" [
    ("basic", [
      test_case "true test" `Quick test_true;
    ]);
  ]