//@@author A0130369H
package parser;

import java.lang.StringBuilder;

import common.*;

public class Parser {
	
	private State state_;
	private ParserErrorChecker errorChecker_;
	private CommandAdd add_;
	private CommandChangeMode changeMode_;
	private CommandClear clear_;
	private CommandDelete delete_;
	private CommandDetail detail_;
	private CommandExit exit_;
	private CommandRedo redo_;
	private CommandSearch search_;
	private CommandTick tick_;
	private CommandUndo undo_;
	private CommandUpdate update_;
	private CommandHelp help_;
	private CommandUntick untick_;
	private CommandConfig config_;
	
	
	/*
	 * Initializing parser
	 * Pre-Cond: None
	 * Post-Cond: A parser instance
	 */
 	public Parser(State state){
		state_ = state;
		errorChecker_ = new ParserErrorChecker(state);
		add_ = new CommandAdd(state);
		changeMode_ = new CommandChangeMode(state);
		clear_ = new CommandClear(state);
		delete_ = new CommandDelete(state);
		detail_ = new CommandDetail(state);
		exit_ = new CommandExit(state);
		redo_ = new CommandRedo(state);
		search_ = new CommandSearch(state);
		tick_ = new CommandTick(state);
		undo_ = new CommandUndo(state);
		update_ = new CommandUpdate(state);
		help_ = new CommandHelp(state);
		config_= new CommandConfig(state);
		untick_ = new CommandUntick(state);
		
	}
	
	/*
	 * Break down a string of input into smaller parts for logic to process
	 * Pre-Cond: Input of the user in the state
	 * Post-Cond: Updated State
	 */
	public boolean processInput(){
		errorChecker_.checkError();
		if(state_.getIsValid()){
			state_.setCommandType(getCommand());
			CommandType command = state_.getCommandType();
			switch(command){
				case ADD:
					add_.processInput();
					break;
				case CHANGEMODE:
					changeMode_.processInput();
					break;
				case CLEAR:
					clear_.processInput();
					break;
				case DELETE:
					delete_.processInput();
					break;
				case DETAIL:
					detail_.processInput();
					break;
					
				case EXIT:
					exit_.processInput();
					break;
					
				case REDO:
					redo_.processInput();
					break;
					
				case SEARCH:
					search_.processInput();
					break;
					
				case TICK:
					tick_.processInput();
					break;
					
				case UNDO:
					undo_.processInput();
					break;
					
				case UPDATE:
					update_.processInput();
					break;
					
				case HELP:
					help_.processInput();
					break;
					
				case UNTICK:
					untick_.processInput();
					break;
					
				case CONFIG:
					config_.processInput();
					break;
			}
			
		}
		return state_.getIsValid();
	}
	
	
	/*
	 * Get the command of an input
	 * Pre-Cond: Input of a user
	 * Post-Cond: CommandType
	 */
	public CommandType getCommand() {
		String inputWords[] = state_.getUserInput().split(" ");
		return determineCommandType(inputWords[0]);
	}

	
	/*
	 * Get the command type based on input
	 * Pre-Cond: String of command
	 * Post-Cond: CommandType of the given input
	 */
	public CommandType determineCommandType(String commandTypeString) {
		if (commandTypeString == null) {
			throw new Error("Command type string cannot be null!");
		}
		
		if (commandTypeString.equalsIgnoreCase("add")) {
			return CommandType.ADD;
		} else if (commandTypeString.equalsIgnoreCase("delete")) {
			return CommandType.DELETE;
		} else if (commandTypeString.equalsIgnoreCase("update")) {
			return CommandType.UPDATE;
		} else if (commandTypeString.equalsIgnoreCase("clear")) {
			return CommandType.CLEAR;
		} else if (commandTypeString.equalsIgnoreCase("tick")) {
			return CommandType.TICK;
		} else if (commandTypeString.equalsIgnoreCase("undo")) {
			return CommandType.UNDO;
		} else if (commandTypeString.equalsIgnoreCase("redo")) {
			return CommandType.REDO;
		} else if (commandTypeString.equalsIgnoreCase("search")) {
			return CommandType.SEARCH;
		} else if (commandTypeString.equalsIgnoreCase("help")) {
			return CommandType.HELP;
		} else if (commandTypeString.equalsIgnoreCase("exit")) {
			return CommandType.EXIT;
		} else if (commandTypeString.equalsIgnoreCase("view")) {
			String inputWords[] = state_.getUserInput().split(" ");
			String argument = inputWords[1];
			if(argument.matches("\\d+")){
				return CommandType.DETAIL;
			}else{
				return CommandType.CHANGEMODE;
			}
		}
		return CommandType.ERROR;
	}
}
