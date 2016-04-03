package parser;

import java.util.ArrayList;
import java.util.Date;

import common.Constant;
import common.State;
import common.TaskType;
import common.ViewMode;

public class CommandTick implements Command{
	private State state_;
	private String content_;
	
	public CommandTick(State state){
		state_ = state;
		content_ = getContentWithoutCommand(state_);
	}
	@Override
	public void processInput() {
		state_.setDetail(getDetail());
		state_.setVenue(getVenue());
		state_.setStartDate(getStartDate());
		state_.setEndDate(getEndDate());
		state_.setPositionIndex(getPositionIndex());
		state_.setContent(getContent());
		state_.setTaskType(getTaskType());
		state_.setSearchKey(getSearchKey());
		state_.setNewViewMode(getNewViewMode());
		
	}

	@Override
	public String getDetail() {
		return Constant.VALUE_DEFAULT_EMPTY;
	}

	@Override
	public String getVenue() {
		return Constant.VALUE_DEFAULT_EMPTY;
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
	public int getPositionIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getContent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TaskType getTaskType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getSearchKey() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ViewMode getNewViewMode() {
		// TODO Auto-generated method stub
		return null;
	}

}
