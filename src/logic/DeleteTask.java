//@@author A0107354L
package logic;

import java.util.ArrayList;

import common.Constant;
import common.State;
import common.Task;
import common.TaskType;
import common.ViewMode;

public class DeleteTask implements Operation {

	//============================
	//       Attributes
	//============================
	private State state;


	//====================================
	//       Constructor and Initiliser
	//====================================

	public DeleteTask(State state) {
		this.state = state;
	}


	/**
	 * Perform deletion of new task
	 * If the index are out of bound or delete under CONFIG, HELP or UNDEFINED mode
	 * return false
	 *
	 * @return     boolean to indicate whether the deletion is successful
	 */
	@Override
	public boolean process() {
		try {
			// change position index from 1 base(user input) to zero base
			int positionIndexLocal = getAndValidatePositionIndex();

			ViewMode viewMode = state.getViewMode();
			
			switch (viewMode) { 
				case FLOATING:
					deletedUnderFloatingMode(positionIndexLocal);
					return true;
				case DEADLINE:
					deleteUnderDeadlineMode(positionIndexLocal);
					return true;				
				case ALL:
					deleteUnderAllMode(positionIndexLocal);
					return true;				
				case SEARCH:
					deleteUnderSearchMode(positionIndexLocal);
					return true;					
				case FINISHED:
					deleteUnderFinishedMode(positionIndexLocal);
					return true;
				case START:
					deleteUnderStartMode(positionIndexLocal);
					return true;
				case UNDEFINED:
					deleteUnderInvalidMode();
					return false;
				case CONFIG:
					deleteUnderInvalidMode();
					return false;
				case HELP:
					deleteUnderInvalidMode();
					return false;
				default:
					return false;
			}	
			
		} catch (IndexOutOfBoundsException e) {
			//logging
			deleteWithInvalidPositionIndex();
			return false;
		}
	}

	/**
	 * delete tasks with invalid position index
	 * (smaller than 0 or larger than max index)
	 * set error message 
	 */
	private void deleteWithInvalidPositionIndex() {
		state.setDisplayMessage(Constant.MESSAGE_INDEX_OUT_OF_BOUND);
	}


	/**
	 * delete tasks under invalid mode
	 * set error message 
	 */
	private void deleteUnderInvalidMode() {
		state.setDisplayMessage(Constant.MESSAGE_CLEAR_IN_WRONG_MODE);
	}
	
	
	/**
	 * delete tasks under start mode
	 * @param positionIndexLocal   position of the task to be deleted in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in starting tasks list.
	 */
	
	private void deleteUnderStartMode(int positionIndexLocal) throws IndexOutOfBoundsException{
		ArrayList<Task> startingTasks = state.getTodaysTasks();

		if(positionIndexLocal >= startingTasks.size()){
			throw new IndexOutOfBoundsException();
		}

		Task toBeDeleted = startingTasks.get(positionIndexLocal);
		deleteTask(toBeDeleted);
		startingTasks.remove(toBeDeleted);
	}
	
	/**
	 * delete tasks under finished mode
	 * @param positionIndexLocal   position of the task to be deleted in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in finished tasks list.
	 */

	private void deleteUnderFinishedMode(int positionIndexLocal) throws IndexOutOfBoundsException{
		ArrayList<Task> finishedTasks = state.getFinishedTasks();

		if(positionIndexLocal >= finishedTasks.size()){
			throw new IndexOutOfBoundsException();
		}

		finishedTasks.remove(positionIndexLocal);
	}

	/**
	 * delete tasks under search mode
	 * @param positionIndexLocal   position of the task to be deleted in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in searched tasks list.
	 */
	private void deleteUnderSearchMode(int positionIndexLocal) throws IndexOutOfBoundsException{
		ArrayList<Task> searchedTasks = state.getSearchResultTasks();

		if(positionIndexLocal >= searchedTasks.size()){
			throw new IndexOutOfBoundsException();
		}

		Task toBeDeleted = searchedTasks.get(positionIndexLocal);
		deleteTask(toBeDeleted);
		searchedTasks.remove(positionIndexLocal);
	}
	
	/**
	 * delete tasks under deadline mode
	 * @param positionIndexLocal   position of the task to be deleted in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in searched tasks list.
	 */
	private void deleteUnderDeadlineMode(int positionIndexLocal) throws IndexOutOfBoundsException{
		ArrayList<Task> deadlineTasks = state.getDeadlineTasks();

		if(positionIndexLocal >= deadlineTasks.size()){
			throw new IndexOutOfBoundsException();
		}

		Task toBeDeleted = deadlineTasks.get(positionIndexLocal);
		deleteTask(toBeDeleted);
	}

	/**
	 * delete tasks under floating mode
	 * @param positionIndexLocal   position of the task to be deleted in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in searched tasks list.
	 */
	private void deletedUnderFloatingMode(int positionIndexLocal) throws IndexOutOfBoundsException {
		ArrayList<Task> floatingTasks = state.getFloatingTasks();

		if(positionIndexLocal >= floatingTasks.size()){
			throw new IndexOutOfBoundsException();
		}

		Task toBeDeleted = floatingTasks.get(positionIndexLocal);
		deleteTask(toBeDeleted);
	}

	/**
	 * delete tasks under all mode
	 * @param positionIndexLocal   position of the task to be deleted in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in all tasks list.
	 */
	private void deleteUnderAllMode(int positionIndexLocal) throws IndexOutOfBoundsException{
		ArrayList<Task> allTasks = state.getAllTasks();

		if(positionIndexLocal >= allTasks.size()){
			throw new IndexOutOfBoundsException();
		}

		Task toBeDeleted = allTasks.get(positionIndexLocal);
		deleteTask(toBeDeleted);
	}

	/**
	 * delete the task from all task list, deadline task list and floating task list where applicable
	 * @param toBeDeleted   task to be deleted
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
	
}
