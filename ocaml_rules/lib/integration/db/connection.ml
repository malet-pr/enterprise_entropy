let uri =
  Uri.of_string
    "postgresql://ocaml:ocaml@localhost:5432/enterprise_entropy"

let connect () =
  Caqti_lwt_unix.connect uri