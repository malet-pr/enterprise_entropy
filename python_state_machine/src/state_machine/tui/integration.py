import json
import logging
import core.scenario_service as css

logger = logging.getLogger("tui")

def write_scenario_payload(payload: dict) -> None:
    scenario = json.dump(payload,indent=2)
    css.create_scenarios(scenario)

