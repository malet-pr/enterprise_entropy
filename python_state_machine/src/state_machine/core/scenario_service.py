import logging
import json
import os, sys
from .config import *
from .model import *

logger = logging.getLogger(__name__)

def create_scenario (scenario,filename=None):
    logger.info(f'Scenario {scenario["scenario_id"]} received for processing.')
    if filename is not None:
        file = os.path.join(set_path('scenarios'), filename)
    else:    
        file = os.path.join(set_path('scenarios'), get_file_name_scenario())    
    try:    
        with open(file, "w") as f:
            json.dump(scenario, f, indent=2)
            logger.info(f'File {file} created.')   
    except Exception as e:
        logging.error(f"Failed to write file {file}: {e}") 
        sys.exit(1)    
    
