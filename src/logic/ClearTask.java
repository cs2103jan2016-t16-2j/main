package logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import common.DisplayMessage;
import common.State;
import common.Task;
import common.TaskType;

public class ClearTask implements Operation {
	private State state;

	public ClearTask(State state) {
		this.state = state;
	}

	@Override
	public boolean process() {
		try {
			TaskType type = state.getTaskType();
			if(type.equals(TaskType.DEADLINE)){
				TreeSet<Task> taskList = state.getNormalTasks();
				taskList.clear();
				return true;
			} else if(type.equals(TaskType.FLOATING)){
				ArrayList<Task> taskList = state.getFloatingTasks();
				taskList.clear();
				return true;
			} else{
				TreeSet<Task> normalTaskList = state.getNormalTasks();
				normalTaskList.clear();
				ArrayList<Task> floatingTaskList = state.getFloatingTasks();
				floatingTaskList.clear();
				return true;
			}
		} catch (Exception e) {
			//logging
			state.setErrorMessage(DisplayMessage.MESSAGE_DUMMY);
			return false;
		}
	}

}
