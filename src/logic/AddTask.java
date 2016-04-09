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
			TaskType tasktype = newTask.getTaskType();
			ViewMode viewMode = state.getViewMode();
			
			collapseAllTasks();
			
			//Add floating task to both floating task list and all task list
			if(tasktype == TaskType.FLOATING){
				addToFloatingList(newTask);
				addToAllTasksList(newTask);		
			}

			//Add deadline task to both deadline task list and all task list
			if(tasktype == TaskType.DEADLINE){
				addToDeadlineTaskList(newTask);
				addToAllTasksList(newTask);		
			}
		
			//update position index for GUI to highlight new task
			//Under Floating view mode
			if(viewMode == ViewMode.FLOATING){
				updateIndexUnderFloatingMode(newTask);				
			}
			
			//under deadline view mode
			if(viewMode == ViewMode.DEADLINE){
				updateIndexUnderDeadlineMode(newTask);
			}

			//under other view mode(including all view mode), 
			//the view mode will be switched to all view mode
			if(viewMode != ViewMode.FLOATING && viewMode != ViewMode.DEADLINE){
				state.setViewMode(ViewMode.ALL);
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
