package gui;

import java.text.SimpleDateFormat;
import java.util.Date;

import common.Task;

public class TaskTimeDisplay {

	private SimpleDateFormat sdf = new SimpleDateFormat("dd MMM y HH:mm");
	private SimpleDateFormat sdfYear = new SimpleDateFormat("yy");
	private SimpleDateFormat sdfThisYear = new SimpleDateFormat("dd MMM HH:mm");
	private SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM y");
	private SimpleDateFormat sdfDateThisYear = new SimpleDateFormat("dd MMM");
	private SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
	private SimpleDateFormat sdfDefaultTime = new SimpleDateFormat("HH:mm:ss");
	private String defaultTime = "23:59:59";
	private static final String DURATION = "%1$s - %2$s"; 
	
	private String timeDisplay;
	
	public TaskTimeDisplay(Task task) {
		Date startDate = task.getStartDate();
		Date endDate = task.getEndDate();
		boolean sameDate = false;
		boolean startThisYear = false;
		boolean endThisYear = sdfYear.format(endDate).equals(sdfYear.format(System.currentTimeMillis()));
		boolean hasEndTime = sdfDefaultTime.format(endDate).equals(defaultTime);
		
		if (startDate != null){
			startThisYear = sdfYear.format(startDate).equals(sdfYear.format(System.currentTimeMillis()));
			sameDate = sdfDate.format(task.getStartDate()).equals(sdfDate.format(task.getEndDate()));
		}
		if (startDate == null && endThisYear && hasEndTime){
		    timeDisplay = sdfDateThisYear.format(task.getEndDate());
		} else if (startDate == null && endThisYear && !hasEndTime){
		    timeDisplay = sdfThisYear.format(task.getEndDate());
		} else if (startDate == null && hasEndTime){
		    timeDisplay = sdf.format(task.getEndDate());
	    } else if (startDate == null && !hasEndTime){
	    	timeDisplay = sdfDate.format(task.getEndDate());
	    } else if (sameDate && startThisYear){
	    	timeDisplay = String.format(DURATION, sdfThisYear.format(task.getStartDate()), sdfTime.format(task.getEndDate()));		
		} else if (sameDate && !startThisYear){
			timeDisplay = String.format(DURATION, sdf.format(task.getStartDate()), sdf.format(task.getEndDate()));				
	    } else if (startThisYear && endThisYear && hasEndTime){
	    	timeDisplay = String.format(DURATION, sdfThisYear.format(task.getStartDate()), sdfThisYear.format(task.getEndDate()));
	    } else if (startThisYear && endThisYear && !hasEndTime){
	    	timeDisplay = String.format(DURATION, sdfDateThisYear.format(task.getStartDate()), sdfDateThisYear.format(task.getEndDate()));
	    } else if (!startThisYear || !endThisYear && hasEndTime){
	    	timeDisplay = String.format(DURATION, sdf.format(task.getStartDate()), sdf.format(task.getEndDate()));
	    } else {
	    	timeDisplay = String.format(DURATION, sdfDate.format(task.getStartDate()), sdfDate.format(task.getEndDate()));
	    }
	}
	
	public String getTaskTime(){
		return timeDisplay;
	}
}
