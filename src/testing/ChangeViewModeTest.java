//@@author A0107354L
package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import common.State;
import common.ViewMode;
import logic.ChangeViewMode;
import model.WallistModel;

public class ChangeViewModeTest {

	@Test
	public void test() {
		WallistModel wm = new WallistModel();
		State state = wm.getState();
		ChangeViewMode changeViewMode = wm.getChangeViewMode();

		//Test change view mode to different mode 
		//Successful cases
		testWithNewViewMode(ViewMode.ALL, state, changeViewMode);
		testWithNewViewMode(ViewMode.FLOATING, state, changeViewMode);
		testWithNewViewMode(ViewMode.DEADLINE, state, changeViewMode);
		testWithNewViewMode(ViewMode.CONFIG, state, changeViewMode);
		testWithNewViewMode(ViewMode.FINISHED, state, changeViewMode);
		testWithNewViewMode(ViewMode.HELP, state, changeViewMode);
		testWithNewViewMode(ViewMode.START, state, changeViewMode);
		testWithNewViewMode(ViewMode.ALL, state, changeViewMode);
		
		//Failure cases
		//cannot view search(meaningless)
		testViewSearch(state, changeViewMode);

	}

	private void testViewSearch(State state, ChangeViewMode changeViewMode) {
		state.setNewViewMode(ViewMode.SEARCH);
		boolean isChangeSuccessful = changeViewMode.process();
		assertEquals(false, isChangeSuccessful);
	}

	private void testWithNewViewMode(ViewMode newView, State state, ChangeViewMode changeViewMode){
		changeNewViewModeInStateTo(state, changeViewMode, newView);
		testChangedViewMode(state, newView);
	}
	
	private void testChangedViewMode(State state, ViewMode newView) {
		assertEquals(newView, state.getViewMode());
	}

	private void changeNewViewModeInStateTo(State state, ChangeViewMode changeViewMode, ViewMode newView) {
		state.setNewViewMode(newView);
		boolean isChangeSuccessful = changeViewMode.process();
		assertEquals(true, isChangeSuccessful);
	}

}
