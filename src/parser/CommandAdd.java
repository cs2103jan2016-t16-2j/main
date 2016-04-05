package parser;

import java.util.ArrayList;
import java.util.Date;

import common.Constant;
import common.State;
import common.TaskType;
import common.ViewMode;
import gui.GUI;

public class CommandAdd implements Command{
	private State state_;
	private String content_;
	
	public CommandAdd(State state){
		this.state_ = state;
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
		String wordList[] = content_.split("details:");
		if(wordList.length <= 1){
			state_.setIsDetailChanged(false);
			return Constant.VALUE_DEFAULT_EMPTY;
		}else{
			String wordListVenue[] = wordList[wordList.length-1].split("at:");
			if(wordListVenue.length <= 1){
				state_.setIsDetailChanged(true);
				return wordList[wordList.length-1].trim();
			}else{
				state_.setIsDetailChanged(true);
				return wordListVenue[0].trim();
			}
		}
	}

	@Override
	public String getVenue() {
		String wordList[] = content_.split("at:");
		if(wordList.length <= 1){
			state_.setIsVenueChanged(false);
			return Constant.VALUE_DEFAULT_EMPTY;
		}else{
			String wordListDetails[] = wordList[wordList.length-1].split("details:");
			if(wordListDetails.length <= 1){
				state_.setIsVenueChanged(true);
				return wordList[wordList.length-1].trim();
			}else{
				state_.setIsVenueChanged(true);
				return wordListDetails[0].trim();
			}
		}
	}

	@Override
	public Date getStartDate() {
		String wordList[] = content_.split("from:");
		if(wordList.length==1){
			state_.setIsStartDateChanged(false);
			return null;
		}
		Date date = TimeParser.stringToDate(wordList[wordList.length-1].trim().substring(0, 14));
		if(date != null){
			state_.setIsStartDateChanged(true);
			return date;
		}else{
			state_.setIsStartDateChanged(false);
			state_.setDisplayMessage(Constant.VALUE_ERROR_DATE_NOT_PARSED);
			return null;
		}
	}

	@Override
	public Date getEndDate() {
		if(state_.getIsStartDateChanged()){
			String wordList[] = content_.split("to:");
			if(wordList.length==1){
				state_.setIsEndDateChanged(false);
				state_.setIsStartDateChanged(false);
				return null;
			}
			Date date = TimeParser.stringToDate(wordList[wordList.length-1].trim().substring(0, 14));
			if(date != null){
				state_.setIsEndDateChanged(true);
				if(date.before(state_.getStartDate())){
					state_.setDisplayMessage(Constant.VALUE_ERROR_DATE_ERROR);
					state_.setIsEndDateChanged(false);
					state_.setIsStartDateChanged(false);
					return null;
				}
				return date;
			}else{
				state_.setDisplayMessage(Constant.VALUE_ERROR_DATE_NOT_PARSED);
				state_.setIsEndDateChanged(false);
				state_.setIsStartDateChanged(false);
				return null;
			}
		}
		String wordList[] = content_.split("on:");
		if(wordList.length==1){
			state_.setIsEndDateChanged(false);
			return null;
		}
		Date date = TimeParser.stringToDate(wordList[wordList.length-1].trim().substring(0, 14));
		if(date != null){
			state_.setIsEndDateChanged(true);
			return date;
		}else{
			state_.setIsEndDateChanged(false);
			state_.setDisplayMessage(Constant.VALUE_ERROR_DATE_NOT_PARSED);
			return null;
		}
	}

	@Override
	public int getPositionIndex() {
		return Constant.VALUE_DEFAULT_POSITION_INDEX;
	}

	@Override
	public String getContent() {
		if(state_.getIsStartDateChanged()){
			String wordList[] = content_.split("from:");
			if(wordList.length <= 1){
				state_.setDisplayMessage(Constant.VALUE_ERROR_NO_INPUT);
				state_.setIsContentChanged(false);
				return Constant.VALUE_DEFAULT_EMPTY;
			}
			state_.setIsContentChanged(true);
			return wordList[0].trim();
		}else if(state_.getIsEndDateChanged()){
			String wordList[] = content_.split("on:");
			if(wordList.length <= 1){
				state_.setDisplayMessage(Constant.VALUE_ERROR_NO_INPUT);
				state_.setIsContentChanged(false);
				return Constant.VALUE_DEFAULT_EMPTY;
			}
			state_.setIsContentChanged(true);
			return wordList[0].trim();
		}else if(state_.getIsVenueChanged()){
			String wordList[] = content_.split("at:");
			if(wordList.length <= 1){
				state_.setDisplayMessage(Constant.VALUE_ERROR_NO_INPUT);
				state_.setIsContentChanged(false);
				return Constant.VALUE_DEFAULT_EMPTY;
			}
			if(state_.getIsDetailChanged()){
				String wordListDetail[] = wordList[0].split("details:");
				if(wordList.length <= 1){
					state_.setIsContentChanged(true);
					return wordList[0].trim();
				}else{
					state_.setIsContentChanged(true);
					return wordListDetail[0].trim();
				}
			}else{
				state_.setIsContentChanged(true);
				return wordList[0].trim();
			}
		}else if(state_.getIsDetailChanged()){
			String wordList[] = content_.split("details:");
			if(wordList.length <= 1){
				state_.setDisplayMessage(Constant.VALUE_ERROR_NO_INPUT);
				state_.setIsContentChanged(false);
				return Constant.VALUE_DEFAULT_EMPTY;
			}
			if(state_.getIsVenueChanged()){
				String wordListVenue[] = wordList[0].split("at:");
				if(wordList.length <= 1){
					state_.setIsContentChanged(true);
					return wordList[0].trim();
				}else{
					state_.setIsContentChanged(true);
					return wordListVenue[0].trim();
				}
			}else{
				state_.setIsContentChanged(true);
				return wordList[0].trim();
			}
		}else{
			return content_;
		}
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
