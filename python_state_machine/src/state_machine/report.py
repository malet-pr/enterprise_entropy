import logging
import os, sys
from config import *
from pydantic import BaseModel, RootModel
from typing import List,Optional
from pathlib import Path


class Error(BaseModel):
    step: Optional[int] = None
    event: Optional[str] = None
    phase: Optional[str] = None
    message: str

class Context(BaseModel):
    revival_signals: int
    qa_rejections: int
    sprints_ignored: int
    
class Step(BaseModel):
    step: int
    event: str
    resulting_state: str
    resulting_context: Context    
    
class State(BaseModel):
    scenario_id: str
    status: str
    error: Optional[Error] = None
    last_state: Optional[str] = None
    last_context: Optional[Context] = None
    final_state: Optional[str] = None
    final_context: Optional[Context] = None
    steps: Optional[List[Step]] = None
  

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

def store_report(args, lines_to_write: List[str]):
    arg = get_args(args)
    if arg is not None:
        print(set_path('reports'))
        file = os.path.join(set_path('reports'), arg+'.md')
    else:    
        print(set_path('reports'))
        print(get_file_name_report())
        file = os.path.join(set_path('reports'), get_file_name_report()) 
    try:   
        #file = './files/reports/report.md' 
        with open(file, "w") as f:
            f.writelines(lines_to_write)
            print(f'File {file} created.')
    except Exception as e:
        logging.error(f"Failed to write file {file}: {e}") 
        sys.exit(1)    
    
def create_report(args):
    arg = get_args(args)
    json_data = {}
    if arg is not None:
        json_data = Path(os.path.join(set_path('data'), arg+'.json')).read_text()
    else:  
        json_data = Path(os.path.join(set_path('data'), get_file_name_data())).read_text()   
    instance = State.model_validate_json(json_data)
    lines = create_md(instance) 
    store_report(args, lines)
    

 
    