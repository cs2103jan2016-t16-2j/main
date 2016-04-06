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
		
		for (String key: keysToSearch) {
			searchWord(key, searchedTasks);
		}
		state.setViewMode(ViewMode.SEARCH);
		return true;
	}

	private void searchWord(String keyToSearch, ArrayList<Task> searchedTasks) {
		ArrayList<Task> allTasks = state.getAllTasks();
		for(int i = 0; i < allTasks.size(); i++){
			Task another = allTasks.get(i);
			if(another.concatString().contains(keyToSearch)){
				searchedTasks.add(another);
			}
		}
	}

}
