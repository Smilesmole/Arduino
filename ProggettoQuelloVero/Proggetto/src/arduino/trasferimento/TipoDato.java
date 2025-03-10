package arduino.trasferimento;

public enum TipoDato {
	
	TEMPERATURA(0x01, "Tempreratura"),
	NULLA(0x00, "Nulla");
	
	private final int codice;
	private final String descrizione;
	
	
	TipoDato(int codice, String descrizione){
		
		this.codice = codice;
		this.descrizione = descrizione;
		
	}


	public int getCodice() {
		return codice;
	}


	public String getDescrizione() {
		return descrizione;
	}
	
	public static  TipoDato tipologia (byte valore) {
		
		for(TipoDato tipo : TipoDato.values()) {
			
			if(tipo.codice == (valore & 0xFF))
			{
				
			return tipo;}
		}
		
		return NULLA;
	}
}
