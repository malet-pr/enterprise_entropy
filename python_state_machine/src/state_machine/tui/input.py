from blessed import Terminal
from .constants import *
from .render import *
from .integration import *
import logging

logger = logging.getLogger(__name__)

term = Terminal()

def prompt_input(label):
    value = ""
    error = None
    while True:
        print(term.home + term.clear)
        print(term.bold("Create Scenario\n"))
        print(term.white(label + ": "))
        if value == "":
            display = "<- type here ->"
        else:
            display = value + "_"
        print(term.purple_underline(display))     
        if error is not None:
            print(term.red(error))
        print("\n(Enter to confirm, Backspace to delete, Esc to cancel)")
        key = term.inkey()
        if key.name == "KEY_ENTER" or key == "\n":
           if len(value.strip()) == 0:
               error ="This field cannot be empty."
               continue
           return value       
        elif key.name in ("KEY_BACKSPACE", "KEY_DELETE") or key == "\x7f":
            value = value[:-1]
        elif key.name == "KEY_ESCAPE":
            logger.info("Exited the screen without any action.")            
            return None
        elif key.is_sequence:
            continue
        else:
            value += key


def select_initial_state(term):
    state_index = 0
    while True:
        render_state_options(state_index)
        key = term.inkey()
        if key.name == "KEY_UP":
            state_index = (state_index - 1) % len(STATES)
        elif key.name == "KEY_DOWN":
            state_index = (state_index + 1) % len(STATES)
        elif key.name == "KEY_ENTER" or str(key) == "\n":
            return STATES[state_index]
        elif key.name == "KEY_ESCAPE":
            return "IdeaFog"
        
def select_events(term):  
    sel_ev = 0
    button_idx = 0
    sel_events_pre = []
    sel_events_post= None
    focus_buttons = False 
    buttons = ["[F]INISH", "[D]ELETE-LAST", "[C]LEAR-ALL"]
    while True:
        render_events_layout(EVENTS, sel_ev, sel_events_pre, [], buttons, button_idx, sel_events_post, focus_buttons)
        key = term.inkey()
        key_lower = key.lower()
        if key_lower == 'f': 
            sel_events_post = list(sel_events_pre)
            return sel_events_post
        elif key_lower == 'd':       
            sel_events_post.pop()
        elif key_lower == 'c':
            sel_events_pre = []                 
        elif key.name == 'KEY_UP' and not focus_buttons:
            sel_ev = (sel_ev - 1) % len(EVENTS)
        elif key.name == 'KEY_DOWN' and not focus_buttons:
            sel_ev = (sel_ev + 1) % len(EVENTS)
        elif key.name == 'KEY_TAB' or str(key) == "\t":
            focus_buttons = not focus_buttons
            button_idx = 0
        elif key.name == 'KEY_LEFT' and focus_buttons:
            button_idx = (button_idx - 1) % len(buttons)
        elif key.name == 'KEY_RIGHT' and focus_buttons:
            button_idx = (button_idx + 1) % len(buttons)
        elif key.name == "KEY_ENTER" or str(key) == "\n":
            if not focus_buttons:
                if len(sel_events_pre) == 0:
                    sel_events_pre = [EVENTS[sel_ev]]
                else:
                    sel_events_pre.append(EVENTS[sel_ev])    
                    sel_events_post = sel_events_pre
            else:
                if button_idx == 0:
                    sel_events_post = list(sel_events_pre)
                    return sel_events_post
                elif button_idx == 1:
                    sel_events_pre.pop()
                elif button_idx == 2:
                    sel_events_pre = []
                    
def context_imput(term):
    active_field = None
    initial_context = {        
        "revival_signals": 0,
        "qa_rejections": 0,
        "sprints_ignored": 0
    }
    edited_fields = set() 
    buttons = {"SAVE": (10, 5), "CLEAN": (10, 20)}
    with term.mouse_enabled():
        while True:
            render_form(initial_context,edited_fields,buttons)
            inp = term.inkey()
            if inp.name == 'KEY_ESCAPE': break
            if inp.name and inp.name.startswith('MOUSE_') and 'LEFT' in inp.name:
                y, x = inp.mouse_yx               
                for name, (by, bx) in buttons.items():
                    if y == by and bx <= x < bx + len(name) + 2:
                        active_field = None
                        if name == "CLEAN":
                            initial_context = { "revival_signals": 0,"qa_rejections": 0,"sprints_ignored": 0}
                            edited_fields.clear()
                        elif name == "SAVE":
                            initial_context.update((k, int(v)) for k, v in initial_context.items())
                            return initial_context         
                for i, label in enumerate(initial_context.keys()):
                    if y == 4 + i * 2 and 2 <= x <= 35:
                        if label not in edited_fields:
                            initial_context[label] = ""
                            edited_fields.add(label)
                        active_field = label
                if not any(y == 4 + j * 2 for j in range(len(initial_context))) and y < 10:
                    active_field = None        
                render_form(initial_context,edited_fields,buttons)
                continue
            if active_field:
                edited_fields.add(active_field)
                if inp.name == 'KEY_ENTER':
                    active_field = None
                elif inp.name in ('KEY_BACKSPACE', 'KEY_DELETE') or inp == '\b':
                    initial_context[active_field] = initial_context[active_field][:-1]
                elif not inp.is_sequence:
                    if not inp.isdigit():
                        continue 
                    else:
                        initial_context[active_field] += inp
                render_form(initial_context,edited_fields,buttons)   
                    
def input_data_for_report(term):
    report_buttons = {"SEND": (10, 5), "DISCARD": (10, 20)}  
    while True:
        report_data = read_report_data()
        render_report_data(report_buttons,report_data)
        rep = term.inkey()
        key_lower = rep.lower()
        if rep == 'd': 
            logger.info("discard")
            return
        elif key_lower == 's':       
            generate_report(report_data)
            return
        
def create_and_show_report(term):
    report_data = read_report_data()
    generate_report(report_data)
    render_report_data(None,report_data)        
    

      
                        