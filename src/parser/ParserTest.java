package parser;

import common.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class ParserTest {
	
	@Test
	public void testCommand(){
		// instantiate State and Parser class for testing
		State state = new State();
		Parser parser = new Parser(state);
		
		//Test Add Float
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
		
		//Test Delete Float
		state.setUserInput("Delete 2 float");
		parser.processInput();
		assertEquals(state.getIsValid(), true);
		assertEquals(state.getCommand(), CommandType.DELETE);
		assertEquals(state.getContent(), "2 float");
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_NO_ERROR);
		assertEquals(state.getTaskType(), TaskType.FLOATING);
		assertEquals(state.getPosition(), 2);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);
		
		//Test Tick Float
		state.setUserInput("Tick 3 float");
		parser.processInput();
		assertEquals(state.getIsValid(), true);
		assertEquals(state.getCommand(), CommandType.TICK);
		assertEquals(state.getContent(), "3 float");
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_NO_ERROR);
		assertEquals(state.getTaskType(), TaskType.FLOATING);
		assertEquals(state.getPosition(), 3);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);
		
		//Test Update Float
		state.setUserInput("Update 4 float eat bingsu");
		parser.processInput();
		assertEquals(state.getIsValid(), true);
		assertEquals(state.getCommand(), CommandType.UPDATE);
		assertEquals(state.getContent(), "eat bingsu");
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_NO_ERROR);
		assertEquals(state.getTaskType(), TaskType.FLOATING);
		assertEquals(state.getPosition(), 4);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);
		
		//Test Add Deadline
		state.setUserInput("Add eat lunch on 02/03/04 05:06");
		parser.processInput();
		assertEquals(state.getIsValid(), true);
		assertEquals(state.getCommand(), CommandType.ADD);
		assertEquals(state.getContent(), "eat lunch");
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_NO_ERROR);
		assertEquals(state.getTaskType(), TaskType.DEADLINE);
		assertEquals(state.getPosition(), 0);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), true);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), TimeParser.stringToDate("02/03/04 05:06"));
		
		//Test Delete Deadline
		state.setUserInput("Delete 2 deadline");
		parser.processInput();
		assertEquals(state.getIsValid(), true);
		assertEquals(state.getCommand(), CommandType.DELETE);
		assertEquals(state.getContent(), "2 deadline");
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_NO_ERROR);
		assertEquals(state.getTaskType(), TaskType.DEADLINE);
		assertEquals(state.getPosition(), 2);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);

		//Test Update Deadline
		state.setUserInput("Update 3 deadline eat dinner");
		parser.processInput();
		assertEquals(state.getIsValid(), true);
		assertEquals(state.getCommand(), CommandType.UPDATE);
		assertEquals(state.getContent(), "eat dinner");
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_NO_ERROR);
		assertEquals(state.getTaskType(), TaskType.DEADLINE);
		assertEquals(state.getPosition(), 3);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);

		//Test Clear Deadline
		state.setUserInput("Clear deadline");
		parser.processInput();
		assertEquals(state.getIsValid(), true);
		assertEquals(state.getCommand(), CommandType.CLEAR);
		assertEquals(state.getContent(), "deadline");
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_NO_ERROR);
		assertEquals(state.getTaskType(), TaskType.DEADLINE);
		assertEquals(state.getPosition(), 0);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);

		//Test Tick Deadline
		state.setUserInput("Tick 5 deadline");
		parser.processInput();
		assertEquals(state.getIsValid(), true);
		assertEquals(state.getCommand(), CommandType.TICK);
		assertEquals(state.getContent(), "5 deadline");
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_NO_ERROR);
		assertEquals(state.getTaskType(), TaskType.DEADLINE);
		assertEquals(state.getPosition(), 5);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);

//		// Test Venue
//		//Test Tick Deadline
//		state.setUserInput("add eat lunch at: soc");
//		parser.processInput();
//		assertEquals(state.getIsValid(), true);
//		assertEquals(state.getCommand(), CommandType.ADD);
//		assertEquals(state.getContent(), "");
//		assertEquals(state.getMessage(), Constant.VALUE_ERROR_NO_ERROR);
//		assertEquals(state.getTaskType(), TaskType.FLOATING);
//		assertEquals(state.getPosition(), 2);
//		assertEquals(state.getIsStartDate(), false);
//		assertEquals(state.getIsEndDate(), false);
//		assertEquals(state.getStartDate(), null);
//		assertEquals(state.getEndDate(), null);		
	}
	
	public void testError(){
		// instantiate State and Parser class for testing
		State state = new State();
		Parser parser = new Parser(state);
		
		//Test empty input
		state.setUserInput("");
		parser.processInput();
		assertEquals(state.getIsValid(), false);
		assertEquals(state.getCommand(), CommandType.UNDEFINED);
		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_NO_INPUT);
		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
		assertEquals(state.getPosition(), 0);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);

		//Test empty input
		state.setUserInput(" ");
		parser.processInput();
		assertEquals(state.getIsValid(), false);
		assertEquals(state.getCommand(), CommandType.UNDEFINED);
		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_NO_INPUT);
		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
		assertEquals(state.getPosition(), 0);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);
		
		//Test invalid command
		state.setUserInput("adds eat lunch");
		parser.processInput();
		assertEquals(state.getIsValid(), false);
		assertEquals(state.getCommand(), CommandType.UNDEFINED);
		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_COMMAND_NOT_FOUND);
		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
		assertEquals(state.getPosition(), 0);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);
		
		//Test invalid argument: ADD space
		state.setUserInput("add ");
		parser.processInput();
		assertEquals(state.getIsValid(), false);
		assertEquals(state.getCommand(), CommandType.UNDEFINED);
		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
		assertEquals(state.getPosition(), 0);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);
		
		//Test invalid argument: ADD empty
		state.setUserInput("add");
		parser.processInput();
		assertEquals(state.getIsValid(), false);
		assertEquals(state.getCommand(), CommandType.UNDEFINED);
		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
		assertEquals(state.getPosition(), 0);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);
		
		//Test invalid argument: Clear wrong argument
		state.setUserInput("clear add");
		parser.processInput();
		assertEquals(state.getIsValid(), false);
		assertEquals(state.getCommand(), CommandType.UNDEFINED);
		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
		assertEquals(state.getPosition(), 0);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);
		
		//Test invalid argument: CLEAR no argument
		state.setUserInput("clear");
		parser.processInput();
		assertEquals(state.getIsValid(), false);
		assertEquals(state.getCommand(), CommandType.UNDEFINED);
		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
		assertEquals(state.getPosition(), 0);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);
		
		//Test invalid argument: UPDATE no index
		state.setUserInput("update float eat dinner");
		parser.processInput();
		assertEquals(state.getIsValid(), false);
		assertEquals(state.getCommand(), CommandType.UNDEFINED);
		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
		assertEquals(state.getPosition(), 0);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);

		//Test invalid argument: UPDATE wrong index
		state.setUserInput("update two float eat dinner");
		parser.processInput();
		assertEquals(state.getIsValid(), false);
		assertEquals(state.getCommand(), CommandType.UNDEFINED);
		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
		assertEquals(state.getPosition(), 0);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);
		
		//Test invalid argument: UPDATE wrong type
		state.setUserInput("update 2 floats eat dinner");
		parser.processInput();
		assertEquals(state.getIsValid(), false);
		assertEquals(state.getCommand(), CommandType.UNDEFINED);
		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
		assertEquals(state.getPosition(), 0);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);
		
		//Test invalid argument: UPDATE no content
		state.setUserInput("update 2 float");
		parser.processInput();
		assertEquals(state.getIsValid(), false);
		assertEquals(state.getCommand(), CommandType.UNDEFINED);
		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
		assertEquals(state.getPosition(), 0);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);

		//Test invalid argument: UPDATE no index
		state.setUserInput("update float eat dinner");
		parser.processInput();
		assertEquals(state.getIsValid(), false);
		assertEquals(state.getCommand(), CommandType.UNDEFINED);
		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
		assertEquals(state.getPosition(), 0);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);

		//Test invalid argument: Delete no index
		state.setUserInput("delete float");
		parser.processInput();
		assertEquals(state.getIsValid(), false);
		assertEquals(state.getCommand(), CommandType.UNDEFINED);
		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
		assertEquals(state.getPosition(), 0);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);
		
		//Test invalid argument: Delete wrong index
		state.setUserInput("delete two float");
		parser.processInput();
		assertEquals(state.getIsValid(), false);
		assertEquals(state.getCommand(), CommandType.UNDEFINED);
		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
		assertEquals(state.getPosition(), 0);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);
		
		//Test invalid argument: Delete wrong type
		state.setUserInput("delete 2 floats");
		parser.processInput();
		assertEquals(state.getIsValid(), false);
		assertEquals(state.getCommand(), CommandType.UNDEFINED);
		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
		assertEquals(state.getPosition(), 0);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);
		
		//Test invalid argument: Tick no index
		state.setUserInput("TICK float");
		parser.processInput();
		assertEquals(state.getIsValid(), false);
		assertEquals(state.getCommand(), CommandType.UNDEFINED);
		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
		assertEquals(state.getPosition(), 0);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);
		
		//Test invalid argument: Delete wrong index
		state.setUserInput("TICK two float");
		parser.processInput();
		assertEquals(state.getIsValid(), false);
		assertEquals(state.getCommand(), CommandType.UNDEFINED);
		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
		assertEquals(state.getPosition(), 0);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);
		
		//Test invalid argument: Delete wrong type
		state.setUserInput("tick 2 floats");
		parser.processInput();
		assertEquals(state.getIsValid(), false);
		assertEquals(state.getCommand(), CommandType.UNDEFINED);
		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
		assertEquals(state.getMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
		assertEquals(state.getPosition(), 0);
		assertEquals(state.getIsStartDate(), false);
		assertEquals(state.getIsEndDate(), false);
		assertEquals(state.getStartDate(), null);
		assertEquals(state.getEndDate(), null);

	}

}
