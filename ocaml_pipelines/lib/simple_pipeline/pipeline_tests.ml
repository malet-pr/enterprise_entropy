open Pipeline_model
open Pipeline_modules

  
let validators = [
  ApplicantValidation.name_required;
  ApplicantValidation.must_be_adult;
  ApplicantValidation.deposit_positive;
  ApplicantValidation.young_unemployed;
]

let test_applicant_1 = {
  name = "";
  age = 16;
  deposit = 0;
  has_income = false;
  validated = false;
}
let test_applicant_2 = {
  name = "Marcus";
  age = 16;
  deposit = 0;
  has_income = true;
  validated = false;
}

let test_applicant_3 = {
  name = "Lucas";
  age = 22;
  deposit = 100;
  has_income = false;
  validated = false;
}

let test_applicant_4 = {
  name = "John";
  age = 42;
  deposit = 1000;
  has_income = true;
  validated = false;
}

let test_applicant_5 = {
  name = "";
  age = 42;
  deposit = 1000;
  has_income = true;
  validated = false;
}

let run () = 
  let ve1 = Pipelines.validate_outcome_pipeline validators test_applicant_1 in
  let ve2 = Pipelines.validate_outcome_pipeline validators test_applicant_4 in
  let ve3 = Pipelines.validate_outcome_pipeline validators test_applicant_5 in
  print_endline(ApplicantString.string_of_validation_outcome ve1);
  print_endline(ApplicantString.string_of_validation_outcome ve2);
  print_endline(ApplicantString.string_of_validation_outcome ve3);

(*   let ve1 = Pipelines.validate test_applicant_1  in
  print_endline(ApplicantString.string_of_validation_result ve1);
  let ve2 = Pipelines.validate test_applicant_4  in
  print_endline(ApplicantString.string_of_validation_result ve2); *)

(*   let ve1 = Pipelines.validate_pipeline_1 test_applicant_1 validators in
  print_endline(ApplicantString.string_of_validation_result ve1);
  let ve2 = Pipelines.validate_pipeline_1 test_applicant_2 validators in
  print_endline(ApplicantString.string_of_validation_result ve2);
  let ve3 = Pipelines.validate_pipeline_1 test_applicant_4 validators in
  print_endline(ApplicantString.string_of_validation_result ve3);
  let ve4 = Pipelines.validate_pipeline_2 test_applicant_1 validators in
  print_endline(ApplicantString.string_of_validation_result ve4);
  let ve5 = Pipelines.validate_pipeline_2 test_applicant_2 validators in
  print_endline(ApplicantString.string_of_validation_result ve5);
  let ve6 = Pipelines.validate_pipeline_2 test_applicant_4 validators in
  print_endline(ApplicantString.string_of_validation_result ve6); *)



(*   let ve1 = ApplicantValidation.validate_with_summary validators test_applicant_1 in
  let ve2 = ApplicantValidation.validate_with_summary validators test_applicant_4 in
  let resp1 = ApplicantValidationResult.map_error (DomainOps.prefix_error ~prefix:"~error~") ve1 in
  let resp2 = ApplicantValidationResult.map_error (DomainOps.prefix_error ~prefix:"~error~") ve2 in
  print_endline(ApplicantString.string_of_validation_result resp1);
  print_endline(ApplicantString.string_of_validation_result resp2);
  let resp3 = ApplicantValidationResult.map_valid (DomainOps.validated) ve1 in
  let resp4 = ApplicantValidationResult.map_valid (DomainOps.validated) ve2 in
  print_endline(ApplicantString.string_of_validation_result resp3);
  print_endline(ApplicantString.string_of_validation_result resp4); 
 *)
(* 
  let ve3 = ApplicantValidation.run_validators validators test_applicant_5 in
  let ve1dw = ApplicantValidationResult.map_warnings (DomainOps.has_warnings) ve1 in
  let ve1de = ApplicantValidationResult.map_errors_list (DomainOps.prefix_error ~prefix:"~Error~") ve1 in
  print_endline("Warnings for test_applicant_1: " ^ string_of_int ve1dw);
  print_endline("Errors for test_applicant_1: " ^ ApplicantString.string_of_error_list ve1de);
  let ve2d = ApplicantValidationResult.map_warnings (DomainOps.has_warnings) ve2 in
  print_endline("Warnings for test_applicant_4: " ^ string_of_int ve2d);
  let ve3dw = ApplicantValidationResult.map_warnings (DomainOps.has_warnings) ve3 in
  let ve3de = ApplicantValidationResult.map_errors_list (DomainOps.prefix_error ~prefix:"~Error~") ve3 in
  print_endline("Warnings for test_applicant_5: " ^ string_of_int ve3dw);
  print_endline("Errors for test_applicant_5: " ^ ApplicantString.string_of_error_list ve3de); *)

(*   print_endline(string_of_summary_messages(summarize(run_validators validators test_applicant_1))); 
  print_newline();  *)
(*   pretty_print_summary_messages (summarize(run_validators validators test_applicant_1));
  print_newline();
  pretty_print_summary_messages (summarize(run_validators validators test_applicant_2)); *)

   (* print_endline(string_of_error_list(run_validators validators test_applicant_1));
  print_endline(string_of_error_option(first_error_validator validators test_applicant_1));
  print_endline(string_of_error_option(first_error_validator_map validators test_applicant_2));
  print_endline(string_of_error_option(first_error_validator_map validators test_applicant_3));
  print_endline(string_of_error_option(first_error_validator_map validators test_applicant_4)); *)

(*   ApplicationProcess.print_process_applicant test_applicant_1 validators;
  print_newline();
  ApplicationProcess.print_process_applicant test_applicant_2 validators; *)

(*   let ve1b = DomainOps.prefix_errors ~prefix:"[test]" ve1 in
  print_endline("Errors for test_applicant_1: " ^ ApplicantString.string_of_error_list ve1b);
  let ve2b = DomainOps.prefix_errors ~prefix:"[test]" ve2 in
  print_endline("Errors for test_applicant_4: " ^ ApplicantString.string_of_error_list ve2b);
  let ve3b = DomainOps.prefix_errors ~prefix:"[test]" ve3 in
  print_endline("Errors for test_applicant_4: " ^ ApplicantString.string_of_error_list ve3b);
  let ve1c = DomainOps.count_warnings ve1 in
  print_endline("Warnings for test_applicant_1: " ^ string_of_int ve1c);
  let ve2c = DomainOps.count_warnings ve2 in
  print_endline("Warnings for test_applicant_4: " ^ string_of_int ve2c);
  let ve3c = DomainOps.count_warnings ve3 in
  print_endline("Warnings for test_applicant_5: " ^ string_of_int ve3c); *)
  