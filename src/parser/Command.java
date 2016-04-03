package parser;

import common.State;
import common.TaskType;
import common.ViewMode;

import java.util.ArrayList;
import java.util.Date;

interface Command {	
	public void processInput();
	public default String getContentWithoutCommand(State state_){
		String inputWords[] = state_.getUserInput().split(" ");
		StringBuilder sb = new StringBuilder("");
		for(int i = 1; i < inputWords.length; i ++){
			sb.append(inputWords[i]);
			sb.append(" ");
		}
		return sb.toString().trim();
	};
	public String getDetail();
	public String getVenue();
	public Date getStartDate();
	public Date getEndDate();
	public int getPositionIndex();
	public String getContent();
	public TaskType getTaskType();
	public ArrayList<String> getSearchKey();
	public ViewMode getNewViewMode();
}
