//@@author A0130369H
package parser;

import com.joestelmach.natty.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class TimeParser{
 private static com.joestelmach.natty.Parser dateParser = new com.joestelmach.natty.Parser();
 
 public static Date stringToDate(String str){
	 try {
		 Date current = getCurrentDate();
		 List<DateGroup> groups = dateParser.parse(str);
		 Date date = groups.get(0).getDates().get(0);
		 Calendar cal = Calendar.getInstance();
		 Calendar calCurrent = Calendar.getInstance();
		 cal.setTime(date);
		 calCurrent.setTime(current);
		 if(cal.get(Calendar.HOUR_OF_DAY) == calCurrent.get(Calendar.HOUR_OF_DAY) && cal.get(Calendar.MINUTE) == calCurrent.get(Calendar.MINUTE)){
			 cal.set(Calendar.HOUR_OF_DAY, 23);
			 cal.set(Calendar.MINUTE, 59);
			 cal.set(Calendar.SECOND, 59);
		 }
		 
		 
		 return cal.getTime();
	 } catch (Exception e){
		 return null;
	 }
 }
 
 public static Date getCurrentDate(){
	 return Calendar.getInstance().getTime();
 }
}


