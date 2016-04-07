package logic;

import java.util.ArrayList;

import common.Constant;
import common.State;
import common.Task;
import common.ViewMode;

public class ViewTaskDetail implements Operation {
	private State state;

	public ViewTaskDetail(State state) {
		this.state = state;
	}

	@Override
	public boolean process() {
		try {
			int positionIndex = state.getPositionIndex();

			// Convert 1 base index to 0 base index
			positionIndex = fromOneBaseToZeroBase(positionIndex);

			if(positionIndex < 0){
				throw new IndexOutOfBoundsException();
			}

			//Search different task list depends on current view mode
			ViewMode viewMode = state.getViewMode();

			//If the viewMode is Floating, find object in floating tasks list
			if(viewMode == ViewMode.FLOATING){
				// find the task in floating tasks list
				Task toBeUpdated = findTaskFromFloatingTasks(positionIndex);

				//tick the task 
				boolean isDisplayDetailSuccessful = viewTaskDetail(toBeUpdated);

				if(isDisplayDetailSuccessful) {
					return true;
				} else {
					state.setDisplayMessage(Constant.MESSAGE_SYSTEM_FAILED_TO_TICK);
					return false;
				}
			}

			//If the viewMode is Deadline, update object deadline tasks list
			if(viewMode == ViewMode.DEADLINE){
				// find the task in deadline tasks list
				Task toBeUpdated = findTaskFromDeadlineTasks(positionIndex);

				//tick the task 
				boolean isDisplayDetailSuccessful = viewTaskDetail(toBeUpdated);

				if(isDisplayDetailSuccessful) {
					return true;
				} else {
					state.setDisplayMessage(Constant.MESSAGE_SYSTEM_FAILED_TO_TICK);
					return false;
				}
			}
			
			//If the viewMode is Search
			if(viewMode == ViewMode.SEARCH){
				// find the task in deadline tasks list
				Task toBeUpdated = findTaskFromSearchedTasks(positionIndex);

				//tick the task 
				boolean isDisplayDetailSuccessful = viewTaskDetail(toBeUpdated);

				if(isDisplayDetailSuccessful) {
					return true;
				} else {
					state.setDisplayMessage(Constant.MESSAGE_SYSTEM_FAILED_TO_TICK);
					return false;
				}
			}

			//If the viewMode is all, update object in all tasks list
			if(viewMode == ViewMode.ALL){
				// find the task in all tasks list
				Task toBeDisplayed = findTaskFromAllTasks(positionIndex);

				//tick the task 
				boolean isDisplayDetailSuccessful = viewTaskDetail(toBeDisplayed);

				if(isDisplayDetailSuccessful) {
					return true;
				} else {
					state.setDisplayMessage(Constant.MESSAGE_SYSTEM_FAILED_TO_TICK);
					return false;
				}
			}

			//in wrong view mode
			state.setDisplayMessage(Constant.MESSAGE_DELETE_IN_WRONG_MODE);
			return false;
		} catch (IndexOutOfBoundsException e){
			state.setDisplayMessage(Constant.MESSAGE_INDEX_OUT_OF_BOUND);
			return false;
		}
	}
	
	private boolean viewTaskDetail(Task task){
		boolean currentDisplayStatus = task.getIsDetailDisplayed();
		boolean changedToDisplayStatus = !currentDisplayStatus;
		task.setIsDetailDisplayed(changedToDisplayStatus);
		return true;
	}
	
	private Task findTaskFromAllTasks(int positionIndex) {
		ArrayList<Task> taskList = state.getAllTasks();

		if(positionIndex >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeUpdated = taskList.get(positionIndex);
		return toBeUpdated;
	}

	private Task findTaskFromDeadlineTasks(int positionIndex) {
		ArrayList<Task> taskList = state.getDeadlineTasks();

		if(positionIndex >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeUpdated = taskList.get(positionIndex);
		return toBeUpdated;
	}
	
	private Task findTaskFromFloatingTasks(int positionIndex) {
		ArrayList<Task> taskList = state.getFloatingTasks();

		if(positionIndex >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeUpdated = taskList.get(positionIndex);
		return toBeUpdated;
	}
	
	private Task findTaskFromSearchedTasks(int positionIndex) {
		ArrayList<Task> taskList = state.getSearchResultTasks();

		if(positionIndex >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeUpdated = taskList.get(positionIndex);
		return toBeUpdated;
	}
	
	
	private int fromOneBaseToZeroBase(int positionIndex) {
		positionIndex--;
		return positionIndex;
	}

}
