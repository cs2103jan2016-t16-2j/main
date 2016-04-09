package logic;

import java.util.ArrayList;
import common.State;
import common.Task;
import common.ViewMode;

public class SearchTasks implements Operation {

	private State state;

	public SearchTasks(State state) {
		this.state = state;
	}

	@Override
	public boolean process() {

		ArrayList<String> keysToSearch = state.getSearchKey();
		
		ArrayList<Task> searchedTasks = state.getSearchResultTasks();
		searchedTasks.clear();

		for (int i = 0 ; i < keysToSearch.size(); i++) {
			String key = keysToSearch.get(i);
			searchWord(key, searchedTasks);
		}
		
		state.setViewMode(ViewMode.SEARCH);
		collapseAllTasks();

		return true;
	}

	private void searchWord(String keyToSearch, ArrayList<Task> searchedTasks) {
		ArrayList<Task> allTasks = state.getAllTasks();

		for(int i = 0; i < allTasks.size(); i++){
			Task task = allTasks.get(i);
			if(task.concatString().contains(keyToSearch) && !searchedTasks.contains(task)){
				searchedTasks.add(task);
			}
		}
	}
	
	private void collapseAllTasks() {
		ArrayList<Task> allTasks = state.getAllTasks();
		for(int i = 0; i < allTasks.size(); i++){
			Task task = allTasks.get(i);
			task.setIsDetailDisplayed(false);
		}
	}

}
