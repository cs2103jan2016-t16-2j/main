package common;
import java.util.Calendar;
import java.util.Date;

public class Task {
	private boolean isImportant, isDetailDisplayed;
	private Date startDate, endDate, creationDate;
	private String content, venue, detail;
	private TaskType taskType;

	private final String TO_STRING = "Task [ content: %s | venue: %s | detail: %s | type: %s"
										+ "isImportant: %b | startDate: %s | endDate: %s | creationDate: %s]";

	private final String DISPLAYED_DETAIL = "%1$s\n\nDetail: %2$s";
	private final String DISPLAYED_VENUE = "%1$s\n\nVenue: %2$s";
	private final String DISPLAYED_VENUE_DETAIL = "%1$s\n\nVenue: %2$s\nDetail: %3$s";
	
	public Task(State state) {
		
		if(state.getIsContentChanged()){
			this.content = state.getContent();
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
		
		this.isImportant = Constant.VALUE_DEFAULT_BOOLEAN_IS_NEW_TASK_IMPORTANT;
		this.isDetailDisplayed = Constant.VALUE_DEFAULT_BOOLEAN_IS_NEW_TASK_DETAIL_DISPLAYED;
				

		if(state.getIsStartDateChanged()){
			this.startDate = state.getStartDate();
		} else {
			this.startDate = null;
		}
			
		if(state.getIsEndDateChanged()){
			this.endDate = state.getEndDate();
			this.taskType = TaskType.DEADLINE;
		} else {
			this.endDate = null;
			this.taskType = TaskType.FLOATING;
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
		if(startDate != null){
			result += startDate.toString();
		}
		if(endDate != null){
			result += endDate.toString();
		}
		result += content;
		result += venue;
		result += detail;
		result += taskType;
		
		return result.toLowerCase();
	}

	@Override
	public String toString(){
		String result;
		if ((startDate != null) && (endDate != null)) {
			result = String.format(TO_STRING, content,venue,detail,taskType,isImportant,
					 startDate.toString(), endDate.toString(), creationDate.toString());
		} else {
			result = String.format(TO_STRING, content,venue,detail,taskType,isImportant,
					 null, null, creationDate.toString());
		}
		
		
		return result;
	}
	
	//@@author A0130717M
	public boolean isOverdue(){
		if (this.endDate == null){
			return false;
		}
		Date today = Calendar.getInstance().getTime();
		return today.after(this.endDate);
	}
	
	public String getDisplayContent(){
		if (isDetailDisplayed){
			return getDetailedContent();
		} else{
			return content;
		}
	}


	private String getDetailedContent() {
		if (!venue.isEmpty() && detail.isEmpty()){
			return String.format(DISPLAYED_VENUE, content, venue);
		} else if (venue.isEmpty() && !detail.isEmpty()){
			return String.format(DISPLAYED_DETAIL, content, detail);
		} else if (!venue.isEmpty() && !detail.isEmpty()){
			return String.format(DISPLAYED_VENUE_DETAIL, content, venue, detail);	
		} else{
			return content;
		}
	}	
}
