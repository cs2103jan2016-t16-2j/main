//@@author A0130369H
package parser;

import common.CommandType;
import common.Constant;
import common.State;

public class ParserErrorChecker {
	
	//============================
	//       Attributes
	//============================
	private State state_;
	
	//====================================
	//       Constructor and Initialiser
	//====================================
	public ParserErrorChecker(State state){
		state_ = state;
	}
	
	//====================================
	//       Public Functions
	//====================================
	/**
	 * Check whether the content of the input has any error
	 * @return display message
	 * @return the validity of the input
	 */
	public void checkError(){
		state_.setDisplayMessage(getErrorMessage());
		state_.setIsValid(getIsValid());
		if(state_.getIsValid()){
			state_.setCommandType(getCommand());
		}
	}
	
	//====================================
	//       Helper Functions
	//====================================
	/**
	 * Check whether parsed command is valid
	 * @return True if the input is valid
	 */
	private boolean getIsValid() {
		return isTaskValid();
	}

	private boolean isTaskValid() {
		return state_.getDisplayMessage().equals(Constant.VALUE_SUCCESS_NO_ERROR)
				|| state_.getDisplayMessage().equals(Constant.VALUE_SUCCESS_ADD)
				|| state_.getDisplayMessage().equals(Constant.VALUE_SUCCESS_UPDATE)
				|| state_.getDisplayMessage().equals(Constant.VALUE_SUCCESS_TICK)
				|| state_.getDisplayMessage().equals(Constant.VALUE_SUCCESS_UNTICK)
				|| state_.getDisplayMessage().equals(Constant.VALUE_SUCCESS_UNDO)
				|| state_.getDisplayMessage().equals(Constant.VALUE_SUCCESS_REDO)
				|| state_.getDisplayMessage().equals(Constant.VALUE_SUCCESS_CLEAR)
				|| state_.getDisplayMessage().equals(Constant.VALUE_SUCCESS_DELETE)
				|| state_.getDisplayMessage().equals(Constant.VALUE_SUCCESS_SEARCH)
				|| state_.getDisplayMessage().equals(Constant.VALUE_SUCCESS_DETAIL)
				|| state_.getDisplayMessage().equals(Constant.VALUE_SUCCESS_VIEW_MODE)
				|| state_.getDisplayMessage().equals(Constant.VALUE_SUCCESS_CONFIG)
				|| state_.getDisplayMessage().equals(Constant.VALUE_SUCCESS_HELP);
	}
	
	/**
	 * Get the error message for a given input. 
	 * @return The respective error message
	 */
	private String getErrorMessage() {
		if(isInputEmpty()){
			return Constant.VALUE_ERROR_NO_INPUT;
		}
		if(isCommandInvalid()){
			return Constant.VALUE_ERROR_COMMAND_NOT_FOUND;
		}
		return checkInvalidArgument();
	}

	/**
	 * Check whether the input is an empty string
	 * @return True if it's empty input
	 */	
	private boolean isInputEmpty() {
		return state_.getUserInput().length() == 0;
	}
	
	/**
	 * Check whether the input has a valid command (add, update, tick, delete, clear, exit, etc)
	 * @return True if the command is invalid
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
					
				case CHANGEMODE:
					return false;	
					
				case SEARCH: 
					return false;
				
				case DETAIL:
					return false;
					
				case HELP:
					return false;

				case UNTICK:
					return false;
				
				case CONFIG:
					return false;
					
				default: 
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Check whether the input has the correct and valid argument for the given command
	 * @return respective error message
	 */
	private String checkInvalidArgument() {
		if(!isCommandInvalid()){
			CommandType commandType = getCommand();
			String content = getContentWithoutCommand();
			try{
				switch(commandType){
				
					case ADD:
						if(isContentEmpty(content)){
							return Constant.VALUE_ERROR_ADD_EMPTY;
						}
						return Constant.VALUE_SUCCESS_ADD;
					
					case CLEAR:
						if(isContentEmpty(content)){
							return Constant.VALUE_SUCCESS_CLEAR;
						}
						return Constant.VALUE_ERROR_ARGUMENT_NOT_EMPTY;
				
					case DELETE:
						if(isContentNumber(content)){
							return Constant.VALUE_SUCCESS_DELETE;
						}
						return Constant.VALUE_ERROR_ARGUMENT_NOT_NUMBER;
				
					case EXIT:
						if(isContentEmpty(content)){
							return Constant.VALUE_SUCCESS_NO_ERROR;
						}
						return Constant.VALUE_ERROR_ARGUMENT_NOT_EMPTY;
					
					case UNDO:
						if(isContentEmpty(content)){
							return Constant.VALUE_SUCCESS_UNDO;
						}
						return Constant.VALUE_ERROR_ARGUMENT_NOT_EMPTY;
					
					case REDO:
						if(isContentEmpty(content)){
							return Constant.VALUE_SUCCESS_REDO;
						}
						return Constant.VALUE_ERROR_ARGUMENT_NOT_EMPTY;
						
					case SEARCH:
						if(isContentEmpty(content)){
							return Constant.VALUE_ERROR_SEARCH_EMPTY;
						}
						return Constant.VALUE_SUCCESS_SEARCH;
						
					case TICK:
						if(isContentNumber(content)){
							return Constant.VALUE_SUCCESS_TICK;
						}
						return Constant.VALUE_ERROR_ARGUMENT_NOT_NUMBER;
					
					case UPDATE:
						String inputWords[] = content.split(" ");
						if(!isContentNumber(inputWords[0]) || isContentEmpty(content) || hasContentOneArgument(inputWords)){
							return Constant.VALUE_ERROR_UPDATE_WRONG_ARGUMENT;
						}
						return Constant.VALUE_SUCCESS_UPDATE;
					
					case DETAIL:
						if(isContentNumber(content)){
							return Constant.VALUE_SUCCESS_DETAIL;
						}
						return Constant.VALUE_ERROR_ARGUMENT_NOT_NUMBER;
					
					case CHANGEMODE:
						if(isContentValidViewMode(content)){
							return Constant.VALUE_SUCCESS_VIEW_MODE;
						}
						return Constant.VALUE_ERROR_INVALID_VIEW_MODE;
					
					case HELP:
						if(isContentEmpty(content)){
							return Constant.VALUE_SUCCESS_HELP;
						}
						return Constant.VALUE_ERROR_ARGUMENT_NOT_EMPTY;
					
					case CONFIG:
						if(isContentValidFontOrTheme(content)){
							return Constant.VALUE_SUCCESS_CONFIG;
						}else{
							return Constant.VALUE_ERROR_INVALID_CONFIG;
						}
					case UNTICK:
						if(isContentNumber(content)){
							return Constant.VALUE_SUCCESS_UNTICK;
						}
						return Constant.VALUE_ERROR_ARGUMENT_NOT_NUMBER;
						
					default: 
						return Constant.VALUE_SUCCESS_NO_ERROR;
				}
			}
			catch(IllegalArgumentException e){
				return Constant.VALUE_ERROR_INVALID_ARGUMENT;
			}
		}else{
			return Constant.VALUE_ERROR_COMMAND_NOT_FOUND;
		}
	}
	
	/**
	 * Check if content is a valid theme or font
	 * @return True if it is
	 */
	private boolean isContentValidFontOrTheme(String content) {
		return content.equalsIgnoreCase("CONSOLAS") || content.equalsIgnoreCase("SEGOE")
			|| content.equalsIgnoreCase("AUTUMN") || content.equalsIgnoreCase("BOKEH")
			|| content.equalsIgnoreCase("BRANCH") || content.equalsIgnoreCase("CAT")
			|| content.equalsIgnoreCase("GREY") || content.equalsIgnoreCase("WARM");
	}
	
	/**
	 * Check if the content is a valid viewmode
	 * @return True if it is
	 */
	private boolean isContentValidViewMode(String content) {
		return content.equalsIgnoreCase("FLOATING") || content.equalsIgnoreCase("SCHEDULED") 
				|| content.equalsIgnoreCase("ALL") || content.equalsIgnoreCase("FINISHED") 
				|| content.equalsIgnoreCase("SETTING") || content.equalsIgnoreCase("TODAY");
	}
	
	/**
	 * Check whether the content has the required index and update content
	 * @return true if it has one argument only
	 */
	private boolean hasContentOneArgument(String[] inputWords) {
		return inputWords.length ==1;
	}
	
	/**
	 * Check if the content is a number
	 * @return true if it is
	 */
	private boolean isContentNumber(String content) {
		return content.matches("\\d+");
	}

	/**
	 * Check whether the content is empty
	 * @return true if it is empty
	 */
	private boolean isContentEmpty(String content) {
		return content.isEmpty();
	}


	/**
	 * Get the content of user input without the command word
	 * @return the user input without the command word
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
	/**
	 * Get the command of an input
	 * @param Input of the user
	 * @return The command type
	 */
	private CommandType getCommand() {
		String inputWords[] = state_.getUserInput().split(" ");
		return determineCommandType(inputWords[0]);
	}

	
	/**
	 * Get the command type based on input
	 * @param String of the command
	 * @return CommandType of the command
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
		}  else if (commandTypeString.equalsIgnoreCase("help") || commandTypeString.equalsIgnoreCase("?")) {
			return CommandType.HELP;
		}  else if (commandTypeString.equalsIgnoreCase("untick")) {
			return CommandType.UNTICK;
		} else if (commandTypeString.equalsIgnoreCase("setting")) {
			return CommandType.CONFIG;
		}else if (commandTypeString.equalsIgnoreCase("view")) {
			return getViewCommand();
		}
		return CommandType.ERROR;
	}
	
	/**
	 * Check whether it is a detail command or a changemode command
	 */
	private CommandType getViewCommand() {
		String inputWords[] = state_.getUserInput().split(" ");
		String argument = inputWords[1];
		if(isContentNumber(argument)){
			return CommandType.DETAIL;
		}else{
			return CommandType.CHANGEMODE;
		}
	}
}
