import logging
import json
import os, sys
from config import *


scenario = {
    "scenario_id": "SCN-001",
    "initial_context": {
        "revival_signals": 0,
        "qa_rejections": 0,
        "sprints_ignored": 0
    },
    "initial_state":"IdeaFog",
    "events": [
        "ClarifySomehow",
        "StartAnyway",
        "DiscoverDisagreement",
        "Postpone",
        "ForgetForLongTime",
        "ForgetForLongTime",
        "CustomerComplains",
        "ExecutiveRemembers",
        "DiscoverDisagreement",
        "StartAnyway",
        "SendToQA",
        "Rework",
        "SendToQA",
        "RejectFundamentally",
        "Postpone",
        "ForgetForLongTime",
        "AuditDiscovers",
        "DeclareEntropyComplete"
    ],
}

# scenario = {"test": set([1, 2, 3])}

def create_scenarios(args):
    arg = get_args(args)
    if arg is not None:
        file = os.path.join(set_output_path(), arg+'.json')
    else:    
        file = os.path.join(set_output_path(), get_file_name_scenario())
    try:    
        with open(file, "w") as f:
            json.dump([scenario], f, indent=2)
            print(f'File {file} created.')
    except Exception as e:
        logging.error(f"Failed to write file {file}: {e}") 
        sys.exit(1)    
    
    