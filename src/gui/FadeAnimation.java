//@@author A0130717M
package gui;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class FadeAnimation {
	FadeTransition ft;
	public FadeAnimation(Label comm) {
		ft = new FadeTransition(Duration.millis(1000), comm);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.setCycleCount(2);
		ft.setAutoReverse(true);
	}

	public FadeAnimation(GridPane pane) {
		ft = new FadeTransition(Duration.millis(500), pane);
		ft.setFromValue(0);
		ft.setToValue(1);
	}
	
	public void playAnimation(){
		ft.play();
	}

}
