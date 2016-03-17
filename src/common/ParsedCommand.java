package common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ParsedCommand {
	private boolean isValid_;
	private int errorCode_;
	private CommandType command_;
	private String content_;
	private TaskType type_;
	private boolean isStartDate_;
	private Date startDate_;
	private boolean isEndDate_;
	private Date endDate_;
	
	public ParsedCommand(){
		Constant c = new Constant();
		isValid_ = true;
		errorCode_ = c.VALUE_ERROR_NO_ERROR;
		command_ = CommandType.UNDEFINED;
		content_ = c.VALUE_DEFAULT_EMPTY;
		type_ = TaskType.UNDEFINED;
		isStartDate_ = false;
		startDate_ = null;
		isEndDate_ = false;
		endDate_ = null;
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
	
	public void setStartDate(Date startDate){
		startDate_ = startDate;
	}
	
	public void setEndDate(Date endDate){
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
	
	public Date getStartDate(){
		return startDate_;
	}
	
	public Date getEndDate(){
		return endDate_;
	}
	
}
