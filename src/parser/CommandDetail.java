//@@author A0130369H
package parser;

import java.util.ArrayList;
import java.util.Date;

import common.Constant;
import common.State;
import common.TaskType;
import common.ViewMode;

public class CommandDetail implements Command{
	//============================
	//       Attributes
	//============================
	private State state_;
	private String content_;
	
	//====================================
	//       Constructor and Initialiser
	//====================================
	/**
	 * Initialize the command
	 * @param state
	 */
	public CommandDetail(State state){
		state_ = state;
		content_ = Constant.VALUE_DEFAULT_EMPTY;
	}
	
	//====================================
	//       Public Functions
	//====================================
	/**
	 * Process the input and update the state accordingly
	 */
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

	//====================================
	//       Helper Functions
	//====================================
	/**
	 * The following methods update the state
	 */
	private String getDetail() {
		state_.setIsDetailChanged(false);
		return Constant.VALUE_DEFAULT_EMPTY;
	}

	 
	private String getVenue() {
		state_.setIsVenueChanged(false);
		return Constant.VALUE_DEFAULT_EMPTY;
	}

	 
	private Date getStartDate() {
		state_.setIsStartDateChanged(false);
		return null;
	}

	 
	private Date getEndDate() {
		state_.setIsEndDateChanged(false);
		return null;
	}

	 
	private int getPositionIndex() {
		return Integer.parseInt(content_);
	}

	 
	private String getContent() {
		state_.setIsContentChanged(false);
		return Constant.VALUE_DEFAULT_EMPTY;
	}

	 
	private TaskType getTaskType() {
		return TaskType.UNDEFINED;
	}

	 
	private ArrayList<String> getSearchKey() {
		return new ArrayList<String>();
	}
	
	 
	private ViewMode getNewViewMode() {
		return ViewMode.UNDEFINED;
	}

}
