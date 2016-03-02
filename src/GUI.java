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

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.util.Duration;

public class GUI extends Application{
	
	Button button;
	Stage window;
	Scene scene;
	GridPane layout = new GridPane();
	Logic logic = new Logic();
	String command;
	TreeSet<Task> taskList;
	
	private final String TITLE = "%1$s's Wallist";
	private final String TITLE_FLOATY = "FLOATY TASKS";
	private final String TITLE_ALL = "ALL TASKS";
	private final String MESSAGE_SUCCESS = "success";
	
	private final int STAGE_HEIGHT = 600;
	private final int STAGE_WIDTH = 900;
	private final int COMPONENT_GAP_H = 30;
	private final int COMPONENT_GAP_V = 30;
	private final int FLOATYBOX_HEIGHT = 250;
	private final int FLOATYBOX_WIDTH = 240;
	private final int TASKBOX_HEIGHT = 560;
	private final int TASKBOX_WIDTH = 460;
	
	private final Insets COMPONENT_PADDING = new Insets(30, 30, 30, 30);
	private final Insets CONTENT_PADDING = new Insets(10, 10, 10, 10);
	private final Insets WARNING_PADDING = new Insets(0, 10, 0, 0);
	
	public static void main(String[] args){
		launch(args);
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
		inputBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent keyEvent) {
		        if (keyEvent.getCode() == KeyCode.ENTER)  {
		        	command = inputBox.getText();
		        	String status = logic.process(command);
		        	Label displayText = new Label(status);
		        	GridPane.setConstraints(displayText, 0, 2, 2, 1, HPos.RIGHT, VPos.CENTER, Priority.NEVER, Priority.NEVER, WARNING_PADDING);
		        	layout.getChildren().add(displayText);
		            FadeTransition fade = fadeAnimation(displayText);
		            fade.play();
		            if (status.equals(MESSAGE_SUCCESS)){
		            	tasks.getChildren().clear();
		            	taskList = logic.getList();
		            	for (Task task: taskList){
		            		Text line = new Text(task.toString());
		            		line.setFill(Color.valueOf("#ffffcb"));
		            		if (task.getIsFloating()){
		            		    tasks.getChildren().add(line);	
		            		} else{
		            			floaties.getChildren().add(line);
		            		}
		            	}
		        	}
		    		inputBox.clear();
		        }
			}
		});
	}
	
	private FadeTransition fadeAnimation(Label comm) {
		FadeTransition ft = new FadeTransition(Duration.millis(2000), comm);
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
		Rectangle taskBox = boxGrid(TASKBOX_HEIGHT, TASKBOX_WIDTH);
		ScrollPane taskPane = new ScrollPane();
		taskPane.setPrefSize(TASKBOX_HEIGHT, TASKBOX_WIDTH);
		//taskPane.setContent();
		taskPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
	    taskDisplay.getChildren().addAll(taskBox, taskPane);
		GridPane.setConstraints(taskDisplay, 1, 0, 1, 2);
		layout.getChildren().add(taskDisplay);
		return taskPane;
	}

	private ScrollPane floatyComponent() {
		StackPane floatyTaskDisplay = new StackPane();
		Rectangle floatyBox = boxGrid(FLOATYBOX_HEIGHT, FLOATYBOX_WIDTH);
		ScrollPane floatyPane = new ScrollPane();
		floatyPane.setPrefSize(FLOATYBOX_HEIGHT, FLOATYBOX_WIDTH);
		//floatyPane.setConent();
		floatyPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		floatyTaskDisplay.getChildren().add(floatyBox);	
		GridPane.setConstraints(floatyTaskDisplay, 0, 1);
		layout.getChildren().add(floatyTaskDisplay);
		return floatyPane;
	}

	private void timeComponent(){
		VBox timeDisplay = new VBox();
		Date today = Calendar.getInstance().getTime();
		String dateStr = new SimpleDateFormat("dd MMMM yyyy").format(today);
		String dayStr = new SimpleDateFormat("EEEE").format(today);
		
		Label date = new Label(dateStr+"\n"+dayStr);
		date.setId("dateDisplay");
		DigitalClock clock = new DigitalClock();
		timeDisplay.getChildren().addAll(clock, date);
		GridPane.setConstraints(timeDisplay, 0, 0);
		layout.getChildren().add(timeDisplay);

	}

	private void stageSetup(Stage primaryStage) {
		window = primaryStage;
		window.setTitle(String.format(TITLE, System.getProperty("user.name")));
		layout.setPadding(COMPONENT_PADDING);
		layout.setVgap(COMPONENT_GAP_V);
		layout.setHgap(COMPONENT_GAP_H);
		timeComponent();
		scene = new Scene(layout, STAGE_WIDTH, STAGE_HEIGHT);
		scene.getStylesheets().add("Stylesheet.css");
		window.setScene(scene);
		window.show();
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