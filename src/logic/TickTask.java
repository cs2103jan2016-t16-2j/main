package logic;

import java.util.ArrayList;

import common.Constant;
import common.State;
import common.Task;
import common.ViewMode;

public class TickTask implements Operation {
	private State state;

	public TickTask(State state) {
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
				Task toBeTicked = findTaskFromFloatingTasks(localPositionIndex);
				
				//tick the task 
				boolean isTickSuccessful = tickTask(localPositionIndex, toBeTicked);
				
				if(isTickSuccessful) {
					return true;
				} else {
					state.setDisplayMessage(Constant.MESSAGE_SYSTEM_FAILED_TO_TICK);
					return false;
				}
			}

			//If the viewMode is Deadline, update object deadline tasks list
			if(viewMode == ViewMode.DEADLINE){
				// find the task in deadline tasks list
				Task toBeTicked = findTaskFromDeadlineTasks(localPositionIndex);
				
				//tick the task 
				boolean isTickSuccessful = tickTask(localPositionIndex, toBeTicked);
				
				if(isTickSuccessful) {
					return true;
				} else {
					state.setDisplayMessage(Constant.MESSAGE_SYSTEM_FAILED_TO_TICK);
					return false;
				}
			}

			//If the viewMode is all, update object in all tasks list
			if(viewMode == ViewMode.ALL){
				// find the task in all tasks list
				Task toBeTicked = findTaskFromAllTasks(localPositionIndex);
				
				//tick the task 
				boolean isTickSuccessful = tickTask(localPositionIndex, toBeTicked);
				
				if(isTickSuccessful) {
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

	private Task findTaskFromAllTasks(int localPositionIndex) throws IndexOutOfBoundsException{
		ArrayList<Task> taskList = state.getAllTasks();

		if(localPositionIndex >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeTicked = taskList.get(localPositionIndex);
		return toBeTicked;
	}

	private Task findTaskFromDeadlineTasks(int localPositionIndex) throws IndexOutOfBoundsException {
		ArrayList<Task> taskList = state.getDeadlineTasks();

		if(localPositionIndex >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeTicked = taskList.get(localPositionIndex);
		return toBeTicked;
	}

	private boolean tickTask(int localPositionIndex, Task toBeTicked) {
		ArrayList<Task> finishedTask = state.getFinishedTasks();
		finishedTask.add(toBeTicked);
		DeleteTask deleteTask = new DeleteTask(state);
		boolean isDeletedSuccessful = deleteTask.process();
		return isDeletedSuccessful;
	}

	private Task findTaskFromFloatingTasks(int localPositionIndex) throws IndexOutOfBoundsException {
		ArrayList<Task> taskList = state.getFloatingTasks();

		if(localPositionIndex >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeTicked = taskList.get(localPositionIndex);
		return toBeTicked;
	}

	private int fromOneBaseToZeroBase(int num) {
		int newNew = num - 1;
		return newNew;
	}
}

