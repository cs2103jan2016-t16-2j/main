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
		ArrayList<String> keyToSearch = state.getSearchKey();
		
		ArrayList<Task> searchedTasks = state.getSearchResultTasks();
		searchedTasks.clear();
	
		searchTasks(keyToSearch, searchedTasks);
		
		return true;
	}

	private void searchTasks(ArrayList<String> keyToSearch, ArrayList<Task> searchedTasks) {
		for(int i = 0; i < keyToSearch.size(); i++){
			String key = keyToSearch.get(i);
			searchWord(key, searchedTasks);
		}
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
