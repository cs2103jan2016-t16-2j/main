package logic;

import java.util.ArrayList;

import common.Constant;
import common.State;
import common.Task;
import common.TaskType;
import common.ViewMode;

public class ClearTask implements Operation {
	private State state;

	public ClearTask(State state) {
		this.state = state;
	}

	@Override
	public boolean process() {
		try {
			ViewMode viewMode = state.getViewMode();
			
			if(viewMode == ViewMode.FLOATING){
				clearFloatingTasks();
				pruneAllTasks(TaskType.FLOATING);
				return true;
			}  
			
			if(viewMode == ViewMode.DEADLINE){
				clearDeadlineTasks();
				pruneAllTasks(TaskType.DEADLINE);
				return true;
			} 
			
			if(viewMode == ViewMode.ALL){
				clearDeadlineTasks();
				clearFloatingTasks();
				clearAllTasks();						
				return true;
			}

			if(viewMode == ViewMode.SEARCH){
				clearSearchTasks();
				return true;
			}
			
			if(viewMode == ViewMode.FINISHED){
				clearFinishedTasks();
				return true;
			}
			
			if(viewMode == ViewMode.START){
				clearTodaysTasks();
				return true;
			}
			
			//in wrong view mode
			state.setDisplayMessage(Constant.MESSAGE_CLEAR_IN_WRONG_MODE);
			return false;
			
		} catch (Exception e) {
			//logging
			state.setDisplayMessage(Constant.MESSAGE_DUMMY);
			return false;
		}
	}
	
	private void clearTodaysTasks() {
		ArrayList<Task> todaysTasks = state.getTodaysTasks();
		for(int i = 0; i < todaysTasks.size(); i++){
			Task task = todaysTasks.get(i);
			deleteTask(task);
		}
		todaysTasks.clear();
	}
	
	private void clearFinishedTasks() {
		ArrayList<Task> finishedTask = state.getFinishedTasks();
		finishedTask.clear();
	}

	private void clearSearchTasks() {
		ArrayList<Task> searchedTaskList = state.getSearchResultTasks();
		for(int i = 0; i < searchedTaskList.size(); i++){
			Task task = searchedTaskList.get(i);
			deleteTask(task);
		}
		searchedTaskList.clear();
	}

	private void clearAllTasks() {
		ArrayList<Task> allTaskList = state.getAllTasks();
		allTaskList.clear();
	}

	private void clearDeadlineTasks() {
		ArrayList<Task> taskList = state.getDeadlineTasks();
		taskList.clear();
	}

	private void clearFloatingTasks() {
		ArrayList<Task> taskList = state.getFloatingTasks();
		taskList.clear();
	}

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
