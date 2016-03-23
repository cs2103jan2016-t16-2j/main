package storage;
import java.util.ArrayList;
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

import common.*;

public class Storage {
	
	// Attributes
	protected File file;
	protected Gson gson = new Gson();
	private Type typeOfTask = new TypeToken<Task>(){}.getType();
	private State state;
	
	// Constructor
 	public Storage(State state){
		File dataDir = createDataDir();
		this.file = new File(dataDir,"data.txt");
		this.state = state;
	}

 	// Operation Methods
 	/**
 	 * This method read all JSON in the text file and convert into Task objects.
 	 * Store the Task Objects in a TreeSet and return it to LOGIC
 	 * @return a TreeSet containing all Tasks
 	 */
	public boolean loadState(){
		
		TreeSet<Task> normalTasks = state.getNormalTasks();
		ArrayList<Task> floatingTasks = state.getFloatingTasks();
		
		Task task;
		BufferedReader reader;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			
		
			while (reader.ready()) {
				task = readCurrentTask(reader);
				TaskType taskType = task.getTaskType();
				switch (taskType) {
					case FLOATING :
						floatingTasks.add(task);
						break;
					case DEADLINE :
						normalTasks.add(task);
						break;
					default :
						continue;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
 	
	/**
	 * This method saves the current State into the file in JSON format
	 * @param TreeSet containing Task Objects
	 * @return boolean value indicating whether saving is successful or not
	 */
	public boolean saveState(){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writeTaskToJson(writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
	// Helper Methods
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
	 * This method will write the state into the text file
	 * @param writer
	 * @throws IOException
	 */
	private void writeTaskToJson(BufferedWriter writer) throws IOException {
		TreeSet<Task> normalTasks = this.state.getNormalTasks();
		ArrayList<Task> floatingTasks = this.state.getFloatingTasks();
		for (Task task: normalTasks) {
			String json = gson.toJson(task);
			writer.write(json + "\n");
		}
		
		for (Task task: floatingTasks) {
			String json = gson.toJson(task);
			writer.write(json + "\n");
		}
	}
	
	/**
	 * This method read the current line in the text file and subsequently construct a Task object 
	 * @param reader
	 * @return a Task object
	 * @throws IOException
	 */
	private Task readCurrentTask(BufferedReader reader) throws IOException {
		Task task;
		String currentTask = reader.readLine();
		task = gson.fromJson(currentTask, typeOfTask);
		return task;
	}
}