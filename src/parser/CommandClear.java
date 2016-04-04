package parser;

import java.util.ArrayList;
import java.util.Date;

import common.Constant;
import common.State;
import common.TaskType;
import common.ViewMode;

public class CommandClear implements Command{
	private State state_;
	private String content_;
	
	public CommandClear(State state){
		state_ = state;
		content_ = Constant.VALUE_DEFAULT_EMPTY;
	}
	@Override
	public void processInput() {
		content_ = getContentWithoutCommand();
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
		state_.setIsDetailChanged(false);
		return Constant.VALUE_DEFAULT_EMPTY;
	}

	@Override
	public String getVenue() {
		state_.setIsVenueChanged(false);
		return Constant.VALUE_DEFAULT_EMPTY;
	}

	@Override
	public Date getStartDate() {
		state_.setIsStartDateChanged(false);
		return null;
	}

	@Override
	public Date getEndDate() {
		state_.setIsEndDateChanged(false);
		return null;
	}

	@Override
	public int getPositionIndex() {
		return Constant.VALUE_DEFAULT_POSITION_INDEX;
	}

	@Override
	public String getContent() {
		state_.setIsContentChanged(false);
		return Constant.VALUE_DEFAULT_EMPTY;
	}

	@Override
	public TaskType getTaskType() {
		return TaskType.UNDEFINED;
	}

	@Override
	public ArrayList<String> getSearchKey() {
		return new ArrayList<String>();
	}
	
	@Override
	public ViewMode getNewViewMode() {
		return ViewMode.UNDEFINED;
	}
	@Override
	public String getContentWithoutCommand(){
		String inputWords[] = state_.getUserInput().split(" ");
		StringBuilder sb = new StringBuilder("");
		for(int i = 1; i < inputWords.length; i ++){
			sb.append(inputWords[i]);
			sb.append(" ");
		}
		return sb.toString().trim();
	};

}
