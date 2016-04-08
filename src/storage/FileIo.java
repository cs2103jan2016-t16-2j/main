package storage;
import java.util.ArrayList;
import java.util.Collections;
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
	
	// Attributes
	protected File file;
	protected Gson gson = new Gson();
	private Type typeOfTask = new TypeToken<Task>(){}.getType();
	private State state;
	private boolean isConnectedToDatafile;
	private ProjectLogger logger;
	
	// Logging Message
	private final static String LOADING_STATE_SUCCESS = "Loading State from datafile...";
	private final static String LOADING_STATE_FAILURE = "State is not successfully loaded!";
	private final static String SAVING_STATE_SUCCESS = "Saving state to datafile...";
	private final static String SAVING_STATE_FAILURE = "State is not successfully saved!";
	private final static String WRITING_TASK_FAILURE = "Task is not succesfully written to datafile...";
	
	public FileIo(State state) {
		this.state = state;
		logger = new ProjectLogger(FileIo.class.getName()); 
	}
		
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
			logger.info(LOADING_STATE_SUCCESS);
			ArrayList<Task> normalTasks = state.getDeadlineTasks();
			ArrayList<Task> floatingTasks = state.getFloatingTasks();
			ArrayList<Task> allTasks = state.getAllTasks();
			Task task;
			BufferedReader reader;
			
			try {
				reader = new BufferedReader(new FileReader(file));
				
			
				while (reader.ready()) {
					task = readCurrentTask(reader);
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
							continue;
					}
				}
				Collections.sort(normalTasks, TaskComparators.compareByCreationDate);
				Collections.sort(floatingTasks, TaskComparators.compareByCreationDate);
				Collections.sort(allTasks, TaskComparators.compareByCreationDate);
			} catch (IOException e) {
				logger.warning(LOADING_STATE_FAILURE);
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
			assert isConnectedToDatafile;
			logger.info(SAVING_STATE_SUCCESS);
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				writeTaskToJson(writer);
				writer.close();
			} catch (IOException e) {
				logger.warning(SAVING_STATE_FAILURE);
				return false;
			}
			return true;
		}
		
		/**
		 * This method will write the state into the text file
		 * @param writer
		 * @throws IOException
		 */
		protected boolean writeTaskToJson(BufferedWriter writer) throws IOException {
			ArrayList<Task> normalTasks = this.state.getDeadlineTasks();
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
				logger.warning(WRITING_TASK_FAILURE);
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
