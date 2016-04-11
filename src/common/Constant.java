//@@author A0130369H
package common;

public class Constant {
		
		// List of all available error values for the parser
		public static final String VALUE_ERROR_COMMAND_NOT_FOUND = "Invalid command. Check your command again?";
		public static final String VALUE_ERROR_NO_INPUT = "Empty input";
		public static final String VALUE_ERROR_INVALID_ARGUMENT = "Invalid argument";
		public static final String VALUE_ERROR_ADD_EMPTY = "What do you want to add?";
		public static final String VALUE_ERROR_SEARCH_EMPTY = "What do you want to search?";
		public static final String VALUE_ERROR_UPDATE_WRONG_ARGUMENT = "Your input seems wrong. You may type Help for input format";
		public static final String VALUE_ERROR_INVALID_VIEW_MODE = "We do not have that view mode. Try again?";
		public static final String VALUE_ERROR_INVALID_CONFIG = "We do not have that font or themes. Check for any typos?";
		public static final String VALUE_ERROR_ARGUMENT_NOT_EMPTY = "This command has no argument, remember?";
		public static final String VALUE_ERROR_ARGUMENT_NOT_NUMBER = "Do you put the index correctly?";
		public static final String VALUE_ERROR_DATE_NOT_PARSED = "Wrong date format?";
		public static final String VALUE_ERROR_DATE_ERROR = "Oops, starting date can't be later than end date";
		public static final String VALUE_ERROR_WRONG_FORMAT = "Seems like you put the wrong input format";
		
		//List of all available success message for parser
		public static final String VALUE_SUCCESS_NO_ERROR = "Success";
		public static final String VALUE_SUCCESS_ADD = "A task has been added!";
		public static final String VALUE_SUCCESS_CLEAR = "Tasks have been cleared!";
		public static final String VALUE_SUCCESS_DELETE = "A task has been deleted";
		public static final String VALUE_SUCCESS_UNDO = "Undo";
		public static final String VALUE_SUCCESS_REDO = "Redo";
		public static final String VALUE_SUCCESS_SEARCH = "Searching the keyword...";
		public static final String VALUE_SUCCESS_TICK = "A task has been finished!";
		public static final String VALUE_SUCCESS_UPDATE = "A task has been updated!";
		public static final String VALUE_SUCCESS_DETAIL = "Checking the detail of a task...";
		public static final String VALUE_SUCCESS_VIEW_MODE = "Changing view mode...";
		public static final String VALUE_SUCCESS_HELP = "Here's your help!";
		public static final String VALUE_SUCCESS_CONFIG = "Changing the setting...";
		public static final String VALUE_SUCCESS_UNTICK = "A task has been marked unfinished";
		
		// List of all default values for state
		public static final String VALUE_DEFAULT_EMPTY = "";
		public static final int VALUE_DEFAULT_POSITION_INDEX = 1;
		public static final boolean VALUE_DEFAULT_BOOLEAN_TRUE = true;
		public static final boolean VALUE_DEFAULT_BOOLEAN_IS_NEW_TASK_DETAIL_DISPLAYED = true;
		public static final boolean VALUE_DEFAULT_BOOLEAN_IS_NEW_TASK_IMPORTANT = false;
		public static final boolean VALUE_DEFAULT_BOOLEAN_FALSE = false;
		public static final ViewMode VALUE_DEFAULT_VIEW_MODE = ViewMode.START;
		
		//Setting displaying message
		public static final String CONFIG_INTRO = "Welcome to Wallist Setting";
		public static final String CONFIG_DIR = "1. Your current Directory is %s\n" + 
		                                        "You will be able to customize this in our next version";
		public static final String CONFIG_THEME = "2. To change the theme, input the theme name: (E.g. input: theme autumn)";
		public static final String CONFIG_FONT = "3. To change the font, input the font name: (E.g. input: font segoe)";
		
		//@@author A0130717M

		//Help manual displaying message
		public static final String HELP_INTRO = "Welcome to Wallist Help Manual";
		public static final String HELP_ADD = "1. To add a task, input \"add + task name + (on: deadline) or (from: start to: end)\n" + 
				                              "(at: location) + (detail: details)\"\n" + 
				                              "(E.g. add homework due on: tomorrow)";
		public static final String HELP_DELETE = "2. To delete a task, input \"delete + task index\"";
		public static final String HELP_TICK = "3. To mark a task as finished, input \"tick + task index\"\n" + 
		                                       "To unmark a task, go to finished tab and input \"untick + task index\"";
		public static final String HELP_UPDATE = "4. To update a task, input \"update + task index + changes\"\n" +
		                                         "(E.g. update 1 at: seminar room)";
		public static final String HELP_VIEW = "5. To view any task details, input \"view + task index\"\n" +
		                                       "To switch tab, input \"view + tab name\", for search, setting and help, simply enter the tab name";
		public static final String HELP_EXIT = "6. To exit Wallist, input \"exit\" or Esc key";
		public static final String HELP_END = "Thank you for supporting Wallist";
		
		//List of displayed messages
		public static final String MESSAGE_SUCCESS = "successfully updated";
		public static final String MESSAGE_INDEX_OUT_OF_BOUND = "Oops, did you enter the correct index?";
		public static final String MESSAGE_EMPTY_STACK = "Oops, this is already the oldest version";	
		public static final String MESSAGE_DELETE_IN_WRONG_MODE = "Oops, you cannot delete here";	
		public static final String MESSAGE_CLEAR_IN_WRONG_MODE = "Oops, you cannot clear here";	
		public static final String MESSAGE_TICK_IN_WRONG_MODE = "Oops, you cannot tick here";	
		public static final String MESSAGE_UPDATE_IN_WRONG_MODE = "Oops, you cannot update here";		
		public static final String MESSAGE_UNTICK_IN_WRONG_MODE = "Oops, you cannot untick here";		
		public static final String MESSAGE_SYSTEM_FAILED_TO_TICK = "Oops, there is an error when ticking the task";	
		public static final String MESSAGE_CANNOT_VIEW_SEARCH = "Oops, you can not view search mode";
		public static final String MESSAGE_DUMMY = "Oops, something wrong has occurred";
	
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
		public static final String EMPTY_TODAY = "Yeah, you have no due today in your Wallist!";
		public static final String EMPTY_ALL = "Yeah, you have no tasks in your Wallist!";
		public static final String EMPTY_DEADLINE = "Yeah, you have no scheduled tasks in your Wallist!";
		public static final String EMPTY_FLOATING = "Yeah, you have no to-do in your Wallist!";
		public static final String EMPTY_SEARCH = "Sorry, there is no such result in your Wallist";
		public static final String EMPTY_FINISHED = "Sorry, there is no finished tasks in your Wallist";
			
		//List of themes
		public static final String STYLE_AUTUMN =  "autumn";
		public static final String STYLE_BOKEH = "bokeh";
		public static final String STYLE_BRANCH = "branch";
		public static final String STYLE_CAT = "cat";
		public static final String STYLE_GREY = "grey"; 
		public static final String STYLE_WARM = "warm";
		
		//List of fonts
		public static final String FONT_CONSOLAS = "consolas";
        public static final String FONT_SEGOE = "segoe";
        
        public static final int THRESHOLD_ERROR_FREQUENCY = 5;
        public static final String ERROR_RECOMMENDATION = "You may wish to go to help manual. If so, input \"help\"";
}
