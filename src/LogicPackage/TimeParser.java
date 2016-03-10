package LogicPackage;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeParser{
 private static SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yy HH:mm");
 private final String EXCEPTION_INVALID_DATE_STRING = "invalid string input";
 
 public static Date stringToDate(String str){
   try {
     Date result = sdf.parse(str);
     return result;
   } catch (Exception e){
     Date date = new Date();
     return date;
   }
 }

 public static String dateToString(Date date){
  String result = sdf.format(date);
  return result;
 }
}








