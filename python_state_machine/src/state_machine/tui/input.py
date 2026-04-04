from blessed import Terminal
from constants import *
from render import render_state_options, draw_events_layout
import logging

term = Terminal()
logger = logging.getLogger("tui")


def prompt_input(label):
    value = ""
    error = None
    while True:
        print(term.home + term.clear)
        print(term.bold("Create Scenario\n"))
        # Label
        print(term.white(label + ": "))
        # Input field (styled)
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
        draw_events_layout(EVENTS, sel_ev, sel_events_pre, [], buttons, button_idx, sel_events_post, focus_buttons)
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
                    
        