//@@author A0130717M
package gui;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class ThemeSelector {

	private GridPane themes;
	private final int GAP = 30;
	private String[] schemes = {"light", "dark"};
	
	public ThemeSelector(){
		themes = new GridPane();
		themes.setHgap(GAP);
		themes.setVgap(GAP);
		
		addTheme("Autumn", 0, 0, 0);
		addTheme("Bokeh", 1, 0, 0);
		addTheme("Branch", 2, 0, 1);
		addTheme("Cat", 0, 1, 1);
		addTheme("Grey", 1, 1, 1);
		addTheme("Warm", 2, 1, 1);
	}
	
	public GridPane getTheme(){
		return themes;
	}
	
	private void addTheme(String theme, int col, int row, int scheme){
		StackPane display = new StackPane();
		ImageView image= new ImageView("/resources/" + theme +".jpg");
		image.setFitHeight(90);
		image.setFitWidth(160);
		Label label = new Label(theme);
		label.setId(schemes[scheme]);
		display.getChildren().addAll(image, label);
		GridPane.setConstraints(display, col, row);
		themes.getChildren().addAll(display);
	}
}
