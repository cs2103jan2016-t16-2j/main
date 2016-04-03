package parser;

import java.util.Date;

interface Command {
	public void processInput();
	public String getRawContent();
	public String getDetail();
	public String getVenue();
	public Date getStartDate();
	public Date getEndDate();
	public int getIndex();
	public String getContent();
	public String getType();
	public String getSearchKey();
}
