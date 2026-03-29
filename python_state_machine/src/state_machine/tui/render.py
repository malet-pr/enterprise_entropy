from blessed import Terminal
from constants import MENU_ITEMS

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

    print(term.bold("Action\n"))
    print(message) 

    print("\nPress any key to return to menu")

