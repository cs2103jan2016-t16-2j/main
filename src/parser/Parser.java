package parser;

import java.lang.StringBuilder;
import java.util.Date;

import common.*;

public class Parser {
	
	private Constant constant_;
	
	/*
	 * Initializing parser
	 * Input: None
	 * Output: A parser instance
	 */
	public Parser(){
		constant_ = new Constant();
	}
	
	/*
	 * Break down a string of input into smaller parts for logic to process
	 * Input: String
	 * Output: A parsedCommand object
	 */
	public ParsedCommand processInput(String input){
		return buildParsedCommand(input);
	}
	
	/*
	 * Build a parsedCommand from a given input
	 * Input: String
	 * Output: ParsedCommand object
	 */
	private ParsedCommand buildParsedCommand(String input) {
		ParsedCommand parsed = new ParsedCommand();
		
		parsed.setErrorCode(getErrorCode(input));
		parsed.setIsValid(getIsValid(parsed.getErrorCode()));
		if(parsed.getIsValid()){
			parsed.setCommand(getCommand(input));
			parsed.setContent(getContent(input));
			parsed.setType(getType(parsed.getContent()));
			parsed.setStartDate(getStartDate(parsed.getContent()));
			parsed.setEndDate(getEndDate(parsed.getContent()));
		}
		return parsed;
	}
	
	
	/*
	 * Get the deadline of a task from a given input
	 * Input: String
	 * Output: String
	 */
	private Date getEndDate(String input) {
		return null; //constant_.VALUE_DEFAULT_EMPTY;
	}
	
	/*
	 * Get the starting date of a task.
	 * Input: String
	 * Output: The start date. Default value is the time of assignment
	 */
	private Date getStartDate(String content) {
		return null; //constant_.VALUE_DEFAULT_EMPTY;
	}

	/*
	 * Get the type of a task from a given input
	 * Input: String
	 * Output: TaskType
	 */
	private TaskType getType(String content) {
		return TaskType.FLOATING;
	}
	
	/*
	 * Get the command of an input
	 * Input: String
	 * Output: CommandType
	 */
	private CommandType getCommand(String input) {
		String inputList[] = input.split(" ");
		return determineCommandType(inputList[0]);
	}
	
	/*
	 * Get the content of an input
	 * Input: String
	 * Output: String
	 */
	private String getContent(String input){
		String inputList[] = input.split(" ");
		return readContent(inputList);
	}
	

	/*
	 * Check whether parsed command is valid
	 * Input: Error code (int)
	 * Output: True if command input is valid. False otherwise.
	 */
	private boolean getIsValid(int errorCode) {
		return errorCode == constant_.VALUE_ERROR_NO_ERROR;
	}
	

	/*
	 * Get the error code for a given input. 
	 * Input: String input
	 * Output: 0 for no error. 1 for command not found. 2 for empty input. 3 for invalid argument
	 */
	private int getErrorCode(String input) {
		if(isInputEmpty(input)){
			return constant_.VALUE_ERROR_NO_INPUT;
		}
		if(isCommandInvalid(input)){
			return constant_.VALUE_ERROR_COMMAND_NOT_FOUND;
		}
		if(isArgumentInvalid(input)){
			return constant_.VALUE_ERROR_INVALID_ARGUMENT;
		}
		return constant_.VALUE_ERROR_NO_ERROR;
	}

	/*
	 * Check whether the input is an empty string
	 * Input: Input string
	 * Output: true if it is empty. false otherwise
	 */	
	private boolean isInputEmpty(String input) {
		return input.length() == 0;
	}
	
	/*
	 * Check whether the input has a valid command (add, update, tick, delete, clear, exit)
	 * Input: Input string
	 * Output: True if it is valid. False otherwise
	 */
	private boolean isCommandInvalid(String input) {
		if(!isInputEmpty(input)){
			CommandType commandType = getCommand(input);
		
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
				
				default: 
					return true;
			}
		}
		return false;
	}
	
	/*
	 * Check whether the input has the correct and valid argument for the given command
	 * Input: Input string
	 * Output: True if it's invalid. False otherwise
	 */
	private boolean isArgumentInvalid(String input) {
		if(!isCommandInvalid(input)){
			CommandType commandType = getCommand(input);
			String content = getContent(input);
			try{
				switch(commandType){
				
					case ADD:
						if(content.length() == 0){
							return true;
						}
						return false;
					
					case CLEAR:
						if(content.length() != 0){
							return true;
						}
						return false;
				
					case DELETE:
						if(content.length() == 0 || !content.matches("\\d+")){
							return true;
						}
						return false;
				
//					case EXIT:
//						if(content_.length() != 0){
//							return true;
//						}
					
					case TICK:
						if(content.length() == 0 || !content.matches("\\d+")){
							return true;
						}
						return false;
					
					case UPDATE:
						String lst[] = content.split(" ");
						if(!lst[0].matches("\\d+") || content.length() == 0 || lst.length < 2){
							return true;
						}
						return false;
				
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
	 * Extract the content of an input from a list of words.
	 * Input: String[] of input words
	 * Output: A string of the content
	 */
	public String readContent(String[] inputList) {
		StringBuilder sb = new StringBuilder("");
		for(int i = 1; i < inputList.length; i ++){
			sb.append(inputList[i]);
			sb.append(" ");
		}
		return sb.toString().trim();
	}
	
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
		}
		return CommandType.ERROR;
	}
}
