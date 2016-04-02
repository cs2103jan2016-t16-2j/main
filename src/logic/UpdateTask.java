package logic;

import java.util.ArrayList;
import common.DisplayMessage;
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
				boolean isTickSuccessful = updateTask(toBeUpdated);

				if(isTickSuccessful) {
					return true;
				} else {
					state.setDisplayMessage(DisplayMessage.MESSAGE_SYSTEM_FAILED_TO_TICK);
					return false;
				}
			}

			//If the viewMode is Deadline, update object deadline tasks list
			if(viewMode == ViewMode.DEADLINE){
				// find the task in deadline tasks list
				Task toBeUpdated = findTaskFromDeadlineTasks(positionIndex);

				//tick the task 
				boolean isTickSuccessful = updateTask(toBeUpdated);

				if(isTickSuccessful) {
					return true;
				} else {
					state.setDisplayMessage(DisplayMessage.MESSAGE_SYSTEM_FAILED_TO_TICK);
					return false;
				}
			}

			//If the viewMode is all, update object in all tasks list
			if(viewMode == ViewMode.ALL){
				// find the task in all tasks list
				Task toBeUpdated = findTaskFromAllTasks(positionIndex);

				//tick the task 
				boolean isTickSuccessful = updateTask(toBeUpdated);

				if(isTickSuccessful) {
					return true;
				} else {
					state.setDisplayMessage(DisplayMessage.MESSAGE_SYSTEM_FAILED_TO_TICK);
					return false;
				}
			}

			//in wrong view mode
			state.setDisplayMessage(DisplayMessage.MESSAGE_DELETE_IN_WRONG_MODE);
			return false;
		} catch (IndexOutOfBoundsException e){
			state.setDisplayMessage(DisplayMessage.MESSAGE_INDEX_OUT_OF_BOUND);
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
	
	private int fromOneBaseToZeroBase(int positionIndex) {
		positionIndex--;
		return positionIndex;
	}

}
