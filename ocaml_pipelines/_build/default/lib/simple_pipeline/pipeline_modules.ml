open Pipeline_model

module ApplicantValidation = struct
  
  let name_required : applicant validator =
    fun a ->
      if a.name = "" then Some {code="NAME"; message="name is required"; severity=Warning} else None

  let must_be_adult : applicant validator =
    fun a ->
      if a.age < 18 then Some {code="AGE"; message="must be adult"; severity=Error} else None

  let deposit_positive : applicant validator =
    fun a ->
      if a.deposit <= 0 then Some {code="AMOUNT"; message="deposit must be positive"; severity=Error} 
      else None

  let young_unemployed : applicant validator =
    fun a -> 
      if (a.age >= 18 && a.age < 25 && not a.has_income) then 
        Some {code="INCOME"; message="young applicants must have an income"; severity=Error} else None

  let run_validators validators value =
    List.filter_map (fun v -> v value) validators

  let validate_with_summary validators applicant = 
    let errors = run_validators validators applicant in
    if errors = [] then Valid applicant else Invalid errors       

  let rec first_error_validator validators value =
    match validators with
    | [] -> None
    | v :: rest ->
        match v value with
        | Some err -> Some err
        | None -> first_error_validator rest value  

  let first_error_validator_map validators value =
    List.find_map (fun v -> v value) validators   
    
  let validate_outcome_summary validators applicant =
    let errors = run_validators validators applicant in
    let warnings, errors =
      List.partition_map (fun e ->
          if e.severity = Warning then Either.Left e
          else Either.Right e
        ) errors in
    let valid_plus_warnings = {applicant=applicant;warnings=warnings} in    
    if errors = [] then OK valid_plus_warnings else NOK errors

end

module ApplicantValidationResult = struct
  
  let summarize (es: validation_error list) =
    let warnings, errors =
      List.partition_map (fun e ->
          if e.severity = Warning then Either.Left e
          else Either.Right e
        ) es in
      let is_valid = (List.length errors) = 0 in
      {
        warnings;
        errors;
        is_valid;
      }  

  let map_errors_list f es = 
    List.fold_left(fun acc e -> 
        if e.severity = Error then (f e) :: acc
        else e :: acc
      ) [] es

  let map_warnings f es = 
    List.fold_left(fun acc e -> 
        if e.severity = Warning then (f e) + acc
        else  acc
      ) 0 es

  let map_error f = function
  | Valid a -> Valid a
  | Invalid es -> Invalid (List.map f es) 

  let map_error_list f = function
  | Valid a -> Valid a
  | Invalid es -> Invalid (f es) 

  let map_valid f = function
  | Valid a -> Valid (f a)
  | Invalid es -> Invalid es

  let map_valid_outcome f = function
  | OK a -> OK (f a)
  | NOK es -> NOK es

  let combine_results (vrs : 'a validation_result list) : 'a list validation_result =
    let rec aux vrs applicant errors = 
      match vrs with
      | [] -> if errors = [] then Valid (List.rev applicant) 
                                        else Invalid (List.concat (List.rev errors))
      | h :: t -> 
          match h with
          | Valid x -> aux t (x :: applicant) errors
          | Invalid es -> aux t applicant (es :: errors)
      in aux vrs [] []

  let bind_result : ('a -> 'b validation_result) -> 'a validation_result -> 'b validation_result =
    fun f b ->
      match b with
      | Valid a -> f a
      | Invalid es -> Invalid es    

  let bind_outcome : ('a -> validation_outcome) -> validation_outcome ->  validation_outcome =
    fun f b ->
      match b with
      | OK a -> f a
      | NOK es -> NOK es       

end

module ApplicantString = struct
  
  let string_of_severity = function
  | Warning -> "Warning"
  | Error -> "Error"

  let string_of_error (e: validation_error) : string = 
    "{\"code\":" ^ "\"" ^ e.code ^ "\"" ^ ", \"message\":" ^ "\"" ^ e.message ^ "\"" ^ ", \"severity\":" ^ "\"" ^ string_of_severity e.severity ^ "\"" ^"}"

  let string_of_error_option (e: validation_error option) : string = 
    match e with
    | None -> ""
    | Some e -> string_of_error e

  let string_of_error_list  (es: validation_error list) : string = 
    "[" ^ String.concat ", "( List.map string_of_error es )^ "]"

  let string_of_message_list (es: validation_error list) : string =
    let messages = List.map (fun e -> "\"" ^ e.message ^ "\"") es in
    "[" ^ String.concat ", " messages ^ "]"

  let string_of_validation_result  = function
    | Valid x -> "Valid: " ^ x.name ^ " - validated: " ^ string_of_bool x.validated
    | Invalid xs -> "Invalid: " ^ string_of_message_list xs    
    
  let string_of_summary_messages (s: validation_summary) : string =
    "{"
    ^ "\"warnings\": " ^ string_of_message_list s.warnings
    ^ ", \"errors\": " ^ string_of_message_list s.errors
    ^ ", \"is_valid\": " ^ string_of_bool s.is_valid
    ^ "}"  

  let string_of_summary (s: validation_summary) : string =
    "{"
    ^ "\"warnings\": " ^ string_of_error_list s.warnings
    ^ ", \"errors\": " ^ string_of_error_list s.errors
    ^ ", \"is_valid\": " ^ string_of_bool s.is_valid
    ^ "}"

  let string_of_message_list_column (es: validation_error list) : string =
    let messages = List.map (fun e -> "    - \"" ^ e.message ^ "\"") es in
    let lines = String.concat "\n" messages in
    if lines = "" then "    (none)" else lines

  let string_of_valid_with_warnings (vw: valid_with_warnings) : string =
    let name = if vw.applicant.name = "" then "Unknown name" else vw.applicant.name  in
    let warn_list = List.rev (List.fold_left(fun acc e -> string_of_error e :: acc) [] vw.warnings) in
    "Applicant: " ^ name ^ "; Warnings: " ^ if warn_list = [] then "None" 
    else "[" ^ String.concat ", " warn_list ^ "]"

  let string_of_validation_outcome vo =
    match vo with
    | OK vw -> string_of_valid_with_warnings vw
    | NOK es -> "Errors: " ^ string_of_error_list es

end

module ApplicantPrinter = struct

  let pretty_print_summary_messages (s: validation_summary):unit =
    print_endline("Validation Summary:");
    print_endline("  Valid: " ^ (string_of_bool s.is_valid));
    print_endline("  Errors:");
    print_endline(ApplicantString.string_of_message_list_column s.errors);
    print_endline("  Warnings:");
    print_endline(ApplicantString.string_of_message_list_column s.warnings)

end

module ApplicationProcess = struct
  
  let process_applicant applicant validators =
    applicant
    |> ApplicantValidation.run_validators validators
    |> ApplicantValidationResult.summarize

  let print_process_applicant applicant validators =
    applicant
    |> ApplicantValidation.run_validators validators
    |> ApplicantValidationResult.summarize
    |> ApplicantPrinter.pretty_print_summary_messages

end

module DomainOps = struct

  let normalize_name_vr applicant = 
    let upper = String.uppercase_ascii applicant.name in
    match applicant.name with
    | "" -> Invalid [{code="NAME"; message="name is required"; severity=Warning}]
    | x -> Valid {applicant with name = upper}

  let normalize_name applicant =
    let upper = String.uppercase_ascii applicant.name in
    if not (applicant.name = "") then {applicant with name = upper} else applicant

  let validated applicant = 
    {applicant with validated = true} 

  let count_warnings ves =
    List.fold_left(fun acc v -> 
      if v.severity = Warning then (acc + 1) else acc
    ) 0 ves

  let prefix_errors ~prefix ves =
    List.rev (List.fold_left(fun acc v -> 
      if v.severity = Error then  {v with message = prefix ^ " " ^ v.message} :: acc
      else v :: acc
    ) [] ves)

  let prefix_error ~prefix ve =
    if ve.severity = Error then {ve with message = prefix ^ " " ^ ve.message} 
    else ve  

end

module Operators = struct
  open ApplicantValidationResult

  let (>>=) r f = bind_result f r
  let ( let* ) r f = bind_result f r
  let (>>>=) r f = bind_outcome f r

end

module Pipelines = struct
  open Operators
  open ApplicantValidationResult
  open ApplicantValidation
  open DomainOps

  let validate_pipeline_1 applicant validators =
    normalize_name_vr applicant
    >>= validate_with_summary validators
    |> map_valid validated  

  let validate_pipeline_2 applicant validators =
    applicant
    |> normalize_name
    |> fun a -> validate_with_summary validators a
    |> map_valid validated  

  let validate applicant =
    let* a = validate_with_summary [must_be_adult] applicant in
    let* b = validate_with_summary [deposit_positive] a in
    let* c = validate_with_summary [young_unemployed] b in
    normalize_name_vr c
    |> map_valid validated

  let validate_all applicant validators =
    validate_with_summary validators applicant  

  let validate_first_error applicant =
    validate applicant  

  let validate_outcome_pipeline validators applicant  =
    applicant
    |> normalize_name
    |> fun a -> validate_outcome_summary validators a
        
end  

