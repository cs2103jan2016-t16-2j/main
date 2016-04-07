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
	
	// Attributes
	protected File file;
	protected Gson gson = new Gson();
	private Type typeOfTask = new TypeToken<Task>(){}.getType();
	private State state;
	private boolean isConnectedToDatafile;
	
	// Logger	
		private final static Logger LOGGER = Logger.getLogger(FileIo.class.getName());
		
		public FileIo(State state) {
			this.state = state;
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
			LOGGER.log(Level.INFO, "Loading State from datafile...");
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
			assert isConnectedToDatafile;
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
