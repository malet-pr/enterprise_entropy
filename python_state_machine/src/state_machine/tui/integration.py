import json
import logging
import state_machine.core.scenario_service as css
import state_machine.core.config as cc

logger = logging.getLogger(__name__)

def write_scenario_payload(scenario_payload: dict) -> None:
    if not scenario_payload:
        logger.info(f"Payload is empty. No scenarios will be sent.")
        return
    logger.info(f"Scenario {scenario_payload["scenario_id"]} sent for processing...")    
    css.create_scenarios(scenario_payload)

def read_scenario(file_name: str = None) -> json:
    data = css.read_scenario(file_name)
    if not data:
        logger.info(f"No data was obtained.")
        return
    logger.info(f"Scenario read: {data}")
    return data
