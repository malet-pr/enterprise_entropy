import logging
import json
import os, sys
from .config import *

def create_scenarios (scenario,filename=None):
    if filename is not None:
        file = os.path.join(set_path('scenarios'), filename)
    else:    
        file = os.path.join(set('scenarios'), get_file_name_scenario())
    try:    
        with open(file, "w") as f:
            json.dump([scenario], f, indent=2)
            print(f'File {file} created.')
    except Exception as e:
        logging.error(f"Failed to write file {file}: {e}") 
        sys.exit(1)    
    
