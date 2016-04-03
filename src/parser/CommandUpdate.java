package parser;

import java.util.Date;

import common.CommandType;
import common.Constant;
import common.TaskType;

public class CommandUpdate implements Command{

	@Override
	public void processInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getRawContent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDetail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getVenue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getStartDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getEndDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getContent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSearchKey() {
		// TODO Auto-generated method stub
		return null;
	}

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
	if(list.length == 1){
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
			state_.setDisplayMessage(Constant.VALUE_ERROR_NO_INPUT);
			return Constant.VALUE_DEFAULT_EMPTY;
		}
		state_.setIsContent(true);
		return lst[0].trim();
	}else if(isDeadline()){
		String lst[] = state_.getRawContent().split("on");
		if(lst.length == 1){
			state_.setDisplayMessage(Constant.VALUE_ERROR_NO_INPUT);
			return Constant.VALUE_DEFAULT_EMPTY;
		}
		state_.setIsContent(true);
		return lst[0].trim();
	}else if(isVenue()){
		String lst[] = state_.getRawContent().split("at:");
		if(lst.length == 1){
			state_.setDisplayMessage(Constant.VALUE_ERROR_NO_INPUT);
			return Constant.VALUE_DEFAULT_EMPTY;
		}
		state_.setIsContent(true);
		return lst[0].trim();
	}else{
		return state_.getRawContent();
	}
}

/*
 * Check whether the task has venue
 * Pre-Cond:None
 * Post-Cond:True if it has, False otherwise
 */
private boolean isVenue() {
	return state_.getIsVenue();
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
				state_.setDisplayMessage(Constant.VALUE_ERROR_DATE_ERROR);
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
 * Get the content of an input
 * Pre-Cond: None
 * Post-Cond: String
 */
private String getRawContent(){
	String inputList[] = state_.getUserInput().split(" ");
	return readContent(inputList);
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
