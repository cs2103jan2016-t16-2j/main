//@@author A0130717M
package gui;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Column {
	private StackPane column;
	private Rectangle rectangle;
	private Text text;
	
	public Column(String content, int colNumber, int width) {
		column = new StackPane();
		rectangle = new Rectangle();
		text = new Text(content);
		rectangle.setWidth(width);	
		text.setId("normal");
		column.getChildren().addAll(rectangle, text);
		GridPane.setConstraints(column, colNumber, 0);
	}
	
	public StackPane getColumn(){
		return column;
	}

	public void setWrap(int width){
		text.setWrappingWidth(width);
	}
	
	public void setAlignRight(){
		column.setAlignment(Pos.TOP_RIGHT);
	}
	
	public void setAlignLeft(){
		column.setAlignment(Pos.TOP_LEFT);
	}
	
	public void setZoom(){
		text.setId("zoom");
	}
	
	public void setOverdue(){
		text.setId("overdue");
	}
	
	public void setZoomOverdue(){
		text.setId("zoomOverdue");
	}

}
