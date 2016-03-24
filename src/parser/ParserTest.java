package parser;

import common.*;
import static org.junit.Assert.*;
import org.junit.Test;
import parser.*;
import storage.Storage;

public class ParserTest {
	
	@Test
	public void testCommand(){
		// instantiate State and Parser class for testing
		State state = new State();
		Parser parser = new Parser(state);
		
		//Test floating tasks
		state.setUserInput("Add eat lunch");
		parser.processInput();
		assertEquals(state.getIsValid(), true);
		assertEquals(state.getCommand(), CommandType.ADD);
		assertEquals(state.getContent(), "eat lunch");
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_NO_ERROR);
		assertEquals(state.getTaskType(), TaskType.FLOATING);
		assertEquals(state.getPosition(), 0);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);
		
		//Test Clear
		state.setUserInput("Clear float");
		parser.processInput();
		assertEquals(state.getIsValid(), true);
		assertEquals(state.getCommand(), CommandType.CLEAR);
		assertEquals(state.getContent(), "float");
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_NO_ERROR);
		assertEquals(state.getTaskType(), TaskType.FLOATING);
		assertEquals(state.getPosition(), 0);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);
		
	}

}
