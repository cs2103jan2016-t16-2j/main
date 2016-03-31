package parser;

import java.lang.StringBuilder;
import java.util.Date;

import common.*;

public class Parser {
	
	private State state_;
	
	/*
	 * Initializing parser
	 * Pre-Cond: None
	 * Post-Cond: A parser instance
	 */
 	public Parser(State state){
		state_ = state;
	}
	
	/*
	 * Break down a string of input into smaller parts for logic to process
	 * Pre-Cond: None
	 * Post-Cond: Updated State
	 */
	public boolean processInput(){
		return buildParsedCommand();
	}
	
	/*
	 * Build a parsedCommand from a given input
	 * Pre-Cond: String
	 * Post-Cond: Updated State object
	 */
	private boolean buildParsedCommand() {
		state_.setErrorMessage(getErrorMessage());
		state_.setIsValid(getIsValid());
		if(state_.getIsValid()){
			state_.setCommand(getCommand());
			state_.setRawContent(getRawContent());
			state_.setDetail(getDetail());
			state_.setVenue(getVenue());
			state_.setStartDate(getStartDate());
			state_.setEndDate(getEndDate());
			state_.setPosition(getPosition());
			state_.setContent(getContent());
			state_.setTaskType(getType());
			state_.setSearchKey(getSearchKey());
			
		}
		return state_.getIsValid();
	}
	
	private String getDetail() {
		if (isDetail()) {
			String list[] = state_.getRawContent().split(" ");
			return list[2];
		}
		return Constant.VALUE_DEFAULT_EMPTY;
	}
	
	private boolean isDetail() {
		return state_.getCommand().equals(CommandType.DETAIL);
	}
	
	/*
	 * This method returns the venue in String if there is one
	 * Pre-Cond: None
	 * Post-Cond: Return venue if there is any then set isVenue to true. False otherwise
	 */
	private String getVenue() {
		String list[] = state_.getRawContent().split("at:");
		if(list.length == 0){
			state_.setIsVenue(false);
			return Constant.VALUE_DEFAULT_EMPTY;
		}else{
			state_.setIsVenue(true);
			return list[list.length-1].trim();
		}
	}
	

	/*
	 * Get the Search Key for Search command
	 * Pre-Cond: Valid search command
	 * Post-cond: Updated state search key
	 */
	private String getSearchKey() {
		if(isSearch()){
			return state_.getContent();
		}
		else{
			return Constant.VALUE_DEFAULT_EMPTY;
		}
	}
	
	/*
	 * Check whether the command is search
	 * Pre-cond: None
	 * Post-Cond: True if it is. False otherwise
	 */
	private boolean isSearch() {
		return state_.getCommand().equals(CommandType.SEARCH);
	}

	/*
	 * Get the content of the task
	 * Pre-Cond: None
	 * Post-Cond: String of the content
	 */
	private String getContent() {
		if(isUpdate()){
			String lst[] = state_.getRawContent().substring(1, state_.getRawContent().length()).trim().split(" ");
			String content = readContent(lst);
			if(isStartingDate()){
				String list[] = content.split("from");
				if(list.length == 1){
					return Constant.VALUE_DEFAULT_EMPTY;
				}
				state_.setIsContent(true);
				return list[0].trim();
			}
			else if (isDeadline()){
				String list[] = content.split("on");
				if(list.length == 1){
					return Constant.VALUE_DEFAULT_EMPTY;
				}
				state_.setIsContent(true);
				return list[0].trim();
			}
			return content;
		}else if(isStartingDate()){
			String lst[] = state_.getRawContent().split("from");
			if(lst.length == 1){
				state_.setErrorMessage(Constant.VALUE_ERROR_NO_INPUT);
				return Constant.VALUE_DEFAULT_EMPTY;
			}
			state_.setIsContent(true);
			return lst[0].trim();
		}else if(isDeadline()){
			String lst[] = state_.getRawContent().split("on");
			if(lst.length == 1){
				state_.setErrorMessage(Constant.VALUE_ERROR_NO_INPUT);
				return Constant.VALUE_DEFAULT_EMPTY;
			}
			state_.setIsContent(true);
			return lst[0].trim();
		}else{
			return state_.getRawContent();
		}
	}
	
	/*
	 * Check whether starting date is given
	 * Pre-Cond: None
	 * Post-Cond: True if starting date is given. False otherwise
	 */
	private boolean isStartingDate() {
		return state_.getIsStartDate();
	}

	/*
	 * Check whether the task is a deadline task
	 * Pre-Cond: None
	 * Post-Cond: True if it is an add deadline task. False otherwise
	 */
	private boolean isDeadline() {
		return state_.getIsEndDate();
	}

	/*
	 * Check whether the task is of update type
	 * Pre-Cond: None
	 * Outpu: True if it is. False, otherwise
	 */

	private boolean isUpdate() {
		return state_.getCommand().equals(CommandType.UPDATE);
	}
	

	/*
	 * Get the index of the task for delete, update and tick
	 * Pre-Cond: None
	 * Post-Cond: Int of the index
	 */
	private int getPosition() {
		if(isIndexRequired()){
			String list[] = state_.getRawContent().split(" ");
			return Integer.parseInt(list[0]);
		}else{
			return 0;
		}
	}
	
	/*
	 * Check whether the current command need index
	 * Pre-Cond: None
	 * Post-Cond: True if it needs index. False othrwise
	 */
	private boolean isIndexRequired() {
		return state_.getCommand().equals(CommandType.DELETE) || state_.getCommand().equals(CommandType.TICK) || state_.getCommand().equals(CommandType.UPDATE) || state_.getCommand().equals(CommandType.DETAIL);
	}

	
	/*
	 * Get the deadline of a task from a given input
	 * Pre-Cond: String
	 * Post-Cond: String
	 */
	private Date getEndDate() {
		if(isStartingDate()){
			String list[] = state_.getRawContent().split("to");
			if(list.length==1){
				state_.setIsEndDate(false);
				state_.setIsStartDate(false);
				return null;
			}
			Date d = TimeParser.stringToDate(list[list.length-1].trim().substring(0, 14));
			if(d != null){
				state_.setIsEndDate(true);
				if(d.before(state_.getStartDate())){
					state_.setErrorMessage(Constant.VALUE_ERROR_DATE_ERROR);
				}
				return d;
			}else{
				state_.setIsEndDate(false);
				state_.setIsStartDate(false);
				return null;
			}
		}
		String list[] = state_.getRawContent().split("on");
		if(list.length==1){
			state_.setIsEndDate(false);
			return null;
		}
		Date d = TimeParser.stringToDate(list[list.length-1].trim().substring(0, 14));
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
	 * Pre-Cond: String
	 * Post-Cond: The start date. Default value is the time of assignment
	 */
	private Date getStartDate() {
		String list[] = state_.getRawContent().split("from");
		if(list.length==1){
			state_.setIsStartDate(false);
			return null;
		}
		Date d = TimeParser.stringToDate(list[list.length-1].trim().substring(0, 14));
		if(d != null){
			state_.setIsStartDate(true);
			return d;
		}else{
			state_.setIsStartDate(false);
			return null;
		}
	}

	/*
	 * Get the type of a task from a given input
	 * Pre-Cond: None
	 * Post-Cond: TaskType
	 */
	private TaskType getType() {
		if(isIndexRequired()){
			String lst[] = state_.getRawContent().split(" ");
			return determineTaskType(lst[1]);
		}else if(isClear()){
			return determineTaskType(state_.getContent());
		}
		
		if(state_.getIsEndDate()){
			return TaskType.DEADLINE;
		}
		else{
			return TaskType.FLOATING;
		}
	}
	
	/*
	 * Check whether the current task type is clear
	 * Pre-Cond: None
	 * Post-Cond: True if it is. False otherwise
	 */
	private boolean isClear() {
		// TODO Auto-generated method stub
		return state_.getCommand().equals(CommandType.CLEAR);
	}

	/*
	 * Get the command of an input
	 * Pre-Cond: None
	 * Post-Cond: CommandType
	 */
	private CommandType getCommand() {
		String inputList[] = state_.getUserInput().split(" ");
		return determineCommandType(inputList[0]);
	}
	
	/*
	 * Get the content of an input
	 * Pre-Cond: None
	 * Post-Cond: String
	 */
	private String getRawContent(){
		String inputList[] = state_.getUserInput().split(" ");
		return readContent(inputList);
	}

	/*
	 * Check whether parsed command is valid
	 * Pre-Cond: None
	 * Post-Cond: True if command input is valid. False otherwise.
	 */
	private boolean getIsValid() {
		return state_.getMessage() == Constant.VALUE_ERROR_NO_ERROR;
	}
	
	/*
	 * Get the error code for a given input. 
	 * Pre-Cond: None
	 * Post-Cond: 0 for no error. 1 for command not found. 2 for empty input. 3 for invalid argument
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
	 * Check whether the input has a valid command (add, update, tick, delete, clear, exit)
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
					
				case SEARCH:
					return false;
				
				case DETAIL:
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
			String content = getRawContent();
			try{
				switch(commandType){
				
					case ADD:
						if(content.length() == 0){
							return true;
						}
						return false;
					
					case CLEAR:
						if(content.equalsIgnoreCase("float")||content.equalsIgnoreCase("deadline") || content.isEmpty()){
							return false;
						}
						return true;
				
					case DELETE:
						String lst[] = content.split(" ");
						if(lst.length < 2){
							return true;
						}
						if(!(content.length() == 0) || lst[0].matches("\\d+")||lst[1].equalsIgnoreCase("float")||lst[1].equalsIgnoreCase("deadline")){
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
					
					case SEARCH:
						if(content.length() == 0){
							return true;
						}
						return false;
						
					case TICK:
						String list[] = content.split(" ");
						if(list.length < 2){
							return true;
						}
						if(!(content.length() == 0) || list[0].matches("\\d+")||list[1].equalsIgnoreCase("float")||list[1].equalsIgnoreCase("deadline")){
							return false;
						}
						return true;
					
					case UPDATE:
						String lst_a[] = content.split(" ");
						if(!lst_a[0].matches("\\d+") || content.length() == 0 || lst_a.length <=2){
							return true;
						}
						return false;
					
					case DETAIL:
						String lst_b[] = content.split(" ");
						if(!lst_b[0].matches("\\d+") || content.length() == 0 || lst_b.length <=2){
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
	 * Pre-Cond: String[] of input words
	 * Post-Cond: A string of the content
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
		} else if (commandTypeString.equalsIgnoreCase("search")) {
			return CommandType.SEARCH;
		} else if (commandTypeString.equalsIgnoreCase("exit")) {
			return CommandType.EXIT;
		} else if (commandTypeString.equalsIgnoreCase("detail")) {
			return CommandType.DETAIL;
		}
		return CommandType.ERROR;
	}
	
	/*
	 * Get the task type based on input
	 * Pre-Cond: String of command
	 * Post-Cond: TaskType of the given input
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
