//@@author A0107375E
package storage;
import java.util.ArrayList;
import java.util.Collections;
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

public class FileIo {
	
	//============================
	//       Attributes
	//============================
	
	// basic attributes
	protected File file;
	protected Gson gson = new Gson();
	private Type typeOfTask = new TypeToken<Task>(){}.getType();
	private State state;
	private boolean isConnectedToDatafile;
	
	// logger
	private final static Logger LOGGER = Logger.getLogger(FileManagement.class.getName());
	
	// logging message displayed
	private final static String LOAD_STATE = "Loading State from datafile...";
	private final static String LOAD_STATE_SUCCESS = "State is loaded succesfully!";
	private final static String LOAD_STATE_FAILURE = "State is not loaded successfully !";
	
	private final static String SAVE_STATE = "Saving state to datafile...";
	private final static String SAVE_STATE_SUCCESS = "State is successfully saved!";
	private final static String SAVE_STATE_FAILURE = "State is not successfully saved!";
	
	private final static String READ_TASK_FAILURE = "This task is not succesfully read!";
	private final static String WRITE_TASK_FAILURE = "This task is not succesfully written!";
	
	//============================
	//       Constructor(s)
	//============================
	
	public FileIo(State state) {
		this.state = state;
	}
	
	//============================
	//       Functions
	//============================
	protected boolean setFile(File file) {
		this.file = file;
		return true;
	}
		
	/**
	  * This method read all JSON in the text file and convert into Task objects.
	  * Store the Task Objects in a TreeSet and return it to LOGIC
	  * @return a TreeSet containing all Tasks
	  */
	public boolean loadState(){
		assert isConnectedToDatafile;
		LOGGER.log(Level.INFO, LOAD_STATE);
		
		ArrayList<Task> normalTasks = state.getDeadlineTasks();
		ArrayList<Task> floatingTasks = state.getFloatingTasks();
		ArrayList<Task> allTasks = state.getAllTasks();
			
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while (reader.ready()) {
				addTaskToList(normalTasks, floatingTasks, allTasks, reader);
			}
		// sort the tasks by its creation date and pass to logic
		Collections.sort(normalTasks, TaskComparators.compareByCreationDate);
		Collections.sort(floatingTasks, TaskComparators.compareByCreationDate);
		Collections.sort(allTasks, TaskComparators.compareByCreationDate);
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, LOAD_STATE_FAILURE, e);
			return false;
		}
		LOGGER.log(Level.INFO, LOAD_STATE_SUCCESS);
		return true;
	}

	/**
	 * This method adds the Task into respective list.
	 * @param normalTasks
	 * @param floatingTasks
	 * @param allTasks
	 * @param reader
	 */
	private void addTaskToList(ArrayList<Task> normalTasks, ArrayList<Task> floatingTasks, ArrayList<Task> allTasks,
			BufferedReader reader) {
		Task task = readOneTask(reader);
		allTasks.add(task);
		TaskType taskType = task.getTaskType();
		switch (taskType) {
			case FLOATING :
				floatingTasks.add(task);
				break;
			case DEADLINE :
				normalTasks.add(task);
				break;
			default :
		}
	}
	 	
	/**
	 * This method saves the current State into the file in JSON format
	 * @param TreeSet containing Task Objects
	 * @return boolean value indicating whether saving is successful or not
	 */
	public boolean saveState(){
		assert isConnectedToDatafile;
		LOGGER.log(Level.INFO, SAVE_STATE);
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writeTasksToFile(writer);
			writer.close();
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, SAVE_STATE_FAILURE, e);
			return false;
		}	
		
		LOGGER.log(Level.INFO, SAVE_STATE_SUCCESS);
		return true;
	}
		
	/**
	 * This method will write the state into the text file
	 * @boolean boolean value of whether this is successful
	 */
	protected boolean writeTasksToFile(BufferedWriter writer) {
		ArrayList<Task> allTasks = this.state.getAllTasks();
		try {
			for (Task task: allTasks) {
				String taskInJsonFormat = gson.toJson(task);
				writer.write(taskInJsonFormat);
				writer.newLine();
			}
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, WRITE_TASK_FAILURE, e);
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
	protected Task readOneTask(BufferedReader reader) {
		try {
			String currentTaskString = reader.readLine();
			Task task = gson.fromJson(currentTaskString, typeOfTask);
			return task;
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, READ_TASK_FAILURE, e);
			e.printStackTrace();
			return null;
		}
	}
}
