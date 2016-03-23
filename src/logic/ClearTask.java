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
			} else {
				ArrayList<Task> taskList = state.getFloatingTasks();
				taskList.clear();
				return true;
			}
		} catch (Exception e) {
			//logging
			state.setMessage(DisplayMessage.MESSAGE_DUMMY);
			return false;
		}
	}

}
