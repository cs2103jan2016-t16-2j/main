package parser;

import common.TaskType;
import common.ViewMode;

import java.util.ArrayList;
import java.util.Date;

interface Command {	
	public void processInput();
	public String getDetail();
	public String getVenue();
	public Date getStartDate();
	public Date getEndDate();
	public int getPositionIndex();
	public String getContent();
	public TaskType getTaskType();
	public ArrayList<String> getSearchKey();
	public ViewMode getNewViewMode();
	public String getContentWithoutCommand();
}
