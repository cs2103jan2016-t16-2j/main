package facade;
import java.lang.StringBuilder;
import java.util.*;

import common.*;
import logic.AddTask;
import logic.ClearTask;
import logic.DeleteTask;
import logic.SearchTasks;
import logic.TickTask;
import logic.UpdateTask;
import parser.*;
import storage.*;

public class WallistModel{

	private Storage storage;
	private Parser parser;
	private State state;
	private AddTask addTask;
	private DeleteTask deleteTask;
	private TickTask tickTask;
	private UpdateTask updateTask;
	private ClearTask clearTask;
	private SearchTasks searchTasks;
	private Stack<State> states;
	
	public WallistModel(){
		state = new State();
		addTask = new AddTask(state);
		deleteTask = new DeleteTask(state);
		tickTask = new TickTask(state);
		updateTask = new UpdateTask(state);
		clearTask = new ClearTask(state);
		searchTasks = new SearchTasks(state);
		storage = new Storage(state);
		parser = new Parser(state); 
		storage.executeLoadState();
		states = new Stack<State>();
		states.push(state);
	}
	
	public State getState(){
		return state;
	}
	
	public boolean process(String msg){
		try{
			state.setUserInput(msg);
			boolean parserResult = parser.processInput();
			boolean isValid = state.getIsValid();
			boolean successfulParser = parserResult && isValid;
			
			if(!successfulParser){
				return false;
			} else {
				boolean isRunningSuccessful = running();
				CommandType cmdType = state.getCommand();

				if(isRunningSuccessful && !cmdType.equals(CommandType.UNDO)){
					states.push(state);
				}
				return isRunningSuccessful;
			}
		} catch (EmptyStackException e){
			state.setErrorMessage(DisplayMessage.MESSAGE_EMPTY_STACK);
			return false;
		} catch (Exception e){
			return false;
		}

	}

	private boolean running() throws EmptyStackException{
		CommandType cmdType = state.getCommand();
		boolean result;
		
		if(cmdType.equals(CommandType.ADD)){
			result = runningAdd();
		} else if (cmdType.equals(CommandType.DELETE)){
			result = runningDelete();
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
		} else if (cmdType.equals(CommandType.EXIT)){
			result = true;
		} else {
			result = false;
		}

		return result;
	}

	private boolean runningUndo() {
		boolean result;
		try{
			state = states.pop();
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
	

}













