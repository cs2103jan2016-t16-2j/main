package parser;

import java.util.Date;

import common.State;

public class CommandUndo implements Command{
	private State state_;
	
	public CommandUndo(State state){
		state_ = state;
	}
	@Override
	public void processInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getRawContent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDetail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getVenue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getStartDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getEndDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getContent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSearchKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
