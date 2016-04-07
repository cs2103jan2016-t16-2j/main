package logic;

import java.util.ArrayList;
import java.util.Collections;

import common.Constant;
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
		try {
			Task newTask = new Task(state);
			ViewMode viewMode = state.getViewMode();
			
			collapseAllTasks();
			
			//If the viewMode is Floating, update both floating tasks and all tasks
			if(viewMode == ViewMode.FLOATING){
				addToFloatingList(newTask);
				addToAllTasksList(newTask);		
				updateIndexUnderFloatingMode(newTask);
			}

			//If the viewMode is Deadline, update both deadline tasks and all tasks
			if(viewMode == ViewMode.DEADLINE){
				addToDeadlineTaskList(newTask);
				addToAllTasksList(newTask);		
				updateIndexUnderDeadlineMode(newTask);
			}

			//If the viewMode is all, update all tasks
			//Depending on the taskType, the newTasks will also be added to corresponding task list
			if(viewMode == ViewMode.ALL || viewMode == ViewMode.SEARCH){
				addToAllTasksList(newTask);
				TaskType type = newTask.getTaskType();
				if(type.equals(TaskType.FLOATING)){
					addToFloatingList(newTask);	
				} else {
					addToDeadlineTaskList(newTask);				
				}
				updateIndexUnderAllMode(newTask);
			}

			return true;
		} catch (Exception e){
			state.setDisplayMessage(Constant.MESSAGE_DUMMY);
			return false;
		}
	}

	private void collapseAllTasks() {
		ArrayList<Task> allTasks = state.getAllTasks();
		for(int i = 0; i < allTasks.size(); i++){
			Task another = allTasks.get(i);
			another.setIsDetailDisplayed(false);
		}
	}

	private void updateIndexUnderAllMode(Task newTask) {
		int indexOfNewTask = state.getAllTasks().indexOf(newTask);
		state.setPositionIndex(indexOfNewTask);
	}

	private void updateIndexUnderDeadlineMode(Task newTask) {
		int indexOfNewTask = state.getDeadlineTasks().indexOf(newTask);
		state.setPositionIndex(indexOfNewTask);
	}

	private void updateIndexUnderFloatingMode(Task newTask) {
		int indexOfNewTask = state.getFloatingTasks().indexOf(newTask);
		state.setPositionIndex(indexOfNewTask);
	}

	private void addToDeadlineTaskList(Task newTask) {
		ArrayList<Task> taskList = state.getDeadlineTasks();
		taskList.add(newTask);	
		Collections.sort(taskList, TaskComparators.compareByEndDate);
	}

	private void addToAllTasksList(Task newTask) {
		ArrayList<Task> allTasks = state.getAllTasks();
		allTasks.add(newTask);
		Collections.sort(allTasks, TaskComparators.compareByCreationDate);
	}

	private void addToFloatingList(Task newTask) {
		ArrayList<Task> taskList = state.getFloatingTasks();
		taskList.add(newTask);
		Collections.sort(taskList, TaskComparators.compareByCreationDate);
	}

}
