package common;

public class ParsedCommand {
	private boolean isValid_;
	private int errorCode_;
	private CommandType command_;
	private String content_;
	private TaskType type_;
	private String startDate_;
	private String endDate_;
	
	public ParsedCommand(){
		Constant c = new Constant();
		isValid_ = true;
		errorCode_ = c.VALUE_ERROR_NO_ERROR;
		command_ = CommandType.UNDEFINED;
		content_ = c.VALUE_DEFAULT_EMPTY;
		type_ = TaskType.UNDEFINED;
		startDate_ = c.VALUE_DEFAULT_EMPTY;
		endDate_ = c.VALUE_DEFAULT_EMPTY;
	}
	/*
	 * List of set commands
	 */
	public void setIsValid(boolean bool){
		isValid_ = bool;
	}
	
	public void setErrorCode(int code){
		errorCode_ = code;
	}
	
	public void setCommand(CommandType command){
		command_ = command;
	}
	
	public void setContent(String content){
		content_ = content;
	}
	
	public void setType(TaskType type){
		type_ = type;
	}
	
	public void setStartDate(String startDate){
		startDate_ = startDate;
	}
	
	public void setEndDate(String endDate){
		endDate_ = endDate;
	}
	
	/*
	 * List of get commands
	 */
	public boolean getIsValid(){
		return isValid_;
	}
	
	public int getErrorCode(){
		return errorCode_;
	}
	
	public CommandType getCommand(){
		return command_;
	}
	
	public String getContent(){
		return content_;
	}
	
	public TaskType getType(){
		return type_;
	}
	
	public String getStartDate(){
		return startDate_;
	}
	
	public String getEndDate(){
		return endDate_;
	}
	
}
