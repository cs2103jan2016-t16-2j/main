package logic;

import java.util.ArrayList;
import java.util.Collections;

import common.State;
import common.Task;
import common.TaskComparators;
import common.TaskType;
import common.ViewMode;

public class AddTask implements Operation {
	
	private State state;

	public AddTask(State state) {
		this.state = state;
	}

	@Override
	public boolean process() {
		collapseAllTasks();
		Task newTask = new Task(state);
		TaskType taskType = newTask.getTaskType();
		
		switch (taskType) { 
			case FLOATING:
				addToFloatingList(newTask);
				addToAllList(newTask);
				setCurrentModeToFloating();
				break;
			case DEADLINE:
				addToDeadlineList(newTask);
				addToAllList(newTask);
				setCurrentModeToDeadline();
				break;
			case UNDEFINED:
				return false;
			default:
				return true;
		}
		return true;
	}

	/**
	 * 
	 */
	private void setCurrentModeToDeadline() {
		state.setViewMode(ViewMode.DEADLINE);
	}

	/**
	 * @param newTask
	 */
	private void addToDeadlineList(Task newTask) {
		ArrayList<Task> taskList = state.getDeadlineTasks();
		taskList.add(newTask);	
		Collections.sort(taskList, TaskComparators.compareByEndDate);
		int indexOfNewTask = state.getDeadlineTasks().indexOf(newTask);
		state.setPositionIndex(indexOfNewTask);
	}

	/**
	 * 
	 */
	private void setCurrentModeToFloating() {
		state.setViewMode(ViewMode.FLOATING);
	}

	/**
	 * @param newTask
	 */
	private void addToAllList(Task newTask) {
		ArrayList<Task> allTasks = state.getAllTasks();
		allTasks.add(newTask);
		Collections.sort(allTasks, TaskComparators.compareByCreationDate);
		int indexOfNewTask = state.getAllTasks().indexOf(newTask);
		state.setPositionIndex(indexOfNewTask);
	}

	/**
	 * @param newTask
	 */
	private void addToFloatingList(Task newTask) {
		ArrayList<Task> taskList = state.getFloatingTasks();
		taskList.add(newTask);
		Collections.sort(taskList, TaskComparators.compareByCreationDate);
		int indexOfNewTask = state.getFloatingTasks().indexOf(newTask);
		state.setPositionIndex(indexOfNewTask);
	}

	private void collapseAllTasks() {
		ArrayList<Task> allTasks = state.getAllTasks();
		for(int i = 0; i < allTasks.size(); i++){
			Task another = allTasks.get(i);
			another.setIsDetailDisplayed(false);
		}
	}
}
