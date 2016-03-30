package common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeSet;

public class State {
	private boolean isValid_;
	private CommandType command_;
	private String rawContent_;
	private String content_;
	private String detail_;
	private String userInput_;
	private String venue_;
	private String errorMessage_;
	private String searchKey_;
	private TaskType type_;
	private int position_;
	private Date startDate_;
	private Date endDate_;
	private HashMap<String, Boolean> attributes_;
	private ArrayList<Task> floatingTasks_;
	private ArrayList<Task> searchResultTasks_;
	private TreeSet<Task> normalTasks_;
	
	public State(){
		isValid_ = true;
		command_ = CommandType.UNDEFINED;
		rawContent_ = Constant.VALUE_DEFAULT_EMPTY;
		content_ = Constant.VALUE_DEFAULT_EMPTY;
		userInput_ = Constant.VALUE_DEFAULT_EMPTY;
		venue_ = Constant.VALUE_DEFAULT_EMPTY;
		errorMessage_ = Constant.VALUE_DEFAULT_EMPTY;
		searchKey_ =  Constant.VALUE_DEFAULT_EMPTY;
		detail_ = Constant.VALUE_DEFAULT_EMPTY;
		type_ = TaskType.UNDEFINED;
		position_ = 0;
		startDate_ = null;
		endDate_ = null;
		floatingTasks_ = new ArrayList<Task>();
		normalTasks_ = new TreeSet<Task>();
		searchResultTasks_ = new ArrayList<Task>();
		attributes_ = new HashMap<String, Boolean>();
		attributes_.put("isStartDate", false);
		attributes_.put("isEndDate", false);
		attributes_.put("isDetails", false);
		attributes_.put("isVenue", false);
		attributes_.put("isFinished", false);
		attributes_.put("isImportant", false);
	}
	/*
	 * List of set commands
	 */
	public void setIsValid(boolean bool){
		isValid_ = bool;
	}
	
	public void setRawContent(String rawContent){
		rawContent_ = rawContent;
	}
	
	public void setCommand(CommandType command){
		command_ = command;
	}
	
	public void setContent(String content){
		content_ = content;
	}
	
	public void setVenue(String venue){
		venue_ = venue;
	}	
	
	public void setDetail(String detail){
		detail_ = detail;
	}
	
	public void setPosition(int position){
		this.position_ = position;
	}
	
	public int getPosition(){
		return position_;
	}
	
	public void setMessage(String errorMessage){
		errorMessage_ = errorMessage;
	}
	
	public void setSearchKey(String searchKey){
		searchKey_ = searchKey;
	}
	
	public void setUserInput(String userInput){
		userInput_ = userInput;
	}
	
	public void setTaskType(TaskType type){
		type_ = type;
	}
	
	public void setStartDate(Date startDate){
		startDate_ = startDate;
	}
	
	public void setEndDate(Date endDate){
		endDate_ = endDate;
	}
	
	public void setIsStartDate(boolean isStartDate){
		attributes_.put("isStartDate", isStartDate);
	}
	
	public void setIsEndDate(boolean isEndDate){
		attributes_.put("isEndDate", isEndDate);
	}
	
	public void setIsDetails(boolean isDetails){
		attributes_.put("isDetails", isDetails);
	}
	
	public void setIsVenue(boolean isVenue){
		attributes_.put("isVenue", isVenue);
	}
	
	public void setIsImportant(boolean isImportant){
		attributes_.put("isImportant", isImportant);
	}
	
	public void setIsFinished(boolean isFinished){
		attributes_.put("isFinished", isFinished);
	}
	
	public void setFloatingTasks (ArrayList<Task> floatingTasks){
		floatingTasks_ = floatingTasks;
	}
	
	public void setNormalTasks (TreeSet<Task> normalTasks){
		normalTasks_ = normalTasks;
	}

	public void setSearchResultTasks (ArrayList<Task> searchResultTasks){
		searchResultTasks_ = searchResultTasks;
	}
	
	/*
	 * List of get commands
	 */
	public boolean getIsValid(){
		return isValid_;
	}
	
	public String getRawContent(){
		return rawContent_;
	}
	
	public CommandType getCommand(){
		return command_;
	}
	
	public String getContent(){
		return content_;
	}
	
	public String getVenue(){
		return venue_;
	}	
	
	public String getDetail(){
		return detail_;
	}	
	
	public String getMessage(){
		return errorMessage_;
	}
	
	public String getSearchKey(){
		return searchKey_;
	}
	
	public String getUserInput(){
		return userInput_;
	}
	
	public TaskType getTaskType(){
		return type_;
	}
	
	public Date getStartDate(){
		return startDate_;
	}
	
	public Date getEndDate(){
		return endDate_;
	}
	
	public boolean getIsStartDate(){
		return attributes_.get("isStartDate");
	}
	
	public boolean getIsEndDate(){
		return attributes_.get("isEndDate");
	}

	public boolean getIsDetails(){
		return attributes_.get("isDetails");
	}
	
	public boolean getIsVenue(){
		return attributes_.get("isVenue");
	}	
	public ArrayList<Task> getFloatingTasks(){
		return floatingTasks_;
	}
	
	public TreeSet<Task> getNormalTasks(){
		return normalTasks_;
	}
	
	public ArrayList<Task> getSearchResultTasks(){
		return searchResultTasks_;
	}
}
