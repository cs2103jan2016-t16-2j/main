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
		public static final boolean VALUE_DEFAULT_BOOLEAN_IS_NEW_TASK_DETAIL_DISPLAYED = true;
		public static final boolean VALUE_DEFAULT_BOOLEAN_IS_NEW_TASK_IMPORTANT = false;
		public static final boolean VALUE_DEFAULT_BOOLEAN_FALSE = false;
		public static final ViewMode VALUE_DEFAULT_VIEW_MODE = ViewMode.START;
		
		//Cofiguration displaying message
		public static final String CONFIG_INTRO = "Welcome to the Configuration Mode";
		public static final String CONFIG_DIR = "1. To change the Storage Directory, please input the absolute path\n" +
												"	Current Directory: %s\n" + 
												"	E.g. for Mac user, input: /Users/username/folder/subfolder\n" +
												"    	 for Windows users, input: C:\\Users\\username\\folder";
		public static final String CONFIG_THEME = "2. To change the theme, please input the theme name:\n" + 
												  "   E.g. input: theme autumn";
		public static final String CONFIG_FONT = "3. To change the font, please input the font name:\n" +
												 "   E.g. input: font segoe";
		
		//@@author A0130717M
		//List of displayed messages
		public static final String MESSAGE_SUCCESS = "successfully updated";
		public static final String MESSAGE_INDEX_OUT_OF_BOUND = "Oops, did you enter the correct index? Index should be from 1 to size of the list";
		public static final String MESSAGE_EMPTY_STACK = "Oops, this is already the oldest  version";	
		public static final String MESSAGE_DELETE_IN_WRONG_MODE = "Oops, you can only delete under floating, deadline, all or search mode";	
		public static final String MESSAGE_SYSTEM_FAILED_TO_TICK = "Oops, there is an error when ticking the task";	
		public static final String MESSAGE_DUMMY = "DIDI is a powerful woman";
		
		//List of headers
		public static final String HEADER_FLOATING = "Floating";
		public static final String HEADER_DEADLINE = "Deadlines";
		public static final String HEADER_ALL = "All";
		public static final String HEADER_SEARCH = "Search";
		public static final String HEADER_FINISHED = "Finished";
		public static final String HEADER_CONFIG = "Config";
		public static final String HEADER_HELP = "Help";
		public static final String HEADER_UNDEFINED = "Undefined Mode";	
		public static final String HEADER_START = "Today";
}
