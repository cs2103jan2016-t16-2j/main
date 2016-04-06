package gui;
/**
 * 
 * @author Kaidi
 *
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
	private final Color RED = Color.valueOf("ff5555");
	
	private final String TITLE = "%1$s's Wallist";
	private final String VENUE = "Venue: %1$s";
	private final String DETAIL = "Detail: %1$s";
	
	private final int COMPONENT_GAP_H = 20;
	private final int COMPONENT_GAP_V = 20;
	private final int INDEX_WIDTH = 30;
	private final int TIME_WIDTH = 350;
	private final int INPUT_BOX_HEIGHT = 30;
	private final int HEADER_HEIGHT = 30;
	
	private final int STAGE_HEIGHT = 650;
	private final int STAGE_WIDTH = 1000;
	
	private int taskBoxHeight;
	private int taskBoxWidth;
	private int contentWidth;
	private Color color;
	
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
		displayText.setId("message");
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
        if (taskList.size() > 0){
		    taskPane.setVvalue(state.getPositionIndex() / taskList.size());
        }
	}
	
	private void displayTaskLine(Task task) {
		taskIndex ++;
		String taskContent = task.getContent();
		if (task.getIsDetailDisplayed()){
			taskContent += "\n" + String.format(VENUE, task.getVenue()) + "\n" + String.format(DETAIL, task.getDetail());
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
			color = RED;
		} else{
			color = WHITE;
		}
		indexPane = indexStackPane(taskIndex);
		contentPane = contentPane(taskContent, contentWidth);
		deadlinePane = timePane(taskDeadline);
		
		taskLine.getChildren().addAll(indexPane, contentPane, deadlinePane);
		tasks.getChildren().add(taskLine);
	}
	
	private StackPane timePane(String taskDeadline) {
		StackPane deadlinePane = new StackPane();
		deadlinePane.setAlignment(Pos.TOP_LEFT);
		Rectangle deadlineRec = new Rectangle();
		deadlineRec.setWidth(TIME_WIDTH);
		deadlineRec.setOpacity(0);
		Text deadline = new Text(taskDeadline);
		deadline.setFill(color);
		deadlinePane.getChildren().addAll(deadlineRec, deadline);
		GridPane.setConstraints(deadlinePane, 2, 0);
		return deadlinePane;
	}

	private StackPane contentPane(String taskContent, int width) {
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

	private StackPane indexStackPane(int index) {
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
		contentLayout.getChildren().add(inputBox);
		return inputBox;
	}

	private ScrollPane taskComponent() {
		StackPane taskDisplay = new StackPane();
		Rectangle taskBox = boxGrid(taskBoxWidth, taskBoxHeight);
		ScrollPane taskPane = new ScrollPane();
		taskPane.setPrefSize(taskBoxWidth, taskBoxHeight);
		taskPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		taskPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	    taskDisplay.getChildren().addAll(taskBox, taskPane);
		contentLayout.getChildren().add(taskDisplay);
		return taskPane;
	}

	private void headerComponent() {
		sectionHeader.setPrefHeight(HEADER_HEIGHT);
		sectionHeader.setAlignment(Pos.CENTER);
		sectionHeader.setTextAlignment(TextAlignment.CENTER);
		contentLayout.getChildren().add(sectionHeader);
	}

	private void stageSetup(Stage primaryStage) {
		window = primaryStage;

        window.setResizable(true);

    	taskBoxHeight = STAGE_HEIGHT - COMPONENT_GAP_V * 4 - INPUT_BOX_HEIGHT -  HEADER_HEIGHT;
    	taskBoxWidth = (int)(STAGE_WIDTH - COMPONENT_GAP_H * 2);
    	contentWidth = taskBoxWidth - INDEX_WIDTH - TIME_WIDTH;
    	
        window.getIcons().add(new Image("/title.png"));
        
        window.setTitle(String.format(TITLE, System.getProperty("user.name")));
		contentLayout.setPadding(COMPONENT_PADDING);
		contentLayout.setSpacing(COMPONENT_GAP_V);
		//
		contentLayout.setAlignment(Pos.CENTER);
		//
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