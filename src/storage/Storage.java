package storage;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
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
	private boolean isFileSuccessfullyCreated;
	
	// Logger
	private final static Logger LOGGER = Logger.getLogger(Storage.class.getName());
	
	
	// Constructor
 	public Storage(State state){
		File dataDir = getDataFileDirectory();
		isFileSuccessfullyCreated = getDataFile(dataDir);
		this.state = state;
	}
 	
 	/**
 	 * This method takes in the datafile directory, and retrieve the file if it exists, 
 	 * if not a new one will be created
 	 * @param dataDir
 	 * @return
 	 */
 	private boolean getDataFile(File dataDir) {
 		LOGGER.log(Level.INFO, "Retrieving Datafile...");
 		this.file = new File(dataDir, "data.txt");
 		if (!file.exists()) {
 			try {
 				LOGGER.log(Level.INFO, "Datafile does not exist, creating new datafile...");
				return file.createNewFile();
			} catch (IOException e) {
				LOGGER.log(Level.WARNING, "Datafile is not created successfully...", e);
				return false;
			}
 		}
 		return true;
 	}
 	
 	// Operation Methods
 	/**
 	 * This method read all JSON in the text file and convert into Task objects.
 	 * Store the Task Objects in a TreeSet and return it to LOGIC
 	 * @return a TreeSet containing all Tasks
 	 */
	public boolean loadState(){
		LOGGER.log(Level.INFO, "Loading State from datafile...");
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
			LOGGER.log(Level.WARNING, "State is not successfully loaded", e);
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
		LOGGER.log(Level.INFO, "Saving state to datafile...");
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writeTaskToJson(writer);
			writer.close();
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "State is not successfully saved", e);
			return false;
		}
		return true;
	}
	
	
	// Helper Methods
	/**
	 * This method creates a directory at user's home directory. Database text file
	 * will be stored here.
	 * @return
	 */
	private File getDataFileDirectory() {
		LOGGER.log(Level.INFO, "Retrieving the datafile directory...");
		File dataDir = new File(System.getProperty("user.home") + "/WallistDatabase");
		
		if (!dataDir.exists()) {
			dataDir.mkdirs();
			LOGGER.log(Level.INFO, "Directory not exist or removed, creating new directory");
		}
		return dataDir;
	}
	
	/**
	 * This method will write the state into the text file
	 * @param writer
	 * @throws IOException
	 */
	protected boolean writeTaskToJson(BufferedWriter writer) throws IOException {
		TreeSet<Task> normalTasks = this.state.getNormalTasks();
		ArrayList<Task> floatingTasks = this.state.getFloatingTasks();
		try {
			for (Task task: normalTasks) {
				String json = gson.toJson(task);
				writer.write(json + "\n");
			}
			
			for (Task task: floatingTasks) {
				String json = gson.toJson(task);
				writer.write(json + "\n");
			}
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Task is not succesfully written to datafile...", e);
			return false;
		}
		return true;
	}
	
	/**
	 * This method read the current line in the text file and subsequently construct a Task object 
	 * @param reader
	 * @return a Task object
	 * @throws IOException
	 */
	protected Task readCurrentTask(BufferedReader reader) throws IOException {
		Task task;
		String currentTask = reader.readLine();
		task = gson.fromJson(currentTask, typeOfTask);
		return task;
	}
}