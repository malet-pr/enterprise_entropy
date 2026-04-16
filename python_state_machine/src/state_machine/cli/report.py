import logging
import os, json
from .utils import *
from state_machine.core.config import *
import state_machine.core.report_service as crs
from pathlib import Path

logger = logging.getLogger(__name__)
    
def create_report(args):
    in_file = get_input_file_from_args(args)
    sm_data = get_data_full_path()
    in_default = get_file_name_data()
    out_file = get_output_file_from_args(args)
    reports = get_report_full_path()
    out_default = get_file_name_report()
    if out_file is not None:
        file = os.path.join(reports, out_file)
    else:    
        file = os.path.join(reports, out_default)  
    logger.info(f"Output path: {file}")
    raw_text = ""
    try:
        if in_file is not None:
            input_path = Path(os.path.join(sm_data, in_file))
        else:
            input_path = Path(os.path.join(sm_data, in_default))
        logger.info(f"Input path: {input_path}")
        raw_text = Path(input_path).read_text(encoding="utf-8")
    except Exception as e:
        logger.error(f"Error reading/parsing json: {e}")
        return
    if not raw_text.strip():
        logger.info("Unable to obtain data. No report will be created.")
        return
    try:
        json_data = json.loads(raw_text)
    except json.JSONDecodeError as e:
        logger.error(f"Broken json: {e}")
        return
    if not json_data:
        logger.info("JSON is valid but empty. Report will not be created.")
        return
    logger.info("Data obtained. Sending to process report...")
    crs.create_report(file, json.dumps(json_data))
 
        
        
    
    

 
    