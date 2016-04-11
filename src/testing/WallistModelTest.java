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
		assertEquals(Constant.HELP_INTRO, state.getHelpManual()[0]);
		assertEquals(Constant.HELP_ADD, state.getHelpManual()[1]);
		assertEquals(Constant.HELP_DELETE, state.getHelpManual()[2]);
		assertEquals(Constant.HELP_TICK, state.getHelpManual()[3]);
		assertEquals(Constant.HELP_UPDATE, state.getHelpManual()[4]);
		assertEquals(Constant.HELP_VIEW, state.getHelpManual()[5]);
		assertEquals(Constant.HELP_EXIT, state.getHelpManual()[6]);
		assertEquals(Constant.HELP_END, state.getHelpManual()[7]);
		
		//test help
		wm.processInputString("View Setting");

		//assertEquals(Constant.MESSAGE_SUCCESS, state.getDisplayMessage());
		assertEquals(ViewMode.CONFIG, state.getViewMode());
		assertEquals(Constant.HELP_INTRO, state.getConfigInfo()[0]);

		assertEquals(Constant.HELP_INTRO, state.getConfigInfo()[1]);

		assertEquals(Constant.HELP_INTRO, state.getConfigInfo()[2]);

		assertEquals(Constant.HELP_INTRO, state.getConfigInfo()[3]);
		
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
		

		//wm.storage.executeChangeDirectory("testing.txt");
//		wm.process("clear");
//		wm.process("add hahahahah");
//		System.out.println(wm.stateHistory.size());
//		System.out.println(wm.stateFuture.size());
//		wm.process("undo");	
//		System.out.println(wm.stateHistory.size());
//		System.out.println(wm.stateFuture.size());
//
//		wm.process("redo");
//		System.out.println(wm.stateHistory.size());
//		System.out.println(wm.stateFuture.size());
		
		
//		//clear before testing
//		wm.process("clear float");
//		wm.process("clear deadline");
//
//		//testing add a floating task
//		wm.process("add Boxin is testing");
//		assertEquals(newTask.getContent(), state.getFloatingTasks().get(0).getContent());
//
//		//testing add a task with deadline
//		wm.process("add boxin is testing something on 11/11/11 11:11");
//		String dateString = "11/11/11 11:11";
//		Date date= TimeParser.stringToDate(dateString);
		//System.out.println(state.getNormalTasks().size());
//		assertEquals(date, state.getNormalTasks().first().getEndDate());
//		System.out.println(state.getNormalTasks().first().getContent());
//		assertEquals("boxin is testing something", state.getNormalTasks().first().getContent());
//		
//		//testing add another deadline task for correct order
//		wm.process("add boxin is testing something very late on 12/11/11 11:11");
//		assertEquals("boxin is testing something very late", state.getNormalTasks().last().getContent());
//		//System.out.println(state.getNormalTasks().last().getContent());
//
//		// testing the very late date -- boundary testing
//		wm.process("add boxin is testing something very late2 on 12/11/35 11:12");
//		//System.out.println(state.getNormalTasks().last().getContent());
//		assertEquals("boxin is testing something very late2", state.getNormalTasks().last().getContent());
//		
//		//testing at venue in add
////		wm.process("add boxin is testing venue at SOC on 12/11/00 11:12");
////		assertEquals("boxin is testing something", state.getNormalTasks().first().getContent());
//
///* right now tasks in the normal tasks list is 
// * 
// * add boxin is testing venue at SOC on 12/11/00 11:12 (not yet there)
// * 
// * add boxin is testing something on 11/11/11 11:11
// * 
// * add boxin is testing something very late on 12/11/11 11:11
// * 
// * add boxin is testing something very late2 on 12/11/35 11:12
// * 
// */
//		//testing delete
//		
//		/* right now tasks in the normal tasks list is 
//		 * 
//		 * add boxin is testing something on 11/11/11 11:11
//		 * 
//		 * add boxin is testing something very late on 12/11/11 11:11
//		 * 
//		 * add boxin is testing something very late2 on 12/11/35 11:12
//		 * 
//		 */
//		assertEquals(3, state.getNormalTasks().size());
//		System.out.println(wm.states.size());
//
//		wm.process("delete 1 deadline");
//		System.out.println(wm.states.size());
//
//		/* right now tasks in the normal tasks list is 
//		 * 
//		 * add boxin is testing something very late on 12/11/11 11:11
//		 * 
//		 * add boxin is testing something very late2 on 12/11/35 11:12
//		 * 
//		 */
//		assertEquals("boxin is testing something very late", state.getNormalTasks().first().getContent());
//		assertEquals("boxin is testing something very late2", state.getNormalTasks().last().getContent());
//		
//		assertEquals(2, state.getNormalTasks().size());
//			
//		//System.out.println(wm.states.size());
//		//test undo
//		//wm.process("undo");
//		//wm.process("undo");
//		//wm.process("undo");
//		System.out.println(wm.states.size());
//
//		wm.getState().setCommand(CommandType.UNDO);
//		System.out.println(wm.getState().getCommand());
//		wm.running();
//		System.out.println(wm.getState().getCommand());
//		System.out.println(wm.getState().getContent());
//
//		System.out.println(wm.states.size());
//
////		wm.process("undo");
////		wm.process("undo");
////		wm.process("undo");
////		wm.process("undo");
//		
//		assertEquals(2, state.getNormalTasks().size());
//
////		assertEquals(CommandType.UNDO, state.getCommand());
////		assertEquals(false, wm.states.empty());
////		assertEquals(2, wm.states.peek().getNormalTasks().size());
//
////		
////		//testing update
//		wm.process("update 1 deadline hahahaha");
////		assertEquals("hahahaha", state.getNormalTasks().first().getContent());
////		
////		//testing tick
//		wm.process("tick 1 deadline hahahaha");
////		assertEquals(true, state.getNormalTasks().first().getIsFinished());
////				
//		//testing clear
//		wm.process("clear deadline");
//		assertEquals(0, state.getNormalTasks().size());
//			
		//System.out.println(newTask.getContent());
		//System.out.println(state.getFloatingTasks().get(0).getContent());
	}

}
