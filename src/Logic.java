import java.util.*;

class Logic{

	private Storage storage;
	private Parser parser;
	private TreeSet<Task> taskList;

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

	private final String ERROR_NO_ERROR = "No Error";
	private final String ERROR_COMMAND_NOT_FOUND = "Command not found";
	private final String ERROR_NO_INPUT = "No input";
	private final String ERROR_INVALID_ARGUMENT = "Invalid argument";

	// List of all available commands for the hashmap
	private final String VALUE_COMMAND_ADD = "add";
	private final String VALUE_COMMAND_DELETE = "delete";
	private final String VALUE_COMMAND_CLEAR = "clear";
	private final String VALUE_COMMAND_TICK = "tick";
	private final String VALUE_COMMAND_UPDATE = "update";
	private final String VALUE_COMMAND_EXIT = "exit";
	private final String VALUE_COMMAND_UNDEFINED = "undefined";

	// List of all available types for the hashmap
	private final String VALUE_TYPE_FLOAT = "float";
	private final String VALUE_TYPE_DEADLINE = "deadline";
	private final String VALUE_TYPE_RECURRING = "recurring";

	//Return message 
	private final String EXCEPTION_FAIL_TO_INSTANTIATE_TASK = "Error occured when try to instantiate a task, please try again";
	private final String SUCCESSFUL_MESSAGE = "success";
	private final String FAILURE_INDEX_OUT_OF_BOUND = "Invalid Input: Index out of bound";


	private boolean stringToBoolean(String str){
		if(str.equals(VALUE_TRUE)){
			return true;
		} else {
			return false;
		}
	}
	private String errorCodeToString(String str){
		String result;
		if(str.equals(VALUE_ERROR_NO_ERROR)){
			result = ERROR_NO_ERROR;
		} else if (str.equals(VALUE_ERROR_COMMAND_NOT_FOUND)){
			result = ERROR_COMMAND_NOT_FOUND;
		} else if (str.equals(VALUE_ERROR_NO_INPUT)){
			result = ERROR_NO_INPUT;
		} else if (str.equals(VALUE_ERROR_INVALID_ARGUMENT)){
			result = ERROR_INVALID_ARGUMENT;
		} else {
			result = null;
		}

		return result;
	}
	
	public Logic(){
		storage = new Storage();
		parser = new Parser(); // processInput
		taskList = storage.loadCurrentTask();
	}
	
	public TreeSet<Task> getList(){
		return taskList;
	}
	
	public String process(String cmd){
		HashMap<String,String> cmdTable = parser.processInput(cmd);
		System.out.println(cmdTable.toString());
		boolean isValid = stringToBoolean(cmdTable.get(KEY_IS_VALID));

		String result;

		if(isValid){
			result = process(cmdTable);
		} else {
			result =  errorCodeToString(cmdTable.get(KEY_ERROR_CODE));
		}

		return result;
	}

	private String process(HashMap<String,String> cmdTable){
		String cmdType = cmdTable.get(KEY_COMMAND);
		String result;

		if(cmdType.equals(VALUE_COMMAND_ADD)){
			result = cmdAdd(cmdTable);
		} else if (cmdType.equals(VALUE_COMMAND_DELETE)){
			result = cmdDelete(cmdTable);
		} else if (cmdType.equals(VALUE_COMMAND_TICK)){
			result = cmdTick(cmdTable);
		} else if (cmdType.equals(VALUE_COMMAND_UPDATE)){
			result = cmdUpdate(cmdTable);
		} else if (cmdType.equals(VALUE_COMMAND_CLEAR)){
			result = cmdClear();
		} else if (cmdType.equals(VALUE_COMMAND_EXIT)){
			result = cmdExit();
		} else {
			result = VALUE_COMMAND_UNDEFINED;
		}

		return result;
	}

	private CommandType determineCommandType(String commandTypeString) {
		if (commandTypeString == null) {
			throw new Error("command type string cannot be null!");
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
	
	
	private String cmdAdd(HashMap<String,String> cmdTable){
		try {
			Task newTask;
			newTask = new Task(cmdTable);
			taskList.add(newTask);
			CommandType commandType = determineCommandType(VALUE_COMMAND_ADD);
			storage.accessStorage(commandType, newTask);
			return SUCCESSFUL_MESSAGE;
		} catch (Exception e){
			return EXCEPTION_FAIL_TO_INSTANTIATE_TASK;
		}
	}
	
	private String cmdDelete(HashMap<String,String> cmdTable){
		int position = Integer.parseInt(cmdTable.get(KEY_CONTENT));

		if(position > taskList.size()){
			return FAILURE_INDEX_OUT_OF_BOUND;
		} else {
			Iterator<Task> itr;
			itr = taskList.iterator();
			for(int i = 1; i< position; i++){
				itr.next();
			}
			Task toBeDeleted = itr.next();
			taskList.remove(toBeDeleted);
			CommandType commandType = determineCommandType(VALUE_COMMAND_DELETE);
			storage.accessStorage(commandType, toBeDeleted);
			return SUCCESSFUL_MESSAGE;
		}
	}

	private String cmdTick(HashMap<String,String> cmdTable){
		int position = Integer.parseInt(cmdTable.get(KEY_CONTENT));

		if(position > taskList.size()){
			return FAILURE_INDEX_OUT_OF_BOUND;
		} else {
			Iterator<Task> itr;
			itr = taskList.iterator();
			for(int i = 1; i< position; i++){
				itr.next();
			}
			Task toBeDeleted = itr.next();
			toBeDeleted.setIsFinished(true);
			CommandType commandType = determineCommandType(VALUE_COMMAND_TICK);
			storage.accessStorage(commandType, toBeDeleted);
			return SUCCESSFUL_MESSAGE;
		}
	}
	private String cmdUpdate(HashMap<String,String> cmdTable){
		String message = cmdTable.get(KEY_CONTENT);
		String[] messageSplit = message.split(" ");
		int position = Integer.parseInt(messageSplit[0]);

		if(position > taskList.size()){
			return FAILURE_INDEX_OUT_OF_BOUND;
		} else {
			Iterator<Task> itr;
			itr = taskList.iterator();
			for(int i = 1; i< position; i++){
				itr.next();
			}
			Task toBeEdited = itr.next();
			toBeEdited.setContent(messageSplit[1]);
			CommandType commandType = determineCommandType(VALUE_COMMAND_UPDATE);
			storage.accessStorage(commandType, toBeEdited);
			return SUCCESSFUL_MESSAGE;
		}
	}
	private String cmdClear(){
		taskList.clear();
		CommandType commandType = determineCommandType(VALUE_COMMAND_CLEAR);
		storage.accessStorage(commandType, null);
		return SUCCESSFUL_MESSAGE;
	}
	
	private String cmdExit(){
		return SUCCESSFUL_MESSAGE;
	}


}













