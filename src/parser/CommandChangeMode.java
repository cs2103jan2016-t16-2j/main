//@@author A0130369H
package parser;

import java.util.ArrayList;
import java.util.Date;

import common.Constant;
import common.State;
import common.TaskType;
import common.ViewMode;

public class CommandChangeMode implements Command{
	private State state_;
	private String content_;
	
	public CommandChangeMode(State state){
		state_ = state;
		content_ = Constant.VALUE_DEFAULT_EMPTY;
	}
	@Override
	public void processInput() {
		content_ = getContentWithoutCommand(state_);
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
		ViewMode viewMode = determineViewMode();
		return viewMode;
	}
	
	/*
	 * Get the view mode based on content
	 * Pre-Cond: Valid view mode specified by the user
	 * Post-Cond: Returning the respective view mode
	 */
	private ViewMode determineViewMode() {
		if(content_.equalsIgnoreCase("FLOATING")){
			return ViewMode.FLOATING;
		} else if(content_.equalsIgnoreCase("SCHEDULED")){
			return ViewMode.DEADLINE;
		} else if(content_.equalsIgnoreCase("ALL")){
			return ViewMode.ALL;
		} else if(content_.equalsIgnoreCase("CONFIG")){
			return ViewMode.CONFIG;
		} else if(content_.equalsIgnoreCase("FINISHED")){
			return ViewMode.FINISHED;
		} else if (content_.equalsIgnoreCase("HELP")) {
			return ViewMode.HELP;
		} else {
			return ViewMode.UNDEFINED;
		}
	}
}
