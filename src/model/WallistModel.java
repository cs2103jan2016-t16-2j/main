package model;

import java.util.EmptyStackException;
import java.util.Stack;
import common.*;
import logic.AddTask;
import logic.ChangeViewMode;
import logic.ClearTask;
import logic.Config;
import logic.DeleteTask;
import logic.Help;
import logic.SearchTasks;
import logic.TickTask;
import logic.UntickTask;
import logic.UpdateTask;
import logic.ViewTaskDetail;
import parser.Parser;
import storage.FileManagement;
import storage.Storage;
import java.util.logging.*;

public class WallistModel{
	
	//============================
	//       Attributes
	//============================
	
	public Storage storage;
	private Parser parser;
	private State state;
	private AddTask addTask;
	private DeleteTask deleteTask;
	private TickTask tickTask;
	private UntickTask untickTask;
	private UpdateTask updateTask;
	private ClearTask clearTask;
	private Help help;
	private Config config;
	private SearchTasks searchTasks;
	private ChangeViewMode changeViewMode;
	private ViewTaskDetail viewTaskDetail;
	public Stack<State> stateHistory, stateFuture ;
	
	// logger
	private final static Logger LOGGER = Logger.getLogger(FileManagement.class.getName());
	
	// logging message displayed
	private static final String PARSER_FAILURE = "User input is not succesfully parsed!";
	private static final String PARSER_RUNNING = "Start to parse the user input...";
	
	//====================================
	//       Constructor and Initiliser
	//====================================
	
	public WallistModel(){
		initialiseState(); // state must be initialised first
		initialiseLogic();
		initialiseStorage();
		initialiseParser();
		initialiseStateTracker();
	}
	
	private void initialiseStateTracker() {
		stateHistory.push(state.deepCopy());
	}
	
	private void initialiseParser() {
		parser = new Parser(state);
	}

	private void initialiseStorage() {
		storage = new Storage(state);
		storage.executeLoadState();
	}

	private void initialiseState() {
		state = new State();
		stateHistory = new Stack<State>();
		stateFuture = new Stack<State>();
	}

	private void initialiseLogic() {
		addTask = new AddTask(state);
		deleteTask = new DeleteTask(state);
		tickTask = new TickTask(state);
		untickTask = new UntickTask(state);
		updateTask = new UpdateTask(state);
		clearTask = new ClearTask(state);
		searchTasks = new SearchTasks(state);
		config = new Config(state);
		changeViewMode = new ChangeViewMode(state);
		viewTaskDetail = new ViewTaskDetail(state);
		help = new Help(state);
	}
	
	public State getState(){
		return state;
	}
	
	public boolean processInputString(String inputString){
		LOGGER.log(Level.INFO, PARSER_RUNNING);
		state.setUserInput(inputString); // store input into state
		boolean isParsed = parser.processInput();
		boolean isValid = state.getIsValid();
		boolean isSuccesfullyParsed = isParsed && isValid;
		if (!isSuccesfullyParsed) {
			LOGGER.log(Level.WARNING, PARSER_FAILURE);
			return false;
		} else {
			return executeInput();
		}
	}

	/**
	 * @return
	 */
	private boolean executeInput() {
		boolean isRunningSuccessful;
		try {
			isRunningSuccessful = running();
		} catch (EmptyStackException e){
			LOGGER.log(Level.WARNING, PARSER_FAILURE,e);
			state.setDisplayMessage(Constant.MESSAGE_EMPTY_STACK);
			return false;
		} catch (Exception e){
			LOGGER.log(Level.WARNING, PARSER_FAILURE,e);
			return false;
		}
		return isRunningSuccessful;
	}

	public boolean running() throws EmptyStackException{
		CommandType cmdType = state.getCommandType();
		boolean result;
		
		if (cmdType.equals(CommandType.ADD)) {
			result = addTask.process();
			stateHistory.push(state.deepCopy());
		} else if (cmdType.equals(CommandType.DELETE)){
			result = deleteTask.process();
			stateHistory.push(state.deepCopy());
		} else if (cmdType.equals(CommandType.TICK)){
			result = tickTask.process();
			stateHistory.push(state.deepCopy());
		} else if (cmdType.equals(CommandType.UNTICK)){
			result = untickTask.process();
			stateHistory.push(state.deepCopy());
		} else if (cmdType.equals(CommandType.UPDATE)){
			result = updateTask.process();
			stateHistory.push(state.deepCopy());
		} else if (cmdType.equals(CommandType.CLEAR)){
			result = clearTask.process();
			stateHistory.push(state.deepCopy());
		} else if (cmdType.equals(CommandType.SEARCH)){
			result = searchTasks.process();
			stateHistory.push(state.deepCopy());
		} else if (cmdType.equals(CommandType.UNDO)){
			result = runningUndo();
			return result;
		} else if (cmdType.equals(CommandType.REDO)){
			result = runningRedo();
			return result;
		} else if (cmdType.equals(CommandType.DETAIL)){
			result = viewTaskDetail.process();
			stateHistory.push(state.deepCopy());
		} else if (cmdType.equals(CommandType.CHANGEMODE)){
			result = changeViewMode.process();
			stateHistory.push(state.deepCopy());
		} else if (cmdType.equals(CommandType.CONFIG)){
			result = config.process();
			stateHistory.push(state.deepCopy());
		} else if (cmdType.equals(CommandType.HELP)){
			result = help.process();
			stateHistory.push(state.deepCopy());
		} else if (cmdType.equals(CommandType.EXIT)){
			result = true;
		} else {
			result = false;
		}
		storage.executeSaveState(); // going to put into each process after refactoring
		return result;
	}

	private boolean runningUndo() throws EmptyStackException{
		if(stateHistory.size() <= 1){
			throw new EmptyStackException();
		}
		State currentCopy = stateHistory.peek();
		stateFuture.push(currentCopy.deepCopy());
		stateHistory.pop();
		state.recoverFrom(stateHistory.peek());
		return true;
	}
	
	private boolean runningRedo() throws EmptyStackException {
		if(stateFuture.isEmpty()){
			throw new EmptyStackException();
		}
		State future = stateFuture.peek();
		state.recoverFrom(future);
		stateHistory.push(future.deepCopy());
		stateFuture.pop();
		return true;
	}
}













