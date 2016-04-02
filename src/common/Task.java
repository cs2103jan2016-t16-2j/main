package common;
import java.util.Date;
import parser.TimeParser;

public class Task {
	private boolean isImportant, isDetailDisplayed;
	private Date startDate, endDate, creationDate;
	private String content, venue, detail;
	private TaskType taskType;

	private final String TO_STRING = "Task [ content: %s | venue: %s | detail: %s | type: %s"
										+ "isImportant: %b | startDate: %s | endDate: %s | creationDate: %s]";
	
	
	public Task(State state) {
		if(state.getIsContentChanged()){
			content = state.getContent();
		} else {
			content = Constant.VALUE_DEFAULT_EMPTY;
		}

		if(state.getIsVenueChanged()){
			venue = state.getVenue();
		} else {
			venue = Constant.VALUE_DEFAULT_EMPTY;
		}
		
		if(state.getIsDetailChanged()){
			detail = state.getDetail();
		} else {
			detail = Constant.VALUE_DEFAULT_EMPTY;
		}
		
		taskType = state.getTaskType();
		isImportant = Constant.VALUE_DEFAULT_BOOLEAN_FALSE;
		isDetailDisplayed = Constant.VALUE_DEFAULT_BOOLEAN_FALSE;
				
		if(taskType == TaskType.FLOATING){
			startDate = null;
			endDate = null;
		} else {
			if(state.getIsStartDateChanged()){
				startDate = state.getStartDate();
			} else {
				startDate = null;
			}
			endDate = state.getEndDate();
		}
		
		creationDate = TimeParser.getCurrentDate();
	}


	public boolean getIsImportant(){
		return isImportant;
	}
	
	public void setIsImportant(boolean input){
		isImportant = input;
	}
	
	public boolean getIsDetailDisplayed(){
		return isDetailDisplayed;
	}
	
	public void setIsDetailDisplayed(boolean input){
		isDetailDisplayed = input;
	}

	public Date getStartDate(){
		return startDate;
	}
	
	public void setStartDate(Date dt){
		startDate = dt;
	}
	
	public Date getEndDate(){
		return endDate;
	}
	
	public void setEndDate(Date dt){
		endDate = dt;
	}	
	
	public Date getCreationDate(){
		return creationDate;
	}
	//no mutator for creation date
	
	public TaskType getTaskType(){
		return taskType;
	}
	
	public void setTaskType(TaskType type){
		taskType = type;
	}
	
	public String getContent(){
		return content;
	}
	
	public void setContent(String str){
		content = str;
	}
	
	public String getVenue(){
		return venue;
	}
	
	public void setVenue(String str){
		venue = str;
	}
	
	public String getDetail(){
		return detail;
	}
	
	public void setDetail(String str){
		detail = str;
	}

	public String concatString(){
		String result = "";
		result += startDate.toString();
		result += endDate.toString();
		result += content;
		result += venue;
		result += detail;
		result += taskType;
		
		return result;
	}

	@Override
	public String toString(){
		String result = String.format(TO_STRING, content,venue,detail,taskType,isImportant,
									 startDate,toString(), endDate.toString(), creationDate.toString());
		return result;
	}
}
