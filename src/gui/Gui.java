//@@author A0130717M
package gui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
	
	private Stage window;
	private Scene scene;
	private ScrollPane taskPane;
	private TextField inputBox;
	private State state;
	private String command;
	private WallistModel wallistModel = new WallistModel();
	private StackPane taskStackPane = new StackPane();
	private Rectangle title = new Rectangle();
	private VBox layout = new VBox();
	private VBox tab = new VBox();
	private VBox tasks = new VBox();
	private VBox configs = new VBox(10);
	private VBox taskTable = new VBox();
	private HBox sectionHeader = new HBox(10);
	private HBox tableHeader = new HBox(1);
	private Label indexHeader = new Label("#");
	private Label contentHeader = new Label("   Task ");
	private Label timeHeader = new Label("   Schedule ");
	
	private Label allHeader;
	private Label deadlineHeader;
	private Label floatingHeader;
	private Label startHeader;
	private Label searchHeader;
	private Label configHeader;
	private Label helpHeader;
	private Label finishedHeader;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy HH:mm");
	private SimpleDateFormat sdfYear = new SimpleDateFormat("yy");
	private SimpleDateFormat sdfThisYear = new SimpleDateFormat("dd MMM HH:mm");
	private SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM yy");
	private SimpleDateFormat sdfDateThisYear = new SimpleDateFormat("dd MMM");
	private SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
	private SimpleDateFormat sdfDefaultTime = new SimpleDateFormat("HH:mm:ss");
	private String defaultTime = "23:59:59";
	
	private int taskBoxHeight;
	private int taskBoxWidth;
	private int contentWidth;
	private int contentHeaderWidth;
	private int taskIndex;
	private double vValue;
	
	private static double xOffset, yOffset;
	
	private static final String TITLE = "    %1$s's Wallist";
	private static final String PROMPT = "Put our command here";
	private static final String DURATION = "%1$s - %2$s"; 
	private static final String EMPTY_MESSAGE = "%1$s\n\n\n\n"; 
	
	private static final int COMPONENT_GAP_H = 20;
	private static final int COMPONENT_GAP_V = 20;
	private static final int INDEX_WIDTH = 30;
	private static final int HEADER_INDEX_WIDTH = 50;
	private static final int TIME_WIDTH = 400;
	private static final int INPUT_BOX_HEIGHT = 30;
	private static final int STAGE_WIDTH = 1000;	
	private static final int TITLE_HEIGHT = 40;	
	private static final int HEADER_HEIGHT = 30;
	private static final int STAGE_HEIGHT = 650;	
	private static final double SCROLL_PERCENTAGE = 0.1;
	private static final int PADDING = 10;
	
	private final Insets COMPONENT_PADDING = new Insets(0, 20, 20, 20);
	private final Insets CONFIG_PADDING = new Insets(20, 20, 20, 20);
	private final Insets CONTENT_PADDING = new Insets(5, 0, 5, 0);
	
	public static void launching(){
		launch();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		state = wallistModel.getState();
		stageSetup(primaryStage);
		configSetup();
    	refreshTaskPane();
		inputProcess();
	}

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
		        } else{
		        	pendingRefresh();
		        }
			}
		});		
	}
	
	private void pendingRefresh() {
		 
	}
	
	private void refresh() {
		command = inputBox.getText();
		boolean isSuccess = wallistModel.processInputString(command);
		state = wallistModel.getState();
		if (isSuccess){
			refreshTaskPane();
		} else {
			Label displayText = new Label(state.getDisplayMessage());
			displayText.setId("message");
			taskStackPane.getChildren().add(displayText);
			FadeAnimation fade = new FadeAnimation(displayText);
			fade.playAnimation();
		}
		inputBox.clear();
	}

	private void refreshTaskPane() {
		loadHeader();
		if (state.getCommandType().equals(CommandType.EXIT)){
			window.close();
		} else if (state.getViewMode().equals(ViewMode.CONFIG)){
			loadConfig();
		} else if (state.isCurrentTasksEmpty()){
			loadEmptyPane();
		} else{
			loadTask();	
		}
	}

	private void loadHeader() {
		resetTab();
		switch(state.getViewMode()){
		case ALL:
			allHeader.setId("tab");
			break;
		case DEADLINE:
			deadlineHeader.setId("tab");
			break;
		case FLOATING:
			floatingHeader.setId("tab");
			break;
		case FINISHED:
			finishedHeader.setId("tab");
			break;
		case START:
		    startHeader.setId("tab");
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
			startHeader.setId("tab");
		}
	}
	
	private void loadConfig(){
		taskTable.getChildren().clear();
		taskTable.getChildren().add(taskPane);
		taskPane.setContent(configs);
	}
	
	private void loadEmptyPane(){
		Rectangle taskBox = loadTaskBox();
		Text emptyMessage = new Text(String.format(EMPTY_MESSAGE, state.getEmptyMessage()));
		emptyMessage.setTextAlignment(TextAlignment.CENTER);
		emptyMessage.setId("empty");
		taskStackPane.getChildren().clear();
		taskStackPane.getChildren().addAll(taskBox, emptyMessage);
	}

	private void loadTask() {
		loadTaskPane();
		taskTable.getChildren().clear();
		taskTable.getChildren().addAll(tableHeader, taskPane);
		updateTableHeader();
		ArrayList<Task> taskList = state.getCurrentTasks();
		tasks.getChildren().clear();
		taskIndex = 0;
		for (Task task: taskList){
			displayRow(task);
		}
        if (taskList.size() > 0){
        	double position = (double) (state.getPositionIndex() + 1) / taskList.size();
		    taskPane.setVvalue(position);
        }
		taskPane.setContent(tasks);
	}
	
	private void loadTaskPane() {
		Rectangle taskBox = loadTaskBox();
		taskStackPane.getChildren().clear();
		taskStackPane.getChildren().addAll(taskBox, taskTable);
	}
	
	private void updateTableHeader() {
		tableHeader.getChildren().clear();
		if (state.getViewMode().equals(ViewMode.FLOATING)){
			indexHeader.setPrefWidth(HEADER_INDEX_WIDTH);
			contentHeader.setPrefWidth(contentHeaderWidth + TIME_WIDTH);
			tableHeader.getChildren().addAll(indexHeader, contentHeader);	
		} else {
			indexHeader.setPrefWidth(HEADER_INDEX_WIDTH);
			contentHeader.setPrefWidth(contentHeaderWidth);
			timeHeader.setPrefWidth(TIME_WIDTH);
			tableHeader.getChildren().addAll(indexHeader, contentHeader, timeHeader);
		}
	}
	
	private void displayRow(Task task) {
		taskIndex ++;
		String taskContent = task.getDisplayContent();
		String taskTime = "";
		if (task.getTaskType().equals(TaskType.DEADLINE)) {
		    taskTime = getTaskTime(task);	
		}
		String taskIdx = Integer.toString(taskIndex);
		
		GridPane taskLine = new GridPane();
		taskLine.setPadding(CONTENT_PADDING);
		taskLine.setHgap(10);
		if (taskIndex % 2 == 0){
			taskLine.setId("evenLine");
		} else {
			taskLine.setId("oddLine");
		}
		Column indexCol = new Column(taskIdx, 0, INDEX_WIDTH);
		indexCol.setAlignRight();
		setTaskView(task, indexCol);
		Column contentCol = new Column(taskContent, 1, contentWidth);
		contentCol.setWrap(contentWidth);
		setTaskView(task, contentCol);
		Column timeCol = new Column(taskTime, 2, TIME_WIDTH);
		timeCol.setAlignLeft();
		setTaskView(task, timeCol);
		taskLine.getChildren().addAll(indexCol.getColumn(), contentCol.getColumn(), timeCol.getColumn());
		tasks.getChildren().add(taskLine);
		if (taskIndex > state.getPositionIndex()){
			FadeAnimation fade = new FadeAnimation(taskLine);
			fade.playAnimation();
		}
	}

	private String getTaskTime(Task task) {
		Date startDate = task.getStartDate();
		Date endDate = task.getEndDate();
		boolean sameDate = false;
		boolean startThisYear = false;
		boolean endThisYear = sdfYear.format(endDate).equals(sdfYear.format(System.currentTimeMillis()));
		boolean hasEndTime = sdfDefaultTime.format(endDate).equals(defaultTime);
		
		System.out.println(sdfDefaultTime.format(endDate));
		
		if (startDate != null){
			startThisYear = sdfYear.format(startDate).equals(sdfYear.format(System.currentTimeMillis()));
			sameDate = sdfDate.format(task.getStartDate()).equals(sdfDate.format(task.getEndDate()));
		}
		if (startDate == null && endThisYear && hasEndTime){
		    return sdfDateThisYear.format(task.getEndDate());
		} else if (startDate == null && endThisYear && !hasEndTime){
		    return sdfThisYear.format(task.getEndDate());
		} else if (startDate == null && hasEndTime){
		    return sdf.format(task.getEndDate());
	    } else if (startDate == null && !hasEndTime){
	    	return sdfDate.format(task.getEndDate());
	    } else if (sameDate && startThisYear){
	    	return String.format(DURATION, sdfThisYear.format(task.getStartDate()), sdfTime.format(task.getEndDate()));		
		} else if (sameDate && !startThisYear){
			return String.format(DURATION, sdf.format(task.getStartDate()), sdf.format(task.getEndDate()));				
	    } else if (startThisYear && endThisYear && hasEndTime){
	    	return String.format(DURATION, sdfThisYear.format(task.getStartDate()), sdfThisYear.format(task.getEndDate()));
	    } else if (startThisYear && endThisYear && !hasEndTime){
	    	return String.format(DURATION, sdfDateThisYear.format(task.getStartDate()), sdfDateThisYear.format(task.getEndDate()));
	    } else if (startThisYear && !endThisYear && hasEndTime){
	    	return String.format(DURATION, sdfThisYear.format(task.getStartDate()), sdf.format(task.getEndDate()));
	    } else if (startThisYear && !endThisYear && !hasEndTime){
	    	return String.format(DURATION, sdfDateThisYear.format(task.getStartDate()), sdfDate.format(task.getEndDate()));
	    } else if (!startThisYear && endThisYear && hasEndTime){
	    	return String.format(DURATION, sdf.format(task.getStartDate()), sdfThisYear.format(task.getEndDate()));
	    } else {
	    	return String.format(DURATION, sdfDate.format(task.getStartDate()), sdfDateThisYear.format(task.getEndDate()));
	    }
	}

	private void setTaskView(Task task, Column col) {
		if (task.isOverdue() && task.getIsDetailDisplayed()){
			col.setZoomOverdue();
		} else if (task.isOverdue()){
			col.setOverdue();
		} else if (task.getIsDetailDisplayed()){
			col.setZoom();
		}
	}
	
	private TextField inputComponent() {
		TextField inputBox = new TextField();
		inputBox.setPrefHeight(INPUT_BOX_HEIGHT);
		inputBox.setMaxHeight(INPUT_BOX_HEIGHT);
		inputBox.setPromptText(PROMPT);
		layout.getChildren().add(inputBox);
		return inputBox;
	}
	

	private ScrollPane taskComponent() {
		Rectangle taskBox = loadTaskBox();
		taskPane = new ScrollPane();
		taskPane.setPrefSize(taskBoxWidth, taskBoxHeight);
		taskPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		taskPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	    taskStackPane.getChildren().addAll(taskBox, taskTable);
		tab.getChildren().add(taskStackPane);
		layout.getChildren().add(tab);
		return taskPane;
	}

	private Rectangle loadTaskBox() {
		Rectangle taskBox = new Rectangle();
		taskBox.setWidth(taskBoxWidth);
		taskBox.setHeight(taskBoxHeight);
		taskBox.setId("taskPane");
		return taskBox;
	}

	private void tableHeaderComponent(){
		indexHeader.setAlignment(Pos.CENTER);
		contentHeader.setTextAlignment(TextAlignment.CENTER);
		timeHeader.setTextAlignment(TextAlignment.CENTER);
		indexHeader.setId("header");
		contentHeader.setId("header");
		timeHeader.setId("header");
	}
	
	private void headerComponent() {
		sectionHeader.setPrefHeight(HEADER_HEIGHT);
		sectionHeader.setAlignment(Pos.CENTER);
		resetTab();
		tab.getChildren().add(sectionHeader);
	}

	private void resetTab() {
		allHeader = new Label(Constant.HEADER_ALL);
		deadlineHeader = new Label(Constant.HEADER_DEADLINE);
		floatingHeader = new Label(Constant.HEADER_FLOATING);
		startHeader = new Label(Constant.HEADER_START);
		searchHeader = new Label(Constant.HEADER_SEARCH);
		configHeader = new Label(Constant.HEADER_CONFIG);
		helpHeader = new Label(Constant.HEADER_HELP);
		finishedHeader = new Label(Constant.HEADER_FINISHED);
		sectionHeader.getChildren().clear();
		sectionHeader.getChildren().addAll(startHeader, allHeader, deadlineHeader, floatingHeader, finishedHeader, searchHeader, configHeader, helpHeader);
	}
	
	private void titleComponent() {
		StackPane titlePane = new StackPane();
		titlePane.setAlignment(Pos.CENTER_LEFT);
        Text titleText = new Text(String.format(TITLE, System.getProperty("user.name")));
        titleText.setId("titleText");
		title.setWidth(STAGE_WIDTH);
		title.setHeight(TITLE_HEIGHT);
		title.setId("title");
		titlePane.getChildren().addAll(title, titleText);
        layout.getChildren().add(titlePane);
	}
	

	private void stageSetup(Stage primaryStage) {
		window = primaryStage;
		window.initStyle(StageStyle.UNDECORATED);
        window.setResizable(true);
        window.getIcons().add(new Image("/title.png"));
    	layoutSetup();
		scene = new Scene(layout, STAGE_WIDTH, STAGE_HEIGHT);
		
		String themeSheet = "/resources/" + state.getTheme() + ".css";
		String fontSheet = "/resources/" + state.getFont() + ".css";
		
		scene.getStylesheets().addAll(themeSheet, fontSheet);
		
		window.setScene(scene);
		window.show();
		enableEscExit();
	}
	

	private void layoutSetup() {
		taskBoxHeight = STAGE_HEIGHT - COMPONENT_GAP_V * 4 - INPUT_BOX_HEIGHT -  HEADER_HEIGHT - TITLE_HEIGHT;
    	taskBoxWidth = (int)(STAGE_WIDTH - COMPONENT_GAP_H * 2);
    	contentWidth = taskBoxWidth - INDEX_WIDTH - TIME_WIDTH;
    	contentHeaderWidth = contentWidth + PADDING * 2;
		layout.setPadding(COMPONENT_PADDING);
		layout.setSpacing(COMPONENT_GAP_V);
		layout.setAlignment(Pos.CENTER);
		titleComponent();
        enableDrag();
		headerComponent();
		tableHeaderComponent();
        taskPane = taskComponent();
		inputBox = inputComponent();
	}
	
	private void configSetup(){
		String[] infoStr = state.getConfigInfo();
		configs.setPadding(CONFIG_PADDING);
		Text intro = new Text(infoStr[0]); 
		intro.setId("normal");		
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
	
	private void enableEscExit() {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override 
			public void handle(KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.ESCAPE) {
					window.close();
				}
			}
		});
	}

	private void enableDrag() {
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
}