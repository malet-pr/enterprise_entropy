type severity =
  | Warning
  | Error

type validation_error = {
  code : string;
  message : string;
  severity : severity;
}

type 'a validator = 'a -> validation_error option

type 'a validation_result =
  | Valid of 'a
  | Invalid of validation_error list

type 'a step = 'a -> 'a validation_result
  
