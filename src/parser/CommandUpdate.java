//@@author A0130369H
package parser;

import java.util.ArrayList;
import java.util.Date;

import common.Constant;
import common.State;
import common.TaskType;
import common.TimeParser;
import common.ViewMode;

public class CommandUpdate implements Command{
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
	public CommandUpdate(State state){
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
		String content = getContentWithoutIndex();
		String wordList[] = splitDetail(content);
		if(hasContentSplit(wordList)){
			state_.setIsDetailChanged(false);
			return Constant.VALUE_DEFAULT_EMPTY;
		}else{
			// Check if the content has venue specified
			String wordListVenue[] = splitVenue(getLastWord(wordList));
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
		String content = getContentWithoutIndex();
		String wordList[] = splitVenue(content);
		if(hasContentSplit(wordList)){
			state_.setIsVenueChanged(false);
			return Constant.VALUE_DEFAULT_EMPTY;
		}else{
			//Check if venue has detail specified
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
		String wordList[] = splitStartDate(content_);
		if(hasContentSplit(wordList)){
			state_.setIsStartDateChanged(false);
			return null;
		}
		//Check whether has end date specified
		String endDate = getLastWord(wordList);
		String wordListEnd[] = splitEndDateWithStartDate(endDate);
		
		//If not end date specified, don't parse the start date
		if(hasContentSplit(wordListEnd)){
			state_.setIsStartDateChanged(false);
			state_.setIsValid(false);
			state_.setDisplayMessage(Constant.VALUE_ERROR_DATE_NOT_PARSED);
			return null;
		}
		Date date = TimeParser.stringToDate(getFirstWord(wordListEnd));
		if(!isDateNull(date)){
			state_.setIsStartDateChanged(true);
			return date;
		}else{
			state_.setIsStartDateChanged(false);
			state_.setIsValid(false);
			state_.setDisplayMessage(Constant.VALUE_ERROR_DATE_NOT_PARSED);
			return null;
		}
	}

	 
	private Date getEndDate() {
		// If has start date
		if(state_.getIsStartDateChanged()){
			String wordList[] = splitEndDateWithStartDate(content_);
			if(hasContentSplit(wordList)){
				state_.setIsEndDateChanged(false);
				state_.setIsStartDateChanged(false);
				return null;
			}
			Date date = TimeParser.stringToDate(getLastWord(wordList));
			if(!isDateNull(date)){
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
				state_.setIsEndDateChanged(false);
				state_.setIsStartDateChanged(false);
				state_.setDisplayMessage(Constant.VALUE_ERROR_DATE_NOT_PARSED);
				state_.setIsValid(false);
				return null;
			}
		}
		//If don't have start date
		String wordList[] = splitEndDate(content_);
		if(hasContentSplit(wordList)){
			state_.setIsEndDateChanged(false);
			return null;
		}
		Date date = TimeParser.stringToDate(getLastWord(wordList));
		if(!isDateNull(date)){
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
		String wordList[] = content_.split(" ");
		return Integer.parseInt(getFirstWord(wordList));
	}

	 
	private String getContent() {
		String content = getContentWithoutIndex();
		// If there is start date
		if(state_.getIsStartDateChanged()){
			String wordList[] = splitStartDate(content);
			if(hasContentSplit(wordList) || getFirstWord(wordList).isEmpty()){
				state_.setIsContentChanged(false);
				return Constant.VALUE_DEFAULT_EMPTY;
			}
			state_.setIsContentChanged(true);
			return getFirstWord(wordList);
		//If there is only end date
		}else if(state_.getIsEndDateChanged()){
			String wordList[] = splitEndDate(content);
			if(hasContentSplit(wordList) || getFirstWord(wordList).isEmpty()){
				state_.setIsContentChanged(false);
				return Constant.VALUE_DEFAULT_EMPTY;
			}
			state_.setIsContentChanged(true);
			return getFirstWord(wordList);
		
		//If there is venue
		}else if(state_.getIsVenueChanged()){
			String wordList[] = splitVenue(content);
			if(hasContentSplit(wordList) || getFirstWord(wordList).isEmpty()){
				state_.setIsContentChanged(false);
				return Constant.VALUE_DEFAULT_EMPTY;
			}
			// Check if there is detail
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
		//If there is detail
		}else if(state_.getIsDetailChanged()){
			String wordList[] = splitDetail(content);
			if(hasContentSplit(wordList) || getFirstWord(wordList).isEmpty()){
				state_.setIsContentChanged(false);
				return Constant.VALUE_DEFAULT_EMPTY;
			}
			//Check if there is venue
			if(state_.getIsVenueChanged()){
				String wordListVenue[] = splitVenue(getFirstWord(wordList));
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
			if(content.trim().isEmpty()){
				state_.setDisplayMessage(Constant.VALUE_ERROR_NO_INPUT);
			}
			if(isContentInvalid(content)){
				state_.setIsContentChanged(false);
				state_.setDisplayMessage(Constant.VALUE_ERROR_WRONG_FORMAT);
			}else{
				state_.setIsContentChanged(true);
			}
			return content;
		}
	}

	/**
	 * Get the content of the input without the index
	 * @return String of the content
	 */
	private String getContentWithoutIndex() {
		String inputWords[] = content_.split(" ");
		StringBuilder sb = new StringBuilder("");
		for(int i = 1; i < inputWords.length; i ++){
			sb.append(inputWords[i]);
			sb.append(" ");
		}
		return sb.toString().trim();
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

}