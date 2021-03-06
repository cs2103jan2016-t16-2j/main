//@@ A0107375E
package logic;
import common.*;

public class Help implements Operation {
	
	private State state;

	public Help(State state) {
		this.state = state;
	}
	
	@Override
	public boolean process() {

		ViewMode newViewMode = state.getNewViewMode();
		state.setViewMode(newViewMode);
		
		return true;
	}

}
