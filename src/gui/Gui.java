//@@author A0130717M
package gui;

import java.util.ArrayList;
import common.*;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.WallistModel;

public class Gui extends Application{
	
	//stage attributes
	private Stage window;
	
	//scene attributes
	private StackPane taskStackPane;
	private Rectangle title;
	private Rectangle mainBox;
	private VBox layout;
	private VBox tab;
	private VBox tasks;
	private VBox help;
	private VBox configs;
	private VBox taskTable;
	private HBox sectionHeader;
	private HBox tableHeader;
	private Label indexHeader;
	private Label contentHeader;
	private Label timeHeader;
	private Scene scene;
	private ScrollPane taskPane;
	private TextField inputBox;
	private State state;
	private String command;
	private Label allHeader;
	private Label scheduledHeader;
	private Label floatingHeader;
	private Label todayHeader;
	private Label searchHeader;
	private Label configHeader;
	private Label helpHeader;
	private Label finishedHeader;
	private Text notificationText;
	
	//Wallist model
	private WallistModel wallistModel;
	
	//Variables
	private int taskBoxHeight;
	private int taskBoxWidth;
	private int contentWidth;
	private int contentHeaderWidth;
	private int taskIndex;
	private double vValue;
	private double xOffset;
	private double yOffset;
	
	//Constants
	private final String TITLE = "    %1$s's Wallist";
    private final String NOTIFICATION = "You have %1$s tasks, %2$s due today, %3$s overdue    ";
	private final String PROMPT = "Input your command here";
	private final String EMPTY_MESSAGE = "%1$s\n\n\n\n"; 
	private final String THEME_SHEET = "/resources/%1$s.css";
	private final String FONT_SHEET = "/resources/%1$s.css";
	private final String BASIC_SHEET = "/resources/basic.css";
	private final String TITLE_IMAGE = "/resources/title.png";
	
	private final int COMPONENT_GAP_H = 20;
	private final int COMPONENT_GAP_V = 20;
	private final int INDEX_WIDTH = 30;
	private final int HEADER_INDEX_WIDTH = 50;
	private final int TIME_WIDTH = 380;
	private final int INPUT_BOX_HEIGHT = 30;
	private final int STAGE_WIDTH = 1000;	
	private final int TITLE_HEIGHT = 40;	
	private final int HEADER_HEIGHT = 30;
	private final int STAGE_HEIGHT = 650;	
	private final int PADDING = 10;
	private final double SCROLL_PERCENTAGE = 0.1;
	
	private final Insets COMPONENT_PADDING = new Insets(0, 20, 20, 20);
	private final Insets INFO_PADDING = new Insets(20, 30, 20, 30);
	private final Insets CONTENT_PADDING = new Insets(5, 0, 5, 0);
	
	//launching function
	public static void launching(){
		launch();
	}
	
	//start program, including initialize Wallist model
	@Override
	public void start(Stage primaryStage) throws Exception{
		initVariables();
		state = wallistModel.getState();
		setupStage(primaryStage);
		setupConfig();
		setupHelp();
    	refreshTaskPane();
		refreshNotification();
		inputProcess();
	}

	private void initVariables(){
		wallistModel = new WallistModel();
		taskStackPane = new StackPane();
		title = new Rectangle();
		layout = new VBox();
		tab = new VBox();
		tasks = new VBox();
		help = new VBox(15);
		configs = new VBox(10);
		taskTable = new VBox();
		sectionHeader = new HBox(10);
		tableHeader = new HBox(1);
		indexHeader = new Label("#");
		contentHeader = new Label("   Task ");
		timeHeader = new Label("   Schedule ");
	}

	//handle keyboard events including scrolling and entering commands
	private void inputProcess() {
		inputBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent keyEvent) {
		        if (keyEvent.getCode() == KeyCode.DOWN)  {
		            vValue = taskPane.getVvalue();
		        	taskPane.setVvalue(vValue + SCROLL_PERCENTAGE);
		        } else if (keyEvent.getCode() == KeyCode.UP)  {
			        vValue = taskPane.getVvalue();
			        taskPane.setVvalue(vValue - SCROLL_PERCENTAGE);
			    } else if (keyEvent.getCode() == KeyCode.ENTER)  {
		        	refresh();
		        }
			}
		});
	}

	//passing command to model and refresh the stage content 
	private void refresh() {
		command = inputBox.getText();
		boolean isSuccess = wallistModel.processInputString(command);
		state = wallistModel.getState();
		if (isSuccess){
			refreshTaskPane();
			loadStyleSheet();
		} else {
			displayMessage();
		}
		inputBox.clear();
		refreshNotification();
	}

	//display notifications in title bar
	private void refreshNotification() {
		int allTasks = state.getAllTasksSize();
		int[] dueTasks = state.getDueTasksSize();
		String notification = String.format(NOTIFICATION, allTasks, dueTasks[0], dueTasks[1]);
		notificationText.setText(notification);
	}

	//display correct content in mainPane
	private void refreshTaskPane() {
		loadHeader();
		if (state.getCommandType().equals(CommandType.EXIT)){
			window.close();
		} else if (state.getViewMode().equals(ViewMode.CONFIG)){
			displayConfig();
		} else if (state.getViewMode().equals(ViewMode.HELP)){
			displayHelp();
		} else if (state.isCurrentTasksEmpty()){
			displayEmpty();
		} else{
			displayTask();	
		}
	}

	//pop feedback when action is not successful
	private void displayMessage() {
		Label displayText = new Label(state.getDisplayMessage());
		displayText.setId("message");
		taskStackPane.getChildren().add(displayText);
		FadeAnimation fade = new FadeAnimation(displayText);
		fade.playAnimation();
	}
	
	//display help manual in mainPane
	private void displayHelp() {
		loadTaskPane();
		taskTable.getChildren().clear();
		taskTable.getChildren().add(taskPane);
		taskPane.setContent(help);
	}
	
	//display setting in mainPane
	private void displayConfig(){
		loadTaskPane();
		taskTable.getChildren().clear();
		taskTable.getChildren().add(taskPane);
		taskPane.setContent(configs);
	}
	
	//display empty message when no tasks in current mode
	private void displayEmpty(){
		String message = String.format(EMPTY_MESSAGE, state.getEmptyMessage());
		Text emptyMessage = new Text(message);
		emptyMessage.setTextAlignment(TextAlignment.CENTER);
		emptyMessage.setId("empty");
		taskStackPane.getChildren().clear();
		taskStackPane.getChildren().addAll(mainBox, emptyMessage);
	}
	
	//display tasks in mainPane in rows
	private void displayTask() {
		loadTaskPane();
		taskTable.getChildren().clear();
		taskTable.getChildren().addAll(tableHeader, taskPane);
		loadTableHeader();
		ArrayList<Task> taskList = state.getCurrentTasks();
		tasks.getChildren().clear();
		taskIndex = 0;
		for (Task task: taskList){
			displayTaskRow(task);
        }
		taskPane.setContent(tasks);
	}

	//display each row by three columns
	private void displayTaskRow(Task task) {
		Column indexCol = loadIndexCol(task);
		Column contentCol = loadContentCol(task);
		Column timeCol = loadTimeCol(task);
		loadTaskRow(indexCol, contentCol, timeCol);
	}
	
	//get styles and font when changes
	private void loadStyleSheet() {
		String theme = String.format(THEME_SHEET, state.getThemeInString());
		String font = String.format(FONT_SHEET, state.getFontInString());
		scene.getStylesheets().clear();
		scene.getStylesheets().addAll(theme, font, BASIC_SHEET);
	}

	//load header tab according to current view mode
	private void loadHeader() {
		setupHeaderTab();
		switch(state.getViewMode()){
		case ALL:
			allHeader.setId("tab");
			break;
		case DEADLINE:
			scheduledHeader.setId("tab");
			break;
		case FLOATING:
			floatingHeader.setId("tab");
			break;
		case FINISHED:
			finishedHeader.setId("tab");
			break;
		case START:
		    todayHeader.setId("tab");
		    break;
		case SEARCH:
			searchHeader.setId("tab");
			break;
		case HELP:
		    helpHeader.setId("tab");
		    break;
		case CONFIG:
			configHeader.setId("tab");
			break;
		default:
			todayHeader.setId("tab");
		}
	}
	
	//load task layout in mainPane
	private void loadTaskPane() {
		taskStackPane.getChildren().clear();
		taskStackPane.getChildren().addAll(mainBox, taskTable);
	}
	
	//load table header when loading task
	private void loadTableHeader() {
		tableHeader.getChildren().clear();
		if (state.getViewMode().equals(ViewMode.FLOATING)){
			setupFloatingTableHeader();	
		} else {
			setupTableHeader();
		}
	}

	//load task rows and refresh the ones that are changed
	private void loadTaskRow(Column indexCol, Column contentCol, Column timeCol) {
		GridPane taskLine = new GridPane();
		taskLine.setPadding(CONTENT_PADDING);
		taskLine.setHgap(10);
		if (taskIndex % 2 == 0){
			taskLine.setId("evenLine");
		} else {
			taskLine.setId("oddLine");
		}
		taskLine.getChildren().addAll(indexCol.getColumn(), contentCol.getColumn(), timeCol.getColumn());
		tasks.getChildren().add(taskLine);
		if (taskIndex > state.getPositionIndex()){
			FadeAnimation fade = new FadeAnimation(taskLine);
			fade.playAnimation();
		}
	}

	//load schedules column
	private Column loadTimeCol(Task task) {
		String taskTime = "";
		if (task.getTaskType().equals(TaskType.DEADLINE)) {
			TaskTimeDisplay taskTimeDisplay = new TaskTimeDisplay(task);
			taskTime = taskTimeDisplay.getTaskTime();	
		}
		Column timeCol = new Column(taskTime, 2, TIME_WIDTH);
		timeCol.setAlignLeft();
		timeCol.setWrap(TIME_WIDTH);
		loadTaskView(task, timeCol);
		return timeCol;
	}

	//load task content and details column
	private Column loadContentCol(Task task) {
		String taskContent = task.getDisplayContent();
		Column contentCol = new Column(taskContent, 1, contentWidth);
		contentCol.setWrap(contentWidth);
		loadTaskView(task, contentCol);
		return contentCol;
	}

	//load task index column
	private Column loadIndexCol(Task task) {
		taskIndex ++;
		String taskIdx = Integer.toString(taskIndex);
		Column indexCol = new Column(taskIdx, 0, INDEX_WIDTH);
		indexCol.setAlignRight();
		loadTaskView(task, indexCol);
		return indexCol;
	}
	
	//determine whether task details are displayed and whether task is overdue
	private void loadTaskView(Task task, Column col) {
		if (task.isOverdue() && task.getIsDetailDisplayed()){
			col.setZoomOverdue();
		} else if (task.isOverdue()){
			col.setOverdue();
		} else if (task.getIsDetailDisplayed()){
			col.setZoom();
		}
	}

	//enable Esc to exit
	private void loadExit(){
		layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent keyEvent) {
		        if (keyEvent.getCode() == KeyCode.ESCAPE) {
					window.close();
				}
			}
		});
	}
	
	//enable click to select tab
	private void loadClick() {
		allHeader.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	state.setViewMode(ViewMode.ALL);
            	refreshTaskPane();
            }
        });
		scheduledHeader.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	state.setViewMode(ViewMode.DEADLINE);
            	refreshTaskPane();
            }
        });
		floatingHeader.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	state.setViewMode(ViewMode.FLOATING);
            	refreshTaskPane();
            }
        });
		finishedHeader.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	state.setViewMode(ViewMode.FINISHED);
            	refreshTaskPane();
            }
        });
		searchHeader.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	state.setViewMode(ViewMode.SEARCH);
            	refreshTaskPane();
            }
        });
		configHeader.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	state.setViewMode(ViewMode.CONFIG);
            	refreshTaskPane();
            }
        });
		helpHeader.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	state.setViewMode(ViewMode.HELP);
            	refreshTaskPane();
            }
        });
		todayHeader.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	state.setViewMode(ViewMode.START);
            	refreshTaskPane();
            }
        });
	}
	
	//enable drag to move the stage
	private void loadDrag() {
		title.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = window.getX() - event.getScreenX();
                yOffset = window.getY() - event.getScreenY();
            }
        });
		title.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                window.setX(event.getScreenX() + xOffset);
                window.setY(event.getScreenY() + yOffset);
            }
        });
	}
	
	//initialize table header setting with index, content and schedules
	private void setupTableHeader() {
		indexHeader.setPrefWidth(HEADER_INDEX_WIDTH);
		contentHeader.setPrefWidth(contentHeaderWidth);
		timeHeader.setPrefWidth(TIME_WIDTH);
		tableHeader.getChildren().addAll(indexHeader, contentHeader, timeHeader);
	}

	//initialize table header setting with index and content
	private void setupFloatingTableHeader() {
		indexHeader.setPrefWidth(HEADER_INDEX_WIDTH);
		contentHeader.setPrefWidth(contentHeaderWidth + TIME_WIDTH);
		tableHeader.getChildren().addAll(indexHeader, contentHeader);
	}
	
	//initialize text input area
	private TextField setupInputLayout() {
		TextField inputBox = new TextField();
		inputBox.setPrefHeight(INPUT_BOX_HEIGHT);
		inputBox.setMaxHeight(INPUT_BOX_HEIGHT);
		inputBox.setPromptText(PROMPT);
		layout.getChildren().add(inputBox);
		return inputBox;
	}
	
	//initialize mainPane layout
	private ScrollPane setupMainLayout() {
		setupMainBox();
		taskPane = new ScrollPane();
		taskPane.setPrefSize(taskBoxWidth, taskBoxHeight);
		taskPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		taskPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	    taskStackPane.getChildren().addAll(mainBox, taskTable);
		tab.getChildren().add(taskStackPane);
		layout.getChildren().add(tab);
		return taskPane;
	}

	//initialize mainPane style
	private void setupMainBox() {
		mainBox = new Rectangle();
		mainBox.setWidth(taskBoxWidth);
		mainBox.setHeight(taskBoxHeight);
		mainBox.setId("taskPane");
	}

	//initialize table header layout and style
	private void setupTableHeaderLayout(){
		indexHeader.setAlignment(Pos.CENTER);
		contentHeader.setTextAlignment(TextAlignment.CENTER);
		timeHeader.setTextAlignment(TextAlignment.CENTER);
		indexHeader.setId("header");
		contentHeader.setId("header");
		timeHeader.setId("header");
	}
	
	//initialize header tab layout and style
	private void setupHeaderLayout() {
		sectionHeader.setPrefHeight(HEADER_HEIGHT);
		sectionHeader.setAlignment(Pos.CENTER);
		setupHeaderTab();
		tab.getChildren().add(sectionHeader);
	}

	//initialize each tabs in header
	private void setupHeaderTab() {
		allHeader = new Label(Constant.HEADER_ALL);
		scheduledHeader = new Label(Constant.HEADER_DEADLINE);
		floatingHeader = new Label(Constant.HEADER_FLOATING);
		todayHeader = new Label(Constant.HEADER_START);
		searchHeader = new Label(Constant.HEADER_SEARCH);
		configHeader = new Label(Constant.HEADER_CONFIG);
		helpHeader = new Label(Constant.HEADER_HELP);
		finishedHeader = new Label(Constant.HEADER_FINISHED);
		loadClick();
		sectionHeader.getChildren().clear();
		sectionHeader.getChildren().addAll(todayHeader, allHeader, scheduledHeader, floatingHeader, finishedHeader, searchHeader, configHeader, helpHeader);
	}
	
	//initialize notificationPane layout and style
	private void setupNotificationLayout() {
		StackPane notificationPane = new StackPane();
		notificationPane.setAlignment(Pos.CENTER_RIGHT);
		notificationText = new Text();
		notificationText.setId("titleText");
		StackPane titlePane = setupTitleLayout();
		notificationPane.getChildren().addAll(titlePane, notificationText);
        layout.getChildren().add(notificationPane);
	}

	//initialize titlePane layout and style
	private StackPane setupTitleLayout() {
		StackPane titlePane = new StackPane();
		titlePane.setAlignment(Pos.CENTER_LEFT);
		String userName = String.format(TITLE, System.getProperty("user.name"));;
        Text titleText = new Text(userName);
        titleText.setId("titleText");
		title.setWidth(STAGE_WIDTH);
		title.setHeight(TITLE_HEIGHT);
		title.setId("title");
		titlePane.getChildren().addAll(title, titleText);
		return titlePane;
	}

	//initialize stage size, properties and style
	private void setupStage(Stage primaryStage) {
		window = primaryStage;
		window.initStyle(StageStyle.UNDECORATED);
        window.setResizable(true);
        window.getIcons().add(new Image(TITLE_IMAGE));
    	setupLayout();
		scene = new Scene(layout, STAGE_WIDTH, STAGE_HEIGHT);
		loadStyleSheet();
		window.setScene(scene);
		window.show();
		loadDrag();
		loadExit();
	}
	
	//initialize layout parameters, positions and spacing
	private void setupLayout() {
		taskBoxHeight = STAGE_HEIGHT - COMPONENT_GAP_V * 4 - INPUT_BOX_HEIGHT - HEADER_HEIGHT - TITLE_HEIGHT;
    	taskBoxWidth = (int)(STAGE_WIDTH - COMPONENT_GAP_H * 2);
    	contentWidth = taskBoxWidth - INDEX_WIDTH - TIME_WIDTH;
    	contentHeaderWidth = contentWidth + PADDING * 2;
		layout.setPadding(COMPONENT_PADDING);
		layout.setSpacing(COMPONENT_GAP_V);
		layout.setAlignment(Pos.CENTER);
		setupNotificationLayout();
		setupHeaderLayout();
		setupTableHeaderLayout();
        taskPane = setupMainLayout();
		inputBox = setupInputLayout();
	}
	
	//initialize setting page content
	private void setupConfig(){
		String[] infoStr = state.getConfigInfo();
		configs.setPadding(INFO_PADDING);
		Text intro = new Text(infoStr[0]); 
		intro.setId("zoom");		
		Text dir = new Text(infoStr[1]); 
		dir.setId("normal");
		Text theme = new Text(infoStr[2]); 
		theme.setId("normal");
		Text font = new Text(infoStr[3]); 
		font.setId("normal");
		ThemeSelector themeSelector = new ThemeSelector();
		FontSelector fontSelector = new FontSelector();
		GridPane themes = themeSelector.getTheme();
		GridPane fonts = fontSelector.getFont();
		configs.getChildren().addAll(intro, dir, theme, themes, font, fonts);
	}
	
	//initialize help page content
	private void setupHelp(){
		String[] helpStr = state.getHelpManual();
		help.setPadding(INFO_PADDING);
		Text intro = new Text(helpStr[0]); 
		intro.setId("zoom");		
		Text add = new Text(helpStr[1]); 
		add.setId("normal");
		Text delete = new Text(helpStr[2]); 
		delete.setId("normal");
		Text tick = new Text(helpStr[3]); 
		tick.setId("normal");
		Text update = new Text(helpStr[4]); 
		update.setId("normal");		
		Text view = new Text(helpStr[5]); 
		view.setId("normal");
		Text exit = new Text(helpStr[6]); 
		exit.setId("normal");
		Text end = new Text(helpStr[7]); 
		end.setId("zoom");
		help.getChildren().addAll(intro, add, delete, tick, update, view, exit, end);
	}
	
	
}