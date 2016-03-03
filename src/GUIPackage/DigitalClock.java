package GUIPackage;
import javafx.animation.*;
import javafx.event.*;
import javafx.scene.control.Label;
import javafx.util.Duration;
import java.util.Calendar;

public class DigitalClock extends Label {
  public DigitalClock() {
    bindToTime();
  }

  // the digital clock updates once a second.
  private void bindToTime() {
    Timeline timeline = new Timeline(
      new KeyFrame(Duration.minutes(0),
        new EventHandler<ActionEvent>() {
          @Override public void handle(ActionEvent actionEvent) {
            Calendar time = Calendar.getInstance();
            String hourString = StringUtilities.pad(2, ' ', time.get(Calendar.HOUR_OF_DAY) + "");
            String minuteString = StringUtilities.pad(2, '0', time.get(Calendar.MINUTE) + "");
            setId("timeDisplay");
            setText(hourString + ":" + minuteString);
          }
        }
      ),
      new KeyFrame(Duration.minutes(1))
    );
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
  }
}

class StringUtilities {
  public static String pad(int fieldWidth, char padChar, String s) {
    StringBuilder sb = new StringBuilder();
    for (int i = s.length(); i < fieldWidth; i++) {
      sb.append(padChar);
    }
    sb.append(s);

    return sb.toString();
  }
}