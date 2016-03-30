package logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import common.DisplayMessage;
import common.State;
import common.Task;
import common.TaskType;

public class SearchTasks implements Operation {

	private State state;

	public SearchTasks(State state) {
		this.state = state;
	}

	@Override
	public boolean process() {
		String keyToSearch = state.getSearchKey();
		ArrayList<Task> searchedTasks = state.getSearchResultTasks();
		
		searchedTasks.clear();
		//searching deadline tasks
		searchNormalTasks(keyToSearch, searchedTasks);
	
		// searching floating tasks
		searchFloatingTasks(keyToSearch, searchedTasks);
		
		state.setSearchResultTasks(searchedTasks); 
		return true;
	}

	private void searchFloatingTasks(String keyToSearch, ArrayList<Task> searchedTasks) {
		ArrayList<Task> floatingTaskList = state.getFloatingTasks();
		for(int i = 0; i < floatingTaskList.size(); i++){
			Task another = floatingTaskList.get(i);
			if(another.toString().contains(keyToSearch)){
				searchedTasks.add(another);
			}
		}
	}

	private void searchNormalTasks(String keyToSearch, ArrayList<Task> searchedTasks) {
		TreeSet<Task> normalTaskList = state.getNormalTasks();
		Iterator<Task> itr;
		itr = normalTaskList.iterator();
		while(itr.hasNext()){
			Task another = itr.next();
			if(another.toString().contains(keyToSearch)){
				searchedTasks.add(another);
			}
		}
	}

}
