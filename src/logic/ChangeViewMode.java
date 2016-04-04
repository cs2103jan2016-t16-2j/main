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
		
		//Collape alArrayList<E> when switching view mode
		ArrayList<Task> allTasks = state.getAllTasks();
		for(int i = 0; i < allTasks.size(); i++){
			Task another = allTasks.get(i);
			another.setIsDetailDisplayed(false);
		}
		return true;
	}

}
