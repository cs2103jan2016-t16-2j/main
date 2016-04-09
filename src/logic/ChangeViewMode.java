package logic;

import java.util.ArrayList;
import common.State;
import common.Task;
import common.ViewMode;

public class ChangeViewMode implements Operation {
	private State state;

	public ChangeViewMode(State state) {
		this.state = state;
	}

	@Override
	public boolean process() {
		ViewMode newViewMode = state.getNewViewMode();
		state.setViewMode(newViewMode);

		//Collapse allArrayList<Task> when switching view mode
		collapseAllTask();
		
		return true;
	}

	private void collapseAllTask() {
		ArrayList<Task> allTasks = state.getAllTasks();
		for(int i = 0; i < allTasks.size(); i++){
			Task another = allTasks.get(i);
			another.setIsDetailDisplayed(false);
		}
	}

}
