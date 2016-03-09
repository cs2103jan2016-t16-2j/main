package StoragePackage;
import java.util.TreeSet;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import CommonPackage.*;

public class Storage {
	
	protected File file;
	protected Gson gson = new Gson();
	private Type typeOfTask = new TypeToken<Task>(){}.getType();
	
 	public Storage(){
		File dataDir = createDataDir();
		this.file = new File(dataDir,"data.txt");
	}

	/**
	 * This method creates a directory at user's home directory. Database text file
	 * will be stored here.
	 * @return
	 */
	private File createDataDir() {
		File dataDir = new File(System.getProperty("user.home") + "/WallistDatabase");
		
		if (!dataDir.exists()) {
			dataDir.mkdirs();
		}
		return dataDir;
	}
	
	/**
	 * This method takes a Command and a Task object(if any), and executes the
	 * commands accordingly.
	 * @param commandType
	 * @param task
	 * @return a boolean value for testing
	 */
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
				return clearTask();
			case UPDATE :
				return update(task);
			case TICK : 
				return tick(task);
			default :
				return false;
		}
	}
	
	/**
	 * This method takes a Task object and save the task into the file in JSON format
	 * @param task
	 * @return
	 */
	public boolean save(Task task){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
			writeTaskToJson(task, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * @param task
	 * @param writer
	 * @throws IOException
	 */
	private void writeTaskToJson(Task task, BufferedWriter writer) throws IOException {
		String json = gson.toJson(task);
		writer.write(json + "\n");
	}
	
	public TreeSet<Task> loadCurrentTask(){
		TreeSet<Task> tasks = new TreeSet<Task>();
		Task task;
		BufferedReader reader;
		
		
		try {
			reader = new BufferedReader(new FileReader(file));
			while (reader.ready()) {
				task = readCurrentTask(reader);
				tasks.add(task);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tasks;
	}

	/**
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	private Task readCurrentTask(BufferedReader reader) throws IOException {
		Task task;
		String currentTask = reader.readLine();
		task = gson.fromJson(currentTask, typeOfTask);
		return task;
	}
	
	public boolean delete(Task deletedTask){
		File tempFile = new File("tempFile.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile, true));
			
			while (reader.ready()) {
				Task task = readCurrentTask(reader);
				if (task.compareTo(deletedTask) != 0) {
					writeTaskToJson(task,writer);
				}
			}
			reader.close();
			writer.close();
			tempFile.renameTo(file);
		} catch (Exception e) {
			System.out.println(e);
		}
		return true;
	}
	
	public boolean tick(Task obj){
		// code for moving tasks from current to complete
		return false;
	}

	public boolean clearTask(){
		File tempFile = new File("tempFile.txt");
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile, true));
			writer.write("");
			writer.close();
		} catch (IOException e) {
			System.out.println(e);
		}
		tempFile.renameTo(file);
		return false;
	}
	
	public boolean update(Task modifiedTask){
		File tempFile = new File("tempFile.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile, true));
			
			while (reader.ready()) {
				Task task = readCurrentTask(reader);
				if (task.compareTo(modifiedTask) != 0) {
					writeTaskToJson(task,writer);
				} else {
					writeTaskToJson(modifiedTask,writer);
				}
			}
			
			reader.close();
			writer.close();
			tempFile.renameTo(file);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return false;
	}
}
