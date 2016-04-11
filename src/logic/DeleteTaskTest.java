package logic;

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
import model.WallistModel;

public class DeleteTaskTest {

	@Test
	public void test() {
		WallistModel wm = new WallistModel();
		State state = wm.getState();
		DeleteTask deleteTask = wm.getDeleteTask();
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
		createDeadlineTaskState(state, startTest, endTestThird, addTask, "D");
		
		ArrayList<Task> deadlineTasks = state.getDeadlineTasks();
		assertEquals(endTestFirst, deadlineTasks.get(0).getEndDate());
		assertEquals(endTestSecond, deadlineTasks.get(1).getEndDate());
		assertEquals(endTestThird, deadlineTasks.get(2).getEndDate());

		
		state.setViewMode(ViewMode.DEADLINE);
		state.setPositionIndex(1);
		boolean isDeleteSuccessful = deleteTask.process();
		assertEquals(true, isDeleteSuccessful);
		assertEquals(endTestSecond, deadlineTasks.get(0).getEndDate());
		assertEquals(endTestThird, deadlineTasks.get(1).getEndDate());

	}
	
	private void createDeadlineTaskState(State state, Date start, Date end, AddTask addTask, String content) {
		state.setIsValid(true);
		state.setIsContentChanged(true);
		state.setContent(content);
		state.setIsVenueChanged(true);
		state.setVenue("testingVenue");
		state.setIsDetailChanged(true);
		state.setDetail("testingDetail");
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
