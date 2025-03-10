package arduino.eventi;
import arduino.eventi.SensorEvent;
import arduino.sensore.Sensor;
import arduino.grafica.*;
import arduino.FakeSensor;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.event.EventTarget;


public class SensorEventTarget  implements EventTarget, Initializable {
	

		private final ArduinoVisual view;
		
		@FXML
		private TableView<FakeSensor> tab;
		@FXML
	    private TableColumn<FakeSensor, String> sensorColumn;

	    @FXML
	    private TableColumn<FakeSensor, Double> valueColumn;
		@FXML
		private Label statusLabel;

		@FXML
		private GridPane grid;

		private final ObservableList<FakeSensor> sensorDataList = FXCollections.observableArrayList();

		
		@FXML
		private void handleClose() {
			Platform.exit();
		}

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			view = new SensorViewMedusa();

			grid.add(view, 0, 0);
			GridPane.setHgrow(view, Priority.ALWAYS);
	        GridPane.setVgrow(view, Priority.ALWAYS);
			statusLabel.setText("pippo");
			// Impostazione delle colonne della TableView
	        sensorColumn.setCellValueFactory(new PropertyValueFactory<>("sensorName"));
	        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

	        tab.setItems(sensorDataList);
		}
		
		public SensorEventTarget(ArduinoVisual view) {
			super();
			this.view = view;
		}

		/**
		 * Metodo che gestisce l'evento e aggiorna i dati all'interno 
		 * degli strumenti digitali.
		 * @param e
		 */
		public void fireEvent(SensorEvent e) {
			Sensor s = e.getSensor();
			view.updateSensorData(s.getSensorName(), s.getValue());
		}

		/* Cos'e' la Event Dispatch Chain?
		 * 
		 * Quando un evento viene generato in JavaFX, 
		 * non viene gestito immediatamente da un solo componente ma viene crea una catena 
		 * di distribuzione degli eventi che permette a più nodi di intercettare, 
		 * modificare o gestire l'evento prima che arrivi al target finale.
		 * Questa catena è utile per:
		 *    - Propagare eventi in una gerarchia di nodi JavaFX.
		 *    - Permettere a più oggetti di gestire lo stesso evento.
		 * Questo metoto Viene chiamato automaticamente dal sistema JavaFX quando un evento 
		 * viene inviato a un EventTarget.
		 * Se una classe implementa EventTarget, deve fornire un'implementazione di questo 
		 * metodo per aggiungere se stessa alla catena degli eventi.
		 */
		@Override
		public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
			
			return tail.prepend(new EventDispatcher() { // Creo una classe anonima per gestire l'evento
				
				@Override
				public Event dispatchEvent(Event event, EventDispatchChain tail) {
					
					if(event instanceof SensorEvent) {
						
						fireEvent((SensorsEvent) event);
						
						return null;
					}
					return tail.dispatchEvent(event);
				}
			});
		}

	}

}
