package common;

public class Constant {
	
		// List of all available error values for the parsedCommand
		public static final String VALUE_ERROR_NO_ERROR = "Success";
		public static final String VALUE_ERROR_COMMAND_NOT_FOUND = "Invalid command";
		public static final String VALUE_ERROR_NO_INPUT = "Empty input";
		public static final String VALUE_ERROR_INVALID_ARGUMENT = "Invalid argument";
		public static final String VALUE_ERROR_DATE_NOT_PARSED = "Wrong date format?";
		public static final String VALUE_ERROR_DATE_ERROR = "Oops, starting date can't be later than end date";
		public static final String VALUE_DEFAULT_EMPTY = "";
		public static final int VALUE_DEFAULT_POSITION_INDEX = 1;
		public static final boolean VALUE_DEFAULT_BOOLEAN_TRUE = true;
		public static final boolean VALUE_DEFAULT_BOOLEAN_FALSE = false;
		public static final ViewMode VALUE_DEFAULT_VIEW_MODE = ViewMode.ALL;
		
		//List of displayed messages
		public static final String MESSAGE_SUCCESS = "successfully updated";
		public static final String MESSAGE_INDEX_OUT_OF_BOUND = "Oops, did you enter the correct index? Index should be from 1 to size of the list";
		public static final String MESSAGE_EMPTY_STACK = "Oops, this is already the oldest  version";	
		public static final String MESSAGE_DELETE_IN_WRONG_MODE = "Oops, you can only delete under floating, deadline, all or search mode";	
		public static final String MESSAGE_SYSTEM_FAILED_TO_TICK = "Oops, there is an error when ticking the task";	
		public static final String MESSAGE_DUMMY = "DIDI is a powerful woman";
		
		//List of headers
		public static final String HEADER_FLOATING = "All Tasks Without Deadlines";
		public static final String HEADER_DEADLINE = "All Tasks With Deadlines";
		public static final String HEADER_ALL = "All Tasks";
		public static final String HEADER_SEARCH = "Search Result";
		public static final String HEADER_FINISHED = "All Finished Tasks";
		public static final String HEADER_CONFIG = "Configuration";
		public static final String HEADER_UNDEFINED = "Undefined Mode";
		

}
