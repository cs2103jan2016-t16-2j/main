package gui;
/**
 * 
 * @author Kaidi
 *
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeSet;

import common.*;
import facade.WallistModel;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GUI extends Application{
	
	private Stage window;
	private Scene scene;
	private GridPane layout = new GridPane();
	private WallistModel wallistModel = new WallistModel();
	private String command;
	private SimpleDateFormat datesdf = new SimpleDateFormat("dd MMMM yyyy");
	private SimpleDateFormat daysdf = new SimpleDateFormat("EEEE");
	private SimpleDateFormat sdf = new SimpleDateFormat("dd MMM y HH:mm");
	
	private int taskIndex, floatyIndex;
	private double vValue;
	

	private final Color WHITE = Color.valueOf("ffffcb");
	private final Color RED = Color.valueOf("b4df4f");
	
	private final String TITLE = "%1$s's Wallist";
	
	private final int COMPONENT_GAP_H = 30;
	private final int COMPONENT_GAP_V = 30;
	private final int INDEX_WIDTH = 30;
	private final int END_TIME_WIDTH = 250;
	private final int TIME_BOX_HEIGHT = 135;
	private final int INPUT_BOX_HEIGHT = 40;
	
	private final int STAGE_HEIGHT = 650;
	private final int STAGE_WIDTH = 1300;
	
	private int floatyBoxHeight;
	private int floatyBoxWidth;
	private int normalBoxHeight;
	private int normalBoxWidth;
	private int floatyContentWidth;
	private int noralContentWidth;

	
	private final double SCROLL_PERCENTAGE = 0.1;
		
	private final Insets COMPONENT_PADDING = new Insets(30, 30, 30, 30);
	private final Insets WARNING_PADDING = new Insets(0, 10, 0, 0);
	
	public static void launching(){
		launch();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		stageSetup(primaryStage);
		
		ScrollPane floatyPane = floatyTaskComponent();
        ScrollPane taskPane = taskComponent();
		TextField inputBox = inputComponent();
		
		VBox tasks = new VBox();
		taskPane.setContent(tasks);
		VBox floaties = new VBox();
		floatyPane.setContent(floaties);
		
		State state = wallistModel.getState();
		TreeSet<Task> taskList = state.getNormalTasks();
		ArrayList<Task> floatyList = state.getFloatingTasks();
    	refresh(tasks, floaties, taskList, floatyList);
	
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
		        	displayStatus(inputBox, tasks, floaties);
		        }
			}
		});
	}
	
	private void displayStatus(TextField inputBox, VBox tasks, VBox floaties) {
		command = inputBox.getText();
		boolean isSuccess = wallistModel.process(command);
		State state = wallistModel.getState();
		if (isSuccess){
			if (state.isSearch()){
				ArrayList<Task> resultList = state.getSearchResultTasks();
				refresh(tasks, resultList);
			}
			else{	
		    	TreeSet<Task> taskList = state.getNormalTasks();
			    ArrayList<Task> floatyList = state.getFloatingTasks();
	    	    refresh(tasks, floaties, taskList, floatyList);
			}
		}
		Label displayText = new Label(state.getMessage());
		GridPane.setConstraints(displayText, 0, 2, 2, 1, 
				HPos.RIGHT, VPos.CENTER, Priority.NEVER, Priority.NEVER, WARNING_PADDING);
		layout.getChildren().add(displayText);
		FadeTransition fade = fadeAnimation(displayText);
		fade.play();
		inputBox.clear();
	}
	
	private void refresh(VBox tasks, ArrayList<Task> resultList) {
		tasks.getChildren().clear();
		taskIndex = 0;
		for (Task task: resultList){
			if (!task.getIsFinished()){
				displayNormalTaskLine(tasks, task);
		    }
		}
	}

	private void refresh(VBox tasks, VBox floaties, TreeSet<Task> taskList, ArrayList<Task> floatyList) {
		tasks.getChildren().clear();
		floaties.getChildren().clear();
		taskIndex = 0;
		floatyIndex = 0;
		for (Task task: taskList){
			if (!task.getIsFinished()){
	    			displayNormalTaskLine(tasks, task);
		    }
		}
		
		for (Task task: floatyList){
			if (!task.getIsFinished()){
			    	displayFloatyTaskLine(floaties, task);
			}
		}
	}
	
	private void displayFloatyTaskLine(VBox floaties, Task task) {
		floatyIndex ++;
		String taskContent = task.getContent();
		//if (!task.getVenue().isEmpty()){
		//	taskContent = taskContent + "\n" + task.getVenue();
		//}
		
		GridPane taskLine = new GridPane();
		if (floatyIndex % 2 == 0){
			taskLine.setId("gridPane");
		}
		taskLine.setHgap(10);
		StackPane indexPane = indexStackPane(floatyIndex, WHITE);
		StackPane contentPane = contentPane(taskContent, floatyContentWidth, WHITE);
		taskLine.getChildren().addAll(indexPane, contentPane);
		floaties.getChildren().add(taskLine);
	}

	private void displayNormalTaskLine(VBox tasks, Task task) {
		taskIndex ++;
		boolean isOverdue = false;
		String taskContent = task.getContent();
		//if (!task.getVenue().isEmpty()){
		//	taskContent = taskContent + "\n" + task.getVenue();
		//}
		String taskDeadline = "";
		StackPane indexPane, contentPane, deadlinePane;
		if (task.getTaskType().equals(TaskType.DEADLINE)){
			taskDeadline = sdf.format(task.getEndDate());

			if (task.getIsStartDate()){

				taskDeadline = sdf.format(task.getStartDate()) + "\n" + taskDeadline;				
			}
			Date today = Calendar.getInstance().getTime();
			if (today.after(task.getEndDate())){
				isOverdue = true;
			}
		}
		GridPane taskLine = new GridPane();
		taskLine.setHgap(10);
		if (taskIndex % 2 == 0){
			taskLine.setId("gridPane");
		}
		if (isOverdue){
			indexPane = indexStackPane(taskIndex, RED);
			contentPane = contentPane(taskContent, noralContentWidth, RED);
			deadlinePane = timePane(taskDeadline, RED);
		} else {
			indexPane = indexStackPane(taskIndex, WHITE);
			contentPane = contentPane(taskContent, noralContentWidth, WHITE);
			deadlinePane = timePane(taskDeadline, WHITE);
		}
		taskLine.getChildren().addAll(indexPane, contentPane, deadlinePane);
		tasks.getChildren().add(taskLine);
	}
	
	private StackPane timePane(String taskDeadline, Color color) {
		StackPane deadlinePane = new StackPane();
		deadlinePane.setAlignment(Pos.TOP_LEFT);
		Rectangle deadlineRec = new Rectangle();
		deadlineRec.setWidth(END_TIME_WIDTH);
		deadlineRec.setOpacity(0);
		Text deadline = new Text(taskDeadline);
		deadline.setFill(color);
		deadlinePane.getChildren().addAll(deadlineRec, deadline);
		GridPane.setConstraints(deadlinePane, 2, 0);
		return deadlinePane;
	}

	private StackPane contentPane(String taskContent, int width, Color color) {
		StackPane contentPane = new StackPane();		            			
		Rectangle contentRec = new Rectangle();
		contentRec.setWidth(width);
		contentRec.setOpacity(0);
		Text content = new Text(taskContent);
		content.setWrappingWidth(width);
		content.setFill(color);
		contentPane.getChildren().addAll(contentRec, content);
		GridPane.setConstraints(contentPane, 1, 0);
		return contentPane;
	}

	private StackPane indexStackPane(int index, Color color) {
		StackPane indexPane = new StackPane();
		indexPane.setAlignment(Pos.TOP_RIGHT);
		Rectangle indexRec = new Rectangle();
		indexRec.setWidth(INDEX_WIDTH);
		indexRec.setOpacity(0);
		Text indexStr = new Text(Integer.toString(index));
		indexStr.setFill(color);
		indexPane.getChildren().addAll(indexRec,indexStr);
		GridPane.setConstraints(indexPane, 0, 0);
		return indexPane;
	}
	
	private FadeTransition fadeAnimation(Label comm) {
		FadeTransition ft = new FadeTransition(Duration.millis(1000), comm);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.setCycleCount(2);
		ft.setAutoReverse(true);
		return ft;
	}
	
	private TextField inputComponent() {
		TextField inputBox = new TextField();
		inputBox.setPrefHeight(INPUT_BOX_HEIGHT);
		inputBox.setMaxHeight(INPUT_BOX_HEIGHT);
		inputBox.setPromptText("How Can I Help You ?");
		GridPane.setConstraints(inputBox, 0, 2, 2, 1);
		layout.getChildren().add(inputBox);
		return inputBox;
	}

	private ScrollPane taskComponent() {
		StackPane taskDisplay = new StackPane();
		Rectangle taskBox = boxGrid(normalBoxWidth, normalBoxHeight);
		ScrollPane taskPane = new ScrollPane();
		taskPane.setPrefSize(normalBoxWidth, normalBoxHeight);
		taskPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		taskPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	    taskDisplay.getChildren().addAll(taskBox, taskPane);
		GridPane.setConstraints(taskDisplay, 1, 0, 1, 2);
		layout.getChildren().add(taskDisplay);
		return taskPane;
	}

	private ScrollPane floatyTaskComponent() {
		StackPane floatyTaskDisplay = new StackPane();
		Rectangle floatyBox = boxGrid(floatyBoxWidth, floatyBoxHeight);
		ScrollPane floatyPane = new ScrollPane();
		floatyPane.setPrefSize(floatyBoxWidth, floatyBoxHeight);
		floatyPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		floatyPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		floatyTaskDisplay.getChildren().addAll(floatyBox, floatyPane);	
		GridPane.setConstraints(floatyTaskDisplay, 0, 1);
		layout.getChildren().add(floatyTaskDisplay);
		return floatyPane;
	}

	private void timeComponent(){
		HBox timeDisplay = new HBox(COMPONENT_GAP_H);
		timeDisplay.setAlignment(Pos.CENTER);
		timeDisplay.setPrefHeight(TIME_BOX_HEIGHT);
		timeDisplay.setMaxHeight(TIME_BOX_HEIGHT);
		
		Date today = Calendar.getInstance().getTime();
		String dateStr = datesdf.format(today);
		String dayStr = daysdf.format(today);

		Label date = new Label(dateStr+"\n"+dayStr);
		date.setId("dateDisplay");
		DigitalClock clock = new DigitalClock();
		timeDisplay.getChildren().addAll(clock, date);
		GridPane.setConstraints(timeDisplay, 0, 0);
		layout.getChildren().add(timeDisplay);
	}

	private void stageSetup(Stage primaryStage) {
		window = primaryStage;

        window.setResizable(true);

    	normalBoxHeight = STAGE_HEIGHT - COMPONENT_GAP_V * 3 - INPUT_BOX_HEIGHT;
    	normalBoxWidth = (int)(STAGE_WIDTH * 2 / 3 - COMPONENT_GAP_H);
    	floatyBoxHeight = STAGE_HEIGHT - COMPONENT_GAP_V * 4 - INPUT_BOX_HEIGHT - TIME_BOX_HEIGHT;
    	floatyBoxWidth = STAGE_WIDTH - COMPONENT_GAP_H * 3 - normalBoxWidth;
    	floatyContentWidth = floatyBoxWidth - INDEX_WIDTH;
    	noralContentWidth = normalBoxWidth - INDEX_WIDTH - END_TIME_WIDTH;
    	
        window.getIcons().add(new Image("/title.png"));
        
        window.setTitle(String.format(TITLE, System.getProperty("user.name")));
		layout.setPadding(COMPONENT_PADDING);
		layout.setVgap(COMPONENT_GAP_V);
		layout.setHgap(COMPONENT_GAP_H);
		timeComponent();
		scene = new Scene(layout, STAGE_WIDTH, STAGE_HEIGHT);
		scene.getStylesheets().add("/gui/Stylesheet.css");
		window.setScene(scene);
		window.show();
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override 
			public void handle(KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.ESCAPE) {
					window.close();
				}
			}
		});
	}
	
	private Rectangle boxGrid(int width, int height) {
		Rectangle box = new Rectangle();
		box.setWidth(width);
		box.setHeight(height);
		box.setArcWidth(10);
		box.setArcHeight(10);
		box.setOpacity(0.1);
		box.setFill(Color.WHITE);
		return box;
	}

}