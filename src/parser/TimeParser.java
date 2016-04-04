package parser;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeParser{
 private static SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yy HH:mm");
 
 public static Date stringToDate(String str){
	 try {
		 Date result = sdf.parse(str);
		 return result;
	 } catch (Exception e){
		 return null;
	 }
 }

 public static String dateToString(Date date){
	 String result = sdf.format(date);
	 return result;
 }
 
 public static Date getCurrentDate(){
	 return Calendar.getInstance().getTime();
 }
}


