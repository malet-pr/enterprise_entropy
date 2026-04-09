import json
from pathlib import Path
from dotenv import load_dotenv
import os, sys
import logging

logger = logging.getLogger("tui")

load_dotenv()
STATE_MACHINE_PATH_DATA = os.getenv('STATE_MACHINE_PATH_DATA','scenario.json')
STATE_MACHINE_PATH_REPORTS = os.getenv('STATE_MACHINE_PATH_REPORTS', 'report.md')

def write_scenario_payload(payload: dict, path: str) -> None:
    logger.info(f"inside function: {payload}")
    logger.info(f"inside function: {STATE_MACHINE_PATH_DATA}")
    output_path = Path(path).expanduser().resolve()
    output_path.parent.mkdir(parents=True, exist_ok=True)
    with output_path.open("w", encoding="utf-8") as f:
        json.dump(payload, f, indent=2)


