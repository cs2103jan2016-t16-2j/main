import java.util.TreeSet;

public class Storage {
	
	public Storage(){
	}
	
	enum CommandType {
		ADD, UPDATE, DELETE, CLEAR, TICK, ERROR;
	};
	
	public boolean accessStorage(CommandType commandType, Task task) {
		if (commandType == CommandType.ERROR) {
			return false;
		}
		
		switch (commandType) {
			case ADD : 
				return save(task);
			case DELETE :
				return delete(task);
			case CLEAR : 
				return clearCurrentTask();
			case UPDATE :
				return update(task);
			case TICK : 
				return tick(task);
			default :
				return false;
		}
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
		// code for delete tasks
		return false;
	}
	
	public boolean tick(Task obj){
		// code for moving tasks from current to complete
		return false;
	}
	
	public boolean accessStorage(Task obj){
		// code for moving tasks from current to complete
		return false;
	}
	
	public boolean clearCurrentTask(){
		// code for moving tasks from current to complete
		return false;
	}
	
	public boolean clearCompleteTask(){
		// code for moving tasks from current to complete
		return false;
	}
	
	public boolean update(Task obj){
		// code for moving tasks from current to complete
		return false;
	}
}
