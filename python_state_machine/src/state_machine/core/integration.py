import logging
import subprocess
from .config import *
from .model import *
from pathlib import Path

logger = logging.getLogger(__name__)

machine_path = Path(get_ocaml_path())  
scenario_path = Path(get_scenario_full_path(),get_file_name_scenario())
result_path = Path(get_data_full_path(),get_file_name_data())

def run_scenario(scenario:Scenario) -> State:
    None
    
def call_ocaml():
    logger.info(f'Sending to ocaml...')
    subprocess.run(['dune', 'exec', 'ocaml_state_machine', scenario_path, result_path],  cwd=machine_path)
    

    
    

