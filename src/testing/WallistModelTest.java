//@@author A0130717M
package testing;
import common.*;
import model.WallistModel;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class WallistModelTest {
	WallistModel wm = new WallistModel();
	State state;
	Task currentTask;
	
	@Test
	public void testInitialization(){
		wm.resetState();
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
		assertEquals(ViewMode.HELP, state.getViewMode());
	}
	
	@Test
	public void testSetting(){
		wm.processInputString("View Setting");
		assertEquals(ViewMode.CONFIG, state.getViewMode());
	}
	
	@Test
	public void testViewToday(){
		wm.processInputString("View Today");
		assertEquals(ViewMode.START, state.getViewMode());
	}
	
	@Test
	public void testViewAll(){
		wm.processInputString("View All");
		assertEquals(ViewMode.ALL, state.getViewMode());
	}
	
	@Test
	public void testViewScheduled(){
		wm.processInputString("View Scheduled");
		assertEquals(ViewMode.DEADLINE, state.getViewMode());				
	}
	
	@Test
	public void testViewFloating(){
		wm.processInputString("View Floating");
		assertEquals(ViewMode.FLOATING, state.getViewMode());				
	}
	@Test
	public void testViewFinished(){
		wm.processInputString("View Finished");
		assertEquals(ViewMode.FINISHED, state.getViewMode());				
	}
	
	@Test
	public void testAddScheduled(){	
		wm.processInputString("Add camp from: 3/3/16 00:00 to: 4/3/16 22:00 at: Malaysia detail: with Max");
		assertEquals(4, state.getCurrentTasks().size());
		currentTask = state.getCurrentTasks().get(3);
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
		assertEquals(4, state.getCurrentTasks().size());
		currentTask = state.getCurrentTasks().get(0);
		assertEquals(TaskType.FLOATING, currentTask.getTaskType());
		assertEquals(ViewMode.FLOATING, state.getViewMode());
		assertEquals(CommandType.ADD, state.getCommandType());
		assertEquals("meeting", state.getCurrentTasks().get(0).getDisplayContent());
    }
	
	@Test
	public void testAddWrongDateFormat(){	
		wm.processInputString("Add meeting on: someday");
		assertEquals(Constant.VALUE_ERROR_WRONG_FORMAT, state.getDisplayMessage());
		assertEquals(3, state.getCurrentTasks().size());
		assertEquals(ViewMode.FLOATING, state.getViewMode());
		assertEquals(CommandType.ADD, state.getCommandType());
    }
	
	@Test
	public void testAddWrongDate(){
		wm.processInputString("Add meeting from: 12/12/12 12:12 to: 10/10/10 10:10");
		assertEquals(Constant.VALUE_ERROR_WRONG_FORMAT, state.getDisplayMessage());
		assertEquals(3, state.getCurrentTasks().size());
		assertEquals(ViewMode.FLOATING, state.getViewMode());
		assertEquals(CommandType.ADD, state.getCommandType());
    }
	
	@Test
	public void testClearAll(){
		wm.resetStorage();
		state = wm.getState();	
		wm.processInputString("View All");
		wm.processInputString("Clear");
		assertEquals(true, state.isCurrentTasksEmpty());
		assertEquals(0, state.getAllTasks().size());
		assertEquals(ViewMode.ALL, state.getViewMode());
		assertEquals(CommandType.CLEAR, state.getCommandType());		
    }
	
	@Test
	public void testClearFloating(){
		wm.processInputString("View Floating");
		wm.processInputString("Clear");
		assertEquals(true, state.isCurrentTasksEmpty());
		assertEquals(0, state.getFloatingTasks().size());
		assertEquals(false, state.getDeadlineTasks().isEmpty());
		assertEquals(ViewMode.FLOATING, state.getViewMode());
		assertEquals(CommandType.CLEAR, state.getCommandType());
    }
	
	@Test
	public void testClearScheduled(){
		wm.processInputString("View Scheduled");
		wm.processInputString("Clear");
		assertEquals(0, state.getDeadlineTasks().size());
		assertEquals(false, state.getFloatingTasks().isEmpty());
		assertEquals(ViewMode.DEADLINE, state.getViewMode());
		assertEquals(CommandType.CLEAR, state.getCommandType());
    }
	
	@Test
	public void testDeleteWrongArg(){	
		wm.processInputString("Delete meeting");
		assertEquals(Constant.VALUE_ERROR_ARGUMENT_NOT_NUMBER, state.getDisplayMessage());
		assertEquals(3, state.getCurrentTasks().size());
		assertEquals(ViewMode.FLOATING, state.getViewMode());
    }
	
	@Test
	public void testDeleteWrongIndex(){	
		wm.processInputString("Delete 4");
		assertEquals(Constant.MESSAGE_INDEX_OUT_OF_BOUND, state.getDisplayMessage());
		assertEquals(3, state.getCurrentTasks().size());
		assertEquals(ViewMode.FLOATING, state.getViewMode());
    }
	
	
	@Test
	public void testDelete(){	
		wm.processInputString("Delete 1");
		assertEquals(2, state.getCurrentTasks().size());
		assertEquals(ViewMode.FLOATING, state.getViewMode());
		
	}

	@Test
	public void testTickWrongArg(){	
		wm.processInputString("Tick meeting");
		assertEquals(Constant.VALUE_ERROR_ARGUMENT_NOT_NUMBER, state.getDisplayMessage());
		assertEquals(3, state.getCurrentTasks().size());
		assertEquals(ViewMode.FLOATING, state.getViewMode());
	}
	
	@Test
	public void testTickWrongIndex(){	
		wm.processInputString("Tick 4");
		assertEquals(Constant.MESSAGE_INDEX_OUT_OF_BOUND, state.getDisplayMessage());
		assertEquals(3, state.getCurrentTasks().size());
		assertEquals(ViewMode.FLOATING, state.getViewMode());
	}
	
	@Test
	public void testTick(){	
		wm.processInputString("Tick 3");
		assertEquals(2, state.getCurrentTasks().size());
		assertEquals(1, state.getFinishedTasks().size());
		assertEquals(ViewMode.FLOATING, state.getViewMode());
		wm.processInputString("View Finished");
		assertEquals(1, state.getCurrentTasks().size());
		assertEquals(ViewMode.FINISHED, state.getViewMode());
	}
	
	@Test
	public void update(){	
		wm.processInputString("Update 2 cs2103 homework at: SOC");
		assertEquals(3, state.getCurrentTasks().size());
		currentTask = state.getCurrentTasks().get(1);
		assertEquals(TaskType.FLOATING, currentTask.getTaskType());
		assertEquals(ViewMode.DEADLINE, state.getViewMode());
		assertEquals(CommandType.ADD, state.getCommandType());
		assertEquals("camp\n\nVenue: Malaysia\nDetail: with Max", currentTask.getDisplayContent());
		assertEquals(TimeParser.stringToDate("3/3/16 00:00"), state.getStartDate());
		assertEquals(TimeParser.stringToDate("4/3/16 22:00"), state.getEndDate());
	}

	@Before
	public void before(){
		wm.resetStorage();
		state = wm.getState();
		wm.processInputString("Add eat lunch from: 10/10/10 10:10 to: 12/12/12 12:12 at: TOA PAYOH detail: with boyfriend");
		wm.processInputString("Add eat lunch from: 10/10/16 10:10 to: 12/12/17 12:12 at: TOA PAYOH detail: with boyfriend");
		wm.processInputString("Add cs2103 meeting on: 5/5/16 10:10");
		wm.processInputString("Add cs2103 project");
		wm.processInputString("Add cs2103 revision");
		wm.processInputString("Add bt3101 assignment");
	}
}
