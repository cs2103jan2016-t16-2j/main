import java.lang.StringBuilder;
import java.util.HashMap;

class Parser {
	// List of all the available keys for the hashmap
	private final String KEY_IS_VALID = "isValid";
	private final String KEY_ERROR_CODE = "errorCode";
	private final String KEY_COMMAND = "command";
	private final String KEY_CONTENT = "content";
	private final String KEY_TYPE = "type";
	private final String KEY_START_DATE = "startDate";
	private final String KEY_END_DATE = "endDate";
	
	// List of all available boolean values for the hashmap
	private final String VALUE_TRUE = "true";
	private final String VALUE_FALSE = "false";
	
	// List of all available error values for the hashmap
	private final String VALUE_ERROR_NO_ERROR = "0";
	private final String VALUE_ERROR_COMMAND_NOT_FOUND = "1";
	private final String VALUE_ERROR_NO_INPUT = "2";
	private final String VALUE_ERROR_INVALID_ARGUMENT = "3";
	
	// List of all available commands for the hashmap
	private final String VALUE_COMMAND_ADD = "add";
	private final String VALUE_COMMAND_DELETE = "delete";
	private final String VALUE_COMMAND_CLEAR = "clear";
	private final String VALUE_COMMAND_TICK = "tick";
	private final String VALUE_COMMAND_UPDATE = "update";
	private final String VALUE_COMMAND_EXIT = "exit";
	
	// List of all available types for the hashmap
	private final String VALUE_TYPE_FLOAT = "float";
	private final String VALUE_TYPE_DEADLINE = "deadline";
	private final String VALUE_TYPE_RECURRING = "recurring";
	
	private final String VALUE_DEFAULT_EMPTY = "";
	
	// Class attributes
	private String isValid_;
	private String errorCode_;
	private String command_;
	private String content_;
	private String type_;
	private String startDate_;
	private String endDate_;
	private String input_;
	
	
	private HashMap<String, String> map;
	
	/*
	 * Initializing parser
	 * Input: None
	 * Output: A parser instance
	 */
	public Parser(){
		isValid_ = VALUE_TRUE;
		errorCode_ = VALUE_ERROR_NO_ERROR;
		command_ = VALUE_DEFAULT_EMPTY;
		content_ = VALUE_DEFAULT_EMPTY;
		type_ = VALUE_TYPE_FLOAT;
		startDate_ = VALUE_DEFAULT_EMPTY;
		endDate_ = VALUE_DEFAULT_EMPTY;
		
		map = new HashMap<String, String>();
	}
	
	/*
	 * Break down a string of input into smaller parts for logic to process
	 * Input: String
	 * Output: A hashmap with predefined keys
	 */
	public HashMap<String, String> processInput(String input){
		input_ = input;
		isInputValid();
		buildMap();
		return map;
	}
	
	/*
	 * Check whether the input is valid (There is a recognizable command and content)
	 * Input: None
	 * Output: None
	 */
	private void isInputValid() {
		if(isInputEmpty()){
			errorCode_ = VALUE_ERROR_NO_INPUT;
			isValid_ = VALUE_FALSE;
		}else if(isCommandInvalid()){
			errorCode_ = VALUE_ERROR_COMMAND_NOT_FOUND;
			isValid_ = VALUE_FALSE;
		}else if(isArgumentInvalid()){
			errorCode_ = VALUE_ERROR_INVALID_ARGUMENT;
			isValid_ = VALUE_FALSE;
		}
	}
	

	/*
	 * Check whether the input is an empty string
	 * Input: None
	 * Output: true if it is empty. false otherwise
	 */

	private boolean isInputEmpty() {
		return input_.length() == 0;
	}
	
	/*
	 * Check whether the input has a valid command (add, update, tick, delete, clear, exit)
	 * Input: None
	 * Output: True if it is valid. False otherwise
	 */
	private boolean isCommandInvalid() {
		String inputList[] = input_.split(" ");
		try{
			switch(CommandType.valueOf(inputList[0])){
			
				case ADD:
					command_ = VALUE_COMMAND_ADD;
					content_ = readContent(inputList);
					return false;
					
				case CLEAR:
					command_ = VALUE_COMMAND_CLEAR;
					content_ = readContent(inputList);
					return false;
				
				case DELETE:
					command_ = VALUE_COMMAND_DELETE;
					content_ = readContent(inputList);
					return false;
				
//				case EXIT:
//					command_ = VALUE_COMMAND_EXIT;
//					content_ = readContent(inputList);
//					return false;
					
				case TICK:
					command_ = VALUE_COMMAND_TICK;
					content_ = readContent(inputList);
					return false;
					
				case UPDATE:
					command_ = VALUE_COMMAND_UPDATE;
					content_ = readContent(inputList);
					return false;
				
				default: 
					return true;
			}
		}
		catch(IllegalArgumentException e){
			return true;
		}
		
	}
	
	/*
	 * Check whether the input has the correct and valid argument for the given command
	 */
	private boolean isArgumentInvalid() {
		if(errorCode_.equals(VALUE_ERROR_NO_ERROR)){
			try{
				switch(CommandType.valueOf(command_)){
				
					case ADD:
						if(content_.length() == 0){
							return true;
						}
					
					case CLEAR:
						if(content_.length() != 0){
							return true;
						}
				
					case DELETE:
						if(content_.length() == 0 || !content_.matches("\\d+")){
							return true;
						}
				
//					case EXIT:
//						if(content_.length() != 0){
//							return true;
//						}
					
					case TICK:
						if(content_.length() == 0 || !content_.matches("\\d+")){
							return true;
						}
					
					case UPDATE:
						String lst[] = content_.split(" ");
						if(!lst[0].matches("\\d+") || content_.length() == 0 || lst.length < 2){
							return true;
						}
				
					default: 
						return false;
				}
			}
			catch(IllegalArgumentException e){
				return false;
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
		return sb.toString();
	}
	

	/*
	 * Build the hashmap with the required keys and values
	 * Input: None
	 * Output: None
	 */
	private void buildMap() {
		map.put(KEY_IS_VALID, isValid_);
		map.put(KEY_ERROR_CODE, errorCode_);
		map.put(KEY_COMMAND, command_);
		map.put(KEY_CONTENT, content_);
		map.put(KEY_TYPE, type_);
		map.put(KEY_START_DATE, startDate_);
		map.put(KEY_END_DATE, endDate_);
	}
}
