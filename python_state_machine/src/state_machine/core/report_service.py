import logging, sys, json
from state_machine.core.config import *
from typing import List
from .model import *

logger = logging.getLogger(__name__)

def create_md(state_instance: State)-> List[str]:
    lines_to_write = [
        "# Scenario Report\n\n",
        "## Scenario\n",
        f"- **ID:** {state_instance.scenario_id}\n",
        f"- **Status:** {state_instance.status}\n",
    ]
    if state_instance.error is not None:
        lines_to_write.append(f"- **Error:**\n")
        if state_instance.error.step is not None:
            lines_to_write.append(f"    - step: {state_instance.error.step}\n")
            lines_to_write.append(f"    - event: {state_instance.error.event}\n")
            lines_to_write.append(f"    - message: {state_instance.error.message}\n")
            lines_to_write.append(f"- **Last state:** {state_instance.last_state}\n") 
            lines_to_write.append(f"- **Last context:**\n")  
            lines_to_write.append(f"    - revival_signals: {state_instance.last_context.revival_signals}\n")
            lines_to_write.append(f"    - qa_rejections: {state_instance.last_context.qa_rejections}\n")
            lines_to_write.append(f"    - sprints_ignored: {state_instance.last_context.sprints_ignored}\n\n")
        else:    
            lines_to_write.append(f"    - phase: {state_instance.error.phase}\n")
            lines_to_write.append(f"    - message: {state_instance.error.message}\n")
    else:
        lines_to_write.append(f"- **Final state:** {state_instance.final_state}\n") 
        lines_to_write.append(f"- **Final context:**\n")   
        lines_to_write.append(f"    - revival_signals: {state_instance.final_context.revival_signals}\n")
        lines_to_write.append(f"    - qa_rejections: {state_instance.final_context.qa_rejections}\n")
        lines_to_write.append(f"    - sprints_ignored: {state_instance.final_context.sprints_ignored}\n\n")
    if state_instance.steps is not None:
        lines_to_write.append("## Events\n")
        for s in state_instance.steps:
            lines_to_write.append(f"{s.step}. {s.event}\n")
            lines_to_write.append(f"    - resulting_state: {s.resulting_state} \n")
            lines_to_write.append(f"    - context:\n")
            lines_to_write.append(f"        - revival_signals: {s.resulting_context.revival_signals}\n")
            lines_to_write.append(f"        - qa_rejections: {s.resulting_context.qa_rejections}\n")
            lines_to_write.append(f"        - sprints_ignored: {s.resulting_context.sprints_ignored}\n")
    return lines_to_write

def create_report(file,json_data):
    list = []
    instance = None
    lines = []
    logger.info(f"Incoming file: {file}")
    if not file or len(file.strip()) == 0:
        file = os.path.join(get_report_full_path(), get_file_name_report())  
        logger.info(f"Will use default path: {file}")
    try:
        data = json.loads(json_data)
        if isinstance(data, dict):
            instance = State.model_validate_json(json_data)  
            logger.info(f"Scenario: {instance.scenario_id} parsed.")
            lines = create_md(instance)    
            store_report(file, lines)
        elif isinstance(data, list):
            for scenario in list:
                instance = State.model_validate_json(json.dumps(scenario))  
                logger.info(f"Scenario: {instance.scenario_id} parsed.")
                lines = create_md(instance)    
                file = os.path.join(get_report_full_path(),'report-'+instance.scenario_id+'.md')
                store_report(file, lines)
    except Exception as e:
        logger.error(f"Error parsing the json: {e}")   
        return


def store_report(file, lines_to_write: List[str]):
    try:   
        with open(file, "w") as f:
            f.writelines(lines_to_write)
            logger.info(f'Report file created: {file}')
    except Exception as e:
        logger.error(f"Failed to write file {file}: {e}") 
        sys.exit(1)    
        


   