package CommonPackage;

public class Constant {
	
		// List of all available boolean values for the parsedCommand
		public final String VALUE_TRUE = "true";
		public final String VALUE_FALSE = "false";
		
		// List of all available error values for the parsedCommand
		public final String VALUE_ERROR_NO_ERROR = "0";
		public final String VALUE_ERROR_COMMAND_NOT_FOUND = "1";
		public final String VALUE_ERROR_NO_INPUT = "2";
		public final String VALUE_ERROR_INVALID_ARGUMENT = "3";
		
		// List of all available commands for the parsedCommand
		public final String VALUE_COMMAND_ADD = "add";
		public final String VALUE_COMMAND_DELETE = "delete";
		public final String VALUE_COMMAND_CLEAR = "clear";
		public final String VALUE_COMMAND_TICK = "tick";
		public final String VALUE_COMMAND_UPDATE = "update";
		public final String VALUE_COMMAND_EXIT = "exit";
		
		// List of all available types for the parsedCommand
		public final String VALUE_TYPE_FLOAT = "float";
		public final String VALUE_TYPE_DEADLINE = "deadline";
		public final String VALUE_TYPE_RECURRING = "recurring";
		
		public final String VALUE_DEFAULT_EMPTY = "";
		
		public Constant(){
			
		}
}
