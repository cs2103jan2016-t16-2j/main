package logic;

import common.State;

public class UntickTask implements Operation {
	private State state;

	public UntickTask(State state) {
		this.state = state;
	}
	@Override
	public boolean process() {
		// TODO Auto-generated method stub
		return false;
	}

}
