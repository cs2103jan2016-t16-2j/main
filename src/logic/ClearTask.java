package logic;

import java.util.Iterator;
import java.util.TreeSet;

import common.State;
import common.Task;

public class ClearTask implements Operation {
	private State state;

	public ClearTask(State state) {
		this.state = state;
	}

	@Override
	public boolean process() {
		try {
			TreeSet<Task> taskList = state.getNormalTasks();
			taskList.clear();
			return true;
		} catch (Exception e) {
			//logging
			return false;
		}
	}

}
