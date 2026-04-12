import json, os, argparse, logging
from state_machine.core.config import *
import state_machine.core.scenario_service as css

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
    
def read_scenario(args):
    in_file = get_input_file_from_args(args)
    logger.info(f"inputFile: {in_file}")
    sm_data = get_data_full_path()
    if in_file is not None:
        file = os.path.join(sm_data, in_file)
    else:
        file = os.path.join(sm_data, 'scenario.json')    
    logger.info(f"file: {file}")
    try:
        with open(file, 'r', encoding='utf-8') as file:
            data = json.load(file)
            logger.info(f"data: {data}")
        return data
    except Exception as e:
        logger.error(f"Error reading from file: {e}")
        return None
    
def create_scenarios(args):
    out_file = get_output_file_from_args(args)
    logger.info(f"outputFile: {out_file}")
    if out_file is not None:
        file = os.path.join(css.set_path('scenarios'), out_file)
    else:    
        file = os.path.join(css.set_path('scenarios'), get_file_name_scenario())
    payload = read_scenario(args) 
    if payload is not None:  
        logger.info(f"file: {file}")
        logger.info(f"payload: {payload}")
        css.create_scenarios(payload,file)
    else:
        logger.error("Payload cannot be null")    

    