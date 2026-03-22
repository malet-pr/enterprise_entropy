import argparse
import logging
import json
import os, sys
from config import set_output_path, get_file_name

scenario = {
    "scenario_id": "SCN-001",
    "meeting": {
        "type": "Daily",
        "duration": 15
    },
    "participants": [
        {"name": "John", "role": "TechLead", "interested": True, "understands": True},
        {"name": "Mary", "role": "Developer", "interested": True, "understands": False}
    ],
    "issue": {
        "priority": "High",
        "understood_by": ["TechLead"]
    }
}

# scenario = {"test": set([1, 2, 3])}

def create_scenarios(args):
    arg = get_args(args)
    if arg is not None:
        file = os.path.join(set_output_path(), arg+'.json')
    else:    
        file = os.path.join(set_output_path(), get_file_name())
    try:    
        with open(file, "w") as f:
            json.dump([scenario], f, indent=2)
            print(f'File {file} created.')
    except Exception as e:
        logging.error(f"Failed to write file {file}: {e}") 
        sys.exit(1)    
    
    
# This is just to run an initial test, it will be deleted.
def get_args(args: argparse.Namespace) -> str:
    if hasattr(args, 'fileName') and args.fileName is not None:
        return args.fileName
    else:
        return None 