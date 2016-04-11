//@@author A0130717M
package testing;
import common.*;
import model.WallistModel;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class WallistModelTest {
	WallistModel wm = new WallistModel();
	State state;
	Task currentTask;
	
	@Ignore
	@Test
	public void testInitialization(){
		wm.resetStorage();
		state = wm.getState();
		assertEquals(Theme.AUTUMN, state.getTheme());
		assertEquals(Font.SEGOE, state.getFont());
		assertEquals(ViewMode.START, state.getViewMode());
		assertEquals(CommandType.UNDEFINED, state.getCommandType());
		assertEquals(true, state.isCurrentTasksEmpty());
		assertEquals(Constant.EMPTY_TODAY, state.getEmptyMessage());
	}
	@Test
	public void testHelp(){
		wm.processInputString("Help");
		assertEquals(Constant.MESSAGE_SUCCESS, state.getDisplayMessage());
		assertEquals(ViewMode.HELP, state.getViewMode());
	}
	@Test
	public void testSetting(){
		wm.processInputString("View Setting");
		assertEquals(Constant.MESSAGE_SUCCESS, state.getDisplayMessage());
		assertEquals(ViewMode.CONFIG, state.getViewMode());
	}
	@Test
	public void testViewToday(){
		wm.processInputString("View Today");
		assertEquals(Constant.MESSAGE_SUCCESS, state.getDisplayMessage());
		assertEquals(ViewMode.START, state.getViewMode());
	}
	@Test
	public void testViewAll(){
		wm.processInputString("View All");
		assertEquals(Constant.MESSAGE_SUCCESS, state.getDisplayMessage());
		assertEquals(ViewMode.ALL, state.getViewMode());
	}
	@Test
	public void testViewScheduled(){
		wm.processInputString("View Scheduled");
		assertEquals(Constant.MESSAGE_SUCCESS, state.getDisplayMessage());
		assertEquals(ViewMode.DEADLINE, state.getViewMode());				
	}
	@Test
	public void testViewFloating(){
		wm.processInputString("View Floating");
		assertEquals(Constant.MESSAGE_SUCCESS, state.getDisplayMessage());
		assertEquals(ViewMode.FLOATING, state.getViewMode());				
	}
	@Test
	public void testViewFinished(){
		wm.processInputString("View Finished");
		assertEquals(Constant.MESSAGE_SUCCESS, state.getDisplayMessage());
		assertEquals(ViewMode.FINISHED, state.getViewMode());				
	}
	@Test
	public void testAddScheduled(){	
		wm.processInputString("Add camp from: 3/3/16 00:00 to: 4/3/16 22:00 at: Malaysia detail: with Max");
		assertEquals(Constant.MESSAGE_SUCCESS, state.getDisplayMessage());
		assertEquals(false, state.isCurrentTasksEmpty());
		assertEquals(4, state.getCurrentTasks().size());
		currentTask = state.getCurrentTasks().get(0);
		assertEquals(TaskType.DEADLINE, currentTask.getTaskType());
		assertEquals(ViewMode.DEADLINE, state.getViewMode());
		assertEquals(CommandType.ADD, state.getCommandType());
		assertEquals("camp\n\nVenue: Malaysia\nDetail: with Max", currentTask.getDisplayContent());
		assertEquals(TimeParser.stringToDate("3/3/16 00:00"), state.getStartDate());
		assertEquals(TimeParser.stringToDate("4/3/16 22:00"), state.getEndDate());
	}
	@Test
	public void testAddFloating(){	
		wm.processInputString("Add meeting");
		assertEquals(Constant.MESSAGE_SUCCESS, state.getDisplayMessage());
		assertEquals(false, state.isCurrentTasksEmpty());
		assertEquals(4, state.getCurrentTasks().size());
		currentTask = state.getCurrentTasks().get(0);
		assertEquals(TaskType.FLOATING, currentTask.getTaskType());
		assertEquals(ViewMode.FLOATING, state.getViewMode());
		assertEquals(CommandType.ADD, state.getCommandType());
		assertEquals("meeting", state.getCurrentTasks().get(0).getDisplayContent());

		//test add wrong date format
		wm.processInputString("Add meeting on: someday");
		assertEquals(Constant.VALUE_ERROR_DATE_NOT_PARSED, state.getDisplayMessage());
		assertEquals(false, state.isCurrentTasksEmpty());
		assertEquals(1, state.getCurrentTasks().size());
		assertEquals(ViewMode.FLOATING, state.getViewMode());
		assertEquals(CommandType.ADD, state.getCommandType());

		//test add wrong date
		wm.processInputString("Add meeting from: 12/12/12 12:12 to: 10/10/10 10:10");
		assertEquals(Constant.VALUE_ERROR_DATE_ERROR, state.getDisplayMessage());
		assertEquals(false, state.isCurrentTasksEmpty());
		assertEquals(1, state.getCurrentTasks().size());
		assertEquals(ViewMode.FLOATING, state.getViewMode());
		assertEquals(CommandType.ADD, state.getCommandType());
    }
	
	@Test
	public void testClear(){
		wm.resetStorage();
		state = wm.getState();	
		
		//test clear in all
		wm.processInputString("View All");
		wm.processInputString("Clear");
		assertEquals(Constant.MESSAGE_SUCCESS, state.getDisplayMessage());
		assertEquals(true, state.isCurrentTasksEmpty());
		assertEquals(0, state.getAllTasks().size());
		assertEquals(ViewMode.ALL, state.getViewMode());
		assertEquals(CommandType.CLEAR, state.getCommandType());		
		
		//test clear in all
		wm.processInputString("View All");
		wm.processInputString("Clear");
		assertEquals(Constant.MESSAGE_SUCCESS, state.getDisplayMessage());
		assertEquals(true, state.isCurrentTasksEmpty());
		assertEquals(0, state.getAllTasks().size());
		assertEquals(ViewMode.ALL, state.getViewMode());
		assertEquals(CommandType.CLEAR, state.getCommandType());
				
		
		//test delete wrong argument
		wm.processInputString("Delete meeting");
		assertEquals(Constant.VALUE_ERROR_ARGUMENT_NOT_NUMBER, state.getDisplayMessage());
		assertEquals(false, state.isCurrentTasksEmpty());
		assertEquals(1, state.getCurrentTasks().size());
		assertEquals(TaskType.FLOATING, currentTask.getTaskType());
		assertEquals(ViewMode.FLOATING, state.getViewMode());
		
		//test delete wrong argument
		wm.processInputString("Delete 2");
		assertEquals(Constant.MESSAGE_INDEX_OUT_OF_BOUND, state.getDisplayMessage());
		assertEquals(false, state.isCurrentTasksEmpty());
		assertEquals(1, state.getCurrentTasks().size());
		assertEquals(TaskType.FLOATING, currentTask.getTaskType());
		assertEquals(ViewMode.FLOATING, state.getViewMode());
				
		//test delete
		wm.processInputString("Delete 1");
		//assertEquals(Constant.MESSAGE_SUCCESS, state.getDisplayMessage());
		assertEquals(false, state.isCurrentTasksEmpty());
		assertEquals(1, state.getCurrentTasks().size());
		assertEquals(TaskType.FLOATING, currentTask.getTaskType());
		assertEquals(ViewMode.FLOATING, state.getViewMode());
		
	}

	@Before
	public void before(){
		wm.resetStorage();
		state = wm.getState();
		wm.processInputString("Add eat lunch from: 10/10/10 10:10 to: 12/12/12 12:12 at: TOA PAYOH detail: with boyfriend");
		wm.processInputString("Add eat lunch from: 10/10/16 10:10 to: 12/12/17 12:12 at: TOA PAYOH detail: with boyfriend");
		wm.processInputString("Add cs2103 meeting on: 5/5/16 10:10");
		wm.processInputString("Add cs2103 project");
		wm.processInputString("Add cs2103 revise");
		wm.processInputString("Add bt3101 assignment");
		
	}
}
