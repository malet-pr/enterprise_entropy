import argparse
import logging 
from .scenarios import create_scenarios
from .report import create_report

from state_machine.core.config import *

logger = logging.getLogger("state_machine.cli.main")

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
    parser_create_scenarios.add_argument("-i", '--input_file', type=str, help=f'File name of the input file')
    parser_create_scenarios.add_argument("-o", '--output_file', type=str, help=f'File name of the output file, (default = {FILE_NAME_DATA})')
    parser_create_scenarios.set_defaults(func=create_scenarios)
    
    # Comand 'create-report'
    parser_create_report = subparsers.add_parser('create-report', help='Generates report files with Ocaml response to the scenario.')
    parser_create_report.add_argument('--fileName', type=str, help=f'File name of the report file, (default = {FILE_NAME_REPORT})')
    parser_create_report.set_defaults(func=create_report)

    args = parser.parse_args()
    #logger.info(f"ARGS RAW: {args}")
    #logger.info(f"ARGS DICT: {vars(args)}")
    
    if args.command is None:
        args.command = 'run-all'
        args.func = run_all
        logger.info("No argument was provided, executing run_all.")   

    logger.info('STARTING THE PROCESS...')

    try:
        args.func(args)
    except Exception as e:
        logger.error(f"Error while executing command '{args.command}': {e}", exc_info=True)
        
    logger.info('PROCESS ENDED.')

if __name__ == "__main__":
    main()  
