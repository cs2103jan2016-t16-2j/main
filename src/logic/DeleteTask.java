package logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import common.State;
import common.Task;

public class DeleteTask implements Operation {
	private State state;

	public DeleteTask(State state) {
		this.state = state;
	}
	@Override
	public boolean process() {
		try {
			int position = Integer.parseInt(state.getContent());
			TreeSet<Task> taskList = state.getNormalTasks();
			
			if(position > taskList.size()){
				throw new IndexOutOfBoundsException();
			}
			
			Iterator<Task> itr;
			itr = taskList.iterator();
			for(int i = 1; i< position; i++){
				itr.next();
			}
			Task toBeDeleted = itr.next();
			taskList.remove(toBeDeleted);

			return true;
		} catch (IndexOutOfBoundsException e) {
			//logging
			return false;
		}
	}

}
