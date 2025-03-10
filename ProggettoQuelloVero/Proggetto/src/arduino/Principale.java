package arduino;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import arduino.eventi.*;
import arduino.grafica.*;


public class Principale {


	private static final Logger LOG = LogManager.getLogger(Arduino.class);
	
	private Server server;
	private Thread thread;
	
	public static void main(String[] args) {
		launch(args);
    }
	
	@Override
    public void start(Stage primaryStage) {
        
		try {
			
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main-scne.fxml"));
	        VBox root = loader.load();
	        
	        server = new Server(loader.getController());
	        thread = new Thread(server);
	                
	        Scene scene = new Scene(root, 900, 700);

	        primaryStage.setTitle("Sensor Simulator");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        
	        LOG.info("Start application");
	        
	        thread.setDaemon(true);
	        thread.start();
		} catch (Exception e) {
			
			LOG.error("", e);
			
		}
    }

	@Override
	public void stop() throws Exception {
		LOG.info("Application stoped");
		if(server != null ) {
			server.stopServer();
			LOG.info("Server stopped");
		}
		
	}
}
