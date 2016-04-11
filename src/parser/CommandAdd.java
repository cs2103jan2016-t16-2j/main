//@@author A0130369H
package parser;

import java.util.ArrayList;
import java.util.Date;

import common.Constant;
import common.State;
import common.TaskType;
import common.TimeParser;
import common.ViewMode;

public class CommandAdd implements Command{
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
	public CommandAdd(State state){
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
		String wordList[] = splitDetail(content_);
		if(hasContentSplit(wordList)){
			state_.setIsDetailChanged(false);
			return Constant.VALUE_DEFAULT_EMPTY;
		}else{
			// Check if the content has venue specified
			String wordListVenue[] = getLastWord(wordList).split("at:");
			if(hasContentSplit(wordListVenue)){
				state_.setIsDetailChanged(true);
				return getLastWord(wordList);
			}else{
				state_.setIsDetailChanged(true);
				return getFirstWord(wordListVenue);
			}
		}
	}
	 
	private String getVenue() {
		String wordList[] = content_.split("at:");
		if(hasContentSplit(wordList)){
			state_.setIsVenueChanged(false);
			return Constant.VALUE_DEFAULT_EMPTY;
		}else{
			String wordListDetails[] = splitDetail(getLastWord(wordList));
			if(hasContentSplit(wordListDetails)){
				state_.setIsVenueChanged(true);
				return getLastWord(wordList);
			}else{
				state_.setIsVenueChanged(true);
				return getFirstWord(wordListDetails);
			}
		}
	}

	 
	private Date getStartDate() {
		String wordList[] = content_.split("from:");
		if(hasContentSplit(wordList)){
			state_.setIsStartDateChanged(false);
			return null;
		}
		String endDate = getLastWord(wordList);
		String wordListEnd[] = endDate.split("to:");
		
		if(hasContentSplit(wordListEnd)){
			state_.setIsStartDateChanged(false);
			state_.setDisplayMessage(Constant.VALUE_ERROR_DATE_NOT_PARSED);
			state_.setIsValid(false);
			return null;
		}
		Date date = TimeParser.stringToDate(getFirstWord(wordListEnd));
		if(date != null){
			state_.setIsStartDateChanged(true);
			return date;
		}else{
			state_.setIsStartDateChanged(false);
			state_.setDisplayMessage(Constant.VALUE_ERROR_DATE_NOT_PARSED);
			state_.setIsValid(false);
			return null;
		}
	}

	 
	private Date getEndDate() {
		if(state_.getIsStartDateChanged()){
			String wordList[] = content_.split("to:");
			if(hasContentSplit(wordList)){
				state_.setIsEndDateChanged(false);
				state_.setIsStartDateChanged(false);
				return null;
			}
			Date date = TimeParser.stringToDate(getLastWord(wordList));
			if(date != null){
				state_.setIsEndDateChanged(true);
				if(date.before(state_.getStartDate())){
					state_.setDisplayMessage(Constant.VALUE_ERROR_DATE_ERROR);
					state_.setIsValid(false);
					state_.setIsEndDateChanged(false);
					state_.setIsStartDateChanged(false);
					return null;
				}
				return date;
			}else{
				state_.setDisplayMessage(Constant.VALUE_ERROR_DATE_NOT_PARSED);
				state_.setIsValid(false);
				state_.setIsEndDateChanged(false);
				state_.setIsStartDateChanged(false);
				return null;
			}
		}
		String wordList[] = content_.split("on:");
		if(hasContentSplit(wordList)){
			state_.setIsEndDateChanged(false);
			return null;
		}
		Date date = TimeParser.stringToDate(getLastWord(wordList));
		if(date != null){
			state_.setIsEndDateChanged(true);
			return date;
		}else{
			state_.setIsEndDateChanged(false);
			state_.setDisplayMessage(Constant.VALUE_ERROR_DATE_NOT_PARSED);
			state_.setIsValid(false);
			return null;
		}
	}

	 
	private int getPositionIndex() {
		return Constant.VALUE_DEFAULT_POSITION_INDEX;
	}

	 
	private String getContent() {
		if(state_.getIsStartDateChanged()){
			String wordList[] = content_.split("from:");
			if(hasContentSplit(wordList) || getFirstWord(wordList).isEmpty()){
				state_.setDisplayMessage(Constant.VALUE_ERROR_NO_INPUT);
				state_.setIsContentChanged(false);
				state_.setIsValid(false);
				return Constant.VALUE_DEFAULT_EMPTY;
			}
			state_.setIsContentChanged(true);
			return getFirstWord(wordList);
		}else if(state_.getIsEndDateChanged()){
			String wordList[] = content_.split("on:");
			if(hasContentSplit(wordList) || getFirstWord(wordList).isEmpty()){
				state_.setDisplayMessage(Constant.VALUE_ERROR_NO_INPUT);
				state_.setIsContentChanged(false);
				state_.setIsValid(false);
				return Constant.VALUE_DEFAULT_EMPTY;
			}
			state_.setIsContentChanged(true);
			return getFirstWord(wordList);
		}else if(state_.getIsVenueChanged()){
			String wordList[] = content_.split("at:");
			if(hasContentSplit(wordList) || getFirstWord(wordList).isEmpty()){
				state_.setDisplayMessage(Constant.VALUE_ERROR_NO_INPUT);
				state_.setIsContentChanged(false);
				state_.setIsValid(false);
				return Constant.VALUE_DEFAULT_EMPTY;
			}
			if(state_.getIsDetailChanged()){
				String wordListDetail[] = splitDetail(getFirstWord(wordList));
				if(hasContentSplit(wordList) || getFirstWord(wordList).isEmpty()){
					state_.setIsContentChanged(true);
					return getFirstWord(wordList);
				}else{
					state_.setIsContentChanged(true);
					return getFirstWord(wordListDetail);
				}
			}else{
				state_.setIsContentChanged(true);
				return getFirstWord(wordList);
			}
		}else if(state_.getIsDetailChanged()){
			String wordList[] = splitDetail(content_);
			if(hasContentSplit(wordList) || getFirstWord(wordList).isEmpty()){
				state_.setDisplayMessage(Constant.VALUE_ERROR_NO_INPUT);
				state_.setIsContentChanged(false);
				state_.setIsValid(false);
				return Constant.VALUE_DEFAULT_EMPTY;
			}
			if(state_.getIsVenueChanged()){
				String wordListVenue[] = getFirstWord(wordList).split("at:");
				if(hasContentSplit(wordList) || getFirstWord(wordList).isEmpty()){
					state_.setIsContentChanged(true);
					return getFirstWord(wordList);
				}else{
					state_.setIsContentChanged(true);
					return getFirstWord(wordListVenue);
				}
			}else{
				state_.setIsContentChanged(true);
				return getFirstWord(wordList);
			}
		}else{
			state_.setIsContentChanged(true);
			return content_;
		}
	}


	 
	private TaskType getTaskType() {
		if(state_.getIsEndDateChanged()){
			return TaskType.DEADLINE;
		}else{
			return TaskType.FLOATING;
		}
	}

	 
	private ArrayList<String> getSearchKey() {
		return new ArrayList<String>();
	}
	
	 
	private ViewMode getNewViewMode() {
		return ViewMode.UNDEFINED;
	}
	

	/**
	 * Check whether the content can be split
	 * @param wordList
	 * @return True if it can't
	 */
	private boolean hasContentSplit(String[] wordList) {
		return wordList.length <= 1;
	}
	
	/**
	 * Split the content using the detail keyword
	 * @param content
	 * @return A list of split content
	 */
	private String[] splitDetail(String content) {
		return content.split("detail:");
	}
	
	private String getFirstWord(String[] wordListVenue) {
		return wordListVenue[0].trim();
	}

	private String getLastWord(String[] wordList) {
		return wordList[wordList.length-1].trim();
	}
}
