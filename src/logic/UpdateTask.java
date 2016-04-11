package logic;

import java.util.ArrayList;

import common.Constant;
import common.State;
import common.Task;
import common.ViewMode;

public class UpdateTask implements Operation {
	//============================
	//       Attributes
	//============================
	private State state;

	
	//====================================
	//       Constructor and Initiliser
	//====================================
	
	public UpdateTask(State state) {
		this.state = state;
	}
	
	
	/**
	 * Perform update of tasks
	 * If the index are out of bound or tick under FINISHED, CONFIG, HELP or UNDEFINED mode
	 * return false
	 * 
	 * @return  boolean to indicate whether the update is successful
	 */
	@Override
	public boolean process() {
		try {
			ViewMode viewMode = state.getViewMode();
			int positionIndexLocal = getAndValidatePositionIndex();
						
			switch (viewMode) { 
				case FLOATING:
					updateUnderFloatingMode(positionIndexLocal);
					return true;
				case DEADLINE:
					updateUnderDeadlineMode(positionIndexLocal);
					return true;
				case ALL:
					updateUnderAllMode(positionIndexLocal);
					return true;
				case SEARCH:
					updateUnderSearchMode(positionIndexLocal);
					return true;
				case START:
					updateUnderStartMode(positionIndexLocal);
					return true;
				case FINISHED:
					updateUnderFinishedMode(positionIndexLocal);
					return true;
				case UNDEFINED:
					updateUnderInvalidMode();
					return false;
				case CONFIG:
					updateUnderInvalidMode();
					return false;
				case HELP:
					updateUnderInvalidMode();
					return false;
				default:
					return false;
			}	

		} catch (IndexOutOfBoundsException e){
			updateWithInvalidPositionIndex();
			return false;
		}
	}

	
	/**
	 * tick tasks with invalid position index
	 * (smaller than 0 or larger than max index)
	 * set error message 
	 */
	private void updateWithInvalidPositionIndex() {
		state.setDisplayMessage(Constant.MESSAGE_INDEX_OUT_OF_BOUND);
	}
	
	/**
	 * tick task under invalid mode
	 * set error message 
	 * 
	 */
	private void updateUnderInvalidMode() {
		state.setDisplayMessage(Constant.MESSAGE_UPDATE_IN_WRONG_MODE);
	}
	
	/**
	 * update task under finished mode
	 * @param positionIndexLocal   position of the task to be updated in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in finished tasks list.
	 */
	private void updateUnderFinishedMode(int positionIndexLocal) {
		Task toBeUpdated = findTaskFromFinishedTasks(positionIndexLocal);

		updateTask(toBeUpdated);
	}

	/**
	 * update task under start mode
	 * @param positionIndexLocal   position of the task to be updated in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in starting tasks list.
	 */
	private void updateUnderStartMode(int positionIndexLocal) {
		Task toBeUpdated = findTaskFromStartingTasks(positionIndexLocal);

		updateTask(toBeUpdated);
	}

	/**
	 * update task under search mode
	 * @param positionIndexLocal   position of the task to be updated in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in searched tasks list.
	 */
	private void updateUnderSearchMode(int positionIndexLocal) {
		Task toBeUpdated = findTaskFromSearchedTasks(positionIndexLocal);

		updateTask(toBeUpdated);
	}
	
	/**
	 * update task under all mode
	 * @param positionIndexLocal   position of the task to be updated in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in all tasks list.
	 */
	private void updateUnderAllMode(int positionIndexLocal) {
		Task toBeUpdated = findTaskFromAllTasks(positionIndexLocal);
		updateTask(toBeUpdated);
	}

	/**
	 * update task under deadline mode
	 * @param positionIndexLocal   position of the task to be updated in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in deadline tasks list.
	 */
	private void updateUnderDeadlineMode(int positionIndexLocal) {
		Task toBeUpdated = findTaskFromDeadlineTasks(positionIndexLocal);

		updateTask(toBeUpdated);
	}

	/**
	 * update task under floating mode
	 * @param positionIndexLocal   position of the task to be updated in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in floating tasks list.
	 */
	private void updateUnderFloatingMode(int positionIndexLocal) {
		Task toBeUpdated = findTaskFromFloatingTasks(positionIndexLocal);

		updateTask(toBeUpdated);
	}
	
	/**
	 * update a task 
	 * @param task       the task that is to be updated
	 */
	private void updateTask(Task task){
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
		
	}
	
	/**
	 * find the task to be updated from finished tasks list
	 * @param positionIndexLocal   position of the task to be updated in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in finished tasks list.
	 * @return the task at postionIndexLocal position
	 */
	private Task findTaskFromFinishedTasks(int positionIndexLocal) throws IndexOutOfBoundsException{
		ArrayList<Task> taskList = state.getFinishedTasks();

		if(positionIndexLocal >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeUpdated = taskList.get(positionIndexLocal);
		return toBeUpdated;
	}
	
	/**
	 * find the task to be updated from starting tasks list
	 * @param positionIndexLocal   position of the task to be updated in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in starting tasks list.
	 * @return the task at postionIndexLocal position
	 */
	private Task findTaskFromStartingTasks(int positionIndexLocal) throws IndexOutOfBoundsException{
		ArrayList<Task> taskList = state.getTodaysTasks();

		if(positionIndexLocal >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeUpdated = taskList.get(positionIndexLocal);
		return toBeUpdated;
	}
	
	/**
	 * find the task to be updated from searched tasks list
	 * @param positionIndexLocal   position of the task to be updated in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in searched tasks list.
	 * @return the task at postionIndexLocal position
	 */
	private Task findTaskFromSearchedTasks(int positionIndexLocal) throws IndexOutOfBoundsException{
		ArrayList<Task> taskList = state.getSearchResultTasks();

		if(positionIndexLocal >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeUpdated = taskList.get(positionIndexLocal);
		return toBeUpdated;
	}
	
	/**
	 * find the task to be updated from all tasks list
	 * @param positionIndexLocal   position of the task to be updated in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in all tasks list.
	 * @return the task at postionIndexLocal position
	 */
	private Task findTaskFromAllTasks(int positionIndexLocal) throws IndexOutOfBoundsException{
		ArrayList<Task> taskList = state.getAllTasks();

		if(positionIndexLocal >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeUpdated = taskList.get(positionIndexLocal);
		return toBeUpdated;
	}

	
	/**
	 * find the task to be updated from deadline tasks list
	 * @param positionIndexLocal   position of the task to be updated in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in deadline tasks list.
	 * @return the task at postionIndexLocal position
	 */
	private Task findTaskFromDeadlineTasks(int positionIndexLocal) throws IndexOutOfBoundsException{
		ArrayList<Task> taskList = state.getDeadlineTasks();

		if(positionIndexLocal >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeUpdated = taskList.get(positionIndexLocal);
		return toBeUpdated;
	}
	
	
	/**
	 * find the task to be updated from floating tasks list
	 * @param positionIndexLocal   position of the task to be updated in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is > max index in floating tasks list.
	 * @return the task at postionIndexLocal position
	 */
	private Task findTaskFromFloatingTasks(int positionIndexLocal) throws IndexOutOfBoundsException{
		ArrayList<Task> taskList = state.getFloatingTasks();

		if(positionIndexLocal >= taskList.size()){
			throw new IndexOutOfBoundsException();	
		}

		Task toBeUpdated = taskList.get(positionIndexLocal);
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
