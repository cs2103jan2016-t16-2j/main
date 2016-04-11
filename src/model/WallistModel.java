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
	
	private Storage storage;
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
	
	//========================
	//       Constructor 
	//========================
	
	public WallistModel(){
		initialiseState(); // state must be initialised first
		initialiseLogic();
		initialiseStorage();
		initialiseParser();
		initialiseStateTracker();
	}
	
	//========================
	//       Functions 
	//========================
	
	/**
	 * This method takes in the actual user input and execute functions accordingly
	 * @param inputString
	 * @return whether it is successfully executed
	 */
	public boolean processInputString(String inputString){
		LOGGER.log(Level.INFO, PARSER_RUNNING);
		boolean isParsed = isSuccessfullyParsed(inputString);
		if (!isParsed) {
			LOGGER.log(Level.WARNING, PARSER_FAILURE);
			state.incErrorFrequency();
			return false;
		} else {
			state.resetErrorFrequency();
			boolean isExecutionSuccessful = executeInput();
			if(isExecutionSuccessful){
				displaySuccessfulMessage();
			}
			return isExecutionSuccessful;
		}
	}

	private void displaySuccessfulMessage() {
		state.setDisplayMessage(Constant.MESSAGE_SUCCESS);
	}
	
	//===========================
	//       Helper functions 
	//===========================
	
	/**
	 * This method initialises the initial state
	 */
	private void initialiseStateTracker() {
		stateHistory.push(state.deepCopy());
	}
	
	/**
	 * This method initialises the Parser
	 */
	private void initialiseParser() {
		parser = new Parser(state);
	}

	/**
	 * This method initialises the Storage
	 */
	private void initialiseStorage() {
		storage = new Storage(state);
		storage.executeLoadState();
	}
	
	public void resetStorage(){
		storage = new Storage(new State());
	}
	/**
	 * This method initialises the State related objects
	 */
	private void initialiseState() {
		state = new State();
		stateHistory = new Stack<State>();
		stateFuture = new Stack<State>();
	}

	/**
	 * This method initialises the Logic components
	 */
	private void initialiseLogic() {
		addTask = new AddTask(state);
		deleteTask = new DeleteTask(state);
		tickTask = new TickTask(state);
		untickTask = new UntickTask(state);
		updateTask = new UpdateTask(state);
		clearTask = new ClearTask(state);
		searchTasks = new SearchTasks(state);
		config = new Config(state, storage);
		changeViewMode = new ChangeViewMode(state);
		viewTaskDetail = new ViewTaskDetail(state);
		help = new Help(state);
	}
	
	/**
	 * This method passes the state to GUI
	 * @return the current State object
	 */
	public State getState(){
		return state;
	}
	
	/**
	 * This method checks whether the input String can be successfully parsed
	 * @param inputString
	 * @return
	 */
	private boolean isSuccessfullyParsed(String inputString) {
		state.setUserInput(inputString); // store input into state
		boolean isParsed = parser.processInput();
		boolean isValid = state.getIsValid();
		boolean isSuccesfullyParsed = isParsed && isValid;
		return isSuccesfullyParsed;
	}
	
	/**
	 * This method executes the commands using the information stored in the State object
	 * @return whether it is successful
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
		CommandType commandType = state.getCommandType();
		boolean isRunningSuccessful = false;
		
		switch (commandType) {
			case ADD :
				isRunningSuccessful = addTask.process();
				stateHistory.push(state.deepCopy());
				break;
			case DELETE :
				isRunningSuccessful = deleteTask.process();
				stateHistory.push(state.deepCopy());
				break;
			case TICK :
				isRunningSuccessful = tickTask.process();
				stateHistory.push(state.deepCopy());
				break;
			case UNTICK :
				isRunningSuccessful = untickTask.process();
				stateHistory.push(state.deepCopy());
				break;
			case UPDATE :
				isRunningSuccessful = updateTask.process();
				stateHistory.push(state.deepCopy());
				break;
			case CLEAR :
				isRunningSuccessful = clearTask.process();
				stateHistory.push(state.deepCopy());
				break;
			case SEARCH :
				isRunningSuccessful = searchTasks.process();
				break;
			case UNDO :
				isRunningSuccessful = runningUndo();
				return isRunningSuccessful;
			case REDO :
				isRunningSuccessful = runningRedo();
				return isRunningSuccessful;
			case DETAIL :
				isRunningSuccessful = viewTaskDetail.process();
				break;
			case CHANGEMODE :
				isRunningSuccessful = changeViewMode.process();
				break;
			case CONFIG :
				isRunningSuccessful = config.process();
				stateHistory.push(state.deepCopy());
				break;
			case HELP :
				isRunningSuccessful = help.process();
				break;
			case EXIT :
				isRunningSuccessful = true;
			default:
				break;
		}
		storage.executeSaveConfig();
		storage.executeSaveState();
		return isRunningSuccessful;
	}
	
	
	// functions to be refactored
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
	
	public Storage getStorage(){
		return storage;
	}

	public Parser getParser(){
		return parser;
	}
	
	public AddTask getAddTask(){
		return addTask;
	}
	
	public DeleteTask getDeleteTask(){
		return deleteTask;
	}
	
	public TickTask getTickTask(){
		return tickTask;
	}
	
	public UntickTask getUntickTask(){
		return untickTask;
	}
	
	public UpdateTask getUpdateTask(){
		return updateTask;
	}
	
	public ClearTask getClearTask(){
		return clearTask;
	}

	public Help getHelp(){
		return help;
	}
	
	public Config getConfig(){
		return config;
	}
	
	public SearchTasks getSearchTasks(){
		return searchTasks;
	}
	
	public ChangeViewMode getChangeViewMode(){
		return changeViewMode;
	}
	
	public ViewTaskDetail getViewTaskDetail(){
		return viewTaskDetail;
	}
}













