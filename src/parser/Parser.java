//@@author A0130369H
package parser;

import common.*;

public class Parser {
	
	//============================
	//       Attributes
	//============================
	
	private State state_;
	private ParserErrorChecker errorChecker_;
	private CommandAdd add_;
	private CommandChangeMode changeMode_;
	private CommandClear clear_;
	private CommandDelete delete_;
	private CommandDetail detail_;
	private CommandExit exit_;
	private CommandRedo redo_;
	private CommandSearch search_;
	private CommandTick tick_;
	private CommandUndo undo_;
	private CommandUpdate update_;
	private CommandHelp help_;
	private CommandUntick untick_;
	private CommandConfig config_;
	
	//====================================
	//       Constructor and Initialiser
	//====================================
	
 	public Parser(State state){
		state_ = state;
		errorChecker_ = new ParserErrorChecker(state);
		add_ = new CommandAdd(state);
		changeMode_ = new CommandChangeMode(state);
		clear_ = new CommandClear(state);
		delete_ = new CommandDelete(state);
		detail_ = new CommandDetail(state);
		exit_ = new CommandExit(state);
		redo_ = new CommandRedo(state);
		search_ = new CommandSearch(state);
		tick_ = new CommandTick(state);
		undo_ = new CommandUndo(state);
		update_ = new CommandUpdate(state);
		help_ = new CommandHelp(state);
		config_= new CommandConfig(state);
		untick_ = new CommandUntick(state);
		
	}
	
	/**
	 * Break down a string of input into smaller parts for logic to process
	 * Pre-Cond: Input of the user in the state
	 * Post-Cond: Respective command function will process the input
	 */
	public boolean processInput(){
		errorChecker_.checkError();
		if(state_.getIsValid()){
			CommandType command = state_.getCommandType();
			System.out.println(command.toString());
			switch(command){
			case ADD:
				add_.processInput();
				break;
			case CHANGEMODE:
				changeMode_.processInput();
				break;
			case CLEAR:
				clear_.processInput();
				break;
			case DELETE:
				delete_.processInput();
				break;
			case DETAIL:
				detail_.processInput();
				break;	
			case EXIT:
				exit_.processInput();
				break;
			case REDO:
				redo_.processInput();
				break;				
			case SEARCH:
				search_.processInput();
				break;				
			case TICK:
				tick_.processInput();
				break;			
			case UNDO:
    			undo_.processInput();
				break;				
			case UPDATE:
				update_.processInput();
				break;			
			case HELP:
	    		help_.processInput();
				break;				
			case UNTICK:
				untick_.processInput();
				break;					
			case CONFIG:;
				config_.processInput();
				break;
			}		
		}
		return state_.getIsValid();
	}
}
