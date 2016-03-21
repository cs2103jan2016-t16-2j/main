package logic;

import java.util.ArrayList;
import java.util.TreeSet;

import common.CommandType;
import common.State;
import common.Task;

public class AddTask implements Operation {
	private State state;

	public AddTask(State state) {
		this.state = state;
	}

	@Override
	public boolean process() {
		try {
			Task newTask = new Task(state);
			boolean isFloating = newTask.getIsFloating();
			if(isFloating){
				TreeSet<Task> taskList = state.getNormalTasks();
				taskList.add(newTask);
			} else {
				ArrayList<Task> taskList = state.getFloatingTasks();
				taskList.add(newTask);
			}
			return true;
		} catch (Exception e){
			return false;
		}
	}

}
