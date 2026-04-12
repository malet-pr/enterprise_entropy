import argparse, logging

logger = logging.getLogger(__name__)

def get_input_file_from_args(args: argparse.Namespace) -> str:
    if not isinstance(args, argparse.Namespace):
        raise TypeError(f"read_scenario expected argparse.Namespace, got {type(args)}")
    if hasattr(args, 'input_file') and args.input_file is not None:
        return args.input_file
    else:
        return None
    
def get_output_file_from_args(args: argparse.Namespace) -> str:  
    if not isinstance(args, argparse.Namespace):
        raise TypeError(f"read_scenario expected argparse.Namespace, got {type(args)}")
    if hasattr(args, 'output_file') and args.output_file is not None:
        return args.output_file
    else:
        return None    