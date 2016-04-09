//@@ A0107375E
package logic;

import common.State;

public class ChangeDirectory implements Operation {
	
	private State state;
	
	public ChangeDirectory(State state) {
		this.state = state;
	}

	@Override
	public boolean process() {
		String desiredDirectory = this.state.getDesiredDirectory();
		return false;
	}
	
}
