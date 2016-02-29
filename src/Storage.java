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
	
	public TreeSet<Task> loadCompletedTask(){
		// read the files, store in data structure
		TreeSet<Task> tasks = new TreeSet<Task>();
		return tasks;
	}
	

	public boolean delete(Task obj){
		// code for delete obj
		return false;
	}
	
	public boolean archive(Task obj){
		
	}

}
