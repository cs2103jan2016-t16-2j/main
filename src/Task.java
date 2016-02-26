import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Task {
	private boolean isFloating, isImportant;
	private Date startDate, endDate;
	private String content, venue, detail;
	
	private final String EXCEPTION_WRONG_INPUT = "Invalid input";
	
	private final String DEFAULT_VENUE = "NA";
	private final String DEFAULT_DETAIL = "NA";
	private final boolean DEFAULT_IS_FLOATING = true;
	private final boolean DEFAULT_IS_IMPORTANT = false;
	private final Date DEFAULT_END_DATE = null;
	
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
		startDate = getCurrentDate();
		endDate = DEFAULT_END_DATE;
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
	
}
