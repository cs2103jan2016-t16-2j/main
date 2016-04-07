package gui;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class ThemeSelector {

	private GridPane themes;
	private final int GAP = 30;
	
	public ThemeSelector(){
		themes = new GridPane();
		themes.setHgap(GAP);
		themes.setVgap(GAP);
		
		addTheme("Leather", 0, 0);
		addTheme("Autumn", 1, 0);
		addTheme("Bokeh", 0, 1);
		addTheme("Raindrop", 1, 1);
	}
	
	public GridPane getTheme(){
		return themes;
	}
	
	private void addTheme(String theme, int col, int row){
		StackPane display = new StackPane();
		ImageView image= new ImageView("/gui/" + theme +".jpg");
		image.setFitHeight(90);
		image.setFitWidth(160);
		Label label = new Label(theme);
		display.getChildren().addAll(image, label);
		GridPane.setConstraints(display, col, row);
		themes.getChildren().addAll(display);
	}
}
