from blessed import Terminal
from constants import MENU_ITEMS,STATES,EVENTS

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
    # print(term.bold("Action\n"))
    print(message) 
    print("\nPress any key to return to menu")

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
        
        