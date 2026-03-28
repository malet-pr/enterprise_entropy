
from blessed import Terminal
import logging

logging.basicConfig(
    filename="../logs/tui.log",
    level=logging.INFO,
    format="%(asctime)s | %(levelname)s | %(message)s"
)

logger = logging.getLogger("tui")
logger.info("=== New TUI session ===")

term = Terminal()

MENU_ITEMS = [
    "create_scenario",
    "create_report",
    "run_all",
    "exit"
]


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


def prompt_input(label):
    value = ""

    while True:
        print(term.home + term.clear)

        print(term.bold("Create Scenario\n"))

        # Label
        print(term.white(label + ": "))

        # Input field (styled)
        print(term.black_on_cyan(f" {value} ") + term.cyan(" ← type here"))

        print("\n(Enter to confirm, Backspace to delete, Esc to cancel)")

        key = term.inkey()

        if key.name == "KEY_ENTER" or key == "\n":
            logger.info(f"[create_scenario] Scenario created: {value}")
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


def run_tui():
    selected_index = 0
    current_screen = "menu"
    running = True

    with term.fullscreen(), term.hidden_cursor(), term.cbreak():

        while running:

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

                    if scenario_id is None:
                        current_screen = "menu"
                        continue

                    render_message_screen(f"Scenario created: {scenario_id}")
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