//@@author A0107354L
package testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import common.State;
import common.ViewMode;
import logic.AddTask;
import logic.ClearTask;
import logic.SearchTasks;
import logic.TickTask;
import model.WallistModel;

public class ClearTaskTest {

	@Test
	public void test() {
		WallistModel wm = new WallistModel();
		State state = wm.getState();
		ClearTask clearTask = wm.getClearTask();
		AddTask addTask = wm.getAddTask();
		SearchTasks searchTask = wm.getSearchTasks();
		TickTask tickTask = wm.getTickTask();
	    //Clear all tasks list for testing
		clearState(state);

		int taskNum = 10, numToTick = 5;
		
		// Add taskNum of floating task and clear under floating mode
		addFloatingTasks(state, addTask, taskNum);
		clearUnderMode(state, clearTask, ViewMode.FLOATING);
		assertEquals(0, state.getFloatingTasks().size());
		assertEquals(0, state.getAllTasks().size());

		// Add taskNum of deadline task and clear under floating mode
		addDeadlineTasks(state, addTask, taskNum);
		clearUnderMode(state, clearTask, ViewMode.DEADLINE);
		assertEquals(0, state.getDeadlineTasks().size());
		assertEquals(0, state.getAllTasks().size());

		// Add taskNum of deadline task and clear under all mode
		addDeadlineTasks(state, addTask, taskNum);
		clearUnderMode(state, clearTask, ViewMode.ALL);
		assertEquals(0, state.getDeadlineTasks().size());
		assertEquals(0, state.getAllTasks().size());

		// Add taskNum of floating task and clear under search mode
		addDeadlineTasks(state, addTask, taskNum);
		addSearchedTasks(state, searchTask);
		clearUnderMode(state, clearTask, ViewMode.SEARCH);
		assertEquals(0, state.getSearchResultTasks().size());

		// Clear all tasks and
		// Add taskNum of floating task, tick numToTick tasks under All mode
		// and clear under finished mode
		clearUnderMode(state, clearTask, ViewMode.ALL);
		addFloatingTasks(state, addTask, taskNum);
		addFinishedTasks(state, tickTask, taskNum, numToTick);
		clearUnderMode(state, clearTask, ViewMode.FINISHED);
		assertEquals(0, state.getFinishedTasks().size());
		
		//Now test clearing in wrong mode
		clearInWrongMode(state, clearTask, ViewMode.CONFIG);
		clearInWrongMode(state, clearTask, ViewMode.HELP);
		clearInWrongMode(state, clearTask, ViewMode.UNDEFINED);
		
	}

	private void clearInWrongMode(State state, ClearTask clearTask, ViewMode vm) {
		state.setViewMode(vm);
		boolean isClearSuccessful = clearTask.process();
		assertEquals(false, isClearSuccessful);
	}

	private void clearUnderMode(State state, ClearTask clearTask, ViewMode vm) {
		state.setViewMode(vm);
		boolean isClearSuccessful = clearTask.process();
		assertEquals(true, isClearSuccessful);
	}

	private void addFinishedTasks(State state, TickTask tickTask, int a, int b) {
		state.setPositionIndex(1);
		state.setViewMode(ViewMode.FLOATING);
		for(int i = 0; i < b; i++){
			tickTask.process();
		}
		assertEquals(b, state.getFinishedTasks().size());
		assertEquals(a - b, state.getAllTasks().size());
	}

	private void addSearchedTasks(State state, SearchTasks searchTask) {
		ArrayList<String> dummySearch = new ArrayList<String>();
		dummySearch.add("");
		state.setSearchKey(dummySearch);
		searchTask.process();
		assertEquals(state.getAllTasks().size(), state.getSearchResultTasks().size());
	}

	private void addDeadlineTasks(State state, AddTask addTask, int a) {
		state.setIsEndDateChanged(true);
		state.setEndDate(new Date(System.currentTimeMillis()));
		
		for(int i = 0; i < a; i++){
			addTask.process();
		}
		assertEquals(a, state.getDeadlineTasks().size());
		assertEquals(a, state.getAllTasks().size());
	}

	private void addFloatingTasks(State state, AddTask addTask, int a) {
		state.setIsEndDateChanged(false);
		for(int i = 0; i < a; i++){
			addTask.process();
		}
		assertEquals(a, state.getAllTasks().size());
		assertEquals(a, state.getFloatingTasks().size());
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

}
