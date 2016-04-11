package logic;


import java.util.ArrayList;
import java.util.Collections;

import common.Constant;
import common.State;
import common.Task;
import common.TaskComparators;
import common.TaskType;
import common.ViewMode;

public class UntickTask implements Operation {
	//============================
	//       Attributes
	//============================
	private State state;

	
	//====================================
	//       Constructor and Initiliser
	//====================================
	
	public UntickTask(State state) {
		this.state = state;
	}
	
	
	/**
	 * Perform unticking of tasks
	 * If the index are out of bound or tick not under finished mode
	 * return false
	 * 
	 * @return  boolean to indicate whether the ticking is successful
	 */
	@Override
	public boolean process() {
		try {
			ViewMode viewMode = state.getViewMode();
			
			if(viewMode != ViewMode.FINISHED){
				state.setDisplayMessage(Constant.MESSAGE_UNTICK_IN_WRONG_MODE);
				return false;
			} else {
				int localPositionIndex = getAndValidatePositionIndex();
				Task taskToBeUnticked = state.getFinishedTasks().get(localPositionIndex);
				TaskType taskType = taskToBeUnticked.getTaskType();
				
				if(taskType == TaskType.DEADLINE){
					addDeadlineTask(taskToBeUnticked);
				}
				
				if(taskType == TaskType.FLOATING){
					addFloatingTask(taskToBeUnticked);
				}

			}
		} catch (IndexOutOfBoundsException e){
			untickWithInvalidPositionIndex();
			return false;
		}
		return false;
	}
	
	/**
	 * untick tasks with invalid position index
	 * (smaller than 0 or larger than max index)
	 * set error message 
	 */
	private void untickWithInvalidPositionIndex() {
		state.setDisplayMessage(Constant.MESSAGE_INDEX_OUT_OF_BOUND);
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
	 * add a deadline task
	 * @param newTask
	 */
	private void addDeadlineTask(Task newTask) {
		addToDeadlineList(newTask);
		addToAllList(newTask);
		setCurrentModeTo(ViewMode.DEADLINE);
		updatePositionIndex(newTask, TaskType.DEADLINE);
	}

	/**
	 * add a floating task
	 * @param newTask
	 */
	private void addFloatingTask(Task newTask) {
		addToFloatingList(newTask);
		addToAllList(newTask);
		setCurrentModeTo(ViewMode.FLOATING);		
		updatePositionIndex(newTask, TaskType.FLOATING);
	}

	
	/**
	 * add new task to deadline task list in state
	 * @param newTask
	 */
	private void addToDeadlineList(Task newTask) {
		ArrayList<Task> taskList = state.getDeadlineTasks();
		taskList.add(newTask);	
		Collections.sort(taskList, TaskComparators.compareByEndDate);
	}

	/**
	 * add new task to floating task list in state
	 * @param newTask
	 */
	private void addToFloatingList(Task newTask) {
		ArrayList<Task> taskList = state.getFloatingTasks();
		taskList.add(newTask);
		Collections.sort(taskList, TaskComparators.compareByCreationDate);
	}

	
	/**
	 * add new task to all task list in state
	 * @param newTask
	 */
	private void addToAllList(Task newTask) {
		ArrayList<Task> allTasks = state.getAllTasks();
		allTasks.add(newTask);
		Collections.sort(allTasks, TaskComparators.compareByCreationDate);
	}
	
	/**
	 * update the positionIndex in the state to the newly added task
	 * @param newTask newly added task
	 * @param taskType type of newly added task
	 * only newTask of type DEADLINE or FLOATING are supposed to invoke this method
	 */
	private void updatePositionIndex(Task newTask, TaskType taskType) {
		if(taskType == TaskType.DEADLINE){
			int indexOfNewTask = state.getDeadlineTasks().indexOf(newTask);
			state.setPositionIndex(indexOfNewTask);
		}
		
		if(taskType == TaskType.FLOATING){
			int indexOfNewTask = state.getFloatingTasks().indexOf(newTask);
			state.setPositionIndex(indexOfNewTask);
		}
	}
	

	/**
	 * switch the view mode to viewMode
	 * @param viewMode new view mode to be displayed
	 */
	private void setCurrentModeTo(ViewMode viewMode) {
		state.setViewMode(viewMode);
		collapseAllTasks();
	}


	/**
	 * change the isDetailDisplayed attribute of every task to false 
	 * to collapse all tasks
	 */
	private void collapseAllTasks() {
		ArrayList<Task> allTasks = state.getAllTasks();
		for(int i = 0; i < allTasks.size(); i++){
			Task another = allTasks.get(i);
			another.setIsDetailDisplayed(false);
		}
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
