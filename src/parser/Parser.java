package parser;

import java.lang.StringBuilder;
import java.util.Date;

import common.*;

public class Parser {
	
	private State state_;
	
	/*
	 * Initializing parser
	 * Input: None
	 * Output: A parser instance
	 */
 	public Parser(State state){
		state_ = state;
	}
	
	/*
	 * Break down a string of input into smaller parts for logic to process
	 * Input: String
	 * Output: A parsedCommand object
	 */
	public boolean processInput(){
		return buildParsedCommand();
	}
	
	/*
	 * Build a parsedCommand from a given input
	 * Input: String
	 * Output: ParsedCommand object
	 */
	private boolean buildParsedCommand() {
		state_.setMessage(getErrorMessage());
		state_.setIsValid(getIsValid());
		if(state_.getIsValid()){
			state_.setCommand(getCommand());
			state_.setRawContent(getRawContent());
			state_.setStartDate(getStartDate());
			state_.setEndDate(getEndDate());
			state_.setPosition(getPosition());
			state_.setContent(getContent());
			state_.setTaskType(getType());
			
		}
		return state_.getIsValid();
	}
	
	/*
	 * Get the content of the task
	 * Input: None
	 * Output: String of the content
	 */
	private String getContent() {
		if(isUpdate()){
			String lst[] = state_.getRawContent().substring(1, state_.getRawContent().length()).trim().split(" ");
			return readContent(lst);
		}else if(isDeadline()){
			String lst[] = state_.getRawContent().split("on");
			return lst[0];
		}else{
			return state_.getRawContent();
		}
	}
	
	/*
	 * Check whether the task is a deadline task
	 * Input: None
	 * Output: True if it is an add deadline task. False otherwise
	 */
	private boolean isDeadline() {
		return state_.getCommand().equals(CommandType.ADD) && state_.getIsEndDate();
	}

	/*
	 * Check whether the task is of update type
	 * Input: None
	 * Outpu: True if it is. False, otherwise
	 */

	private boolean isUpdate() {
		return state_.getCommand().equals(CommandType.UPDATE);
	}
	

	/*
	 * Get the index of the task for delete, update and tick
	 * Input: None
	 * Output: Int of the index
	 */
	private int getPosition() {
		if(isIndexRequired()){
			return Integer.parseInt(state_.getRawContent().substring(0,1));
		}else{
			return 0;
		}
	}
	
	/*
	 * Check whether the current command need index
	 * Input: None
	 * Output: True if it needs index. False othrwise
	 */
	private boolean isIndexRequired() {
		return state_.getCommand().equals(CommandType.DELETE) || state_.getCommand().equals(CommandType.TICK) || state_.getCommand().equals(CommandType.UPDATE);
	}

	
	/*
	 * Get the deadline of a task from a given input
	 * Input: String
	 * Output: String
	 */
	private Date getEndDate() {
		String list[] = state_.getRawContent().split("on");
		if(list.length==1){
			state_.setIsEndDate(false);
			return null;
		}
		Date d = TimeParser.stringToDate(list[list.length-1].trim());
		if(d != null){
			state_.setIsEndDate(true);
			return d;
		}else{
			state_.setIsEndDate(false);
			return null;
		}
	}
	
	/*
	 * Get the starting date of a task.
	 * Input: String
	 * Output: The start date. Default value is the time of assignment
	 */
	private Date getStartDate() {
		return null;
	}

	/*
	 * Get the type of a task from a given input
	 * Input: None
	 * Output: TaskType
	 */
	private TaskType getType() {
		if(isIndexRequired()){
			String lst[] = state_.getRawContent().split(" ");
			return determineTaskType(lst[1]);
		}
		if(state_.getIsEndDate()){
			return TaskType.DEADLINE;
		}
		else{
			return TaskType.FLOATING;
		}
	}
	
	/*
	 * Get the command of an input
	 * Input: None
	 * Output: CommandType
	 */
	private CommandType getCommand() {
		String inputList[] = state_.getUserInput().split(" ");
		return determineCommandType(inputList[0]);
	}
	
	/*
	 * Get the content of an input
	 * Input: None
	 * Output: String
	 */
	private String getRawContent(){
		String inputList[] = state_.getUserInput().split(" ");
		return readContent(inputList);
	}

	/*
	 * Check whether parsed command is valid
	 * Input: None
	 * Output: True if command input is valid. False otherwise.
	 */
	private boolean getIsValid() {
		return state_.getMessage() == Constant.VALUE_ERROR_NO_ERROR;
	}
	
	/*
	 * Get the error code for a given input. 
	 * Input: None
	 * Output: 0 for no error. 1 for command not found. 2 for empty input. 3 for invalid argument
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
	 * Input: None
	 * Output: true if it is empty. false otherwise
	 */	
	private boolean isInputEmpty() {
		return state_.getUserInput().length() == 0;
	}
	
	/*
	 * Check whether the input has a valid command (add, update, tick, delete, clear, exit)
	 * Input: None
	 * Output: True if it is valid. False otherwise
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
				
				default: 
					return true;
			}
		}
		return false;
	}
	
	/*
	 * Check whether the input has the correct and valid argument for the given command
	 * Input: None
	 * Output: True if it's invalid. False otherwise
	 */
	private boolean isArgumentInvalid() {
		if(!isCommandInvalid()){
			CommandType commandType = getCommand();
			String content = getRawContent();
			try{
				switch(commandType){
				
					case ADD:
						if(content.length() == 0){
							return true;
						}
						return false;
					
					case CLEAR:
//						if(content.length() != 1){
//							return true;
//						}
						return false;
				
					case DELETE:
//						if(content.length() == 0 || !content.matches("\\d+")){
//							return true;
//						}
						return false;
				
//					case EXIT:
//						if(content_.length() != 0){
//							return true;
//						}
					
					case TICK:
//						if(content.length() == 0 || !content.matches("\\d+")){
//							return true;
//						}
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
	private String readContent(String[] inputList) {
		StringBuilder sb = new StringBuilder("");
		for(int i = 1; i < inputList.length; i ++){
			sb.append(inputList[i]);
			sb.append(" ");
		}
		return sb.toString().trim();
	}
	
	/*
	 * Get the command type based on input
	 * Input: String of command
	 * Output: CommandType of the given input
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
		}
		return CommandType.ERROR;
	}
	
	/*
	 * Get the task type based on input
	 * Input: String of command
	 * Output: TaskType of the given input
	 */
	private TaskType determineTaskType(String taskTypeString) {
		if (taskTypeString == null) {
			throw new Error("Task type string cannot be null!");
		}
		
		if (taskTypeString.equalsIgnoreCase("float")) {
			return TaskType.FLOATING;
		} else if (taskTypeString.equalsIgnoreCase("deadline")) {
			return TaskType.DEADLINE;
		} 
		return TaskType.UNDEFINED;
	}
}
