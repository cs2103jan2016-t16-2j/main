import java.util.TreeSet;

public class Storage {
	
	public Storage(){
	}
	
	public boolean save(Task obj){
		// code for saving
		return false; // saving unsuccessful
	}
	
	public TreeSet<Task> loadCurrentTask(){
		// read the files, store in data structure
		TreeSet<Task> tasks = new TreeSet<Task>();
		return tasks;
	}
	
	
	reloadCurrentTasks()
	reloadCompletedTasks()
	reloadArchivedTasks()

	return a required data structure

	delete(Task obj): delete a task in the text file
	Input: Task Object
	Output: Boolean

	getTaskCount(): get the number of tasks stored in the text file
	getTaskCount(String word) get the number of tasks that contains the search word stored in the text file
	getTaskCount(Date) :get the number of tasks that related to the input date stored in the text file
	Output: int

	searchByDate(Date/DateTime)
	searchByString(String)
	searchByPriority() : check Tasks attribute

	return a data structure of tasks found


	getDirectory()
	return the directory of the text file in String
	moveFiles

	clearFile() erases all content in the file

}
