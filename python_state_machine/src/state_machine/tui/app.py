from blessed import Terminal
import logging
from core.config import *
from constants import MENU_ITEMS,STATES,EVENTS
from render import *
from input import *
from integration import *


logging.basicConfig(
    filename="/home/nuria/enterprise_entropy/python_state_machine/logs/tui.log",
    level=logging.INFO,
    format="%(asctime)s | %(levelname)s | %(message)s"
)

logger = logging.getLogger("tui")
logger.info("=== New TUI session ===")

term = Terminal()

def run_tui():
    selected_index = 0
    selected_item = ""
    current_screen = "menu"
    running = True
    value = ""
    error = None
    
    with term.fullscreen(), term.hidden_cursor(), term.cbreak():
        while running:
            error = None
            if current_screen == "menu":
                render_menu(selected_index)
                key = term.inkey()
                if key.name == "KEY_UP":
                    selected_index = (selected_index - 1) % len(MENU_ITEMS)
                elif key.name == "KEY_DOWN":
                    selected_index = (selected_index + 1) % len(MENU_ITEMS)
                elif key.name == "KEY_ENTER" or key == "\n":
                    selected_item = MENU_ITEMS[selected_index]
                    if selected_item != "exit":
                        logger.info(f"Selected {selected_item}.")
                    if selected_item == "exit":
                        logger.info(f"Exit tui.")
                        running = False
                    else:
                        current_screen = selected_item
                elif key.name == "KEY_ESCAPE":
                    logger.info("Exit tui (ESC).")
                    running = False
            else:
                if current_screen == "create_scenario":
                    scenario_id = prompt_input("Scenario ID")
                    initial_state = select_initial_state(term)
                    events_list = select_events(term)
                    initial_context = context_imput(term)
                    render_message_screen(
                        f"Scenario created:\n\nID: {scenario_id}\nInitial State: {initial_state}\nSelected Events: {events_list}\nInitial Context: {initial_context}"
                    )                    
                    logger.info(f"[create_scenario] Scenario created: [{scenario_id},{initial_state},{events_list},{initial_context}]")
                    scenario_payload = {
                        "scenario_id": scenario_id,
                        "initial_context": initial_context,
                        "initial_state": initial_state,
                        "events": events_list,
                    }            
                    write_scenario_payload(scenario_payload,STATE_MACHINE_PATH_DATA)
                    term.inkey()
                    current_screen = "menu"
                elif current_screen == "create_report":
                    logger.info(f"Entered {current_screen}.")
                    render_message_screen("Entered create_report")
                    term.inkey()
                    current_screen = "menu"
                elif current_screen == "run_all":
                    logger.info(f"Entered {current_screen}.")
                    render_message_screen("Entered run_all")
                    term.inkey()
                    current_screen = "menu"
                    


if __name__ == "__main__":
    run_tui()