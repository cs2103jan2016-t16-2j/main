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
		
	default String getContentWithoutCommand(State state){
		String inputWords[] = state.getUserInput().split(" ");
		StringBuilder sb = new StringBuilder("");
		for(int i = 1; i < inputWords.length; i ++){
			sb.append(inputWords[i]);
			sb.append(" ");
		}
		return sb.toString().trim();
	}
}
