package logic;

import java.util.ArrayList;
import common.State;
import common.Task;
import common.ViewMode;

public class SearchTasks implements Operation {
	//============================
	//       Attributes
	//============================
	private State state;

	
	//====================================
	//       Constructor and Initiliser
	//====================================
	
	public SearchTasks(State state) {
		this.state = state;
	}
	
	
	/**
	 * Perform searching of all tasks on a set of Strings
	 *
	 * @return  boolean to indicate whether the addition is successful
	 */
	@Override
	public boolean process() {

		ArrayList<String> keysToSearch = state.getSearchKey();
		
		ArrayList<Task> searchedTasks = state.getSearchResultTasks();
		searchedTasks.clear();

		for (int i = 0 ; i < keysToSearch.size(); i++) {
			String key = keysToSearch.get(i);
			searchWord(key.toLowerCase(), searchedTasks);
		}
		
		state.setViewMode(ViewMode.SEARCH);
		collapseAllTasks();

		return true;
	}

	
	/**
	 * search all tasks and add all tasks that contain the keyword to search
	 * @param keyToSearch     String object to be searched
	 * @param searchedTasks   ArrayList that contains all the tasks searched
	 * @return  boolean to indicate whether the addition is successful
	 */
	private void searchWord(String keyToSearch, ArrayList<Task> searchedTasks) {
		ArrayList<Task> allTasks = state.getAllTasks();

		for(int i = 0; i < allTasks.size(); i++){
			Task task = allTasks.get(i);
			if(task.concatString().contains(keyToSearch) && !searchedTasks.contains(task)){
				searchedTasks.add(task);
			}
		}
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
