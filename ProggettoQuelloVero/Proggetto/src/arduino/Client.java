package arduino;

import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

import arduino.trasferimento.*;

public class Client {
	private static final String SERVER_IP = "127.0.0.1"; // IP locale
	private static final int SERVER_PORT = 5000; // Porta del server

	public static void main(String[] args) {
		try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
				OutputStream out = socket.getOutputStream()) {

			System.out.println("Connesso al server: " + SERVER_IP + ":" + SERVER_PORT);
			Random random = new Random();

			while (true) {
				byte[] pacchetto = generaPacchetto(random);
				out.write(pacchetto);
				out.flush();
				System.out.println("Pacchetto inviato: " + toHex(pacchetto));
				Thread.sleep(5000); // Invia un pacchetto ogni secondo
			}
		} catch (Exception e) {
			System.err.println("Errore nella comunicazione con il server: " + e.getMessage());
		}
	}

	private static byte[] generaPacchetto(Random random) {
		byte[] pacchetto = new byte[9];

		
		pacchetto[Segmentazione_tipo.DATA_TYPE] = (byte) TipoDato.TEMPERATURA.getCodice();

	
		int timestamp = random.nextInt(65536);
		pacchetto[Segmentazione_tipo.TIMESTAMP_SOPRA] = (byte) ((timestamp >> 8) & 0xFF);
		pacchetto[Segmentazione_tipo.TIMESTAMP_SOTTO] = (byte) (timestamp & 0xFF);

		// Valore misurato (-500 a 500)
		short valore = (short) (random.nextInt(100) - 50);
		System.out.format("Valore generato %s\n", valore);
		
		pacchetto[Segmentazione_tipo.INFORMATION_BYTE1] = (byte) ((valore >> 24) & 0xFF);
		pacchetto[Segmentazione_tipo.INFORMATION_BYTE2] = (byte) ((valore >> 16) & 0xFF);
		pacchetto[Segmentazione_tipo.INFORMATION_BYTE3] = (byte) ((valore >> 8) & 0xFF);
		pacchetto[Segmentazione_tipo.INFORMATION_BYTE4] = (byte) (valore & 0xFF);

		// Checksum (XOR di tutti i byte precedenti)
		pacchetto[Segmentazione_tipo.CHECKSUM] = calcolaChecksum(pacchetto);

		return pacchetto;
	}

	private static byte calcolaChecksum(byte[] pacchetto) {
		byte checksum = 0;
		for (int i = 0; i < 5; i++) { // XOR di byte 0-4
			checksum ^= pacchetto[i];
		}
		return checksum;
	}

	private static String toHex(byte[] data) {
		StringBuilder sb = new StringBuilder();
		for (byte b : data) {
			sb.append(String.format("%02X ", b));
		}
		return sb.toString().trim();
	}

}
