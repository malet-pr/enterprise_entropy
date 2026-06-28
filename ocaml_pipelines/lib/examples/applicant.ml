open Validation_types
open Pipeline
open Step
open Runner
open Utils

(******** TYPES ***********)

type applicant = {
  name : string;
  age : int;
  deposit : int;
  has_income: bool;
  validated: bool;
}

(******** STRINGS ***********)

let string_of_applicant_validation_result  = function
  | Valid x -> "Valid: " ^ x.name 
  | Invalid xs -> "Invalid: " ^ string_of_message_list xs    

(******** RULES ***********)

let name_required applicant =
  if applicant.name = "" then
    Some { code = "NAME"; message = "name is required"; severity = Error }
  else
    None

let normalize_name applicant =
  { applicant with name = String.uppercase_ascii applicant.name }

(******** PIPELINES ***********)  
let applicant_pipeline =
  [
    step_of_validator name_required;
    step_of_transformer normalize_name;
  ]  

(******** EXAMPLES ***********)  

let applicant1 = {
  name = "";
  age = 16;
  deposit = 0;
  has_income = false;
  validated = false;
}

let applicant2 = {
  name = "Marcus";
  age = 16;
  deposit = 0;
  has_income = true;
  validated = false;
}  

let run () = 
  let result1 = run_pipeline applicant1 applicant_pipeline in
  print_endline(string_of_applicant_validation_result result1);
  let result2 = run_pipeline applicant2 applicant_pipeline in
  print_endline(string_of_applicant_validation_result result2);


