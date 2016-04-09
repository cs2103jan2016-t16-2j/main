package logic;

import java.util.ArrayList;

import common.Constant;
import common.State;
import common.Task;
import common.ViewMode;

public class UpdateTask implements Operation {
	private State state;

	public UpdateTask(State state) {
		this.state = state;
	}

	@Override
	public boolean process() {
		try {
			int positionIndex = state.getPositionIndex();

			// Convert 1 base index to 0 base index
			int localPositionIndex = fromOneBaseToZeroBase(positionIndex);

			if(localPositionIndex < 0){
				throw new IndexOutOfBoundsException();
			}

			//Search different task list depends on current view mode
			ViewMode viewMode = state.getViewMode();

			//If the viewMode is Floating, find object in floating tasks list
			if(viewMode == ViewMode.FLOATING){
				// find the task in floating tasks list
				Task toBeUpdated = findTaskFromFloatingTasks(localPositionIndex);

				//update the task 
				boolean isUpdateSuccessful = updateTask(toBeUpdated);

				if(isUpdateSuccessful) {
					return true;
				} else {
					state.setDisplayMessage(Constant.MESSAGE_SYSTEM_FAILED_TO_TICK);
					return false;
				}
			}

			//If the viewMode is Deadline, update object deadline tasks list
			if(viewMode == ViewMode.DEADLINE){
				// find the task in deadline tasks list
				Task toBeUpdated = findTaskFromDeadlineTasks(localPositionIndex);

				//update the task 
				boolean isUpdateSuccessful = updateTask(toBeUpdated);

				if(isUpdateSuccessful) {
					return true;
				} else {
					state.setDisplayMessage(Constant.MESSAGE_SYSTEM_FAILED_TO_TICK);
					return false;
				}
			}

			//If the viewMode is all, update object in all tasks list
			if(viewMode == ViewMode.ALL){
				// find the task in all tasks list
				Task toBeUpdated = findTaskFromAllTasks(localPositionIndex);

				//tick the task 
				boolean isTickSuccessful = updateTask(toBeUpdated);

				if(isTickSuccessful) {
					return true;
				} else {
					state.setDisplayMessage(Constant.MESSAGE_SYSTEM_FAILED_TO_TICK);
					return false;
				}
			}

			//If the viewMode is search, update object in searched tasks list
			if(viewMode == ViewMode.SEARCH){
				// find the task in searched tasks list
				Task toBeUpdated = findTaskFromSearchedTasks(localPositionIndex);

				//update the task 
				boolean isTickSuccessful = updateTask(toBeUpdated);

				if(isTickSuccessful) {
					return true;
				} else {
					state.setDisplayMessage(Constant.MESSAGE_SYSTEM_FAILED_TO_TICK);
					return false;
				}
			}
			
			//If the viewMode is start, update object in todays tasks list
			if(viewMode == ViewMode.START){
				// find the task in todays tasks list
				Task toBeUpdated = findTaskFromStartingTasks(localPositionIndex);

				//update the task 
				boolean isTickSuccessful = updateTask(toBeUpdated);

				if(isTickSuccessful) {
					return true;
				} else {
					state.setDisplayMessage(Constant.MESSAGE_SYSTEM_FAILED_TO_TICK);
					return false;
				}
			}
			
			//If the viewMode is finished, update object in finished tasks list
			if(viewMode == ViewMode.FINISHED){
				// find the task in finished tasks list
				Task toBeUpdated = findTaskFromFinishedTasks(localPositionIndex);

				//update the task 
				boolean isTickSuccessful = updateTask(toBeUpdated);

				if(isTickSuccessful) {
					return true;
				} else {
					state.setDisplayMessage(Constant.MESSAGE_SYSTEM_FAILED_TO_TICK);
					return false;
				}
			}
			
			//in wrong view mode
			state.setDisplayMessage(Constant.MESSAGE_UPDATE_IN_WRONG_MODE);
			return false;
		} catch (IndexOutOfBoundsException e){
			state.setDisplayMessage(Constant.MESSAGE_INDEX_OUT_OF_BOUND);
			return false;
		}
	}
	
	private boolean updateTask(Task task){
		if(state.getIsContentChanged()){
			task.setContent(state.getContent());
		}
		
		if(state.getIsVenueChanged()){
			task.setVenue(state.getVenue());
		}
		
		if(state.getIsDetailChanged()){
			task.setDetail(state.getDetail());
		}
		
		if(state.getIsStartDateChanged()){
			task.setStartDate(state.getStartDate());
		}
		
		if(state.getIsEndDateChanged()){
			task.setEndDate(state.getEndDate());
		}
		
		return true;
	}
	
	private Task findTaskFromFinishedTasks(int localPositionIndex) throws IndexOutOfBoundsException{
		ArrayList<Task> taskList = state.getFinishedTasks();

		if(localPositionIndex >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeUpdated = taskList.get(localPositionIndex);
		return toBeUpdated;
	}
	
	
	private Task findTaskFromStartingTasks(int localPositionIndex) throws IndexOutOfBoundsException{
		ArrayList<Task> taskList = state.getTodaysTasks();

		if(localPositionIndex >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeUpdated = taskList.get(localPositionIndex);
		return toBeUpdated;
	}
	
	private Task findTaskFromSearchedTasks(int localPositionIndex) throws IndexOutOfBoundsException{
		ArrayList<Task> taskList = state.getSearchResultTasks();

		if(localPositionIndex >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeUpdated = taskList.get(localPositionIndex);
		return toBeUpdated;
	}
	
	private Task findTaskFromAllTasks(int localPositionIndex) throws IndexOutOfBoundsException{
		ArrayList<Task> taskList = state.getAllTasks();

		if(localPositionIndex >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeUpdated = taskList.get(localPositionIndex);
		return toBeUpdated;
	}

	private Task findTaskFromDeadlineTasks(int localPositionIndex) throws IndexOutOfBoundsException{
		ArrayList<Task> taskList = state.getDeadlineTasks();

		if(localPositionIndex >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeUpdated = taskList.get(localPositionIndex);
		return toBeUpdated;
	}
	
	private Task findTaskFromFloatingTasks(int localPositionIndex) throws IndexOutOfBoundsException{
		ArrayList<Task> taskList = state.getFloatingTasks();

		if(localPositionIndex >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeUpdated = taskList.get(localPositionIndex);
		return toBeUpdated;
	}
	
	private int fromOneBaseToZeroBase(int num) {
		int newNum = num - 1;
		return newNum;
	}

}
