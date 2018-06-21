package fxflights.gui2D;

import java.net.URL;
import java.util.Collection;

import java.util.ArrayList;
import java.util.List;

import java.util.ResourceBundle;

import fxflights.gui3D.Aircraft;
import fxflights.gui3D.Earth3D;
import fxflights.model.Airport;
import fxflights.model.Flight;
import fxflights.model.FlightLive;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import fxflights.parsers.FlightsListener;

public class Controller implements Initializable, FlightsListener {
    Stage primaryStage;
	@FXML Pane pane3D;
	@FXML ComboBox<String> fromChoiceBox;
	@FXML ComboBox<String> toChoiceBox;
	@FXML ListView<Flight> flightsList;
	@FXML Button searchButton;
	@FXML ListView<String> infoList;
	FlightLive dataBase;
	Earth3D earth3D;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	earth3D = new Earth3D(pane3D);
		dataBase = new FlightLive();
    	dataBase.flightRetriever.addFlightListener(this);
		AirportSelectPopup airportSelectPopup = new AirportSelectPopup(primaryStage, dataBase.getCountries().values());
    	
    	flightsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			 @Override
	    	public void handle(MouseEvent event) {
				 Flight selectedFlight = flightsList.getSelectionModel().getSelectedItem();
				 
				 
					ObservableList<String> content = FXCollections.observableArrayList();
					selectedFlight.fillInfoList(content);
					infoList.setItems(content);
					
//				 for (Node node : earth3D.getPlanesGroup().getChildren()) {
//					 if(node) 
//				 } // Can't link the flight / aircraft to the plane node 
				 
	    	}
	 });

    	//Display every airports
//    	earth3D.displayAirportList(dataBase.getAirports().values(), Color.YELLOW);
    	
//    	System.out.println("Nombre de vols : " + dataBase.flightRetriever.acList.size());
    	
//    	dataBase.displayFlights(dataBase.getFlights());
    	
//    	Flight testFlight = new Flight("394A0D", "Air France", dataBase.getAirports().get("AYGA"), dataBase.getAirports().get("BIHN"), 148.7532958984375, 0.8450319766998291, true, "Avion de test", 45.0);
//    	Flight testFlightBis = new Flight("364A0D", "Air France", dataBase.getAirports().get("AYGA"), dataBase.getAirports().get("BIHN"), 48.7532958984375, 1.8450319766998291, true, "Avion de testBis", 85.0);
//    	
//    	ArrayList<Flight> testList = new ArrayList<Flight>();; 
//    	testList.add(testFlight);
//    	testList.add(testFlightBis);
//    	earth3D.displayFlightList(testList);
    	
    	
//    	Aircraft testAircraft = new Aircraft(Color.GREEN);
//    	Aircraft testAircraftBis = new Aircraft(Color.RED);
//    	testAircraft.displayAircraft(testFlight, earth3D.getRoot3D());
//    	testAircraftBis.displayAircraft(testFlightBis, earth3D.getRoot3D());


		fromChoiceBox.setOnMouseClicked(event->
			airportSelectPopup.show(dataBase.flightRetriever.getFrom(), place-> {
				fromChoiceBox.setValue(place.toString());
				dataBase.flightRetriever.setFrom(place);
			})
		);

		toChoiceBox.setOnMouseClicked(event->
				airportSelectPopup.show(dataBase.flightRetriever.getTo(), place-> {
					toChoiceBox.setValue(place.toString());
					dataBase.flightRetriever.setTo(place);
				})
		);

    	searchButton.setOnMouseClicked(event->{
    		searchButton.setDisable(true);
    		searchButton.setText("Fetching...");
			dataBase.flightRetriever.fetchFlights();
		});

    }

    void bindPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

	public void onFlightsUpdate(List<Flight> flights) {
		searchButton.setText("Search");
    	searchButton.setDisable(false);
		System.out.println("Nombre de vols : " + flights.size());
		dataBase.displayFlights(flights); // To display it in console


//		System.out.println("DEBUT DISPLAY 3D");
		earth3D.resetPlanesGroup();
		earth3D.displayFlightList(flights);

		ObservableList<Flight> content = FXCollections.observableArrayList();
		for (Flight flight : flights) {
			content.add(flight);
		}
		flightsList.setItems(content);


		
		// TODO : afficher dans la flightsList
	}



}
