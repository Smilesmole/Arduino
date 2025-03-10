package arduino;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.javafx.event.EventDispatchChainImpl;

import arduino.sensore.*;
import arduino.eventi.*;
import arduino.grafica.*;
import arduino.trasferimento.*;
import javafx.application.Platform;
import javafx.event.EventTarget;
/**
 * La classe definisce un codice che puo' essere seguito all'interno di un thread.
 * 
 * Viene lanciato in esecuzione il matodo run.
 */
public class Server implements Runnable {

	private static final Logger LOG = LogManager.getLogger(Server.class);

	private int port;
	private boolean running;
	private ServerSocket serverSocket;
	private EventTarget eventTarget;

	public Server(EventTarget eventTarget) {
		super();
		init(5000, eventTarget);
	}

	public Server(int port, EventTarget et) {
		super();
		init(port, et);
		LOG.debug("Initialization Server complete");
	}
	
	protected void init(int port, EventTarget et) {
		this.port = port;
		this.eventTarget = et;
		this.running = true;

	}

	@Override
	public void run() {

		try {
			serverSocket = new ServerSocket(port);

			LOG.info("Sterting Server ...");
			while (running) {

				Socket clientSocket = serverSocket.accept();

				InputStream in = clientSocket.getInputStream();

				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

				byte[] data = new byte[Decodifica.DIMENSIONE_PACCHETTO];
				
				Decodifica dd = new Decodifica();
				
				while ((in.read(data)!= -1) && running) {
					try {
						
						dd.decodifica(data);
						
						Sensor sensor = new Arduino(dd.getTipoDato().getDescrizione(), 
														dd.getValore(), dd.getTimestamp());
						
						LOG.debug(String.format("Receiving data (%s)", sensor));

						SensorEvent event = new SensorEvent(sensor);
						
						Platform.runLater(() -> {
							eventTarget.buildEventDispatchChain(
									new EventDispatchChainImpl()
									).dispatchEvent(event);
						});

						LOG.debug("Evento generto");
						
					} catch (Exception e) {
						LOG.error(e);
					}
				}
				
				// Thread.sleep(5000); // Pausa di 5 sec per generare il nuovo dato

			}

		} catch (Exception e) {
			if (running) {
				LOG.error(e);
			}
		} finally {
			stopServer();
		}

	}

	/**
	 * Metodo utilizzato per chiudere il server.
	 */
	public void stopServer() {
		running = false;
		try {
			if (serverSocket != null) {
				serverSocket.close();
			}
		} catch (Exception e) {
			LOG.error(e);
		}

	}
}

