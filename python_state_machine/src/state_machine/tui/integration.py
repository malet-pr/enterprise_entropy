import json
import logging
import state_machine.core.scenario_service as css
import state_machine.core.report_service as crs
import state_machine.core.config as cc

logger = logging.getLogger(__name__)

def write_scenario_payload(scenario_payload: dict) -> None:
    if not scenario_payload:
        logger.info(f"Payload is empty. No scenarios will be sent.")
        return
    logger.info(f"Scenario {scenario_payload["scenario_id"]} sent for processing...")    
    css.create_scenarios(scenario_payload)

def read_report_data(file_name: str = None) -> json:
    data = crs.read_machine_result(file_name)
    if not data:
        logger.info(f"No data was obtained.")
        return
    logger.info(f"Data read: {data}")
    return data

def generate_report(report_data: json) -> None:
    if not report_data:
        logger.info(f"Emtpy report_data. No report will be created.")
    logger.info(f'Sending report_data {report_data.get("scenario_id")} to generate report...')   
    crs.create_report(None,json.dumps(report_data)) 

