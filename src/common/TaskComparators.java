package common;

import java.util.Comparator;

public class TaskComparators {

	public static Comparator<Task> compareByEndDate = new Comparator<Task>(){
		public int compare(Task a, Task b){
			return  a.getEndDate().compareTo(b.getEndDate()); 
		}
	};

	public static Comparator<Task> compareByCreationDate = new Comparator<Task>(){
		public int compare(Task a, Task b){
			return  b.getCreationDate().compareTo(a.getCreationDate()); 
		}
	};
}
