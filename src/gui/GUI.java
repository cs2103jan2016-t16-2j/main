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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GUI extends Application{
	
	static double xOffset, yOffset;
	
	private Stage window;
	private Scene scene;
	private StackPane taskStackPane = new StackPane();
	private VBox layout = new VBox();
	private VBox tasks = new VBox();
	private Label sectionHeader = new Label();
	private Rectangle title = new Rectangle();
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
	
	private final String TITLE = "    %1$s's Wallist";
	private final String PROMPT = "Put our command here";
	private final String VENUE = "Venue: %1$s";
	private final String DETAIL = "Detail: %1$s";
	private final String DURATION = "%1$s - %2$s"; 
	
	private final int COMPONENT_GAP_H = 20;
	private final int COMPONENT_GAP_V = 20;
	private final int INDEX_WIDTH = 30;
	private final int TIME_WIDTH = 400;
	private final int INPUT_BOX_HEIGHT = 30;
	private final int STAGE_WIDTH = 1000;	
	private final int TITLE_HEIGHT = 40;	
	private final int HEADER_HEIGHT = 30;
	private final int STAGE_HEIGHT = 650;	
	private final double SCROLL_PERCENTAGE = 0.1;
		
	private final Insets COMPONENT_PADDING = new Insets(0, 20, 20, 20);
	private final Insets CONTENT_PADDING = new Insets(5, 0, 5, 0);
	
	public static void launching(){
		launch();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		stageSetup(primaryStage);
		state = wallistModel.getState();
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
		        }
			}
		});
	}
	
	private void refresh() {
		command = inputBox.getText();
		boolean isSuccess = wallistModel.process(command);
		state = wallistModel.getState();
		if (isSuccess){
			refreshTaskPane();
		}
		Label displayText = new Label(state.getDisplayMessage());
		displayText.setId("message");
		taskStackPane.getChildren().add(displayText);
		FadeAnimation fade = new FadeAnimation(displayText);
		fade.playAnimation();
		inputBox.clear();
	}

	private void refreshTaskPane() {
		sectionHeader.setText(state.getHeader());
		//state.setViewMode(ViewMode.CONFIG);
		if (state.getViewMode().equals(ViewMode.CONFIG)){
			loadConfig();
		}else{
			loadTask();	
		}
	}
	
	private void loadConfig(){
		String infoStr = state.getConfigInfo();
		tasks.getChildren().clear();
		tasks.setPadding(COMPONENT_PADDING);
		Text info = new Text(infoStr); 
		info.setId("normal");
		ThemeSelector themeSelector = new ThemeSelector();
		GridPane themes = themeSelector.getTheme();
		tasks.getChildren().addAll(info, themes);
	}

	private void loadTask() {
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
	}
	
	private void displayRow(Task task) {
		taskIndex ++;
		String taskContent = task.getContent();
		if (task.getIsDetailDisplayed()){
			taskContent += "\n\n" + String.format(VENUE, task.getVenue()) + "\n" + String.format(DETAIL, task.getDetail());	
		}
		String taskTime = "";
		if (task.getTaskType().equals(TaskType.DEADLINE)){
			taskTime = sdf.format(task.getEndDate());
			if (task.getStartDate()!= null){
				taskTime = String.format(DURATION, sdf.format(task.getStartDate()), taskTime);				
			}
		}
		
		GridPane taskLine = new GridPane();
		taskLine.setPadding(CONTENT_PADDING);
		taskLine.setHgap(10);
		if (taskIndex % 2 == 0){
			taskLine.setId("gridPane");
		}
		Column indexCol = new Column(Integer.toString(taskIndex), 0, INDEX_WIDTH);
		indexCol.setAlignRight();
		Column contentCol = new Column(taskContent, 1, contentWidth);
		contentCol.setWrap(contentWidth);
		Column timeCol = new Column(taskTime, 2, TIME_WIDTH);
		timeCol.setAlignLeft();
		
		if (task.isOverdue()){
			if (task.getIsDetailDisplayed()){
				indexCol.setZoomOverdue();
				contentCol.setZoomOverdue();
				timeCol.setZoomOverdue();
			} else{
				indexCol.setOverdue();
				contentCol.setOverdue();
				timeCol.setOverdue();
			}
		} else if (task.getIsDetailDisplayed()){
			indexCol.setZoom();
			contentCol.setZoom();
			timeCol.setZoom();
		}
		
		taskLine.getChildren().addAll(indexCol.getColumn(), contentCol.getColumn(), timeCol.getColumn());
		tasks.getChildren().add(taskLine);
		if (taskIndex > state.getPositionIndex()){
			FadeAnimation fade = new FadeAnimation(taskLine);
			fade.playAnimation();;
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
		Rectangle taskBox = new Rectangle();
		taskBox.setWidth(taskBoxWidth);
		taskBox.setHeight(taskBoxHeight);
		taskBox.setId("taskPane");
		ScrollPane taskPane = new ScrollPane();
		taskPane.setPrefSize(taskBoxWidth, taskBoxHeight);
		taskPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		taskPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	    taskStackPane.getChildren().addAll(taskBox, taskPane);
		layout.getChildren().add(taskStackPane);
		return taskPane;
	}

	private void headerComponent() {
		sectionHeader.setPrefHeight(HEADER_HEIGHT);
		sectionHeader.setAlignment(Pos.CENTER);
		sectionHeader.setTextAlignment(TextAlignment.CENTER);
		layout.getChildren().add(sectionHeader);
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
		scene.getStylesheets().add("/gui/Autumn.css");
		window.setScene(scene);
		window.show();
		enableEscExit();
	}

	private void layoutSetup() {
		taskBoxHeight = STAGE_HEIGHT - COMPONENT_GAP_V * 4 - INPUT_BOX_HEIGHT -  HEADER_HEIGHT - TITLE_HEIGHT;
    	taskBoxWidth = (int)(STAGE_WIDTH - COMPONENT_GAP_H * 2);
    	contentWidth = taskBoxWidth - INDEX_WIDTH - TIME_WIDTH;
		layout.setPadding(COMPONENT_PADDING);
		layout.setSpacing(COMPONENT_GAP_V);
		layout.setAlignment(Pos.CENTER);
		titleComponent();
        enableDrag();
		headerComponent();
        taskPane = taskComponent();
		inputBox = inputComponent();
		taskPane.setContent(tasks);
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