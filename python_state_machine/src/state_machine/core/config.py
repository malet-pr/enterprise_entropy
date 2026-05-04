from pathlib import Path
from dotenv import load_dotenv
import os, sys
import logging

load_dotenv()

# Parmeters
FILE_NAME_DATA = os.getenv('FILE_NAME_DATA','sm_output.json')
FILE_NAME_REPORT = os.getenv('FILE_NAME_REPORT', 'report.md')
FILE_NAME_SCENARIO = os.getenv("FILE_NAME_SCENARIO",'scenario.json')
STATE_MACHINE_PATH_DATA = os.getenv('STATE_MACHINE_PATH_DATA')
STATE_MACHINE_PATH_REPORT = os.getenv('STATE_MACHINE_PATH_REPORT')
STATE_MACHINE_PATH_SCENARIO = os.getenv('STATE_MACHINE_PATH_SCENARIO')
OCAML_PATH = os.getenv('OCAML_PATH')

# Logging config
logging.basicConfig(
    filename="./logs/state-machine.log",
    level=logging.INFO,
    format="%(asctime)s | %(name)s | %(levelname)s | %(message)s"
)
logger = logging.getLogger(__name__)
logger.info("=== New State Machine Session ===")


def get_file_name_data(): 
    return FILE_NAME_DATA 

def get_file_name_report(): 
    return FILE_NAME_REPORT 

def get_file_name_scenario(): 
    return FILE_NAME_SCENARIO 

def get_data_full_path(): 
    return STATE_MACHINE_PATH_DATA

def get_report_full_path(): 
    return STATE_MACHINE_PATH_REPORT  

def get_scenario_full_path(): 
    return STATE_MACHINE_PATH_SCENARIO 

def get_ocaml_path():
    return OCAML_PATH

def get_default_output_path(type:str) -> Path:
    """
    Defines a path to store outputs or read inputs relative to 'files' in the current directory.
    """
    if type == 'data':
        default_path = Path(f"files/data")
    elif type == 'reports':    
        default_path = Path(f"files/reports")
    elif type == 'scenarios':
        default_path = Path(f"files/scenarios")    
    logging.debug(f"Using default path: {default_path}")  
    return default_path

def check_and_create_path(path: Path) -> Path:
    """
    Verifies if path exists or creates it.
    Should path exists but it's not a folder, throws an error.
    Returns default path object
    """
    try:
        if path.exists():
            if not path.is_dir():
                logging.error(f"Error: the path exixts but it's not a folder: {path}")
                sys.exit(1)
        else:
            path.mkdir(parents=True, exist_ok=True)
            logging.info(f"Path crated: {path}")
        return path   
    except Exception as e:
        logging.error(f"Fatal error creating '{path}': {e}")
        sys.exit(1)

def set_path(type:str) -> Path:
    """
    Returns output path via ENV or via OS default. Secures path existence.
    Returns path object.
    """
    if type == 'data':
        full_path_data = os.getenv('STATE_MACHINE_PATH_DATA')
        if full_path_data:
            path = check_and_create_path(Path(full_path_data)) 
        else:
            path = check_and_create_path(get_default_output_path('data')) 
    elif type == 'reports':   
        full_path_reports = os.getenv('STATE_MACHINE_PATH_REPORTS')
        if full_path_reports:
            path = check_and_create_path(Path(full_path_reports)) 
        else:
            path = check_and_create_path(get_default_output_path('reports')) 
    elif type == 'scenarios':   
        if STATE_MACHINE_PATH_SCENARIO:
            path = check_and_create_path(Path(STATE_MACHINE_PATH_SCENARIO)) 
        else:
            path = check_and_create_path(get_default_output_path('scenarios')) 
    return path        
    
