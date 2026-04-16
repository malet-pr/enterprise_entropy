from blessed import Terminal
from .constants import MENU_ITEMS,STATES,EVENTS
from .integration import *
import logging, json
from collections import defaultdict

logger = logging.getLogger(__name__)

term = Terminal()

def render_menu(selected_index):
    print(term.home + term.clear)
    print(term.bold("State Machine CLI\n"))
    for i, item in enumerate(MENU_ITEMS):
        if i == selected_index:
            print(term.reverse(f"> {item}"))    
        else:
            print(f"  {item}") 
    print("\nUse ↑ ↓ to navigate, Enter to select, Esc to exit")



def render_message_screen(message):
    print(term.home + term.clear)
    print(message) 
    

def render_state_options(selected_index):
    print(term.home + term.clear)
    print(term.bold("Select an Initial State\n"))
    for i, item in enumerate(STATES):
        if i == selected_index:
            print(term.reverse(f"> {item}"))    
        else:
            print(f"  {item}") 
    print("\nUse ↑ ↓ to navigate, Enter to select, Esc to select a default.")    
    
def render_events_layout(EVENTS, sel_idx, sel_events_pre, logs, buttons, button_idx, sel_events_post,focus_button):
    print(term.clear + term.home)
    w_half, h_half = term.width // 2, term.height // 2
    # --- LEFT HALF ---
    with term.location(2, 1):
        print(term.bold_purple_underline("EVENTS"))
    for i, item in enumerate(EVENTS):
        with term.location(4, i + 3):
            style = term.black_on_white if i == sel_idx else term.normal
            if i == sel_idx:
                print(term.reverse(f"> {item}"))    
            else:
                print(f"  {item}") 
    # --- RIGHT-TOP ---
    with term.location(w_half + 2, 1):
        print(term.bold_purple_underline("SELECTED"))
    if sel_events_pre:
        for i, item in enumerate(sel_events_pre):
            with term.location(w_half + 4, i + 3):
                print(f"• {item}")
    # --- RIGHT-BOTTOM ---
    with term.location(w_half + 2, h_half + 1):
        print(term.bold_purple_underline("ACTIONS"))
    btn_line = ""
    for i, btn in enumerate(buttons):
        style = term.black_on_purple if i == button_idx and focus_button else term.normal
        btn_line += f" {style} {btn} {term.normal}  "
    with term.location(w_half + 4, h_half + 3):
        print(btn_line)

    
def render_form(initial_context,edited_fields,buttons):
    print(term.clear + term.home)
    print(term.bold_purple_underline("INITIAL CONTEXT"))
    active_field = None
    for i, (label, value) in enumerate(initial_context.items()):
        y = 4 + i * 2
        text_color = term.bright_black if label not in edited_fields else term.white
        if active_field == label:
            box = term.black_on_purple
        else:
            box = term.on_gray 
        formatted_text= f" {value:5} "
        print(term.move_yx(y, 2) + f"{label}: " + box(text_color(formatted_text)) + term.normal)
    for name, (y, x) in buttons.items():
        color = term.white_on_purple 
        print(term.move_yx(y, x) + color(f" {name} ") + term.normal)
        
def render_scenario(report_buttons,scenario):
    print(term.home + term.clear)
    print(term.bold("Scenario Retrieved\n"))   
    if scenario:
        scenario_id = scenario.get("scenario_id")
        initial_context = scenario.get("initial_context")
        initial_state = scenario.get("initial_state")
        events = len(scenario.get("events"))
        print(f"ID: {scenario_id}\nInitial State: {initial_state}\nQuantity of Events: {events}\nInitial Context: {initial_context}")
    else:    
        print(f"ID: ''\nInitial State: ''\nQuantity of Events: ''\nInitial Context: ''")    
    for name, (y, x) in report_buttons.items():
        color = term.white_on_purple 
        print(term.move_yx(y, x) + color(f" {name} ") + term.normal)      
        
def render_report_data(report_buttons,report_data):
    print(term.home + term.clear)
    print(term.bold("Report Data\n"))      
    if report_data:
        scenario_id = report_data.get("scenario_id")  
        status = report_data.get("status") 
        final_state = report_data.get("final_state")
        last_state = report_data.get("last_state")    
        steps = report_data.get("steps")
        if steps:
            q_steps = len(steps)
        error = report_data.get("error")
        if error:
            phase = error.get("phase")
            error_event = error.get("event")
            error_message = error.get("message")
        if status == 'ok' :
            print(f"ID: {scenario_id}\nStatus: {status}\nFinal State: {final_state}\nQuantity of Steps: {q_steps}")
        elif status == 'error' and last_state :    
            print(f"ID: {scenario_id}\nStatus: {status}\nLast State: {last_state}\nFailed Event: {error_event}\nFailure Message: {error_message}\nQuantity of Steps Completed: {q_steps}")
        else:
            print(f"ID: {scenario_id}\nStatus: {status}\nPhase: {phase}\nFailure Message: {error_message}")   
    else:
        print(f"Unable to retrieve report data.")   
    for name, (y, x) in report_buttons.items():
        color = term.white_on_purple 
        print(term.move_yx(y, x) + color(f" {name} ") + term.normal)      