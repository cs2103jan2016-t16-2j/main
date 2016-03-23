package facade;
import java.lang.StringBuilder;
import java.util.*;

import common.*;
import logic.AddTask;
import logic.ClearTask;
import logic.DeleteTask;
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
	
	public WallistModel(){
		state = new State();
		addTask = new AddTask(state);
		deleteTask = new DeleteTask(state);
		tickTask = new TickTask(state);
		updateTask = new UpdateTask(state);
		clearTask = new ClearTask(state);
		storage = new Storage(state);
		parser = new Parser(state); 
		storage.loadState();
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
				boolean runningResult = running();
				return runningResult;
			}
		} catch (Exception e){
			return false;
		}

	}

	private boolean running(){
		CommandType cmdType = state.getCommand();
		boolean result;
		
		if(cmdType.equals(CommandType.ADD)){
			boolean addResult = addTask.process();
			boolean parserResult = storage.saveState();
			result = addResult && parserResult;
		} else if (cmdType.equals(CommandType.DELETE)){
			boolean deleteResult = deleteTask.process();
			boolean parserResult = storage.saveState();
			result = deleteResult && parserResult;
		} else if (cmdType.equals(CommandType.TICK)){
			boolean tickResult = tickTask.process();
			boolean parserResult = storage.saveState();
			result = tickResult && parserResult;
		} else if (cmdType.equals(CommandType.UPDATE)){
			boolean updateResult = updateTask.process();
			boolean parserResult = storage.saveState();
			result = updateResult && parserResult;
		} else if (cmdType.equals(CommandType.CLEAR)){
			boolean clearResult = clearTask.process();
			boolean parserResult = storage.saveState();
			result = clearResult && parserResult;
		} else if (cmdType.equals(CommandType.EXIT)){
			result = true;
		} else {
			result = false;
		}

		return result;
	}
	

}













