package common;
import java.util.Date;
import parser.TimeParser;

public class Task {
	private boolean isImportant, isDetailDisplayed;
	private Date startDate, endDate, creationDate;
	private String content, venue, detail;
	private TaskType taskType;

	private final String TO_STRING = "Task [ content: %s | venue: %s | detail: %s | type: %s"
										+ "isImportant: %b ";//| startDate: %s | endDate: %s | creationDate: %s]";
	
	
	public Task(State state) {
		
		if(state.getIsContentChanged()){
			this.content = state.getContent();
			System.out.println(state.getContent());
			System.out.println(this.content);
		} else {
			this.content = Constant.VALUE_DEFAULT_EMPTY;
		}
		
		

		if(state.getIsVenueChanged()){
			this.venue = state.getVenue();
		} else {
			this.venue = Constant.VALUE_DEFAULT_EMPTY;
		}
		
		if(state.getIsDetailChanged()){
			this.detail = state.getDetail();
		} else {
			this.detail = Constant.VALUE_DEFAULT_EMPTY;
		}
		
		this.taskType = state.getTaskType();
		this.isImportant = Constant.VALUE_DEFAULT_BOOLEAN_FALSE;
		this.isDetailDisplayed = Constant.VALUE_DEFAULT_BOOLEAN_FALSE;
				
		if(taskType == TaskType.FLOATING){
			this.startDate = null;
			this.endDate = null;
		} else {
			if(state.getIsStartDateChanged()){
				this.startDate = state.getStartDate();
			} else {
				this.startDate = null;
			}
			this.endDate = state.getEndDate();
		}
		
		this.creationDate = TimeParser.getCurrentDate();
	
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
									 startDate.toString(), endDate.toString(), creationDate.toString());

		return result;
	}
}
