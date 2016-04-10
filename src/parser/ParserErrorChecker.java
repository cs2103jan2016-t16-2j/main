//@@author A0130369H
package parser;

import common.CommandType;
import common.Constant;
import common.State;

public class ParserErrorChecker {
	private State state_;
	
	public ParserErrorChecker(State state){
		state_ = state;
	}
	
	public void checkError(){
		state_.setDisplayMessage(getErrorMessage());
		state_.setIsValid(getIsValid());
	}
	
	/*
	 * Check whether parsed command is valid
	 * Pre-Cond: None
	 * Post-Cond: True if command input is valid. False otherwise.
	 */
	private boolean getIsValid() {
		return state_.getDisplayMessage().equals(Constant.VALUE_ERROR_NO_ERROR);
	}
	
	/*
	 * Get the error message for a given input. 
	 * Pre-Cond: None
	 * Post-Cond: Respective error message 
	 */
	private String getErrorMessage() {
		if(isInputEmpty()){
			return Constant.VALUE_ERROR_NO_INPUT;
		}
		if(isCommandInvalid()){
			return Constant.VALUE_ERROR_COMMAND_NOT_FOUND;
		}
		if(isArgumentInvalid()){
			return Constant.VALUE_ERROR_INVALID_ARGUMENT;
		}
		return Constant.VALUE_ERROR_NO_ERROR;
	}

	/*
	 * Check whether the input is an empty string
	 * Pre-Cond: None
	 * Post-Cond: true if it is empty. false otherwise
	 */	
	private boolean isInputEmpty() {
		return state_.getUserInput().length() == 0;
	}
	
	/*
	 * Check whether the input has a valid command (add, update, tick, delete, clear, exit, etc)
	 * Pre-Cond: None
	 * Post-Cond: True if it is valid. False otherwise
	 */
	private boolean isCommandInvalid() {
		if(!isInputEmpty()){
			CommandType commandType = getCommand();
			switch(commandType) {
				case ADD:
					return false;	
				case CLEAR:
					return false;
				
				case DELETE:
					return false;
				
				case TICK:
					return false;	
				
				case UPDATE:
					return false;
					
				case EXIT:
					return false;
					
				case UNDO:
					return false;
				
				case REDO:
					return false;
					
				case CHANGEMODE: // view all/deadline
					return false;		
				case SEARCH: 
					return false;
				
				case DETAIL:
					return false;
					
				case HELP:
					return false;

				case UNTICK:
					return false;
					
				default: 
					return true;
			}
		}
		return false;
	}
	
	/*
	 * Check whether the input has the correct and valid argument for the given command
	 * Pre-Cond: None
	 * Post-Cond: True if it's invalid. False otherwise
	 */
	private boolean isArgumentInvalid() {
		if(!isCommandInvalid()){
			CommandType commandType = getCommand();
			String content = getContentWithoutCommand();
			try{
				switch(commandType){
				
					case ADD:
						if(content.length() == 0){
							return true;
						}
						return false;
					
					case CLEAR:
						if(content.isEmpty()){
							return false;
						}
						return true;
				
					case DELETE:
						if(content.matches("\\d+")){
							return false;
						}
						return true;
				
					case EXIT:
						if(content.length() != 0){
							return true;
						}
						return false;
					
					case UNDO:
						if(content.length() != 0){
							return true;
						}
						return false;
					
					case REDO:
						if(content.length() != 0){
							return true;
						}
						return false;
						
					case SEARCH:
						if(content.length() == 0){
							return true;
						}
						return false;
						
					case TICK:
						if(content.matches("\\d+")){
							return false;
						}
						return true;
					
					case UPDATE:
						String inputWords[] = content.split(" ");
						if(!inputWords[0].matches("\\d+") || content.length() == 0 || inputWords.length ==1){
							return true;
						}
						return false;
					
					case DETAIL:
						if(content.matches("\\d+")){
							return false;
						}
						return true;
					
					case CHANGEMODE:
						if(content.equalsIgnoreCase("FLOATING") || content.equalsIgnoreCase("DEADLINE") 
								|| content.equalsIgnoreCase("ALL") || content.equalsIgnoreCase("FINISHED") 
								|| content.equalsIgnoreCase("CONFIG") || content.equalsIgnoreCase("TODAY")){
							return false;
						}
						return true;
					
					case HELP:
						if(content.length() != 0){
							return true;
						}
						return false;
					
					case CONFIG:
						if(content.equalsIgnoreCase("CONSOLAS") || content.equalsIgnoreCase("SEGOE")
							|| content.equalsIgnoreCase("AUTUMN") || content.equalsIgnoreCase("BOKEH")
							|| content.equalsIgnoreCase("BRANCH") || content.equalsIgnoreCase("CAT")
							|| content.equalsIgnoreCase("JAPANESE") || content.equalsIgnoreCase("LEATHER")
							|| content.equalsIgnoreCase("PARIS") || content.equalsIgnoreCase("RAINDROP")
							|| content.equalsIgnoreCase("WARM") || content.equalsIgnoreCase("WHEAT")){
							return false;
						}else{
							return true;
						}
					case UNTICK:
						if(content.matches("\\d+")){
							return false;
						}
						return true;
						
					default: 
						return false;
				}
			}
			catch(IllegalArgumentException e){
				return true;
			}
		}else{
			return false;
		}
	}


	/*
	 * Get the content of user input without the command word
	 */
	private String getContentWithoutCommand() {
		String inputWords[] = state_.getUserInput().split(" ");
		StringBuilder sb = new StringBuilder("");
		for(int i = 1; i < inputWords.length; i ++){
			sb.append(inputWords[i]);
			sb.append(" ");
		}
		return sb.toString().trim();
	}
	/*
	 * Get the command of an input
	 * Pre-Cond: Input of a user
	 * Post-Cond: CommandType
	 */
	private CommandType getCommand() {
		String inputWords[] = state_.getUserInput().split(" ");
		return determineCommandType(inputWords[0]);
	}

	
	/*
	 * Get the command type based on input
	 * Pre-Cond: String of command
	 * Post-Cond: CommandType of the given input
	 */
	private CommandType determineCommandType(String commandTypeString) {
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
