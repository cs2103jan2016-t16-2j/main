package GUIPackage;
/**
 * 
 * @author Kaidi
 *
 */

import LogicPackage.*;
import CommonPackage.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeSet;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class GUI extends Application{
	
	private Stage window;
	private Scene scene;
	private GridPane layout = new GridPane();
	private Logic logic = new Logic();
	private String command;
	private TreeSet<Task> taskList;
	private SimpleDateFormat datesdf = new SimpleDateFormat("dd MMMM yyyy");
	private SimpleDateFormat daysdf = new SimpleDateFormat("EEEE");
	private SimpleDateFormat sdf = new SimpleDateFormat("dd MMM H:mm");
	
	private int taskIndex, floatyIndex;
	
	private final String TITLE = "%1$s's Wallist";
	private final String MESSAGE_SUCCESS = "success";
	
	private final int STAGE_HEIGHT = 600;
	private final int STAGE_WIDTH = 900;
	private final int COMPONENT_GAP_H = 30;
	private final int COMPONENT_GAP_V = 30;
	private final int FLOATYBOX_HEIGHT = 260;
	private final int FLOATYBOX_WIDTH = 250;
	private final int TASKBOX_HEIGHT = 470;
	private final int TASKBOX_WIDTH = 560;
	private final int TASK_CONTENT_WIDTH = 300;
	private final int TASK_CONTENT_WIDTH_FLOATY = 200;
	private final int TASK_INDEX_WIDTH = 30;
	private final int TASK_DEADLINE_WIDTH = 120;
	
	
	private final Insets COMPONENT_PADDING = new Insets(30, 30, 30, 30);
	private final Insets WARNING_PADDING = new Insets(0, 10, 0, 0);
	
	public static void main(String[] args){
		launch();
	}
	
	public GUI(){
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		stageSetup(primaryStage);
		
		ScrollPane floatyPane = floatyComponent();
        ScrollPane taskPane = taskComponent();
		TextField inputBox = inputComponent();
		
		VBox tasks = new VBox();
		taskPane.setContent(tasks);
		VBox floaties = new VBox();
		floatyPane.setContent(floaties);
		
    	refresh(tasks, floaties);
		
		inputBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent keyEvent) {
		        if (keyEvent.getCode() == KeyCode.ENTER)  {
		        	displayStatus(inputBox, tasks, floaties);
		        }
			}
		});
	}
	
	private void displayStatus(TextField inputBox, VBox tasks, VBox floaties) {
		command = inputBox.getText();
		String status = logic.process(command);
		Label displayText = new Label(status);
		GridPane.setConstraints(displayText, 0, 2, 2, 1, 
				HPos.RIGHT, VPos.CENTER, Priority.NEVER, Priority.NEVER, WARNING_PADDING);
		layout.getChildren().add(displayText);
		FadeTransition fade = fadeAnimation(displayText);
		fade.play();
		if (status.equals(MESSAGE_SUCCESS)){
			refresh(tasks, floaties);
		}
		inputBox.clear();
	}
	
	private void refresh(VBox tasks, VBox floaties) {
		tasks.getChildren().clear();
		taskList = logic.getList();
		taskIndex = 0;
		floatyIndex = 0;
		for (Task task: taskList){
			if (!task.getIsFinished()){
    			if (task.getIsFloating()){
	    			displayTaskLine(tasks, task);
		    	} else{
			    	displayFloatyLine(floaties, task);
			    }
			}
		}
	}
	
	private void displayFloatyLine(VBox floaties, Task task) {
		floatyIndex ++;
		String taskContent = task.getContent();
		GridPane taskLine = new GridPane();
		taskLine.setHgap(10);
		StackPane indexPane = indexCol(floatyIndex);
		StackPane contentPane = contentCol(taskContent, TASK_CONTENT_WIDTH_FLOATY);
		taskLine.getChildren().addAll(indexPane, contentPane);
		floaties.getChildren().add(taskLine);
		floaties.getChildren().add(taskLine);
	}

	private void displayTaskLine(VBox tasks, Task task) {
		taskIndex ++;
		String taskContent = task.getContent();
		String taskDeadline = sdf.format(task.getStartDate());
		GridPane taskLine = new GridPane();
		if (taskIndex % 2 == 0){
			taskLine.setId("gridPane");
		}
		taskLine.setHgap(10);
		StackPane indexPane = indexCol(taskIndex);
		StackPane contentPane = contentCol(taskContent, TASK_CONTENT_WIDTH);
		StackPane deadlinePane = deadlineCol(taskDeadline);
		taskLine.getChildren().addAll(indexPane, contentPane, deadlinePane);
		tasks.getChildren().add(taskLine);
	}
	
	private StackPane deadlineCol(String taskDeadline) {
		StackPane deadlinePane = new StackPane();
		deadlinePane.setAlignment(Pos.TOP_LEFT);
		Rectangle deadlineRec = new Rectangle();
		deadlineRec.setWidth(TASK_DEADLINE_WIDTH);
		deadlineRec.setOpacity(0);
		Text deadline = new Text(taskDeadline);
		deadline.setFill(Color.valueOf("#ffffcb"));
		deadlinePane.getChildren().addAll(deadlineRec, deadline);
		GridPane.setConstraints(deadlinePane, 2, 0);
		return deadlinePane;
	}

	private StackPane contentCol(String taskContent, int width) {
		StackPane contentPane = new StackPane();		            			
		Rectangle contentRec = new Rectangle();
		contentRec.setWidth(width);
		contentRec.setOpacity(0);
		Text content = new Text(taskContent);
		content.setWrappingWidth(width);
		content.setFill(Color.valueOf("#ffffcb"));
		contentPane.getChildren().addAll(contentRec, content);
		GridPane.setConstraints(contentPane, 1, 0);
		return contentPane;
	}

	private StackPane indexCol(int index) {
		StackPane indexPane = new StackPane();
		indexPane.setAlignment(Pos.TOP_RIGHT);
		Rectangle indexRec = new Rectangle();
		indexRec.setWidth(TASK_INDEX_WIDTH);
		indexRec.setOpacity(0);
		Text indexStr = new Text(Integer.toString(index));
		indexStr.setFill(Color.valueOf("#ffffcb"));
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
		inputBox.setPromptText("How Can I Help You ?");
		GridPane.setConstraints(inputBox, 0, 2, 2, 1);
		layout.getChildren().add(inputBox);
		return inputBox;
	}

	private ScrollPane taskComponent() {
		StackPane taskDisplay = new StackPane();
		Rectangle taskBox = boxGrid(TASKBOX_WIDTH, TASKBOX_HEIGHT);
		ScrollPane taskPane = new ScrollPane();
		taskPane.setPrefSize(TASKBOX_WIDTH, TASKBOX_HEIGHT);
		taskPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		taskPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	    taskDisplay.getChildren().addAll(taskBox, taskPane);
		GridPane.setConstraints(taskDisplay, 1, 0, 1, 2);
		layout.getChildren().add(taskDisplay);
		return taskPane;
	}

	private ScrollPane floatyComponent() {
		StackPane floatyTaskDisplay = new StackPane();
		Rectangle floatyBox = boxGrid(FLOATYBOX_WIDTH, FLOATYBOX_HEIGHT);
		ScrollPane floatyPane = new ScrollPane();
		floatyPane.setPrefSize(FLOATYBOX_WIDTH, FLOATYBOX_HEIGHT);
		floatyPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		floatyPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		floatyTaskDisplay.getChildren().add(floatyBox);	
		GridPane.setConstraints(floatyTaskDisplay, 0, 1);
		layout.getChildren().add(floatyTaskDisplay);
		return floatyPane;
	}

	private void timeComponent(){
		VBox timeDisplay = new VBox();
		timeDisplay.setAlignment(Pos.CENTER);
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

        window.initStyle(StageStyle.UNDECORATED);
		
        window.setTitle(String.format(TITLE, System.getProperty("user.name")));
		layout.setPadding(COMPONENT_PADDING);
		layout.setVgap(COMPONENT_GAP_V);
		layout.setHgap(COMPONENT_GAP_H);
		timeComponent();
		scene = new Scene(layout, STAGE_WIDTH, STAGE_HEIGHT);
		scene.getStylesheets().add("Stylesheet.css");
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