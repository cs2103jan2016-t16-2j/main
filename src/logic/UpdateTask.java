package logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import common.DisplayMessage;
import common.State;
import common.Task;
import common.TaskType;

public class UpdateTask implements Operation {
	private State state;

	public UpdateTask(State state) {
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
				Task toBeUpdated = itr.next();
				String newContent = state.getContent();
				toBeUpdated.setContent(newContent);
				
				return true;
			}
			
			if(type.equals(TaskType.FLOATING)){
				ArrayList<Task> taskList = state.getFloatingTasks();
				
				if(position > taskList.size()){
					throw new IndexOutOfBoundsException();
				}
				
				Task toBeUpdated = taskList.get(position - 1);
				String newContent = state.getContent();
				toBeUpdated.setContent(newContent);
				
				return true;				
				
			}

			return true;
		} catch (IndexOutOfBoundsException e) {
			//logging
			state.setMessage(DisplayMessage.MESSAGE_INDEX_OUT_OF_BOUND);
			return false;
		}
	}

}
