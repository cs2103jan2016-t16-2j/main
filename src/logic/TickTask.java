package logic;

import java.util.Iterator;
import java.util.TreeSet;

import common.State;
import common.Task;

public class TickTask implements Operation {
	private State state;

	public TickTask(State state) {
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
			Task toBeTicked = itr.next();
			toBeTicked.setIsFinished(true);

			return true;
		} catch (IndexOutOfBoundsException e) {
			//logging
			return false;
		}
	}

}
