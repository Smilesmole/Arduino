package arduino.grafica;
import javafx.scene.layout.VBox;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;

public class ArduinoMedusa extends VBox  implements ArduinoVisual {

	private Gauge thermometer;
    private Gauge lcdDisplay;
    
    public SensorViewMedusa() {
    	// Creazione termometro con Medusa
        thermometer = GaugeBuilder.create()
            .skinType(Gauge.SkinType.DASHBOARD)
            .title("Termometro")
            .unit("°C")
            .minValue(-50)
            .maxValue(50)
            .animated(true)
            .build();

        // Creazione display LCD per temperatura numerica
        lcdDisplay = GaugeBuilder.create()
            .skinType(Gauge.SkinType.LCD)
            .title("Temperatura")
            .unit("°C")
            .minValue(-50)
            .maxValue(50)
            .animated(true)
            .build();

        getChildren().addAll(lcdDisplay, thermometer);
        setSpacing(20);
	}
    
    
    public void updateSensorData(String sensorName, double value) {
        lcdDisplay.setValue(value);
        thermometer.setValue(value);
    }
	
}
