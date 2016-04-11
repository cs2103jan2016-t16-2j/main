//@@author A0107354L
package logic;

import java.util.ArrayList;

import common.Constant;
import common.State;
import common.Task;
import common.ViewMode;

public class TickTask implements Operation {
	//============================
	//       Attributes
	//============================
	private State state;

	
	//====================================
	//       Constructor and Initiliser
	//====================================
	
	public TickTask(State state) {
		this.state = state;
	}
	
	
	/**
	 * Perform ticking of tasks
	 * If the index are out of bound or tick under FINISHED, CONFIG, HELP or UNDEFINED mode
	 * return false
	 * 
	 * @return  boolean to indicate whether the addition is successful
	 */
	@Override
	public boolean process() {
		try {
			int localPositionIndex = getAndValidatePositionIndex();
	
			ViewMode viewMode = state.getViewMode();
			boolean isTickSuccessful;
			
			switch (viewMode) { 
				case FLOATING:
					isTickSuccessful = tickUnderFloatingMode(localPositionIndex);
					return isTickSuccessful;
				case DEADLINE:
					isTickSuccessful = tickUnderDeadlineMode(localPositionIndex);
					return isTickSuccessful;
				case ALL:
					isTickSuccessful = tickUnderAllMode(localPositionIndex);
					return isTickSuccessful;
				case SEARCH:
					isTickSuccessful = tickUnderSearchMode(localPositionIndex);
					return isTickSuccessful;
				case START:
					isTickSuccessful = tickUnderStartMode(localPositionIndex);
					return isTickSuccessful;
				case FINISHED:
					tickUnderInvalidMode();
					return false;
				case UNDEFINED:
					tickUnderInvalidMode();
					return false;
				case CONFIG:
					tickUnderInvalidMode();
					return false;
				case HELP:
					tickUnderInvalidMode();
					return false;
				default:
					return false;
			}	

		} catch (IndexOutOfBoundsException e){
			tickWithInvalidPositionIndex();
			return false;
		}
	}

	/**
	 * check and return the index of task to be ticked 
	 * @return position of the task to be deleted in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is < 0.
	 */
	private int getAndValidatePositionIndex() throws IndexOutOfBoundsException{
		LogicUtils logicUtils = new LogicUtils(state);
		int localPositionIndex = logicUtils.getAndValidatePositionIndex();
		return localPositionIndex;
	}

	
	/**
	 * tick tasks with invalid position index
	 * (smaller than 0 or larger than max index)
	 * set error message 
	 */
	private void tickWithInvalidPositionIndex() {
		state.setDisplayMessage(Constant.MESSAGE_INDEX_OUT_OF_BOUND);
	}

	/**
	 * tick task under invalid mode
	 * set error message 
	 * 
	 */
	private void tickUnderInvalidMode() {
		state.setDisplayMessage(Constant.MESSAGE_CLEAR_IN_WRONG_MODE);
	}


	/**
	 * tick task under start mode
	 * @param positionIndexLocal   position of the task to be ticked in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in starting tasks list.
	 * @return a boolean to indicate whether tick is successful
	 */
	private boolean tickUnderStartMode(int positionIndexLocal) {
		// find the task in all tasks list
		Task toBeTicked = findTaskFromStartingTasks(positionIndexLocal);
		
		//tick the task 
		boolean isTickSuccessful = tickTask(toBeTicked);
		
		if(isTickSuccessful) {
			return true;
		} else {
			state.setDisplayMessage(Constant.MESSAGE_SYSTEM_FAILED_TO_TICK);
			return false;
		}
	}

	/**
	 * tick task under search mode
	 * @param positionIndexLocal   position of the task to be ticked in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in starting tasks list.
	 * @return a boolean to indicate whether tick is successful
	 */
	private boolean tickUnderSearchMode(int positionIndexLocal) {
		// find the task in all tasks list
		Task toBeTicked = findTaskFromSearchedTasks(positionIndexLocal);
		
		//tick the task 
		boolean isTickSuccessful = tickTask(toBeTicked);
		
		if(isTickSuccessful) {
			return true;
		} else {
			state.setDisplayMessage(Constant.MESSAGE_SYSTEM_FAILED_TO_TICK);
			return false;
		}
	}

	/**
	 * tick task under all mode
	 * @param positionIndexLocal   position of the task to be ticked in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in starting tasks list.
	 * @return a boolean to indicate whether tick is successful
	 */
	private boolean tickUnderAllMode(int positionIndexLocal) {
		// find the task in all tasks list
		Task toBeTicked = findTaskFromAllTasks(positionIndexLocal);
		
		//tick the task 
		boolean isTickSuccessful = tickTask(toBeTicked);
		
		if(isTickSuccessful) {
			return true;
		} else {
			state.setDisplayMessage(Constant.MESSAGE_SYSTEM_FAILED_TO_TICK);
			return false;
		}
	}

	
	/**
	 * tick task under deadline mode
	 * @param positionIndexLocal   position of the task to be ticked in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in starting tasks list.
	 * @return a boolean to indicate whether tick is successful
	 */
	private boolean tickUnderDeadlineMode(int positionIndexLocal) {
		// find the task in deadline tasks list
		Task toBeTicked = findTaskFromDeadlineTasks(positionIndexLocal);
		
		//tick the task 
		boolean isTickSuccessful = tickTask(toBeTicked);
		
		if(isTickSuccessful) {
			return true;
		} else {
			state.setDisplayMessage(Constant.MESSAGE_SYSTEM_FAILED_TO_TICK);
			return false;
		}
	}

	/**
	 * tick task under floating mode
	 * @param positionIndexLocal   position of the task to be ticked in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in starting tasks list.
	 * @return a boolean to indicate whether tick is successful
	 */
	private boolean tickUnderFloatingMode(int positionIndexLocal) {
		Task toBeTicked = findTaskFromFloatingTasks(positionIndexLocal);
		
		//tick the task 
		boolean isTickSuccessful = tickTask(toBeTicked);
		
		if(isTickSuccessful) {
			return true;
		} else {
			state.setDisplayMessage(Constant.MESSAGE_SYSTEM_FAILED_TO_TICK);
			return false;
		}
	}

	
	/**
	 * find task from starting task list
	 * @param positionIndexLocal   position of the task to be ticked in zero based index
	 * @return the task if found in starting tasks
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in starting tasks list.
	 */
	private Task findTaskFromStartingTasks(int positionIndexLocal) throws IndexOutOfBoundsException{
		ArrayList<Task> taskList = state.getTodaysTasks();

		if(positionIndexLocal >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeTicked = taskList.get(positionIndexLocal);
		return toBeTicked;
	}

	/**
	 * find task from searched task list
	 * @param positionIndexLocal   position of the task to be ticked in zero based index
	 * @return the task if found in searched tasks
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in searched tasks list.
	 */
	private Task findTaskFromSearchedTasks(int localPositionIndex) throws IndexOutOfBoundsException{
		ArrayList<Task> taskList = state.getSearchResultTasks();

		if(localPositionIndex >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeTicked = taskList.get(localPositionIndex);
		return toBeTicked;
	}
	
	/**
	 * find task from all task list
	 * @param positionIndexLocal   position of the task to be ticked in zero based index
	 * @return the task if found in all tasks
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in all tasks list.
	 */
	private Task findTaskFromAllTasks(int localPositionIndex) throws IndexOutOfBoundsException{
		ArrayList<Task> taskList = state.getAllTasks();

		if(localPositionIndex >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeTicked = taskList.get(localPositionIndex);
		return toBeTicked;
	}
	
	/**
	 * find task from deadline task list
	 * @param positionIndexLocal   position of the task to be ticked in zero based index
	 * @return the task if found in deadline tasks
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in deadline tasks list.
	 */
	private Task findTaskFromDeadlineTasks(int localPositionIndex) throws IndexOutOfBoundsException {
		ArrayList<Task> taskList = state.getDeadlineTasks();

		if(localPositionIndex >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeTicked = taskList.get(localPositionIndex);
		return toBeTicked;
	}
	
	
	/**
	 * find task from floating task list
	 * @param positionIndexLocal   position of the task to be ticked in zero based index
	 * @return the task if found in floating tasks
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in all floating list.
	 */
	private Task findTaskFromFloatingTasks(int localPositionIndex) throws IndexOutOfBoundsException {
		ArrayList<Task> taskList = state.getFloatingTasks();

		if(localPositionIndex >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeTicked = taskList.get(localPositionIndex);
		return toBeTicked;
	}
	
	/**
	 * tick the task by removing the task and put it into finished task list
	 * @param toBeTicked    the task to be ticked
	 * @return a boolean to indicate whether tick is successful
	 */
	private boolean tickTask(Task toBeTicked) {
		ArrayList<Task> finishedTask = state.getFinishedTasks();
		finishedTask.add(toBeTicked);
		DeleteTask deleteTask = new DeleteTask(state);
		boolean isDeletedSuccessful = deleteTask.process();
		state.setViewMode(ViewMode.FINISHED);
		return isDeletedSuccessful;
	}

	
}

