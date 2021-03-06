//@@author A0107354L
package testing;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import common.State;
import common.Task;
import common.TaskType;
import common.ViewMode;
import logic.AddTask;
import logic.UpdateTask;
import model.WallistModel;

public class UpdateTaskTest {

	@Test
	public void test() {
		WallistModel wm = new WallistModel();
		State state = wm.getState();
		UpdateTask updateTask = wm.getUpdateTask();
		AddTask addTask = wm.getAddTask();
		clearState(state);
		
		Date startTest = stringToDate("18 MAR 6 00:00");
		Date endTestSecond = stringToDate("19 MAR 6 00:00");
		Date endTestFirst = stringToDate("17 MAR 6 00:00");
		Date endTestThird = stringToDate("20 MAR 6 00:00");
		
		
		//more testing on ordering of deadline task
		/*
		 * The correct list of deadline tasklist should be : (content , endDate)
		 * C 17 MAR 6 00:00
		 * A 19 MAR 6 00:00
		 * D 20 MAR 6 00:00
		 * Testing focus on the first and last instantce
		 */
		createDeadlineTaskState(state, startTest, endTestSecond, addTask, "A");
		createDeadlineTaskState(state, startTest, endTestFirst, addTask, "C");
		createDeadlineTaskState(state, startTest, endTestThird, addTask, "unique");
		
		ArrayList<Task> deadlineTasks = state.getDeadlineTasks();
		assertEquals(endTestFirst, deadlineTasks.get(0).getEndDate());
		assertEquals(endTestSecond, deadlineTasks.get(1).getEndDate());
		assertEquals(endTestThird, deadlineTasks.get(2).getEndDate());
		
		// update second task
		state.setViewMode(ViewMode.DEADLINE);
		state.setPositionIndex(2);
		state.setIsContentChanged(true);
		state.setContent("newContent");
		state.setIsEndDateChanged(false);
		state.setIsDetailChanged(false);
		state.setIsVenueChanged(false);
		state.setIsStartDateChanged(false);
		boolean isUpdateSuccessful = updateTask.process();
		assertEquals(true, isUpdateSuccessful);

		assertEquals(endTestFirst, deadlineTasks.get(0).getEndDate());
		assertEquals(endTestSecond, deadlineTasks.get(1).getEndDate());
		assertEquals(endTestThird, deadlineTasks.get(2).getEndDate());
		
		assertEquals("newContent", deadlineTasks.get(1).getContent());

	}
	

	private void createDeadlineTaskState(State state, Date start, Date end, AddTask addTask, String content) {
		state.setIsValid(true);
		state.setIsContentChanged(true);
		state.setContent(content);
		state.setIsVenueChanged(true);
		state.setVenue("");
		state.setIsDetailChanged(true);
		state.setDetail("");
		state.setTaskType(TaskType.DEADLINE);
		state.setIsStartDateChanged(true);
		state.setStartDate(start);
		state.setIsEndDateChanged(true);
		state.setEndDate(end);

		boolean isSuccessful = addTask.process();
		assertEquals(true, isSuccessful);
		assertEquals(ViewMode.DEADLINE, state.getViewMode());			
		// pause to ensure correct ordering by creation time
		pause(100);

	}
	
	private void clearState(State state) {
		//Storage storage = new Storage(state);
	    //state.setNewDirectory("/Users/Boxin_Yang/testing");
	    //storage.executeChangeDirectory();
	    state.getAllTasks().clear();
	    state.getDeadlineTasks().clear();
	    state.getFloatingTasks().clear();
	    state.getFinishedTasks().clear();
	    state.getSearchResultTasks().clear();
	}
	
	private Date stringToDate(String str){
		try {
		    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM y HH:mm");
			Date date;
			date = sdf.parse(str);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private void pause(int timeInterval) {
		try {
			Thread.sleep(timeInterval);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
