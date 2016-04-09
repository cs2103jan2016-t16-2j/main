package common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class State {
	private boolean isValid_, isStartDateChanged_, isEndDateChanged_, isContentChanged_, isVenueChanged_, isDetailChanged_;
	private String content_, detail_, userInput_, venue_, displayMessage_;
	private Font font_;
	private Theme theme_;
	private TaskType taskType_;
	private CommandType commandType_;
	private ViewMode viewMode_;
	private ViewMode newViewMode_;	
	private int positionIndex_;
	private Date startDate_, endDate_;
	private ArrayList<Task> floatingTasks_,deadlineTasks_, allTasks_, searchResultTasks_, finishedTasks_, todaysTasks_;
	private ArrayList<String> searchKey_;
	private String currentDirectory_;
	private String desiredDirectory;
	
	public State(){
		isValid_ = Constant.VALUE_DEFAULT_BOOLEAN_TRUE;
		isStartDateChanged_ = Constant.VALUE_DEFAULT_BOOLEAN_FALSE;
		isEndDateChanged_ = Constant.VALUE_DEFAULT_BOOLEAN_FALSE; 
		isContentChanged_ = Constant.VALUE_DEFAULT_BOOLEAN_FALSE; 
		isVenueChanged_ = Constant.VALUE_DEFAULT_BOOLEAN_FALSE; 
		isDetailChanged_ = Constant.VALUE_DEFAULT_BOOLEAN_FALSE;
		commandType_ = CommandType.UNDEFINED;
		viewMode_ = Constant.VALUE_DEFAULT_VIEW_MODE;
		newViewMode_ = null;
		content_ = Constant.VALUE_DEFAULT_EMPTY;
		detail_ = Constant.VALUE_DEFAULT_EMPTY;
		userInput_ = Constant.VALUE_DEFAULT_EMPTY;
		venue_ = Constant.VALUE_DEFAULT_EMPTY;
		displayMessage_ = Constant.VALUE_DEFAULT_EMPTY;
		taskType_ = TaskType.UNDEFINED;
		positionIndex_ = Constant.VALUE_DEFAULT_POSITION_INDEX;
		startDate_ = null;
		endDate_ = null;
		floatingTasks_ = new ArrayList<Task>();
		deadlineTasks_ = new ArrayList<Task>();
		allTasks_ = new ArrayList<Task>();
		searchResultTasks_ = new ArrayList<Task>();
		finishedTasks_ = new ArrayList<Task>();
		todaysTasks_ = new ArrayList<Task>();
		searchKey_ = new ArrayList<String>();
		theme_ = Theme.AUTUMN;
		font_ = Font.SEGOE;
	}
	
	/*
	 * List of accessors and mutators for private attributes
	 */
	
	public void setCurrentDirectory(String directory) {
		this.currentDirectory_ = directory;
	}
	
	public String getDesiredDirectory() {
		return this.desiredDirectory;
	}
	
	public void setDesiredDirectory(String directory) {
		this.desiredDirectory = directory;
	}
	
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

	public void setNewViewMode(ViewMode vm){
		newViewMode_ = vm;
	}
	
	public ViewMode getNewViewMode(){
		return newViewMode_;
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
	
	public void setSearchKey(ArrayList<String> searchKey){
		searchKey_ = searchKey;
	}

	public ArrayList<String> getSearchKey(){
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

	public ArrayList<Task> getTodaysTasks(){
		return todaysTasks_;
	}
	
	
	public boolean recoverFrom(State oldState){
		isValid_ = oldState.getIsValid();
		isStartDateChanged_ = oldState.getIsStartDateChanged();
		isEndDateChanged_ = oldState.getIsEndDateChanged();
		isContentChanged_ = oldState.getIsContentChanged();
		isVenueChanged_ = oldState.getIsVenueChanged();
		isDetailChanged_ = oldState.getIsDetailChanged();
		content_ = oldState.getContent();
		detail_ = oldState.getDetail();
		userInput_ = oldState.getUserInput();
		venue_ = oldState.getVenue();
		displayMessage_ = oldState.getDisplayMessage();
		taskType_ = oldState.getTaskType();
		commandType_ = oldState.getCommandType();
		viewMode_ = oldState.getViewMode();
		newViewMode_ = oldState.getNewViewMode();	
		positionIndex_ = oldState.getPositionIndex();
		startDate_ = oldState.getStartDate();
		endDate_ = oldState.getEndDate();

		ArrayList<Task> floatingTasks = oldState.getFloatingTasks();
		ArrayList<Task> deadlineTasks = oldState.getDeadlineTasks();
		ArrayList<Task> allTasks = oldState.getAllTasks();
		ArrayList<Task> searchResultTasks = oldState.getSearchResultTasks();
		ArrayList<Task> finishedTasks = oldState.getFinishedTasks();
		ArrayList<String> searchKey = oldState.getSearchKey();	
		
		floatingTasks_ = new ArrayList<Task>();
		for(int i = 0; i < floatingTasks.size(); i++){
			floatingTasks_.add(floatingTasks.get(i));
		}
		
		deadlineTasks_ = new ArrayList<Task>();
		for(int i = 0; i < deadlineTasks.size(); i++){
			deadlineTasks_.add(deadlineTasks.get(i));
		}
		
		allTasks_ = new ArrayList<Task>();
		for(int i = 0; i < allTasks.size(); i++){
			allTasks_.add(allTasks.get(i));
		}
		
		searchResultTasks_ = new ArrayList<Task>();
		for(int i = 0; i < searchResultTasks.size(); i++){
			searchResultTasks_.add(searchResultTasks.get(i));
		}
		
		finishedTasks_ = new ArrayList<Task>();
		for(int i = 0; i < finishedTasks.size(); i++){
			finishedTasks_.add(finishedTasks.get(i));
		}
		
		searchKey_ = new ArrayList<String>();
		for(int i = 0; i < searchKey.size(); i++){
			searchKey_.add(searchKey.get(i));
		}

		return true;
	}

	public State deepCopy(){
		State newState = new State();
		boolean isValid = isValid_; 
		boolean isStartDateChanged = isStartDateChanged_, isEndDateChanged = isEndDateChanged_;
		boolean isContentChanged= isContentChanged_, isVenueChanged = isVenueChanged_, isDetailChanged =isDetailChanged_;
		String content, detail, userInput, venue, displayMessage;
		Date startDate;
		Date endDate;
		content = new String(content_);
		detail = new String(detail_);
		userInput = new String(userInput_);
		venue = new String(venue_);
		displayMessage = new String(displayMessage_);
		
		TaskType taskType = taskType_;
		CommandType commandType = commandType_;
		ViewMode viewMode = viewMode_;
		int positionIndex = positionIndex_;
		
		
		if (startDate_!=null) {
			startDate = new Date(startDate_.getTime());
		} else {
			startDate = null;
		}
		if (endDate_!=null) {
			endDate = new Date(endDate_.getTime());
		} else {
			endDate = null;
		}
		
		
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
		
		ArrayList<Task> startTasks = new ArrayList<Task>();
		for(int i = 0; i < startTasks.size(); i++){
			startTasks.add(startTasks.get(i));
		}
		
		ArrayList<String> searchKey = new ArrayList<String>();
		for(int i = 0; i < searchKey_.size(); i++){
			searchKey.add(searchKey_.get(i));
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
	
	//@@author A0130717M
	public ArrayList<Task> getCurrentTasks(){
		switch(viewMode_){
		case FLOATING: 
			return floatingTasks_;
		case DEADLINE:
			return deadlineTasks_;
		case ALL:
			return allTasks_;
		case SEARCH:
			return searchResultTasks_;
		case FINISHED:
			return finishedTasks_;
		case START:
			refreshTodaysTasks();
			return todaysTasks_;

		default:
			return allTasks_;
		}
	}
	
	
	//This method returns all tasks that are due before the end of today
	public void refreshTodaysTasks() {
		todaysTasks_.clear();
		Calendar calender =  Calendar.getInstance();
	    calender.set(calender.get(Calendar.YEAR), 
	                 calender.get(Calendar.MONTH), 
	                 calender.get(Calendar.DAY_OF_MONTH), 
	                 23,59,59);
	     
	    Date endOfToday = calender.getTime();
		
	    for(int i = 0; i < deadlineTasks_.size(); i++){
			Task task = deadlineTasks_.get(i);
			if (task.getEndDate().before(endOfToday)){
				todaysTasks_.add(task);	
			}
	    }
	}
	
	public boolean isCurrentTasksEmpty(){
		return this.getCurrentTasks().isEmpty();
	}
	
	public String[] getConfigInfo(){
		String[] configInfo = new String[4];
		configInfo[0] = Constant.CONFIG_INTRO;
		configInfo[1] = String.format(Constant.CONFIG_DIR, currentDirectory_);
		configInfo[2] = Constant.CONFIG_THEME;
		configInfo[3] = Constant.CONFIG_FONT;
		return configInfo;
	}
	
	public String getHeader(){
		switch(viewMode_){
		case FLOATING: 
			return Constant.HEADER_FLOATING;
		case DEADLINE:
			return Constant.HEADER_DEADLINE;
		case ALL:
			return Constant.HEADER_ALL;
		case SEARCH:
			return Constant.HEADER_SEARCH;
		case FINISHED:
			return Constant.HEADER_FINISHED;
		case CONFIG:
			return Constant.HEADER_CONFIG;
		case START:
			return Constant.HEADER_START;
		default:
			return Constant.HEADER_ALL;
		}
	}
	
	public String getEmptyMessage(){
		switch(viewMode_){
		case START:
			return Constant.EMPTY_TODAY;
		case ALL:
			return Constant.EMPTY_ALL;
		case DEADLINE:
			return Constant.EMPTY_DEADLINE;
		case FLOATING:
			return Constant.EMPTY_FLOATING;
		case SEARCH:
			return String.format(Constant.EMPTY_SEARCH, userInput_);
		default:
			return "";
		}
	}
	
	public void setTheme(Theme theme) {
		this.theme_ = theme;
	}
	
	public String getTheme(){
		switch(theme_){
		case AUTUMN:
			return Constant.STYLE_AUTUMN;
		case BOKEH:
			return Constant.STYLE_BOKEH;
		case BRANCH:
			return Constant.STYLE_BRANCH;
		case CAT:
			return Constant.STYLE_CAT;
		case LEATHER:
			return Constant.STYLE_LEATHER;
		case PARIS:
			return Constant.STYLE_PARIS;
		case WARM:
			return Constant.STYLE_WARM;
		case WHEAT:
			return Constant.STYLE_WHEAT;
		default:
			return Constant.STYLE_AUTUMN;
		}
	}
	
	public void setFont(Font font) {
		this.font_ = font;
	}
	
	public String getFont(){
		switch(font_){
		case CONSOLAS:
			return Constant.FONT_CONSOLAS;
		case SEGOE:
			return Constant.FONT_SEGOE;
		default:
			return Constant.FONT_SEGOE;
		}
	}
}
