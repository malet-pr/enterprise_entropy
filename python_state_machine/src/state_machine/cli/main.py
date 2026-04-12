import argparse
import logging 
from scenarios import create_scenarios
from report import create_report
from core.config import FILE_NAME_DATA, FILE_NAME_REPORT, FILE_NAME_SCENARIO


logging.basicConfig(
    filename="../../../logs/cli.log",
    level=logging.INFO,
    format="%(asctime)s | %(levelname)s | %(message)s"
)
logger = logging.getLogger("cli")
logger.info("=== New CLI session ===")

def get_args(args: argparse.Namespace) -> str:
    if hasattr(args, 'fileName') and args.fileName is not None:
        return args.fileName
    else:
        return None 

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
    parser_create_scenarios.add_argument('--fileName', type=str, help=f'File name of the output file, (default = {FILE_NAME_DATA})')
    parser_create_scenarios.set_defaults(func=create_scenarios)
    
    # Comand 'create-report'
    parser_create_report = subparsers.add_parser('create-report', help='Generates report files with Ocaml response to the scenario.')
    parser_create_report.add_argument('--fileName', type=str, help=f'File name of the report file, (default = {FILE_NAME_REPORT})')
    parser_create_report.set_defaults(func=create_report)

    args = parser.parse_args()
    
    if args.command is None:
        args.command = 'run-all'
        args.func = run_all
        logging.info("No argument was provided, executing run_all.")   

    logging.info('STARTING THE PROCESS...')

    try:
        args.func(args)
    except Exception as e:
        logging.error(f"Error while executing command '{args.command}': {e}", exc_info=True)
        
    logging.info('PROCESS ENDED.')

if __name__ == "__main__":
    main()  
