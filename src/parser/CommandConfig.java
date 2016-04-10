//@@author A0130369H
package parser;

import java.util.ArrayList;
import java.util.Date;

import common.Constant;
import common.Font;
import common.State;
import common.TaskType;
import common.Theme;
import common.ViewMode;

public class CommandConfig implements Command{
	private State state_;
	private String content_;
	
	public CommandConfig(State state){
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
		state_.setNewFont(getFont());
		state_.setNewTheme(getTheme());
		
	}

	private Theme getTheme() {
		if(content_.equalsIgnoreCase("autumn")){
			return Theme.AUTUMN;
		}else if(content_.equalsIgnoreCase("bokeh")){
			return Theme.BOKEH;
		}else if(content_.equalsIgnoreCase("branch")){
			return Theme.BRANCH;
		}else if(content_.equalsIgnoreCase("cat")){
			return Theme.CAT;
		}else if(content_.equalsIgnoreCase("grey")){
			return Theme.GREY;
		}else if(content_.equalsIgnoreCase("warm")){
			return Theme.WARM;
		}else{
			return Theme.AUTUMN;
		}
	}
	private Font getFont() {
		if(content_.equalsIgnoreCase("consolas")){
			return Font.CONSOLAS;
		}else if(content_.equalsIgnoreCase("segoe")){
			return Font.SEGOE;
		}else{
			return Font.SEGOE;
		}
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
		state_.setIsContentChanged(true);
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
		return ViewMode.CONFIG;
	}

}
