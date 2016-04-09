package logic;
import common.*;
public class UndoCommand implements Operation {
	
	State state;
	
	public UndoCommand(State state) {
		this.state = state;
	}
	
	@Override
	public boolean process() {
		// TODO Auto-generated method stub
		return false;
	}

}
