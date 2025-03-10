package arduino.sensore;

public interface Sensor {

	public String getSensorName();
	public double getValue();
	public long getTimestamp();
	
}
