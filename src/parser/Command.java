//@@author A0130369H
package parser;

import common.State;
import common.TaskType;
import common.ViewMode;

import java.util.ArrayList;
import java.util.Date;

interface Command {
	//====================================
	//       Public Functions
	//====================================
	public void processInput();
	
	//====================================
	//       Helper Functions
	//====================================
	default String getContentWithoutCommand(State state){
		String inputWords[] = state.getUserInput().split(" ");
		StringBuilder sb = new StringBuilder("");
		for(int i = 1; i < inputWords.length; i ++){
			sb.append(inputWords[i]);
			sb.append(" ");
		}
		return sb.toString().trim();
	}
	
	/**
	 * Check whether the content can be split
	 * @param wordList
	 * @return True if it can't
	 */
	default boolean hasContentSplit(String[] wordList) {
		return wordList.length <= 1;
	}
	
	/**
	 * Split the content using the detail keyword
	 * @param content
	 * @return A list of split content
	 */
	default String[] splitDetail(String content) {
		return content.split("detail:");
	}
	
	default String getFirstWord(String[] wordListVenue) {
		return wordListVenue[0].trim();
	}

	default String getLastWord(String[] wordList) {
		return wordList[wordList.length-1].trim();
	}
	
	default String[] splitVenue(String content) {
		return content.split("at:");
	}
}
