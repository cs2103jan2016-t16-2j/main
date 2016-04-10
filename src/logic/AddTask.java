package logic;

import java.util.ArrayList;
import java.util.Collections;

import common.State;
import common.Task;
import common.TaskComparators;
import common.TaskType;
import common.ViewMode;

public class AddTask implements Operation {
	
	//============================
	//       Attributes
	//============================
	private State state;

	
	//====================================
	//       Constructor and Initiliser
	//====================================
	
	public AddTask(State state) {
		this.state = state;
	}

	/**
	 * Perform addition of new task
	 * If the taskType is undefined, return false.
	 *
	 * @return     boolean to indicate whether the addition is successful
	 */
	@Override
	public boolean process() {
		collapseAllTasks();
		Task newTask = new Task(state);
		TaskType taskType = newTask.getTaskType();
		
		switch (taskType) { 
			case FLOATING:
				addFloatingTask(newTask);
				return true;
			case DEADLINE:
				addDeadlineTask(newTask);
				return true;
			case UNDEFINED:
				return false;
			default:
				return true;
		}
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
}
