//@@author A0130717M
package testing;
import common.*;
import model.WallistModel;
import storage.Storage;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class WallistModelTest {

	@Test
	public void test(){
		WallistModel wm = new WallistModel();
		wm.storage = new Storage(new State());
		State state = wm.getState();
		Task currentTask;

		//test initialization
		assertEquals(Theme.AUTUMN, state.getTheme());
		assertEquals(Font.SEGOE, state.getFont());
		assertEquals(ViewMode.START, state.getViewMode());
		assertEquals(CommandType.UNDEFINED, state.getCommandType());
		assertEquals(true, state.isCurrentTasksEmpty());
		assertEquals(Constant.EMPTY_TODAY, state.getEmptyMessage());
		
		//test help
		wm.processInputString("Help");
		//assertEquals(Constant.MESSAGE_SUCCESS, state.getDisplayMessage());
		assertEquals(ViewMode.HELP, state.getViewMode());
		
		//test setting
		wm.processInputString("View Setting");
		//assertEquals(Constant.MESSAGE_SUCCESS, state.getDisplayMessage());
		assertEquals(ViewMode.CONFIG, state.getViewMode());

		//test view today
		wm.processInputString("View Today");
		//assertEquals(Constant.MESSAGE_SUCCESS, state.getDisplayMessage());
		assertEquals(ViewMode.START, state.getViewMode());
		
		//test view all
		wm.processInputString("View All");
		//assertEquals(Constant.MESSAGE_SUCCESS, state.getDisplayMessage());
		assertEquals(ViewMode.ALL, state.getViewMode());
		
		//test view all
		wm.processInputString("View All");
		//assertEquals(Constant.MESSAGE_SUCCESS, state.getDisplayMessage());
		assertEquals(ViewMode.ALL, state.getViewMode());
		
		//test view scheduled
		wm.processInputString("View Scheduled");
		//assertEquals(Constant.MESSAGE_SUCCESS, state.getDisplayMessage());
		assertEquals(ViewMode.DEADLINE, state.getViewMode());				
		
		//test view floating
		wm.processInputString("View Floating");
		//assertEquals(Constant.MESSAGE_SUCCESS, state.getDisplayMessage());
		assertEquals(ViewMode.FLOATING, state.getViewMode());				
		
		//test view finished
		wm.processInputString("View Finished");
		//assertEquals(Constant.MESSAGE_SUCCESS, state.getDisplayMessage());
		assertEquals(ViewMode.FINISHED, state.getViewMode());				
		
		//test view finished
		wm.processInputString("View Finished");
		//assertEquals(Constant.MESSAGE_SUCCESS, state.getDisplayMessage());
		assertEquals(ViewMode.FINISHED, state.getViewMode());				
				
				
		
		//test add deadline
		wm.processInputString("Add eat lunch from: 10/10/10 10:10 to: 12/12/12 12:12 at: TOA PAYOH detail: with boyfriend");
		//assertEquals(Constant.MESSAGE_SUCCESS, state.getDisplayMessage());
		assertEquals(false, state.isCurrentTasksEmpty());
		assertEquals(1, state.getCurrentTasks().size());
		currentTask = state.getCurrentTasks().get(0);
		assertEquals(TaskType.DEADLINE, currentTask.getTaskType());
		assertEquals(ViewMode.DEADLINE, state.getViewMode());
		assertEquals(CommandType.ADD, state.getCommandType());
		assertEquals("eat lunch\n\nVenue: TOA PAYOH\nDetail: with boyfriend", currentTask.getDisplayContent());
		assertEquals(TimeParser.stringToDate("10/10/10 10:10"), state.getStartDate());
		assertEquals(TimeParser.stringToDate("12/12/12 12:12"), state.getEndDate());

		//test add floating
		wm.processInputString("Add meeting");
		//assertEquals(Constant.MESSAGE_SUCCESS, state.getDisplayMessage());
		assertEquals(false, state.isCurrentTasksEmpty());
		assertEquals(1, state.getCurrentTasks().size());
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

}
