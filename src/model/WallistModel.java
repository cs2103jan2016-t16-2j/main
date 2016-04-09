package model;

import java.util.EmptyStackException;
import java.util.Stack;
import common.*;
import logic.AddTask;
import logic.ChangeViewMode;
import logic.ClearTask;
import logic.DeleteTask;
import logic.Help;
import logic.SearchTasks;
import logic.TickTask;
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
	private UpdateTask updateTask;
	private ClearTask clearTask;
	private Help help;
	private SearchTasks searchTasks;
	private ChangeViewMode changeViewMode;
	private ViewTaskDetail viewTaskDetail;
	public Stack<State> stateHistory, stateFuture ;
	
	// logger
	private final static Logger LOGGER = Logger.getLogger(FileManagement.class.getName());
	
	// logging message displayed
	private static final String PARSER_FAILURE = "User input is not succesfully parsed!";
	
	//====================================
	//       Constructor and Initiliser
	//====================================
	
	public WallistModel(){
		initialiseState(); // state must be initialised first
		initialiseLogic();
		initialiseStorage();
		initialiseParser(); 
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
		updateTask = new UpdateTask(state);
		clearTask = new ClearTask(state);
		searchTasks = new SearchTasks(state);
		changeViewMode = new ChangeViewMode(state);
		viewTaskDetail = new ViewTaskDetail(state);
		help = new Help(state);
	}
	
	public State getState(){
		return state;
	}
	
	public boolean processInputString(String inputString){
		try{
			state.setUserInput(inputString); // store input into state
			boolean isParsed = parser.processInput();
			boolean isValid = state.getIsValid();
			boolean isSuccesfullyParsed = isParsed && isValid;
			
			if (!isSuccesfullyParsed){
				LOGGER.log(Level.WARNING, PARSER_FAILURE);
				return false;
			} else {
				boolean isRunningSuccessful = running();
				return isRunningSuccessful;
			}
			
		} catch (EmptyStackException e){
			state.setDisplayMessage(Constant.MESSAGE_EMPTY_STACK);
			return false;
		} catch (Exception e){
			return false;
		}

	}

	public boolean running() throws EmptyStackException{
		CommandType cmdType = state.getCommandType();
		boolean result;
		
		if(cmdType.equals(CommandType.ADD)){
			result = runningAdd();
			stateHistory.push(state.deepCopy());
		} else if (cmdType.equals(CommandType.DELETE)){
			result = runningDelete();
			stateHistory.push(state.deepCopy());
		} else if (cmdType.equals(CommandType.TICK)){
			result = runningTick();
		} else if (cmdType.equals(CommandType.UPDATE)){
			result = runningUpdate();
		} else if (cmdType.equals(CommandType.CLEAR)){
			result = runningClear();
		} else if (cmdType.equals(CommandType.SEARCH)){
			result = runningSearch();
		} else if (cmdType.equals(CommandType.UNDO)){
			result = runningUndo();
			return result;
		} else if (cmdType.equals(CommandType.REDO)){
			result = runningRedo();
			return result;
		} else if (cmdType.equals(CommandType.DETAIL)){
			result = runningViewDetail();
		} else if (cmdType.equals(CommandType.CHANGEMODE)){
			result = runningChangeMode();
		} else if (cmdType.equals(CommandType.EXIT)){
			result = true;
		} else if (cmdType.equals(CommandType.HELP)) {
			result = runningHelp();
		} else {
			result = false;
		}
		

		return result;
	}

	private boolean runningChangeMode() {
		boolean result;
		boolean isChangeModeSuccessful = changeViewMode.process();
		result = isChangeModeSuccessful;
		return result;
	}
	
	private boolean runningViewDetail() {
		boolean result;
		boolean isViewDetailSuccessful = viewTaskDetail.process();
		result = isViewDetailSuccessful;
		return result;
	}
	
	private boolean runningUndo() throws EmptyStackException{
		boolean result;
		try{
			System.out.println(stateHistory.size());
			if(stateHistory.size() <= 1){
				throw new EmptyStackException();
			}
			State currentCopy = stateHistory.peek();
			stateFuture.push(currentCopy.deepCopy());
			stateHistory.pop();
			System.out.println(stateHistory.size());
			state.recoverFrom(stateHistory.peek());
		} finally{
			result = true;				
		}
		return result;
	}
	
	private boolean runningRedo() throws EmptyStackException{
		boolean result;
		try{
			if(stateFuture.isEmpty()){
				throw new EmptyStackException();
			}
			State future = stateFuture.peek();
			state.recoverFrom(future);
			stateHistory.push(future.deepCopy());
			stateFuture.pop();
		} finally{
			result = true;				
		}
		return result;
	}
	
	
	private boolean runningSearch() {
		boolean result;
		boolean searchResult = searchTasks.process();
		result = searchResult;
		return result;
	}

	private boolean runningClear() {
		boolean result;
		boolean clearResult = clearTask.process();
		boolean parserResult = storage.executeSaveState();
		result = clearResult && parserResult;
		return result;
	}

	private boolean runningUpdate() {
		boolean result;
		boolean updateResult = updateTask.process();
		boolean parserResult = storage.executeSaveState();
		result = updateResult && parserResult;
		return result;
	}

	private boolean runningTick() {
		boolean result;
		boolean tickResult = tickTask.process();
		boolean parserResult = storage.executeSaveState();
		result = tickResult && parserResult;
		return result;
	}

	private boolean runningDelete() {
		boolean result;
		boolean deleteResult = deleteTask.process();
		boolean parserResult = storage.executeSaveState();
		result = deleteResult && parserResult;
		return result;
	}

	private boolean runningAdd() {
		boolean result;
		boolean addResult = addTask.process();
		boolean parserResult = storage.executeSaveState();
		result = addResult && parserResult;
		return result;
	}
	
	//@@ A0107375E
	private boolean runningHelp() {
		boolean isChangedToHelpMode = help.process();
		boolean isSaved = storage.executeSaveState();
		boolean isSuccessful = isChangedToHelpMode && isSaved;
		return isSuccessful;
	}
	
}













