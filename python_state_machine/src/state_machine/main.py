import argparse
import logging 
from config import *
from scenarios import create_scenarios
from report import create_report

def run_all(args):
    create_scenarios(args)
    create_report(args)

def main():
    """Main entry point. It handles all CLI commands."""

    parser = argparse.ArgumentParser(
        description="Feature State Machine - creates scenarios for the state machine and report results.",
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
    parser_create_scenarios.add_argument('--fileName', type=str, help=f'File name of the output file, (default = {FILE_NAME_SCENARIO})')
    parser_create_scenarios.set_defaults(func=create_scenarios)
    
    # Comand 'create-report'
    parser_create_report = subparsers.add_parser('create-report', help='Generates report files with Ocaml response to the scenario.')
    parser_create_report.add_argument('--fileName', type=str, help=f'File name of the report file, (default = {FILE_NAME_REPORT})')
    parser_create_report.set_defaults(func=create_report)

    args = parser.parse_args()
    
    if args.command is None:
        args.command = 'run-all'
        args.func = run_all
        print('\n###############################################################################')
        print("No argument was provided, executing run_all.")   


    
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
