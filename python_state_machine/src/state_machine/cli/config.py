from pathlib import Path
from dotenv import load_dotenv
import os, sys
import logging
import argparse

load_dotenv()

# Parmeters
FILES_PATH = os.getenv('FILES_PATH')
DATA_PATH = os.getenv('DATA_PATH','data')
REPORT_PATH = os.getenv('REPORT_PATH','reports')
FILE_NAME_DATA = os.getenv('FILE_NAME_DATA','output.json')
FILE_NAME_REPORT = os.getenv('FILE_NAME_REPORT', 'report.md')

# Logging config
logging.basicConfig(
    filename="../../../logs/cli.log",
    level=logging.INFO,
    format="%(asctime)s | %(levelname)s | %(message)s"
)
logger = logging.getLogger("cli")
logger.info("=== New CLI session ===")


def get_file_name_data(): 
    return FILE_NAME_DATA 

def get_file_name_report(): 
    return FILE_NAME_REPORT 

def get_args(args: argparse.Namespace) -> str:
    if hasattr(args, 'fileName') and args.fileName is not None:
        return args.fileName
    else:
        return None 

def get_default_output_path(type:str) -> Path:
    """
    Defines a path to store outputs or read inputs relative to 'files' in the current directory.
    """
    if type == 'data':
        default_path = Path(f"files/{DATA_PATH}")
    elif type == 'reports':    
        default_path = Path(f"files/{REPORT_PATH}")
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
    return path        
    

