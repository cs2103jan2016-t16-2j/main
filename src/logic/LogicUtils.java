package logic;

import java.util.ArrayList;

import common.State;
import common.Task;

public class LogicUtils {
	
	private State state;
	
	public LogicUtils(State state) {
		this.state = state;
	}
	
	public void collapseAllTasks() {
		ArrayList<Task> allTasks = state.getAllTasks();
		for(int i = 0; i < allTasks.size(); i++){
			Task another = allTasks.get(i);
			another.setIsDetailDisplayed(false);
		}
	}
}
