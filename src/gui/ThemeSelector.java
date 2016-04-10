//@@author A0130717M
package gui;

import common.Constant;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class ThemeSelector {

	private GridPane themes;
	private final int HGAP = 60;
	private final int VGAP = 20;
	private final int WIDTH = 160;
	private final int HEIGHT = 90;
	private String[] schemes = {"light", "dark"};
	private final String source = "/resources/%1$s.jpg";
	
	public ThemeSelector(){
		themes = new GridPane();
		themes.setHgap(HGAP);
		themes.setVgap(VGAP);
		addTheme(Constant.STYLE_AUTUMN, 0, 0, 0);
		addTheme(Constant.STYLE_BOKEH, 1, 0, 0);
		addTheme(Constant.STYLE_BRANCH, 2, 0, 1);
		addTheme(Constant.STYLE_CAT, 0, 1, 1);
		addTheme(Constant.STYLE_GREY, 1, 1, 1);
		addTheme(Constant.STYLE_WARM, 2, 1, 1);
	}
	
	public GridPane getTheme(){
		return themes;
	}
	
	private void addTheme(String theme, int col, int row, int scheme){
		StackPane display = new StackPane();
		String imageName = String.format(source, theme);
		ImageView image= new ImageView(imageName);
		image.setFitHeight(HEIGHT);
		image.setFitWidth(WIDTH);
		Label label = new Label(theme);
		label.setId(schemes[scheme]);
		display.getChildren().addAll(image, label);
		GridPane.setConstraints(display, col, row);
		themes.getChildren().addAll(display);
	}
}
