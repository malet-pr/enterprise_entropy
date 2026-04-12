import json, os, logging
from .utils import *
from state_machine.core.config import *
import state_machine.core.scenario_service as css

logger = logging.getLogger(__name__)

    
def read_scenario(args):
    in_file = get_input_file_from_args(args)
    logger.info(f"input file name: {in_file}")
    sm_data = get_data_full_path()
    if in_file is not None:
        file = os.path.join(sm_data, in_file)
    else:
        file = os.path.join(sm_data, 'scenario.json')    
    logger.info(f"full input file: {file}")
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
    logger.info(f"output file name: {out_file}")
    if out_file is not None:
        file = os.path.join(css.set_path('scenarios'), out_file)
    else:    
        file = os.path.join(css.set_path('scenarios'), get_file_name_scenario())
    payload = read_scenario(args) 
    if payload is not None:  
        logger.info(f"full output file: {file}")
        css.create_scenarios(payload,file)
    else:
        logger.error("Payload cannot be null")    

    