package logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import common.DisplayMessage;
import common.State;
import common.Task;
import common.TaskType;

public class DeleteTask implements Operation {
	private State state;

	public DeleteTask(State state) {
		this.state = state;
	}
	@Override
	public boolean process() {
		try {
			int position = state.getPosition();
			TaskType type = state.getTaskType();
			if(type.equals(TaskType.DEADLINE)){
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
			}
			
			if(type.equals(TaskType.FLOATING)){
				ArrayList<Task> taskList = state.getFloatingTasks();
				
				if(position > taskList.size()){
					throw new IndexOutOfBoundsException();
				}
				
				taskList.remove(position - 1);
				return true;
			}
			
			return true;
		} catch (IndexOutOfBoundsException e) {
			//logging
			state.setErrorMessage(DisplayMessage.MESSAGE_INDEX_OUT_OF_BOUND);
			return false;
		}
	}

}
