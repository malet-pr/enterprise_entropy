import logging, json
import subprocess
from .config import *
from .model import *
from .scenario_service import create_scenario
from pathlib import Path

logger = logging.getLogger(__name__)

machine_path = Path(get_ocaml_path())  
scenario_path = Path(get_scenario_full_path(),get_file_name_scenario())
result_path = Path(get_data_full_path(),get_file_name_data())

def read_machine_result (filename=None):
    data = None
    if filename is not None:
        file = os.path.join(get_data_full_path(), filename)
    else:    
        file = os.path.join(get_data_full_path(), get_file_name_data())    
    logger.info(f"Reading report data form file {file}")
    try:    
        with open(file, "r") as f:
            data = json.load(f)
            return data
        if not data :
            logger.error(f"No data was recovered ")
            return None
    except Exception as e:
        logging.error(f"Failed to read file {file}: {e}") 
        return None

    
def call_ocaml():
    logger.info(f'Sending to ocaml...')
    subprocess.run(['dune', 'exec', 'ocaml_state_machine', scenario_path, result_path],  cwd=machine_path)

def call_ocaml_multiple(scenarios: list[Scenario]) -> None:
    logger.info(f'Sending multiple scenarios to ocaml...')
    result = []
    for scenario in scenarios:
        logger.info(f"Sending scenario {scenario["scenario_id"]}")
        create_scenario(scenario,None)
        subprocess.run(['dune', 'exec', 'ocaml_state_machine', scenario_path, result_path],  cwd=machine_path)
        data = read_machine_result()
        logger.info(f"Data: {data}")
        result.append(data.copy())   
        logger.info(f"# results: {len(result)}")
    try:    
        with open(result_path, "w") as f:
            json.dump(result, f, indent=2)
            logger.info(f'File {result_path} created.')   
    except Exception as e:
        logging.error(f"Failed to write file {result_path}: {e}") 
        sys.exit(1)    
   
    
    
    

