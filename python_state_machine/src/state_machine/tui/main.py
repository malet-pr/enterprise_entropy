from blessed import Terminal
import logging
from state_machine.core.config import *
import state_machine.core.integration as itg
from .constants import MENU_ITEMS,STATES,EVENTS
from .render import *
from .input import *
from .integration import *

logger = logging.getLogger("state_machine.tui.main")

term = Terminal()

def main():
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
                    scenario = run_create_scenario_flow(term)
                    if scenario:
                        render_message_screen(
                            f"Scenario created:\n\nID: {scenario['scenario_id']}\nInitial State: {scenario['initial_state']}\nSelected Events: {scenario['events']}\nInitial Context: {scenario['initial_context']}"
                        )   
                        logger.info(f"[create_scenario] Scenario created: [{scenario['scenario_id']},{scenario['initial_state']},{scenario['events']},{scenario['initial_context']}]")
                    else:
                        render_message_screen('Something went wrong with scenario creation.')    
                        logger.info('Error creating or saving scenario')
                    print("\n\nPress any key to return to menu")                 
                    term.inkey()
                    current_screen = "menu"
                elif current_screen == "create_report":
                    input_data_for_report(term)
                    print(term.home + term.clear)
                    print("\n\nPress any key to return to menu")  
                    term.inkey()
                    current_screen = "menu"
                elif current_screen == "run_all":
                    scenario = run_create_scenario_flow(term)
                    if scenario:
                        itg.call_ocaml()
                        input_data_for_report(term)
                    else: 
                        print(term.home + term.clear)
                        render_message_screen('Something went wrong with scenario creation.')  
                    print(term.home + term.clear)
                    print("\n\nPress any key to return to menu")       
                    term.inkey()
                    current_screen = "menu"
                    
def run_create_scenario_flow(term) -> json:
    scenario_id = prompt_input("Scenario ID")
    initial_state = select_initial_state(term)
    events_list = select_events(term)
    initial_context = context_imput(term)
    scenario_payload = {
        "scenario_id": scenario_id,
        "initial_context": initial_context,
        "initial_state": initial_state,
        "events": events_list,
    }            
    write_scenario_payload(scenario_payload)
    return scenario_payload

if __name__ == "__main__":
    main()