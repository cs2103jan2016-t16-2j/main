package parser;

import com.joestelmach.natty.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class TimeParser{
 private static com.joestelmach.natty.Parser dateParser = new com.joestelmach.natty.Parser();
 
 public static Date stringToDate(String str){
	 try {
		 List<DateGroup> groups = dateParser.parse(str);
		 return groups.get(0).getDates().get(0);
	 } catch (Exception e){
		 return null;
	 }
 }
 
 public static Date getCurrentDate(){
	 return Calendar.getInstance().getTime();
 }
}


