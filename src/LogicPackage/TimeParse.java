package LogicPackage;
import java.text.SimpleDateFormat;

public class TimeParser{
	private static SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yy HH:mm");

	public static Date stringToDate(String str){
		Date result = sdf.parse(str);
		return result;
	}

	public static String dateToString(Date date){
		String result = sdf.format(date);
		return result;
	}
}









