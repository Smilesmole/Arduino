package arduino.eventi;

import arduino.sensore.*;
import javafx.event.Event;
import javafx.event.EventType;

public class SensorEvent implements Sensor {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Attributi utilizzato per savare i dati ricevuti 
	 * e propagarli allinterno dell'applicazione
	 */
	private final Sensor sensor;
	
	public static final EventType<SensorEventTarget> SENSOR_DATA_RECEIVED =
            new EventType<>(Event.ANY, "SENSOR_DATA_RECEIVED");

	public SensorDataEvent(Sensor sensor) {
		super(SENSOR_DATA_RECEIVED);
		this.sensor = sensor;
	}

	public Sensor getSensor() {
		return sensor;
	}

	@Override
	public String toString() {
		return "SensorDataEvent [sensor=" + sensor + "]";
	}

	
	
	
}
