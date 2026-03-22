import argparse
import logging 
from config import *
from scenarios import create_scenarios

def main():
    """Main entry point. It handles all CLI commands."""

    parser = argparse.ArgumentParser(
        description="Jira State Machine - creates scenarios for the state machine and report results.",
        epilog="Use state_machine <command> --help to get details of a specific command."
    )

    # Create a sub-parser to handle different commands
    subparsers = parser.add_subparsers(
        title='Available commands',
        dest='command',
        required=False
    )

    # Comand 'create-scenarios'
    parser_create_scenarios = subparsers.add_parser('create-scenarios', help='Generates files that will be used by Ocaml to run the state machine.')
    parser_create_scenarios.add_argument('--fileName', type=str, help=f'File name of the output file, (default = {FILE_NAME})')
    parser_create_scenarios.set_defaults(func=create_scenarios)

    args = parser.parse_args()
    
    if args.command is None:
        args.command = 'create-scenarios'
        args.func = create_scenarios
        print('\n###############################################################################')
        print("No argument was provided, executing create-scenarios.")   


    
    print('\n###############################################################################')
    logging.info('STARTING THE PROCESS...')
    print('###############################################################################\n')

    try:
        args.func(args)
    except Exception as e:
        logging.error(f"Error while executing command '{args.command}': {e}", exc_info=True)

    print('\n###############################################################################')
    logging.info('PROCESS ENDED.')
    print('###############################################################################\n')

if __name__ == "__main__":
    main()  
