from pydantic import BaseModel, RootModel
from typing import List,Optional
import logging
from .model import *

logger = logging.getLogger(__name__)


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
  
  
  