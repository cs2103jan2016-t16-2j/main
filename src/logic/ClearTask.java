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
			
			return true;
		} catch (Exception e) {
			//logging
			state.setDisplayMessage(Constant.MESSAGE_DUMMY);
			return false;
		}
	}

	private void clearSearchTasks() {
		ArrayList<Task> searchedTaskList = state.getSearchResultTasks();
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

}
