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

import common.*;
import facade.WallistModel;
import javafx.animation.FadeTransition;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GUI extends Application{
	
	private Stage window;
	private Scene scene;
	private StackPane layout = new StackPane();
	private VBox contentLayout = new VBox();
	private VBox tasks = new VBox();
	private Label sectionHeader = new Label();
	
	private ScrollPane taskPane;
	private TextField inputBox;
	
	private WallistModel wallistModel = new WallistModel();
	private String command;
	private SimpleDateFormat datesdf = new SimpleDateFormat("dd MMMM yyyy");
	private SimpleDateFormat daysdf = new SimpleDateFormat("EEEE");
	private SimpleDateFormat sdf = new SimpleDateFormat("dd MMM y HH:mm");
	
	private State state;
	
	private int taskIndex;
	private double vValue;

	private final Color WHITE = Color.valueOf("ffffcb");
	private final Color RED = Color.valueOf("b4df4f");
	
	private final String TITLE = "%1$s's Wallist";
	private final String VENUE = "Venue: %1$s";
	private final String DETAIL = "Detail: %1$s";
	
	private final int COMPONENT_GAP_H = 20;
	private final int COMPONENT_GAP_V = 20;
	private final int INDEX_WIDTH = 30;
	private final int END_TIME_WIDTH = 250;
	private final int TIME_BOX_HEIGHT = 135;
	private final int INPUT_BOX_HEIGHT = 30;
	
	private final int STAGE_HEIGHT = 650;
	private final int STAGE_WIDTH = 1000;
	
	private int normalBoxHeight;
	private int normalBoxWidth;
	private int noralContentWidth;
	
	private final double SCROLL_PERCENTAGE = 0.1;
		
	private final Insets COMPONENT_PADDING = new Insets(20, 20, 20, 20);
	private final Insets CONTENT_PADDING = new Insets(5, 0, 5, 0);
	
	public static void launching(){
		launch();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		stageSetup(primaryStage);

		headerComponent();
        taskPane = taskComponent();
		inputBox = inputComponent();
		
		taskPane.setContent(tasks);
		
		state = wallistModel.getState();
    	refresh();
	
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
		        	displayStatus();
		        }
			}
		});
	}
	
	private void displayStatus() {
		command = inputBox.getText();
		boolean isSuccess = wallistModel.process(command);
		state = wallistModel.getState();
		if (isSuccess){
			refresh();
		}
		Label displayText = new Label(state.getDisplayMessage());
		layout.getChildren().add(displayText);
		FadeTransition fade = fadeAnimation(displayText);
		fade.play();
		inputBox.clear();
	}

	private void refresh() {
		sectionHeader.setText(state.getHeader());
		ArrayList<Task> taskList = state.getCurrentTasks();
		tasks.getChildren().clear();
		taskIndex = 0;
		for (Task task: taskList){
			displayTaskLine(task);
		}
		taskPane.setVvalue(state.getPositionIndex() / taskList.size());
	}
	
	private void displayTaskLine(Task task) {
		taskIndex ++;
		boolean isOverdue = false;
		String taskContent = task.getContent();
		if (task.getIsDetailDisplayed()){
			taskContent = taskContent + "\n" + String.format(VENUE, task.getVenue()) + "\n" + String.format(DETAIL, task.getDetail());
		}
		String taskDeadline = "";
		StackPane indexPane, contentPane, deadlinePane;
		if (task.getTaskType().equals(TaskType.DEADLINE)){
			taskDeadline = sdf.format(task.getEndDate());

			if (task.getStartDate()!= null){
				taskDeadline = sdf.format(task.getStartDate()) + " - " + taskDeadline;				
			}
		}
		GridPane taskLine = new GridPane();
		taskLine.setPadding(CONTENT_PADDING);
		taskLine.setHgap(10);
		if (taskIndex % 2 == 0){
			taskLine.setId("gridPane");
		}
		if (task.isOverdue()){
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
		indexPane.setAlignment(Pos.CENTER_RIGHT);
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
		contentLayout.getChildren().add(inputBox);
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
		contentLayout.getChildren().add(taskDisplay);
		return taskPane;
	}

	private void headerComponent() {
		sectionHeader.setPrefHeight(30);
		sectionHeader.setMaxHeight(30);
		sectionHeader.setAlignment(Pos.CENTER);
		sectionHeader.setTextAlignment(TextAlignment.CENTER);
		contentLayout.getChildren().add(sectionHeader);
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
		contentLayout.getChildren().add(timeDisplay);
	}

	private void stageSetup(Stage primaryStage) {
		window = primaryStage;

        window.setResizable(true);

    	normalBoxHeight = STAGE_HEIGHT - COMPONENT_GAP_V * 4 - INPUT_BOX_HEIGHT * 3;
    	normalBoxWidth = (int)(STAGE_WIDTH - COMPONENT_GAP_H * 2);
    	noralContentWidth = normalBoxWidth - INDEX_WIDTH - END_TIME_WIDTH;
    	
        window.getIcons().add(new Image("/title.png"));
        
        window.setTitle(String.format(TITLE, System.getProperty("user.name")));
		contentLayout.setPadding(COMPONENT_PADDING);
		contentLayout.setSpacing(COMPONENT_GAP_V);

        layout.getChildren().add(contentLayout);
        
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