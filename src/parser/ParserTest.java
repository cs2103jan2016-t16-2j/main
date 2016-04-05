package parser;

import common.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class ParserTest {
	
	@Test
	public void testCommandAdd(){
		// instantiate State and Parser class for testing
		State state = new State();
		Parser parser = new Parser(state);
		
		//Test Add Float
		state.setUserInput("Add eat lunch");
		parser.processInput();
		assertEquals(true, state.getIsValid());
		assertEquals(Constant.VALUE_ERROR_NO_ERROR, state.getDisplayMessage());
		assertEquals(CommandType.ADD, state.getCommandType());
		assertEquals(false, state.getIsStartDateChanged());
		assertEquals(false, state.getIsEndDateChanged());
		assertEquals(false, state.getIsVenueChanged());
		assertEquals(false, state.getIsDetailChanged());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getVenue());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getDetail());
		assertEquals("eat lunch", state.getContent());
		assertEquals(TaskType.FLOATING, state.getTaskType());
		assertEquals(1, state.getPositionIndex());
		assertEquals(null, state.getStartDate());
		assertEquals(null, state.getEndDate());

		//Test Add Float
		state.setUserInput("Add eat lunch details: with boyfriend");
		parser.processInput();
		assertEquals(true, state.getIsValid());
		assertEquals(Constant.VALUE_ERROR_NO_ERROR, state.getDisplayMessage());
		assertEquals(CommandType.ADD, state.getCommandType());
		assertEquals(false, state.getIsStartDateChanged());
		assertEquals(false, state.getIsEndDateChanged());
		assertEquals(false, state.getIsVenueChanged());
		assertEquals(true, state.getIsDetailChanged());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getVenue());
		assertEquals("with boyfriend", state.getDetail());
		assertEquals("eat lunch", state.getContent());
		assertEquals(TaskType.FLOATING, state.getTaskType());
		assertEquals(1, state.getPositionIndex());
		assertEquals(null, state.getStartDate());
		assertEquals(null, state.getEndDate());
		
		//Test Add Deadline
		state.setUserInput("Add eat lunch on: 12/12/12");
		parser.processInput();
		assertEquals(true, state.getIsValid());
		assertEquals(Constant.VALUE_ERROR_NO_ERROR, state.getDisplayMessage());
		assertEquals(CommandType.ADD, state.getCommandType());
		assertEquals(false, state.getIsStartDateChanged());
		assertEquals(true, state.getIsEndDateChanged());
		assertEquals(false, state.getIsVenueChanged());
		assertEquals(false, state.getIsDetailChanged());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getVenue());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getDetail());
		assertEquals("eat lunch", state.getContent());
		assertEquals(TaskType.DEADLINE, state.getTaskType());
		assertEquals(1, state.getPositionIndex());
		assertEquals(null, state.getStartDate());
		assertEquals(TimeParser.stringToDate("12/12/12"), state.getEndDate());
		
		//Test Add Deadline
		state.setUserInput("Add eat lunch from: 10/10/10 10:10 to: 12/12/12 12:12");
		parser.processInput();
		assertEquals(true, state.getIsValid());
		assertEquals(Constant.VALUE_ERROR_NO_ERROR, state.getDisplayMessage());
		assertEquals(CommandType.ADD, state.getCommandType());
		assertEquals(true, state.getIsStartDateChanged());
		assertEquals(true, state.getIsEndDateChanged());
		assertEquals(false, state.getIsVenueChanged());
		assertEquals(false, state.getIsDetailChanged());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getVenue());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getDetail());
		assertEquals("eat lunch", state.getContent());
		assertEquals(TaskType.DEADLINE, state.getTaskType());
		assertEquals(1, state.getPositionIndex());
		assertEquals(TimeParser.stringToDate("10/10/10 10:10"), state.getStartDate());
		assertEquals(TimeParser.stringToDate("12/12/12 12:12"), state.getEndDate());
		
		//Test Add Deadline
		state.setUserInput("Add eat lunch from: 10/10/10 10:10 to: 12/12/12 12:12 at: TOA PAYOH");
		parser.processInput();
		assertEquals(true, state.getIsValid());
		assertEquals(Constant.VALUE_ERROR_NO_ERROR, state.getDisplayMessage());
		assertEquals(CommandType.ADD, state.getCommandType());
		assertEquals(true, state.getIsStartDateChanged());
		assertEquals(true, state.getIsEndDateChanged());
		assertEquals(true, state.getIsVenueChanged());
		assertEquals(false, state.getIsDetailChanged());
		assertEquals("TOA PAYOH", state.getVenue());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getDetail());
		assertEquals("eat lunch", state.getContent());
		assertEquals(TaskType.DEADLINE, state.getTaskType());
		assertEquals(1, state.getPositionIndex());
		assertEquals(TimeParser.stringToDate("10/10/10 10:10"), state.getStartDate());
		assertEquals(TimeParser.stringToDate("12/12/12 12:12"), state.getEndDate());		
		
		//Test Add Deadline
		state.setUserInput("Add eat lunch from: 10/10/10 10:10 to: 12/12/12 12:12 at: TOA PAYOH details: with boyfriend");
		parser.processInput();
		assertEquals(true, state.getIsValid());
		assertEquals(Constant.VALUE_ERROR_NO_ERROR, state.getDisplayMessage());
		assertEquals(CommandType.ADD, state.getCommandType());
		assertEquals(true, state.getIsStartDateChanged());
		assertEquals(true, state.getIsEndDateChanged());
		assertEquals(true, state.getIsVenueChanged());
		assertEquals(true, state.getIsDetailChanged());
		assertEquals("TOA PAYOH", state.getVenue());
		assertEquals("with boyfriend", state.getDetail());
		assertEquals("eat lunch", state.getContent());
		assertEquals(TaskType.DEADLINE, state.getTaskType());
		assertEquals(1, state.getPositionIndex());
		assertEquals(TimeParser.stringToDate("10/10/10 10:10"), state.getStartDate());
		assertEquals(TimeParser.stringToDate("12/12/12 12:12"), state.getEndDate());
		
		//Test Add Deadline
		state.setUserInput("Add eat lunch from: 10/10/10 10:10 to: 12/12/12 12:12 details: with boyfriend at: TOA PAYOH ");
		parser.processInput();
		assertEquals(true, state.getIsValid());
		assertEquals(Constant.VALUE_ERROR_NO_ERROR, state.getDisplayMessage());
		assertEquals(CommandType.ADD, state.getCommandType());
		assertEquals(true, state.getIsStartDateChanged());
		assertEquals(true, state.getIsEndDateChanged());
		assertEquals(true, state.getIsVenueChanged());
		assertEquals(true, state.getIsDetailChanged());
		assertEquals("TOA PAYOH", state.getVenue());
		assertEquals("with boyfriend", state.getDetail());
		assertEquals("eat lunch", state.getContent());
		assertEquals(TaskType.DEADLINE, state.getTaskType());
		assertEquals(1, state.getPositionIndex());
		assertEquals(TimeParser.stringToDate("10/10/10 10:10"), state.getStartDate());
		assertEquals(TimeParser.stringToDate("12/12/12 12:12"), state.getEndDate());

		//Test Add Deadline
		state.setUserInput("Add eat lunch from: 10/10/10 10:10 to: 12/12/12 12:12 details: with boyfriend");
		parser.processInput();
		assertEquals(true, state.getIsValid());
		assertEquals(Constant.VALUE_ERROR_NO_ERROR, state.getDisplayMessage());
		assertEquals(CommandType.ADD, state.getCommandType());
		assertEquals(true, state.getIsStartDateChanged());
		assertEquals(true, state.getIsEndDateChanged());
		assertEquals(false, state.getIsVenueChanged());
		assertEquals(true, state.getIsDetailChanged());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getVenue());
		assertEquals("with boyfriend", state.getDetail());
		assertEquals("eat lunch", state.getContent());
		assertEquals(TaskType.DEADLINE, state.getTaskType());
		assertEquals(1, state.getPositionIndex());
		assertEquals(TimeParser.stringToDate("10/10/10 10:10"), state.getStartDate());
		assertEquals(TimeParser.stringToDate("12/12/12 12:12"), state.getEndDate());

		//Test Add Deadline
		state.setUserInput("Add eat lunch from:  to: 10/10/10 10:10 details: with boyfriend");
		parser.processInput();
		assertEquals(true, state.getIsValid());
		assertEquals(Constant.VALUE_ERROR_DATE_NOT_PARSED, state.getDisplayMessage());
		assertEquals(CommandType.ADD, state.getCommandType());
		assertEquals(false, state.getIsStartDateChanged());
		assertEquals(false, state.getIsEndDateChanged());
		assertEquals(false, state.getIsVenueChanged());
		assertEquals(true, state.getIsDetailChanged());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getVenue());
		assertEquals("with boyfriend", state.getDetail());
		//assertEquals("eat lunch", state.getContent());
		assertEquals(TaskType.FLOATING, state.getTaskType());
		assertEquals(1, state.getPositionIndex());
		assertEquals(null, state.getStartDate());
		assertEquals(null, state.getEndDate());

	}
	
	@Test
	public void testCommandOthers(){
		// instantiate State and Parser class for testing
		State state = new State();
		Parser parser = new Parser(state);
		
		//TEST CLEAR
		state.setUserInput("clear");
		parser.processInput();
		assertEquals(true, state.getIsValid());
		assertEquals(Constant.VALUE_ERROR_NO_ERROR, state.getDisplayMessage());
		assertEquals(CommandType.CLEAR, state.getCommandType());
		assertEquals(false, state.getIsStartDateChanged());
		assertEquals(false, state.getIsEndDateChanged());
		assertEquals(false, state.getIsVenueChanged());
		assertEquals(false, state.getIsDetailChanged());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getVenue());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getDetail());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getContent());
		assertEquals(TaskType.UNDEFINED, state.getTaskType());
		assertEquals(1, state.getPositionIndex());
		assertEquals(null, state.getStartDate());
		assertEquals(null, state.getEndDate());
		
		//TEST delete
		state.setUserInput("delete 3");
		parser.processInput();
		assertEquals(true, state.getIsValid());
		assertEquals(Constant.VALUE_ERROR_NO_ERROR, state.getDisplayMessage());
		assertEquals(CommandType.DELETE, state.getCommandType());
		assertEquals(false, state.getIsStartDateChanged());
		assertEquals(false, state.getIsEndDateChanged());
		assertEquals(false, state.getIsVenueChanged());
		assertEquals(false, state.getIsDetailChanged());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getVenue());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getDetail());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getContent());
		assertEquals(TaskType.UNDEFINED, state.getTaskType());
		assertEquals(3, state.getPositionIndex());
		assertEquals(null, state.getStartDate());
		assertEquals(null, state.getEndDate());
		
		//TEST tick
		state.setUserInput("tick 3");
		parser.processInput();
		assertEquals(true, state.getIsValid());
		assertEquals(Constant.VALUE_ERROR_NO_ERROR, state.getDisplayMessage());
		assertEquals(CommandType.TICK, state.getCommandType());
		assertEquals(false, state.getIsStartDateChanged());
		assertEquals(false, state.getIsEndDateChanged());
		assertEquals(false, state.getIsVenueChanged());
		assertEquals(false, state.getIsDetailChanged());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getVenue());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getDetail());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getContent());
		assertEquals(TaskType.UNDEFINED, state.getTaskType());
		assertEquals(3, state.getPositionIndex());
		assertEquals(null, state.getStartDate());
		assertEquals(null, state.getEndDate());
		
		//TEST tick
		state.setUserInput("redo");
		parser.processInput();
		assertEquals(true, state.getIsValid());
		assertEquals(Constant.VALUE_ERROR_NO_ERROR, state.getDisplayMessage());
		assertEquals(CommandType.REDO, state.getCommandType());
		assertEquals(false, state.getIsStartDateChanged());
		assertEquals(false, state.getIsEndDateChanged());
		assertEquals(false, state.getIsVenueChanged());
		assertEquals(false, state.getIsDetailChanged());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getVenue());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getDetail());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getContent());
		assertEquals(TaskType.UNDEFINED, state.getTaskType());
		assertEquals(1, state.getPositionIndex());
		assertEquals(null, state.getStartDate());
		assertEquals(null, state.getEndDate());
		
		//TEST tick
		state.setUserInput("undo");
		parser.processInput();
		assertEquals(true, state.getIsValid());
		assertEquals(Constant.VALUE_ERROR_NO_ERROR, state.getDisplayMessage());
		assertEquals(CommandType.UNDO, state.getCommandType());
		assertEquals(false, state.getIsStartDateChanged());
		assertEquals(false, state.getIsEndDateChanged());
		assertEquals(false, state.getIsVenueChanged());
		assertEquals(false, state.getIsDetailChanged());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getVenue());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getDetail());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getContent());
		assertEquals(TaskType.UNDEFINED, state.getTaskType());
		assertEquals(1, state.getPositionIndex());
		assertEquals(null, state.getStartDate());
		assertEquals(null, state.getEndDate());
		
		//TEST tick
		state.setUserInput("exit");
		parser.processInput();
		assertEquals(true, state.getIsValid());
		assertEquals(Constant.VALUE_ERROR_NO_ERROR, state.getDisplayMessage());
		assertEquals(CommandType.EXIT, state.getCommandType());
		assertEquals(false, state.getIsStartDateChanged());
		assertEquals(false, state.getIsEndDateChanged());
		assertEquals(false, state.getIsVenueChanged());
		assertEquals(false, state.getIsDetailChanged());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getVenue());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getDetail());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getContent());
		assertEquals(TaskType.UNDEFINED, state.getTaskType());
		assertEquals(1, state.getPositionIndex());
		assertEquals(null, state.getStartDate());
		assertEquals(null, state.getEndDate());
		
		state.setUserInput("view 1");
		parser.processInput();
		assertEquals(true, state.getIsValid());
		assertEquals(Constant.VALUE_ERROR_NO_ERROR, state.getDisplayMessage());
		assertEquals(CommandType.DETAIL, state.getCommandType());
		assertEquals(false, state.getIsStartDateChanged());
		assertEquals(false, state.getIsEndDateChanged());
		assertEquals(false, state.getIsVenueChanged());
		assertEquals(false, state.getIsDetailChanged());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getVenue());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getDetail());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getContent());
		assertEquals(TaskType.UNDEFINED, state.getTaskType());
		assertEquals(1, state.getPositionIndex());
		assertEquals(null, state.getStartDate());
		assertEquals(null, state.getEndDate());
		
		state.setUserInput("view floating");
		parser.processInput();
		assertEquals(true, state.getIsValid());
		assertEquals(Constant.VALUE_ERROR_NO_ERROR, state.getDisplayMessage());
		assertEquals(CommandType.CHANGEMODE, state.getCommandType());
		assertEquals(false, state.getIsStartDateChanged());
		assertEquals(false, state.getIsEndDateChanged());
		assertEquals(false, state.getIsVenueChanged());
		assertEquals(false, state.getIsDetailChanged());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getVenue());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getDetail());
		assertEquals(Constant.VALUE_DEFAULT_EMPTY, state.getContent());
		assertEquals(TaskType.UNDEFINED, state.getTaskType());
		assertEquals(1, state.getPositionIndex());
		assertEquals(null, state.getStartDate());
		assertEquals(null, state.getEndDate());
	}
//	public void testError(){
//		// instantiate State and Parser class for testing
//		State state = new State();
//		Parser parser = new Parser(state);
//		
//		//Test empty input
//		state.setUserInput("");
//		parser.processInput();
//		assertEquals(state.getIsValid(), false);
//		assertEquals(state.getCommandType(), CommandType.UNDEFINED);
//		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
//		assertEquals(state.getDisplayMessage(), Constant.VALUE_ERROR_NO_INPUT);
//		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
//		assertEquals(state.getPositionIndex(), 0);
//		assertEquals(state.getIsStartDateChanged(), false);
//		assertEquals(state.getIsEndDateChanged(), false);
//		assertEquals(state.getStartDate(), null);
//		assertEquals(state.getEndDate(), null);
//
//		//Test empty input
//		state.setUserInput(" ");
//		parser.processInput();
//		assertEquals(state.getIsValid(), false);
//		assertEquals(state.getCommandType(), CommandType.UNDEFINED);
//		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
//		assertEquals(state.getDisplayMessage(), Constant.VALUE_ERROR_NO_INPUT);
//		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
//		assertEquals(state.getPositionIndex(), 0);
//		assertEquals(state.getIsStartDateChanged(), false);
//		assertEquals(state.getIsEndDateChanged(), false);
//		assertEquals(state.getStartDate(), null);
//		assertEquals(state.getEndDate(), null);
//		
//		//Test invalid command
//		state.setUserInput("adds eat lunch");
//		parser.processInput();
//		assertEquals(state.getIsValid(), false);
//		assertEquals(state.getCommandType(), CommandType.UNDEFINED);
//		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
//		assertEquals(state.getDisplayMessage(), Constant.VALUE_ERROR_COMMAND_NOT_FOUND);
//		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
//		assertEquals(state.getPositionIndex(), 0);
//		assertEquals(state.getIsStartDateChanged(), false);
//		assertEquals(state.getIsEndDateChanged(), false);
//		assertEquals(state.getStartDate(), null);
//		assertEquals(state.getEndDate(), null);
//		
//		//Test invalid argument: ADD space
//		state.setUserInput("add ");
//		parser.processInput();
//		assertEquals(state.getIsValid(), false);
//		assertEquals(state.getCommandType(), CommandType.UNDEFINED);
//		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
//		assertEquals(state.getDisplayMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
//		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
//		assertEquals(state.getPositionIndex(), 0);
//		assertEquals(state.getIsStartDateChanged(), false);
//		assertEquals(state.getIsEndDateChanged(), false);
//		assertEquals(state.getStartDate(), null);
//		assertEquals(state.getEndDate(), null);
//		
//		//Test invalid argument: ADD empty
//		state.setUserInput("add");
//		parser.processInput();
//		assertEquals(state.getIsValid(), false);
//		assertEquals(state.getCommandType(), CommandType.UNDEFINED);
//		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
//		assertEquals(state.getDisplayMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
//		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
//		assertEquals(state.getPositionIndex(), 0);
//		assertEquals(state.getIsStartDateChanged(), false);
//		assertEquals(state.getIsEndDateChanged(), false);
//		assertEquals(state.getStartDate(), null);
//		assertEquals(state.getEndDate(), null);
//		
//		//Test invalid argument: Clear wrong argument
//		state.setUserInput("clear add");
//		parser.processInput();
//		assertEquals(state.getIsValid(), false);
//		assertEquals(state.getCommandType(), CommandType.UNDEFINED);
//		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
//		assertEquals(state.getDisplayMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
//		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
//		assertEquals(state.getPositionIndex(), 0);
//		assertEquals(state.getIsStartDateChanged(), false);
//		assertEquals(state.getIsEndDateChanged(), false);
//		assertEquals(state.getStartDate(), null);
//		assertEquals(state.getEndDate(), null);
//		
//		//Test invalid argument: CLEAR no argument
//		state.setUserInput("clear");
//		parser.processInput();
//		assertEquals(state.getIsValid(), false);
//		assertEquals(state.getCommandType(), CommandType.UNDEFINED);
//		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
//		assertEquals(state.getDisplayMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
//		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
//		assertEquals(state.getPositionIndex(), 0);
//		assertEquals(state.getIsStartDateChanged(), false);
//		assertEquals(state.getIsEndDateChanged(), false);
//		assertEquals(state.getStartDate(), null);
//		assertEquals(state.getEndDate(), null);
//		
//		//Test invalid argument: UPDATE no index
//		state.setUserInput("update float eat dinner");
//		parser.processInput();
//		assertEquals(state.getIsValid(), false);
//		assertEquals(state.getCommandType(), CommandType.UNDEFINED);
//		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
//		assertEquals(state.getDisplayMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
//		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
//		assertEquals(state.getPositionIndex(), 0);
//		assertEquals(state.getIsStartDateChanged(), false);
//		assertEquals(state.getIsEndDateChanged(), false);
//		assertEquals(state.getStartDate(), null);
//		assertEquals(state.getEndDate(), null);
//
//		//Test invalid argument: UPDATE wrong index
//		state.setUserInput("update two float eat dinner");
//		parser.processInput();
//		assertEquals(state.getIsValid(), false);
//		assertEquals(state.getCommandType(), CommandType.UNDEFINED);
//		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
//		assertEquals(state.getDisplayMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
//		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
//		assertEquals(state.getPositionIndex(), 0);
//		assertEquals(state.getIsStartDateChanged(), false);
//		assertEquals(state.getIsEndDateChanged(), false);
//		assertEquals(state.getStartDate(), null);
//		assertEquals(state.getEndDate(), null);
//		
//		//Test invalid argument: UPDATE wrong type
//		state.setUserInput("update 2 floats eat dinner");
//		parser.processInput();
//		assertEquals(state.getIsValid(), false);
//		assertEquals(state.getCommandType(), CommandType.UNDEFINED);
//		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
//		assertEquals(state.getDisplayMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
//		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
//		assertEquals(state.getPositionIndex(), 0);
//		assertEquals(state.getIsStartDateChanged(), false);
//		assertEquals(state.getIsEndDateChanged(), false);
//		assertEquals(state.getStartDate(), null);
//		assertEquals(state.getEndDate(), null);
//		
//		//Test invalid argument: UPDATE no content
//		state.setUserInput("update 2 float");
//		parser.processInput();
//		assertEquals(state.getIsValid(), false);
//		assertEquals(state.getCommandType(), CommandType.UNDEFINED);
//		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
//		assertEquals(state.getDisplayMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
//		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
//		assertEquals(state.getPositionIndex(), 0);
//		assertEquals(state.getIsStartDateChanged(), false);
//		assertEquals(state.getIsEndDateChanged(), false);
//		assertEquals(state.getStartDate(), null);
//		assertEquals(state.getEndDate(), null);
//
//		//Test invalid argument: UPDATE no index
//		state.setUserInput("update float eat dinner");
//		parser.processInput();
//		assertEquals(state.getIsValid(), false);
//		assertEquals(state.getCommandType(), CommandType.UNDEFINED);
//		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
//		assertEquals(state.getDisplayMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
//		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
//		assertEquals(state.getPositionIndex(), 0);
//		assertEquals(state.getIsStartDateChanged(), false);
//		assertEquals(state.getIsEndDateChanged(), false);
//		assertEquals(state.getStartDate(), null);
//		assertEquals(state.getEndDate(), null);
//
//		//Test invalid argument: Delete no index
//		state.setUserInput("delete float");
//		parser.processInput();
//		assertEquals(state.getIsValid(), false);
//		assertEquals(state.getCommandType(), CommandType.UNDEFINED);
//		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
//		assertEquals(state.getDisplayMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
//		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
//		assertEquals(state.getPositionIndex(), 0);
//		assertEquals(state.getIsStartDateChanged(), false);
//		assertEquals(state.getIsEndDateChanged(), false);
//		assertEquals(state.getStartDate(), null);
//		assertEquals(state.getEndDate(), null);
//		
//		//Test invalid argument: Delete wrong index
//		state.setUserInput("delete two float");
//		parser.processInput();
//		assertEquals(state.getIsValid(), false);
//		assertEquals(state.getCommandType(), CommandType.UNDEFINED);
//		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
//		assertEquals(state.getDisplayMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
//		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
//		assertEquals(state.getPositionIndex(), 0);
//		assertEquals(state.getIsStartDateChanged(), false);
//		assertEquals(state.getIsEndDateChanged(), false);
//		assertEquals(state.getStartDate(), null);
//		assertEquals(state.getEndDate(), null);
//		
//		//Test invalid argument: Delete wrong type
//		state.setUserInput("delete 2 floats");
//		parser.processInput();
//		assertEquals(state.getIsValid(), false);
//		assertEquals(state.getCommandType(), CommandType.UNDEFINED);
//		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
//		assertEquals(state.getDisplayMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
//		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
//		assertEquals(state.getPositionIndex(), 0);
//		assertEquals(state.getIsStartDateChanged(), false);
//		assertEquals(state.getIsEndDateChanged(), false);
//		assertEquals(state.getStartDate(), null);
//		assertEquals(state.getEndDate(), null);
//		
//		//Test invalid argument: Tick no index
//		state.setUserInput("TICK float");
//		parser.processInput();
//		assertEquals(state.getIsValid(), false);
//		assertEquals(state.getCommandType(), CommandType.UNDEFINED);
//		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
//		assertEquals(state.getDisplayMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
//		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
//		assertEquals(state.getPositionIndex(), 0);
//		assertEquals(state.getIsStartDateChanged(), false);
//		assertEquals(state.getIsEndDateChanged(), false);
//		assertEquals(state.getStartDate(), null);
//		assertEquals(state.getEndDate(), null);
//		
//		//Test invalid argument: Delete wrong index
//		state.setUserInput("TICK two float");
//		parser.processInput();
//		assertEquals(state.getIsValid(), false);
//		assertEquals(state.getCommandType(), CommandType.UNDEFINED);
//		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
//		assertEquals(state.getDisplayMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
//		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
//		assertEquals(state.getPositionIndex(), 0);
//		assertEquals(state.getIsStartDateChanged(), false);
//		assertEquals(state.getIsEndDateChanged(), false);
//		assertEquals(state.getStartDate(), null);
//		assertEquals(state.getEndDate(), null);
//		
//		//Test invalid argument: Delete wrong type
//		state.setUserInput("tick 2 floats");
//		parser.processInput();
//		assertEquals(state.getIsValid(), false);
//		assertEquals(state.getCommandType(), CommandType.UNDEFINED);
//		assertEquals(state.getContent(), Constant.VALUE_DEFAULT_EMPTY);
//		assertEquals(state.getDisplayMessage(), Constant.VALUE_ERROR_INVALID_ARGUMENT);
//		assertEquals(state.getTaskType(), TaskType.UNDEFINED);
//		assertEquals(state.getPositionIndex(), 0);
//		assertEquals(state.getIsStartDateChanged(), false);
//		assertEquals(state.getIsEndDateChanged(), false);
//		assertEquals(state.getStartDate(), null);
//		assertEquals(state.getEndDate(), null);
//
//	}

}
