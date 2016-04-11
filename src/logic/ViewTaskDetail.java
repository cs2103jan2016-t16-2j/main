package logic;

import java.util.ArrayList;

import common.Constant;
import common.State;
import common.Task;
import common.ViewMode;

public class ViewTaskDetail implements Operation {
	//============================
	//       Attributes
	//============================
	private State state;

	
	//====================================
	//       Constructor and Initiliser
	//====================================
	
	public ViewTaskDetail(State state) {
		this.state = state;
	}
	
	
	/**
	 * Perform view tasks detail 
	 * toggle the isDetailDisplayed attribute of the selected task to 
	 * fold or collapse the task
	 * If the index are out of bound or view detail under CONFIG, HELP or UNDEFINED mode
	 * return false
	 * 
	 * @return  boolean to indicate whether the update is successful
	 */
	@Override
	public boolean process() {
		try {
			int localPositionIndex = getAndValidatePositionIndex();

			ViewMode viewMode = state.getViewMode();

			switch (viewMode) { 
				case FLOATING:
					viewUnderFloatingMode(localPositionIndex);
					return true;
				case DEADLINE:
					viewUnderDeadlineMode(localPositionIndex);
					return true;
				case ALL:
					viewUnderAllMode(localPositionIndex);
					return true;
				case SEARCH:
					viewUnderSearchMode(localPositionIndex);
					return true;
				case START:
					viewUnderStartMode(localPositionIndex);
					return true;
				case FINISHED:
					viewUnderFinishedMode(localPositionIndex);
					return false;
				case UNDEFINED:
					viewUnderInvalidMode();
					return false;
				case CONFIG:
					viewUnderInvalidMode();
					return false;
				case HELP:
					viewUnderInvalidMode();
					return false;
				default:
					return false;
			}	

		} catch (IndexOutOfBoundsException e){
			viewWithInvalidPositionIndex();
			return false;
		}
	}

	
	/**
	 * view detail with invalid position index
	 * (smaller than 0 or larger than max index)
	 * set error message 
	 */
	private void viewWithInvalidPositionIndex() {
		state.setDisplayMessage(Constant.MESSAGE_INDEX_OUT_OF_BOUND);
	}

	/**
	 * view detail under invalid mode
	 * set error message 
	 * 
	 */
	private void viewUnderInvalidMode() {
		state.setDisplayMessage(Constant.MESSAGE_DELETE_IN_WRONG_MODE);
	}
	
	
	/**
	 * view task detail under finished mode
	 * @param positionIndex   position of the task to be folded/collapsed in zero based index
	 */
	private void viewUnderFinishedMode(int positionIndex) {
		Task toBeDisplayed = findTaskFromFinishedTasks(positionIndex);
		viewTaskDetail(toBeDisplayed);
	}
	
	/**
	 * view task detail under all mode
	 * @param positionIndex   position of the task to be folded/collapsed in zero based index
	 */
	private void viewUnderAllMode(int positionIndex) {
		Task toBeDisplayed = findTaskFromAllTasks(positionIndex);
		viewTaskDetail(toBeDisplayed);
	}

	/**
	 * view task detail under search mode
	 * @param positionIndex   position of the task to be folded/collapsed in zero based index
	 */
	private void viewUnderSearchMode(int positionIndex) {
		Task toBeUpdated = findTaskFromSearchedTasks(positionIndex);
		viewTaskDetail(toBeUpdated);
	}

	/**
	 * view task detail under start mode
	 * @param positionIndex   position of the task to be folded/collapsed in zero based index
	 */
	private void viewUnderStartMode(int positionIndex) {
		Task toBeUpdated = findTaskFromStartingTasks(positionIndex);
		viewTaskDetail(toBeUpdated);
	}
	
	/**
	 * view task detail under deadline mode
	 * @param positionIndex   position of the task to be folded/collapsed in zero based index
	 */
	private void viewUnderDeadlineMode(int positionIndex) {
		Task toBeUpdated = findTaskFromDeadlineTasks(positionIndex);

		viewTaskDetail(toBeUpdated);
	}

	/**
	 * view task detail under floating mode
	 * @param positionIndex   position of the task to be folded/collapsed in zero based index
	 */
	private void viewUnderFloatingMode(int positionIndex) {
		Task toBeUpdated = findTaskFromFloatingTasks(positionIndex);

		viewTaskDetail(toBeUpdated);
	}

	/**
	 * unfold a task or fold a task to view its detail
	 * fold if it is collapsed and collapse if it is folded
	 * @param task       the task that is to be folded/collapsed
	 */
	private void viewTaskDetail(Task task){
		boolean currentDisplayStatus = task.getIsDetailDisplayed();
		boolean changedToDisplayStatus = !currentDisplayStatus;
		task.setIsDetailDisplayed(changedToDisplayStatus);
	}
	
	/**
	 * find task from all tasks list
	 * @param positionIndex   position of the task to be found in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in all tasks list.
	 */
	private Task findTaskFromAllTasks(int positionIndex) {
		ArrayList<Task> taskList = state.getAllTasks();

		if(positionIndex >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeUpdated = taskList.get(positionIndex);
		return toBeUpdated;
	}
	
	/**
	 * find task from deadline tasks list
	 * @param positionIndex   position of the task to be found in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in deadline tasks list.
	 */
	private Task findTaskFromDeadlineTasks(int positionIndex) {
		ArrayList<Task> taskList = state.getDeadlineTasks();

		if(positionIndex >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeUpdated = taskList.get(positionIndex);
		return toBeUpdated;
	}
	
	/**
	 * find task from all floating list
	 * @param positionIndex   position of the task to be found in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in floating tasks list.
	 */
	private Task findTaskFromFloatingTasks(int positionIndex) {
		ArrayList<Task> taskList = state.getFloatingTasks();

		if(positionIndex >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeUpdated = taskList.get(positionIndex);
		return toBeUpdated;
	}
	
	/**
	 * find task from searched tasks list
	 * @param positionIndex   position of the task to be found in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in searched tasks list.
	 */
	private Task findTaskFromSearchedTasks(int positionIndex) {
		ArrayList<Task> taskList = state.getSearchResultTasks();

		if(positionIndex >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeUpdated = taskList.get(positionIndex);
		return toBeUpdated;
	}
	
	/**
	 * find task from starting tasks list
	 * @param positionIndex   position of the task to be found in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in starting tasks list.
	 */
	private Task findTaskFromStartingTasks(int positionIndex) {
		ArrayList<Task> taskList = state.getTodaysTasks();

		if(positionIndex >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeUpdated = taskList.get(positionIndex);
		return toBeUpdated;
	}
	
	/**
	 * find task from finished tasks list
	 * @param positionIndex   position of the task to be found in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in finished tasks list.
	 */
	private Task findTaskFromFinishedTasks(int positionIndex) {
		ArrayList<Task> taskList = state.getFinishedTasks();

		if(positionIndex >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeUpdated = taskList.get(positionIndex);
		return toBeUpdated;
	}
	
	
	/**
	 * check and return the index of task to be ticked 
	 * @return position of the task to be deleted in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is < 0.
	 */
	private int getAndValidatePositionIndex() {
		int positionIndex = state.getPositionIndex();

		// Convert 1 base index to 0 base index
		int localPositionIndex = fromOneBaseToZeroBase(positionIndex);

		if(localPositionIndex < 0){
			throw new IndexOutOfBoundsException();
		}
		return localPositionIndex;
	}
	
	/**
	 * return a number that is 1 less than the input
	 * @param num   number to be subtracted
	 * @return subtracted number that is 1 less than the input
	 */
	private int fromOneBaseToZeroBase(int num) {
		int newNew = num - 1;
		return newNew;
	}

}
