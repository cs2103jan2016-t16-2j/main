package logic;

import java.util.ArrayList;
import common.State;
import common.Task;

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
			searchTasks(key, searchedTasks);
		}
		
		
		return true;
	}

	private void searchTasks(String keyToSearch, ArrayList<Task> searchedTasks) {
		ArrayList<Task> allTasks = state.getAllTasks();
		for(int i = 0; i < allTasks.size(); i++){
			Task another = allTasks.get(i);
			if(another.concatString().contains(keyToSearch)){
				searchedTasks.add(another);
			}
		}
	}

}
