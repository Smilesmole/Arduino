package arduino.trasferimento;

public interface Segmentazione_tipo {
	
	public static final int TESTA = 0x00;
	
	public static final byte TIMESTAMP_SOPRA = 0x01;
	public static final byte TIMESTAMP_SOTTO = 0x02;

	public static final int DATA_TYPE = 0x03;
	
	public static final int INFORMATION_BYTE1 = 0x04;
	public static final int INFORMATION_BYTE2 = 0x05;
	public static final int INFORMATION_BYTE3 = 0x06;
	public static final int INFORMATION_BYTE4 = 0x07;
	
	public static final int CHECKSUM = 0x08;
	
}

