//@@author A0107354L
package testing;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import common.State;
import common.Task;
import common.TaskType;
import common.ViewMode;
import logic.AddTask;
import model.WallistModel;

public class AddTaskTest {

	@Test
	public void test() {
		WallistModel wm = new WallistModel();
		State state = wm.getState();
		AddTask addTask = wm.getAddTask();

		// clear the state before testing
	    clearState(state);
		Date startTest = stringToDate("18 MAR 6 00:00");
		Date endTestSecond = stringToDate("19 MAR 6 00:00");
		Date endTestFirst = stringToDate("17 MAR 6 00:00");
		Date endTestThird = stringToDate("20 MAR 6 00:00");
	    	    	    
	    //successful cases:
	    //add a deadline task and test
		createDeadlineTaskState(state, startTest, endTestSecond, addTask, "A");
		testingDeadlineTask(state, startTest, endTestSecond, "A");
        
		//add a floating task and test
		createFloatingTaskState(state, startTest, addTask, "B");
		testFloatingTask(state, startTest, "B");
		

		//more testing on ordering of deadline task
		/*
		 * The correct list of deadline tasklist should be : (content , endDate)
		 * C 17 MAR 6 00:00
		 * A 19 MAR 6 00:00
		 * D 20 MAR 6 00:00
		 * Testing focus on the first and last instantce
		 */
		createDeadlineTaskState(state, startTest, endTestFirst, addTask, "C");
		createDeadlineTaskState(state, startTest, endTestThird, addTask, "D");
		testDeadlineTaskOrdering(state, startTest,endTestFirst, endTestThird, "C", "D");

	}

	private void testDeadlineTaskOrdering(State state, Date start, Date earliest, Date latest, 
										  String earliestContent, String LastestContent){
		// check the all task list
		Task newTask = state.getDeadlineTasks().get(0);
		assertEquals(earliestContent, newTask.getContent());
		assertEquals("testingVenue", newTask.getVenue());
		assertEquals("testingDetail", newTask.getDetail());
		assertEquals(TaskType.DEADLINE, newTask.getTaskType());
		assertEquals(start, newTask.getStartDate());
		assertEquals(earliest, newTask.getEndDate());

		// check floating task list
		Task newTask2 = state.getDeadlineTasks().get(state.getDeadlineTasks().size() - 1);
		assertEquals(LastestContent, newTask2.getContent());
		assertEquals("testingVenue", newTask2.getVenue());
		assertEquals("testingDetail", newTask2.getDetail());
		assertEquals(TaskType.DEADLINE, newTask2.getTaskType());
		assertEquals(start, newTask2.getStartDate());
		assertEquals(latest, newTask2.getEndDate());

	}
	private void pause(int timeInterval) {
		try {
			Thread.sleep(timeInterval);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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


	
	private void testFloatingTask(State state, Date start, String content) {

		// check the all task list
		Task newTask = state.getAllTasks().get(0);
		assertEquals(content, newTask.getContent());
		assertEquals("testingVenue", newTask.getVenue());
		assertEquals("testingDetail", newTask.getDetail());
		assertEquals(TaskType.FLOATING, newTask.getTaskType());
		assertEquals(start, newTask.getStartDate());
		assertEquals(null, newTask.getEndDate());

		// check floating task list
		Task newTask2 = state.getFloatingTasks().get(0);
		assertEquals(content, newTask2.getContent());
		assertEquals("testingVenue", newTask2.getVenue());
		assertEquals("testingDetail", newTask2.getDetail());
		assertEquals(TaskType.FLOATING, newTask2.getTaskType());
		assertEquals(start, newTask2.getStartDate());
		assertEquals(null, newTask2.getEndDate());

	}

	private void createFloatingTaskState(State state, Date start, AddTask addTask, String content) {
		state.setIsValid(true);
		state.setIsContentChanged(true);
		state.setContent(content);
		state.setIsVenueChanged(true);
		state.setVenue("testingVenue");
		state.setIsDetailChanged(true);
		state.setDetail("testingDetail");
		state.setTaskType(TaskType.FLOATING);
		state.setIsStartDateChanged(true);
		state.setStartDate(start);

		state.setIsEndDateChanged(false);
		state.setEndDate(null);
		
		boolean isSuccessful = addTask.process();
		assertEquals(true, isSuccessful);
		assertEquals(ViewMode.FLOATING, state.getViewMode());			
		// pause to ensure correct ordering by creation time
		pause(100);
	}

	private void testingDeadlineTask(State state,Date start, Date end, String content) {
		// check from all task list
		Task newTask = state.getAllTasks().get(0);
		assertEquals(content, newTask.getContent());
		assertEquals("testingVenue", newTask.getVenue());
		assertEquals("testingDetail", newTask.getDetail());
		assertEquals(TaskType.DEADLINE, newTask.getTaskType());
		assertEquals(start, newTask.getStartDate());
		assertEquals(end, newTask.getEndDate());

		// check floating task list
		Task newTask2 = state.getDeadlineTasks().get(0);
		assertEquals(content, newTask2.getContent());
		assertEquals("testingVenue", newTask2.getVenue());
		assertEquals("testingDetail", newTask2.getDetail());
		assertEquals(TaskType.DEADLINE, newTask2.getTaskType());
		assertEquals(start, newTask2.getStartDate());
		assertEquals(end, newTask2.getEndDate());

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

}
