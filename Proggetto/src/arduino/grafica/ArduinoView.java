package arduino.grafica;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ArduinoView extends VBox implements ArduinoVisual {

private Label label;
	
	public ArduinoView() {
		label = new Label("Dati sensore: ");
        getChildren().add(label);
    }
	
	public void updateSensorData(String sensorName, double value) {
        label.setText(sensorName + ": " + value + "°C");
    }	
	
}
