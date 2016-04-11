//@@author A0130717M
package gui;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class FadeAnimation {
	private FadeTransition fadeTransition;
	public FadeAnimation(Label comm) {
		fadeTransition = new FadeTransition(Duration.millis(1000), comm);
		fadeTransition.setFromValue(0);
		fadeTransition.setToValue(1);
		fadeTransition.setCycleCount(2);
		fadeTransition.setAutoReverse(true);
	}

	public FadeAnimation(GridPane pane) {
		fadeTransition = new FadeTransition(Duration.millis(500), pane);
		fadeTransition.setFromValue(0);
		fadeTransition.setToValue(1);
	}
	
	public void playAnimation(){
		fadeTransition.play();
	}

}
