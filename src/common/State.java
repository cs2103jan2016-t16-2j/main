package common;

import java.util.ArrayList;
import java.util.Date;

public class State {
	private boolean isValid_, isStartDateChanged_, isEndDateChanged_, isContentChanged_, isVenueChanged_, isDetailChanged_;
	private String content_, detail_, userInput_, venue_, displayMessage_, searchKey_;
	private TaskType taskType_;
	private CommandType commandType_;
	private ViewMode viewMode_;
	private int positionIndex_;
	private Date startDate_, endDate_;
	private ArrayList<Task> floatingTasks_,deadlineTasks_, allTasks_, searchResultTasks_, finishedTasks_;
	

	public State(){
		isValid_ = Constant.VALUE_DEFAULT_BOOLEAN_TRUE;
		isStartDateChanged_ = Constant.VALUE_DEFAULT_BOOLEAN_FALSE;
		isEndDateChanged_ = Constant.VALUE_DEFAULT_BOOLEAN_FALSE; 
		isContentChanged_ = Constant.VALUE_DEFAULT_BOOLEAN_FALSE; 
		isVenueChanged_ = Constant.VALUE_DEFAULT_BOOLEAN_FALSE; 
		isDetailChanged_ = Constant.VALUE_DEFAULT_BOOLEAN_FALSE;
		commandType_ = CommandType.UNDEFINED;
		content_ = Constant.VALUE_DEFAULT_EMPTY;
		detail_ = Constant.VALUE_DEFAULT_EMPTY;
		userInput_ = Constant.VALUE_DEFAULT_EMPTY;
		venue_ = Constant.VALUE_DEFAULT_EMPTY;
		displayMessage_ = Constant.VALUE_DEFAULT_EMPTY;
		searchKey_ =  Constant.VALUE_DEFAULT_EMPTY;
		taskType_ = TaskType.UNDEFINED;
		positionIndex_ = Constant.VALUE_DEFAULT_POSITION_INDEX;
		startDate_ = null;
		endDate_ = null;
		floatingTasks_ = new ArrayList<Task>();
		deadlineTasks_ = new ArrayList<Task>();
		allTasks_ = new ArrayList<Task>();
		searchResultTasks_ = new ArrayList<Task>();
		finishedTasks_ = new ArrayList<Task>();
	}
	
	/*
	 * List of accessors and mutators for private attributes
	 */

	public void setIsValid(boolean bool){
		isValid_ = bool;
	}
	
	public boolean getIsValid(){
		return isValid_;
	}

	public void setIsStartDateChanged(boolean bool){
		isStartDateChanged_ = bool;
	}
	
	public boolean getIsStartDateChanged(){
		return isStartDateChanged_;
	}
	
	public void setIsEndDateChanged(boolean bool){
		isEndDateChanged_ = bool;
	}
	
	public boolean getIsEndDateChanged(){
		return isEndDateChanged_;
	}
	
	public void setIsContentChanged(boolean bool){
		isContentChanged_ = bool;
	}
	
	public boolean getIsContentChanged(){
		return isContentChanged_;
	}
	
	public void setIsVenueChanged(boolean bool){
		isVenueChanged_ = bool;
	}
	
	public boolean getIsVenueChanged(){
		return isVenueChanged_;
	}
	
	public void setIsDetailChanged(boolean bool){
		isDetailChanged_ = bool;
	}
	
	public boolean getIsDetailChanged(){
		return isDetailChanged_;
	}

	public void setCommandType(CommandType command){
		commandType_ = command;
	}

	public CommandType getCommandType(){
		return commandType_;
	}
	
	public void setTaskType(TaskType type){
		taskType_ = type;
	}
	
	public TaskType getTaskType(){
		return taskType_;
	}
	
	public void setViewMode(ViewMode vm){
		viewMode_ = vm;
	}
	
	public ViewMode getViewMode(){
		return viewMode_;
	}

	public void setContent(String content){
		content_ = content;
	}

	public String getContent(){
		return content_;
	}
	
	public void setVenue(String venue){
		venue_ = venue;
	}	

	public String getVenue(){
		return venue_;
	}
	
	public void setDetail(String detail){
		detail_ = detail;
	}

	public String getDetail(){
		return detail_;
	}
	
	public void setDisplayMessage(String errorMessage){
		displayMessage_ = errorMessage;
	}

	public String getDisplayMessage(){
		return displayMessage_;
	}
	
	public void setSearchKey(String searchKey){
		searchKey_ = searchKey;
	}

	public String getSearchKey(){
		return searchKey_;
	}
	
	public void setUserInput(String userInput){
		userInput_ = userInput;
	}	
	
	public String getUserInput(){
		return userInput_;
	}
	
	public void setPositionIndex(int position){
		this.positionIndex_ = position;
	}

	public int getPositionIndex(){
		return positionIndex_;
	}

	public void setStartDate(Date startDate){
		startDate_ = startDate;
	}
	
	public Date getStartDate(){
		return startDate_;
	}

	public void setEndDate(Date endDate){
		endDate_ = endDate;
	}
	
	public Date getEndDate(){
		return endDate_;
	}
	
//	private ArrayList<Task> floatingTasks_,normalTasks_, allTask_, searchResultTasks_, FinishedTasks_;

	public void setFloatingTasks (ArrayList<Task> floatingTasks){
		floatingTasks_ = floatingTasks;
	}
	
	public ArrayList<Task> getFloatingTasks (){
		return floatingTasks_;
	}
	
	public void setDeadlineTasks (ArrayList<Task> deadlineTasks){
		deadlineTasks_ = deadlineTasks;
	}

	public ArrayList<Task> getDeadlineTasks (){
		return deadlineTasks_;
	}
	
	public void setAllTasks (ArrayList<Task> allTasks){
		allTasks_ = allTasks;
	}

	public ArrayList<Task> getAllTasks (){
		return allTasks_;
	}
	
	public void setSearchResultTasks (ArrayList<Task> searchResultTasks){
		searchResultTasks_ = searchResultTasks;
	}
	
	public ArrayList<Task> getSearchResultTasks (){
		return searchResultTasks_;
	}
	
	public void setFinishedTasks (ArrayList<Task> finishedTasks){
		finishedTasks_ = finishedTasks;
	}

	public ArrayList<Task> getFinishedTasks(){
		return finishedTasks_;
	}
	
	public State deepCopy(){
		State newState = new State();
		boolean isValid = isValid_; 
		boolean isStartDateChanged = isStartDateChanged_, isEndDateChanged = isEndDateChanged_;
		boolean isContentChanged= isContentChanged_, isVenueChanged = isVenueChanged_, isDetailChanged =isDetailChanged_;
		String content, detail, userInput, venue, displayMessage, searchKey;
		content = new String(content_);
		detail = new String(detail_);
		userInput = new String(userInput_);
		venue = new String(venue_);
		displayMessage = new String(displayMessage_);
		searchKey = new String(searchKey_);
		
		TaskType taskType = taskType_;
		CommandType commandType = commandType_;
		ViewMode viewMode = viewMode_;
		int positionIndex = positionIndex_;
		Date startDate = new Date(startDate_.getTime());
		Date endDate = new Date(endDate_.getTime());;
		
		ArrayList<Task> floatingTasks = new ArrayList<Task>();
		for(int i = 0; i < floatingTasks_.size(); i++){
			floatingTasks.add(floatingTasks_.get(i));
		}
		
		ArrayList<Task> deadlineTasks = new ArrayList<Task>();
		for(int i = 0; i < deadlineTasks_.size(); i++){
			deadlineTasks.add(deadlineTasks_.get(i));
		}
		
		ArrayList<Task> allTasks = new ArrayList<Task>();
		for(int i = 0; i < allTasks_.size(); i++){
			allTasks.add(allTasks_.get(i));
		}
		
		ArrayList<Task> searchResultTasks = new ArrayList<Task>();
		for(int i = 0; i < searchResultTasks_.size(); i++){
			searchResultTasks.add(searchResultTasks_.get(i));
		}
		
		ArrayList<Task> finishedTasks = new ArrayList<Task>();
		for(int i = 0; i < finishedTasks_.size(); i++){
			finishedTasks.add(finishedTasks_.get(i));
		}
				
		newState.setIsValid(isValid);
		newState.setIsStartDateChanged(isStartDateChanged);
		newState.setIsEndDateChanged(isEndDateChanged);
		newState.setIsContentChanged(isContentChanged);
		newState.setIsVenueChanged(isVenueChanged);
		newState.setIsDetailChanged(isDetailChanged);
		
		newState.setContent(content);
		newState.setDetail(detail);
		newState.setUserInput(userInput);
		newState.setVenue(venue);
		newState.setDisplayMessage(displayMessage);
		newState.setSearchKey(searchKey);	
		
		newState.setTaskType(taskType);
		newState.setCommandType(commandType);
		newState.setViewMode(viewMode);
		newState.setPositionIndex(positionIndex);
		newState.setStartDate(startDate);
		newState.setEndDate(endDate);
		
		newState.setFloatingTasks(floatingTasks);
		newState.setDeadlineTasks(deadlineTasks);
		newState.setAllTasks(allTasks);
		newState.setFinishedTasks(finishedTasks);
		newState.setSearchResultTasks(searchResultTasks);
		
		return newState;
	}



}
