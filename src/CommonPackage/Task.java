package CommonPackage;
import java.util.Date;
import java.util.HashMap;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Task implements Comparable<Task> {
	private boolean isFloating, isImportant, isFinished;
	private Date startDate, endDate;
	private String content, venue, detail;
	
	private final String EXCEPTION_WRONG_INPUT = "Invalid input";
	
	private final String DEFAULT_VENUE = "NA";
	private final String DEFAULT_DETAIL = "NA";
	private final boolean DEFAULT_IS_FLOATING = true;
	private final boolean DEFAULT_IS_IMPORTANT = false;
	private final boolean DEFAULT_IS_FINISHED = false;
	private final Date DEFAULT_END_DATE = null;
	private final String KEY_CONTENT = "content";
	private final String KEY_TYPE = "type";
	private final String KEY_START_DATE = "startDate";
	private final String KEY_END_DATE = "endDate";

	private final String TO_STRING = "Task [ content: %s | venue: %s | detail: %s | isFloating: %b | "
										+ "isImportant: %b | startDate: %s | endDate: %s ]";
	
	private static SimpleDateFormat sdf= new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz y");
	
	private static Date getCurrentDate(){
		return Calendar.getInstance().getTime();
	}
	
	public Task(String content) {
		this.content = content;
		venue = DEFAULT_VENUE;
		detail = DEFAULT_DETAIL;
		isFloating = DEFAULT_IS_FLOATING;
		isImportant = DEFAULT_IS_IMPORTANT;
		isFinished = DEFAULT_IS_FINISHED;
		startDate = getCurrentDate();
		endDate = DEFAULT_END_DATE;
	}
	
	public Task(HashMap<String,String> cmdTable) throws Exception{
		content = cmdTable.get(KEY_CONTENT);
		venue = cmdTable.get(KEY_CONTENT);
		detail = cmdTable.get(KEY_CONTENT);
		isFloating = true;
		isImportant = false;
		isFinished = false;
		if (cmdTable.get(KEY_START_DATE) != "") {
			startDate = sdf.parse(cmdTable.get(KEY_START_DATE));
		}
		if (cmdTable.get(KEY_END_DATE) != "") {
			endDate = sdf.parse(cmdTable.get(KEY_END_DATE));
		}
		startDate = getCurrentDate();
		endDate = getCurrentDate();
	}
	
	public boolean getIsFloating(){
		return isFloating;
	}
	
	public void setIsFloating(boolean input){
		isFloating = input;
	}
	
	public boolean getIsImportant(){
		return isImportant;
	}
	
	public void setIsImportant(boolean input){
		isImportant = input;
	}

	public boolean getIsFinished(){
		return isFinished;
	}
	
	public void setIsFinished(boolean input){
		isFinished = input;
	}
	
	public Date getStartDate(){
		return startDate;
	}
	
	public void setStartDate(String str){
		try{
			startDate = sdf.parse(str);
		} catch (Exception e){
			System.out.println(EXCEPTION_WRONG_INPUT);
		}
	}
	
	public void setStartDate(Date dt){
		startDate = dt;
	}
	
	public Date getEndDate(){
		return endDate;
	}
	
	public void setEndDate(String str){
		try{
			endDate = sdf.parse(str);
		} catch (Exception e){
			System.out.println(EXCEPTION_WRONG_INPUT);
		}
	}
	
	public void setEndDate(Date dt){
		endDate = dt;
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
	
	@Override
	public String toString(){
		String result;
		result= String.format(TO_STRING, content, venue, detail, isFloating, 
							  isImportant,sdf.format(startDate), sdf.format(endDate));
		return result;
	}
	
	@Override
    public int compareTo(Task other){
        // compareTo should return < 0 if this is supposed to be
        // less than other, > 0 if this is supposed to be greater than 
        // other and 0 if they are supposed to be equal
        return this.startDate.compareTo(other.startDate);
        
    }
}
