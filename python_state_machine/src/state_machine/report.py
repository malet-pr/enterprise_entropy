import logging
import json
import os, sys
from config import *


lines_to_write = [
    "# Scenario Report\n\n",
    "## Scenario\n",
    "- **ID:** SCN-001\n",
    "- **Number of steps:** 18\n",
    "- **Final state:** EntropyComplete\n",
    "- **Feature completed:** NO\n\n",
    "## Events\n",
    "1- ClarifySomehow\n",
    "2- StartAnyway\n",
    "3- DiscoverDisagreement\n",
    "4- Postpone\n",
    "5- ForgetForLongTime\n",
    "6- ForgetForLongTime\n",
    "7- CustomerComplains\n",
    "8- ExecutiveRemembers\n",
    "9- DiscoverDisagreement\n",
    "10- StartAnyway\n",
    "11- SendToQA\n",
    "12- Rework\n",
    "13- SendToQA\n",
    "14- RejectFundamentally\n",
    "15- Postpone\n",
    "16- ForgetForLongTime\n",
    "17- AuditDiscovers\n",
    "18- DeclareEntropyComplete\n\n",
    "## Warnings\n",
    "The feature was abandoned.\n\n",
   " ## Summary\n",
    "This scenario ended in `EntropyComplete`. It was abandoned after several attempts, disagreement among developers in how to proceed, and QA rejection.\n\n"
]

def create_report(args):
    arg = get_args(args)
    if arg is not None:
        file = os.path.join(set_output_path(), arg+'.md')
    else:    
        file = os.path.join(set_output_path(), get_file_name_report())
    try:    
        with open(file, "w") as f:
            f.writelines(lines_to_write)
            print(f'File {file} created.')
    except Exception as e:
        logging.error(f"Failed to write file {file}: {e}") 
        sys.exit(1)    
    
