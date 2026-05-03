type severity = 
| Warning
| Error


type validation_error = {
  code : string;
  message : string;
  severity: severity;
}

type 'a validator = 'a -> validation_error option

type applicant = {
  name : string;
  age : int;
  deposit : int;
  has_income: bool;
  validated: bool;
}

type validation_summary = {
  warnings : validation_error list;
  errors : validation_error list;
  is_valid : bool;
}

type 'a validation_result =
  | Valid of 'a
  | Invalid of validation_error list

type valid_with_warnings = {
  applicant: applicant;
  warnings: validation_error list;
}  

type validation_outcome =
  | OK of valid_with_warnings
  | NOK of validation_error list

