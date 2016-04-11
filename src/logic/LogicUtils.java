//@@author A0107354L
package logic;

import java.util.ArrayList;

import common.State;
import common.Task;

public class LogicUtils {
	
	private State state;
	
	public LogicUtils(State state) {
		this.state = state;
	}
	
	
	/**
	 * change the isDetailDisplayed attribute of every task to false 
	 * to collapse all tasks
	 */
	public void collapseAllTasks() {
		ArrayList<Task> allTasks = state.getAllTasks();
		for(int i = 0; i < allTasks.size(); i++){
			Task another = allTasks.get(i);
			another.setIsDetailDisplayed(false);
		}
		
		ArrayList<Task> finishedTasks = state.getFinishedTasks();
		for(int i = 0; i < finishedTasks.size(); i++){
			Task another = finishedTasks.get(i);
			another.setIsDetailDisplayed(false);
		}
	}
	
	/**
	 * check and return the index of task to be ticked 
	 * @return position of the task to be deleted in zero based index
	 * @throws IndexOutOfBoundsException  If positionIndexLocal is < 0.
	 */
	public int getAndValidatePositionIndex() {
		int positionIndex = state.getPositionIndex();

		// Convert 1 base index to 0 base index
		int localPositionIndex = fromOneBaseToZeroBase(positionIndex);

		if(localPositionIndex < 0){
			throw new IndexOutOfBoundsException();
		}
		return localPositionIndex;
	}
	
	/**
	 * return a number that is 1 less than the input
	 * @param num   number to be subtracted
	 * @return subtracted number that is 1 less than the input
	 */
	private int fromOneBaseToZeroBase(int num) {
		int newNew = num - 1;
		return newNew;
	}
}
