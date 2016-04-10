package logic;

import java.util.ArrayList;
import common.State;
import common.Task;
import common.ViewMode;

public class ChangeViewMode implements Operation {
	//============================
	//       Attributes
	//============================
	private State state;

	
	//====================================
	//       Constructor and Initiliser
	//====================================
	
	public ChangeViewMode(State state) {
		this.state = state;
	}
	
	/**
	 * Perform change of viewMode
	 *
	 * @return     boolean to indication whether the addition is successful
	 * collapse all tasks when changing view mode for better visual appearance
	 */
	@Override
	public boolean process() {
		ViewMode newViewMode = state.getNewViewMode();
		state.setViewMode(newViewMode);

		//Collapse allArrayList<Task> when switching view mode
		collapseAllTasks();
		
		return true;
	}

	/**
	 * change the isDetailDisplayed attribute of every task to false 
	 * to collapse all tasks
	 */
	private void collapseAllTasks() {
		ArrayList<Task> allTasks = state.getAllTasks();
		for(int i = 0; i < allTasks.size(); i++){
			Task another = allTasks.get(i);
			another.setIsDetailDisplayed(false);
		}
	}

}
