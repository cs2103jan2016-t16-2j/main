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
		
		//searching deadline tasks
		TreeSet<Task> normalTaskList = state.getNormalTasks();
		Iterator<Task> itr;
		itr = normalTaskList.iterator();
		while(itr.hasNext()){
			Task another = itr.next();
			if(another.toString().contains(keyToSearch)){
				searchedTasks.add(another);
			}
		}
	
		// searching floating tasks
		ArrayList<Task> floatingTaskList = state.getFloatingTasks();
		for(int i = 0; i < floatingTaskList.size(); i++){
			Task another = floatingTaskList.get(i);
			if(another.toString().contains(keyToSearch)){
				searchedTasks.add(another);
			}
		}
		
		return true;
	}

}
