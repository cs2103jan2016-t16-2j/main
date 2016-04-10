package logic;

import java.util.ArrayList;

import common.Constant;
import common.State;
import common.Task;
import common.TaskType;
import common.ViewMode;

public class ClearTask implements Operation {
	//============================
	//       Attributes
	//============================
	private State state;

	
	//====================================
	//       Constructor and Initiliser
	//====================================
	
	public ClearTask(State state) {
		this.state = state;
	}
	
	
	/**
	 * Perform clearance of all tasks
	 * If clearing under invalid mode(UNDEFINED, CONFIG, HELP)
	 *
	 * @return  boolean to indicate whether the addition is successful
	 */
	@Override
	public boolean process() {
		try {
			ViewMode viewMode = state.getViewMode();
			
			switch (viewMode) { 
				case FLOATING:
					clearUnderFloatingMode();
					return true;
				case DEADLINE:
					clearUnderDeadlineMode();
					return true;
				case ALL:
					clearUnderAllMode();
					return true;
				case SEARCH:
					clearUnderSearchMode();
					return true;
				case FINISHED:
					clearUnderFinishedMode();
					return true;
				case START:
					clearUnderStartMode();
					return true;
				case UNDEFINED:
					clearUnderInvalidMode();
					return false;
				case CONFIG:
					clearUnderInvalidMode();
					return false;
				case HELP:
					clearUnderInvalidMode();
					return false;
				default:
					return false;
			}		
					
		} catch (Exception e) {
			//logging
			state.setDisplayMessage(Constant.MESSAGE_DUMMY);
			return false;
		}
	}
	
	
	/**
	 * clear tasks under invalid mode
	 * set error message 
	 */
	private void clearUnderInvalidMode() {
		state.setDisplayMessage(Constant.MESSAGE_CLEAR_IN_WRONG_MODE);
	}
	

	/**
	 * clear tasks under all mode
	 * clear floating task list, deadline task list and all task list
	 */
	private void clearUnderAllMode() {
		clearDeadlineTasks();
		clearFloatingTasks();
		clearAllTasks();
	}

	/**
	 * clear tasks under deadline mode
	 * clear both deadline task list and deadline task in all task list
	 */
	private void clearUnderDeadlineMode() {
		clearDeadlineTasks();
		pruneAllTasks(TaskType.DEADLINE);
	}

	/**
	 * clear tasks under floating mode
	 * clear both floating task list and floating task in all task list
	 */
	private void clearUnderFloatingMode() {
		clearFloatingTasks();
		pruneAllTasks(TaskType.FLOATING);
	}
	
	/**
	 * clear all deadline tasks due before today midnight 
	 * from all task list, deadline task list and floating task list
	 * 
	 */
	private void clearUnderStartMode() {
		ArrayList<Task> todaysTasks = state.getTodaysTasks();
		for(int i = 0; i < todaysTasks.size(); i++){
			Task task = todaysTasks.get(i);
			deleteTask(task);
		}
		todaysTasks.clear();
	}
	
	
	/**
	 * clear all finished tasks from finished task list
	 * 
	 */
	private void clearUnderFinishedMode() {
		ArrayList<Task> finishedTask = state.getFinishedTasks();
		finishedTask.clear();
	}
	
	
	/**
	 * clear all searched tasks displayed in search mode
	 * from all task list, deadline task list and floating task list
	 * 
	 */
	private void clearUnderSearchMode() {
		ArrayList<Task> searchedTaskList = state.getSearchResultTasks();
		for(int i = 0; i < searchedTaskList.size(); i++){
			Task task = searchedTaskList.get(i);
			deleteTask(task);
		}
		searchedTaskList.clear();
	}
	


	/**
	 * clear all tasks list
	 * 
	 */
	private void clearAllTasks() {
		ArrayList<Task> allTaskList = state.getAllTasks();
		allTaskList.clear();
	}

	/**
	 * clear deadline tasks list
	 * 
	 */
	private void clearDeadlineTasks() {
		ArrayList<Task> taskList = state.getDeadlineTasks();
		taskList.clear();
	}

	/**
	 * clear floating tasks list
	 * 
	 */
	private void clearFloatingTasks() {
		ArrayList<Task> taskList = state.getFloatingTasks();
		taskList.clear();
	}

	/**
	 * remove tasks from all tasks list that has a certain task type
	 * @param taskType  task type of tasks to be pruned
	 * 
	 */
	private void pruneAllTasks(TaskType taskType) {

		ArrayList<Task> prunedAllTaskList = new ArrayList<Task>();
		ArrayList<Task> allTaskList = state.getAllTasks();
		for(int i = 0; i < allTaskList.size(); i++){
			Task another = allTaskList.get(i);
			if(another.getTaskType() != taskType){
				prunedAllTaskList.add(another);
			}
		}
		state.setAllTasks(prunedAllTaskList);
	}
	
	/**
	 * remove a certain task from all task list, deadline task list and floating task list
	 * if applicable
	 * @param task  task to be deleted
	 * 
	 */
	private void deleteTask(Task toBeDeleted) {
		ArrayList<Task> allTasks = state.getAllTasks();
		allTasks.remove(toBeDeleted);
		
		TaskType taskType = toBeDeleted.getTaskType();
		
		if(taskType == TaskType.FLOATING){
			ArrayList<Task> floatingTasks = state.getFloatingTasks();
			floatingTasks.remove(toBeDeleted);
		}
		
		if(taskType == TaskType.DEADLINE){
			ArrayList<Task> deadlineTasks = state.getDeadlineTasks();
			deadlineTasks.remove(toBeDeleted);
		}
	}

}
