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
	
	// Attributes
	protected File file;
	protected Gson gson = new Gson();
	private Type typeOfTask = new TypeToken<Task>(){}.getType();
	
	
	// Constructor
 	public Storage(){
		File dataDir = createDataDir();
		this.file = new File(dataDir,"data.txt");
	}

 	// Operation Methods
 	/**
 	 * This method read all JSON in the text file and convert into Task objects.
 	 * Store the Task Objects in a TreeSet and return it to LOGIC
 	 * @return a TreeSet containing all Tasks
 	 */
	public TreeSet<Task> read(){
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
	 * This method takes a Task object and save the task into the file in JSON format
	 * @param task
	 * @return
	 */
	public boolean save(TreeSet<Task> taskList){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writeTaskToJson(taskList, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
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
	 * This method takes a Task object and save the task into the file in JSON format
	 * @param task
	 * @return
	 */
	public boolean save(TreeSet<Task> taskList){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writeTaskToJson(taskList, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * @param taskList
	 * @param writer
	 * @throws IOException
	 */
	private void writeTaskToJson(TreeSet<Task> taskList, BufferedWriter writer) throws IOException {
		for (Task task: taskList) {
			String json = gson.toJson(task);
			writer.write(json + "\n");
		}	
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

}
