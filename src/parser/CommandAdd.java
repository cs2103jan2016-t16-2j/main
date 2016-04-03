package parser;

import java.util.ArrayList;
import java.util.Date;

import common.Constant;
import common.State;
import common.TaskType;
import common.ViewMode;

public class CommandAdd implements Command{
	private State state_;
	private String content_;
	
	public CommandAdd(State state){
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
		state_.setIsDetailChanged(false);
		return Constant.VALUE_DEFAULT_EMPTY;
	}

	@Override
	public String getVenue() {
		String wordList[] = content_.split("at:");
		if(wordList.length == 1){
			state_.setIsVenueChanged(false);
			return Constant.VALUE_DEFAULT_EMPTY;
		}else{
			state_.setIsVenueChanged(true);
			return wordList[wordList.length-1].trim();
		}
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
		if(state_.getIsEndDateChanged()){
			return TaskType.DEADLINE;
		}else{
			return TaskType.FLOATING;
		}
	}

	@Override
	public ArrayList<String> getSearchKey() {
		return new ArrayList<String>();
	}
	
	@Override
	public ViewMode getNewViewMode() {
		return ViewMode.UNDEFINED;
	}
}
