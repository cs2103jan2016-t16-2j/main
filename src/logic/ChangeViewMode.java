//@@author A0107354L
package logic;

import common.Constant;
import common.State;
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
	 * @return     boolean to indicate whether the addition is successful
	 * collapse all tasks when changing view mode for better visual appearance
	 */
	@Override
	public boolean process() {
		ViewMode newViewMode = state.getNewViewMode();
		
		//View Search mode is meaningless
		if(newViewMode == ViewMode.SEARCH){
			state.setDisplayMessage(Constant.MESSAGE_CANNOT_VIEW_SEARCH);
			return false;
		}
		
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
		LogicUtils logicUtils = new LogicUtils(state);
		logicUtils.collapseAllTasks();
	}

}
