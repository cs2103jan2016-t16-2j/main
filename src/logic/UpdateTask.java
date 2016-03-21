package logic;

import java.util.Iterator;
import java.util.TreeSet;

import common.State;
import common.Task;

public class UpdateTask implements Operation {
	private State state;

	public UpdateTask(State state) {
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
			Task toBeUpdated = itr.next();
			String newContent = state.getContent();
			toBeUpdated.setContent(newContent);
			return true;
		} catch (IndexOutOfBoundsException e) {
			//logging
			return false;
		}
	}

}
