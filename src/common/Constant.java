//@@author A0130369H
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
		public static final String CONFIG_DIR = "1. To change the Storage Directory, input the absolute path\n" +
												"	    Current Directory: %s\n" + 
												"(E.g. for Mac user, input: /Users/username/folder/subfolder, for Windows users, input: C:\\Users\\username\\folder)";
		public static final String CONFIG_THEME = "2. To change the theme, input the theme name: (E.g. input: theme autumn)";
		public static final String CONFIG_FONT = "3. To change the font, input the font name: (E.g. input: font segoe)";
		
		//@@author A0130717M
		//List of displayed messages
		public static final String MESSAGE_SUCCESS = "successfully updated";
		public static final String MESSAGE_INDEX_OUT_OF_BOUND = "Oops, did you enter the correct index? Index should be from 1 to size of the list";
		public static final String MESSAGE_EMPTY_STACK = "Oops, this is already the oldest  version";	
		public static final String MESSAGE_DELETE_IN_WRONG_MODE = "Oops, you can only delete under starting, floating, scheduled, all, search and finished mode";	
		public static final String MESSAGE_CLEAR_IN_WRONG_MODE = "Oops, you can only clear under starting, floating, scheduled, all, search and finished mode";	
		public static final String MESSAGE_TICK_IN_WRONG_MODE = "Oops, you can only delete under starting, floating, scheduled, all and search mode";	
		public static final String MESSAGE_UPDATE_IN_WRONG_MODE = "Oops, you can only delete under starting, floating, scheduled, all, search and finished mode";		
		public static final String MESSAGE_SYSTEM_FAILED_TO_TICK = "Oops, there is an error when ticking the task";	
		public static final String MESSAGE_DUMMY = "DIDI is a powerful woman";
		
		//List of headers
		public static final String HEADER_FLOATING = "Floating";
		public static final String HEADER_DEADLINE = "Scheduled";
		public static final String HEADER_ALL = "All";
		public static final String HEADER_SEARCH = "Search";
		public static final String HEADER_FINISHED = "Finished";
		public static final String HEADER_CONFIG = "Setting";
		public static final String HEADER_HELP = "Help";
		public static final String HEADER_UNDEFINED = "Undefined Mode";	
		public static final String HEADER_START = "Today";
		
		//List of empty messages
		public static final String EMPTY_TODAY = "Yeah, you have no due today!";
		public static final String EMPTY_ALL = "Yeah, you have no tasks!";
		public static final String EMPTY_DEADLINE = "Yeah, you have no scheduled tasks!";
		public static final String EMPTY_FLOATING = "Yeah, you have no to-do!";
		public static final String EMPTY_SEARCH = "Sorry, there is no result tasks when %1$s";
		
		//List of themes
		public static final String STYLE_AUTUMN =  "autumn";
		public static final String STYLE_BOKEH = "bokeh";
		public static final String STYLE_BRANCH = "branch";
		public static final String STYLE_CAT = "cat";
		public static final String STYLE_LEATHER = "leather";
		public static final String STYLE_PARIS = "paris"; 
		public static final String STYLE_WARM = "warm";
		public static final String STYLE_WHEAT = "wheat";
		
		//List of fonts
		public static final String FONT_CONSOLAS = "consolas";
        public static final String FONT_SEGOE = "segoe";
}
