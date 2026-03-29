from blessed import Terminal
from constants import MENU_ITEMS
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
