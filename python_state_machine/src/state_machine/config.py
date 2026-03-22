from pathlib import Path
from dotenv import load_dotenv
import os, sys
import logging

load_dotenv()

# Parmeters
OUTPUT_FILE_PATH = os.getenv('OUTPUT_FILE_PATH')
PATH_NAME = os.getenv('PATH_NAME','ocaml')
FILE_NAME = os.getenv('FILE_NAME','scenario.json')

# Logging config
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')

def get_file_name(): 
    return FILE_NAME

def get_default_output_path() -> Path:
    """
    Defines a path to store outputs relative to 'output-files' in the current directory.
    """
    default_path = Path(f"output-files/{PATH_NAME}")
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
            logging.info(f"Output path crated: {path}")
        return path   
    except Exception as e:
        logging.error(f"Fatal error creating '{path}': {e}")
        sys.exit(1)

def set_output_path() -> Path:
    """
    Returns output path via ENV or via OS default. Secures path existence.
    Returns path object.
    """
    output_path_env = os.getenv('STATE_MACHINE_OUTPUT_PATH')
    if output_path_env:
        output_path = Path(output_path_env)
    else:
        output_path = get_default_output_path()
    final_path = check_and_create_path(output_path)
    return final_path

