package StoragePackage;
import java.util.TreeSet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import CommonPackage.*;

public class Storage {
	
	protected File file;
	
	public Storage(){
		File dataDir = createDataDir();
		this.file = new File(dataDir,"data.txt");
	}

	/**
	 * @return
	 */
	private File createDataDir() {
		File dataDir = new File(System.getProperty("user.home") + "/WallistDatabase");
		
		if (!dataDir.exists()) {
			dataDir.mkdirs();
		}
		return dataDir;
	}
	
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
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(this.file, true));
			writer.write("haha111");
			System.out.println("x");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true; // saving unsuccessful
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
