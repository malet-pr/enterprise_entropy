import logging
import json
import os, sys
from .config import *
from .model import *

logger = logging.getLogger(__name__)

def create_scenarios (scenario,filename=None):
    logger.info(f'Scenario {scenario["scenario_id"]} received for processing.')
    if filename is not None:
        file = os.path.join(set_path('scenarios'), filename)
    else:    
        file = os.path.join(set_path('scenarios'), get_file_name_scenario())    
    try:    
        with open(file, "w") as f:
            json.dump([scenario], f, indent=2)
            logger.info(f'File {file} created.')
    except Exception as e:
        logging.error(f"Failed to write file {file}: {e}") 
        sys.exit(1)    
    
def read_scenario (filename=None):
    data = None
    if filename is not None:
        file = os.path.join(get_data_full_path(), filename)
    else:    
        file = os.path.join(get_data_full_path(), get_file_name_scenario())    
    logger.info(f"Reading scenario form file {file}")
    try:    
        with open(file, "r") as f:
            data = json.load(f)
        if not data :
            logger.error(f"No data was recovered ")
            return None
        return data
    except Exception as e:
        logging.error(f"Failed to read file {file}: {e}") 
        return None
    """    
    try:
        State.model_validate_json(data) 
        return data
    except Exception as e:
        logging.error(f"The data obtained does not match the model {e}")
        return None
     """