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
import javafx.scene.text.TextAlignment;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
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
	private State state;
	private int taskIndex;
	private double vValue;
	
	private WallistModel wallistModel = new WallistModel();
	private String command;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd MMM y HH:mm");
	
	private int taskBoxHeight;
	private int taskBoxWidth;
	private int contentWidth;
	private Color color;
	
	private final Color COLOR_ZOOM = Color.PALEGOLDENROD;
	private final Color COLOR_NORMAL = Color.WHITE;
	private final Color COLOR_OVERDUE = Color.RED;
	
	private final String TITLE = "%1$s's Wallist";
	private final String VENUE = "Venue: %1$s";
	private final String DETAIL = "Detail: %1$s";
	private final String DURATION = "%1$s - %2$s"; 
	
	private final int COMPONENT_GAP_H = 20;
	private final int COMPONENT_GAP_V = 20;
	private final int INDEX_WIDTH = 30;
	private final int TIME_WIDTH = 400;
	private final int INPUT_BOX_HEIGHT = 30;
	private final int HEADER_HEIGHT = 30;
	private final int STAGE_HEIGHT = 650;
	private final int STAGE_WIDTH = 1000;	
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
        	double position = (double) (state.getPositionIndex() + 1) / taskList.size();
		    taskPane.setVvalue(position);
        }
	}
	
	private void displayTaskLine(Task task) {
		taskIndex ++;
		System.out.println("Here \n" + task.getContent());
		String taskContent = task.getContent();
		if (task.getIsDetailDisplayed()){
			taskContent += "\n\n" + String.format(VENUE, task.getVenue()) + "\n" + String.format(DETAIL, task.getDetail());	
		}
		String taskDeadline = "";
		if (task.getTaskType().equals(TaskType.DEADLINE)){
			taskDeadline = sdf.format(task.getEndDate());
			if (task.getStartDate()!= null){
				taskDeadline = String.format(DURATION, sdf.format(task.getStartDate()), taskDeadline);				
			}
		}
		
		GridPane taskLine = new GridPane();
		taskLine.setPadding(CONTENT_PADDING);
		taskLine.setHgap(10);
		if (taskIndex % 2 == 0){
			taskLine.setId("gridPane");
		}
		if (task.isOverdue()){
			color = COLOR_OVERDUE;
		} else if (task.getIsDetailDisplayed()){
			color = COLOR_ZOOM;
		} else{
			color = COLOR_NORMAL;
		}
		Column indexCol = new Column(Integer.toString(taskIndex), 0);
		indexCol.setWidth(INDEX_WIDTH);
		indexCol.setColor(color);
		indexCol.setAlignRight();
		Column contentCol = new Column(taskContent, 1);
		contentCol.setWidth(contentWidth);
		contentCol.setWrap(contentWidth);
		contentCol.setColor(color);
		Column timeCol = new Column(taskDeadline, 2);
		timeCol.setWidth(TIME_WIDTH);
		timeCol.setColor(color);
		timeCol.setAlignLeft();
		if (task.getIsDetailDisplayed()){
			indexCol.setZoom();
			contentCol.setZoom();
			timeCol.setZoom();
		}
		
		taskLine.getChildren().addAll(indexCol.getColumn(), contentCol.getColumn(), timeCol.getColumn());
		tasks.getChildren().add(taskLine);
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