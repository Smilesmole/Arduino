package arduino.trasferimento;




public class Decodifica implements Segmentazione_tipo {
	
	private int timestamp;
	private double temperatura;
	private TipoDato tipo;
	private int checksum;
	
	
	public static final int DIMENSIONE_PACCHETTO = 9;
	
	public void decodifica (byte [] dati)
	{
		if(dati.length != DIMENSIONE_PACCHETTO)
		{
			System.out.println("Numero di byte diverso da" + DIMENSIONE_PACCHETTO + "riprovare.");
			
		}
		
		this.timestamp = (dati[Segmentazione_tipo.TIMESTAMP_SOPRA] & 0xFF) | (dati[Segmentazione_tipo.TIMESTAMP_SOTTO] & 0xFF << 8);
	
		
		int temperaturaint = (dati[Segmentazione_tipo.INFORMATION_BYTE1] & 0xFF) | (dati[Segmentazione_tipo.INFORMATION_BYTE2] & 0xFF << 8) |
							(dati[Segmentazione_tipo.INFORMATION_BYTE3] & 0xFF << 16) | (dati[Segmentazione_tipo.INFORMATION_BYTE4] & 0xFF << 24); 
		
		temperatura = Double.valueOf(temperaturaint);
		
		this.tipo = TipoDato.tipologia(dati[Segmentazione_tipo.DATA_TYPE]);
		
		this.checksum = (dati[Segmentazione_tipo.CHECKSUM] & 0xFF );	
		
	}
	
	
	
	
	
	
	
public int getTimestamp() {
		return timestamp;
	}
	
public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

public double getTemperatura() {
		return temperatura;
	}
	public void setTemperatura(double temperatura) {
		this.temperatura = temperatura;
	}
	public TipoDato getTipo() {
		return tipo;
	}
	public void setTipo(TipoDato tipo) {
		this.tipo = tipo;
	}
	public int getChecksum() {
		return checksum;
	}
	public void setChecksum(int checksum) {
		this.checksum = checksum;
	}
	
	

}
