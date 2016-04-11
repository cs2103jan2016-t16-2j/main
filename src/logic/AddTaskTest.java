package logic;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import common.CommandType;
import common.Font;
import common.State;
import common.Task;
import common.TaskType;
import common.Theme;
import common.ViewMode;
import model.WallistModel;
import storage.Storage;

public class AddTaskTest {

	@Test
	public void test() {
		WallistModel wm = new WallistModel();
		State state = wm.getState();
	    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM y HH:mm");

	    clearState(state);
	    
	    //add a deadline task and test
		createDeadlineTask(state, sdf);
		//testingDeadlineTask(state, sdf);
		
		//add a floating task and test
		createFloatingTask(state, sdf);
		//testFloatingTask(state, sdf);
		
		System.out.println(state.getAllTasks().size());
	}

	private void clearState(State state) {
		Storage storage = new Storage(state);
	    state.setNewDirectory("/Users/Boxin_Yang/testing");
	    //storage.executeChangeDirectory();
	    state.getAllTasks().clear();
	    state.getDeadlineTasks().clear();
	    state.getFloatingTasks().clear();
	    state.getFinishedTasks().clear();
	    state.getSearchResultTasks().clear();
	}

	private void testFloatingTask(State state, SimpleDateFormat sdf) {
		try {
			Date startTest = sdf.parse("18 MAR 6 00:00");
		
			AddTask addTest = new AddTask(state);
			boolean isSuccessful = addTest.process();
			assertEquals(true, isSuccessful);
			// check the all task list
			Task newTask = state.getAllTasks().get(1);
			assertEquals("testingContent", newTask.getContent());
			assertEquals("testingVenue", newTask.getVenue());
			assertEquals("testingDetail", newTask.getDetail());
			assertEquals(TaskType.FLOATING, newTask.getTaskType());
			assertEquals(startTest, newTask.getStartDate());
			assertEquals(null, newTask.getEndDate());

			// check floating task list
			Task newTask2 = state.getFloatingTasks().get(0);
			assertEquals("testingContent", newTask2.getContent());
			assertEquals("testingVenue", newTask2.getVenue());
			assertEquals("testingDetail", newTask2.getDetail());
			assertEquals(TaskType.FLOATING, newTask2.getTaskType());
			assertEquals(startTest, newTask2.getStartDate());
			assertEquals(null, newTask2.getEndDate());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createFloatingTask(State state, SimpleDateFormat sdf) {
		state.setIsValid(true);
		state.setIsContentChanged(true);
		state.setContent("testingContent");
		state.setIsVenueChanged(true);
		state.setVenue("testingVenue");
		state.setIsDetailChanged(true);
		state.setDetail("testingDetail");
		state.setTaskType(TaskType.FLOATING);
		state.setIsStartDateChanged(true);
		try {
			state.setStartDate(sdf.parse("18 MAR 6 00:00"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		state.setIsEndDateChanged(false);
		state.setEndDate(null);
	}

	private void testingDeadlineTask(State state, SimpleDateFormat sdf) {
		try {
			Date startTest = sdf.parse("18 MAR 6 00:00");
			Date endTest = sdf.parse("19 MAR 6 00:00");
		
			AddTask addTest = new AddTask(state);
			boolean isSuccessful = addTest.process();
			assertEquals(true, isSuccessful);
			
			// check from all task list
			Task newTask = state.getAllTasks().get(0);
			assertEquals("testingContent", newTask.getContent());
			assertEquals("testingVenue", newTask.getVenue());
			assertEquals("testingDetail", newTask.getDetail());
			assertEquals(TaskType.DEADLINE, newTask.getTaskType());
			assertEquals(startTest, newTask.getStartDate());
			assertEquals(endTest, newTask.getEndDate());
			
			// check floating task list
//			Task newTask2 = state.getDeadlineTasks().get(0);
//			assertEquals("testingContent", newTask2.getContent());
//			assertEquals("testingVenue", newTask2.getVenue());
//			assertEquals("testingDetail", newTask2.getDetail());
//			assertEquals(TaskType.FLOATING, newTask2.getTaskType());
//			assertEquals(startTest, newTask2.getStartDate());
//			assertEquals(endTest, newTask2.getEndDate());
//			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createDeadlineTask(State state, SimpleDateFormat sdf) {
		state.setIsValid(true);
		state.setIsContentChanged(true);
		state.setContent("testingContent");
		state.setIsVenueChanged(true);
		state.setVenue("testingVenue");
		state.setIsDetailChanged(true);
		state.setDetail("testingDetail");
		state.setTaskType(TaskType.DEADLINE);
		state.setIsStartDateChanged(true);
		try {
			state.setStartDate(sdf.parse("18 MAR 6 00:00"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		state.setIsEndDateChanged(true);
		try {
			state.setEndDate(sdf.parse("19 MAR 6 00:00"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
