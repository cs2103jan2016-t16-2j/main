package logic;

import common.State;
import common.ViewMode;

public class ChangeViewMode implements Operation {
	private State state;

	public ChangeViewMode(State state) {
		this.state = state;
	}

	@Override
	public boolean process() {
		ViewMode newViewMode = state.getNewViewMode();
		state.setViewMode(newViewMode);
		return true;
	}

}
