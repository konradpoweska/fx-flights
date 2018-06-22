package fxflights.gui2D;

import fxflights.gui3D.Earth3D;
import fxflights.model.Flight;
import fxflights.model.FlightLive;
import fxflights.parsers.FlightsListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
    	dataBase.flightRetriever.addFlightsListener(this);
		AirportSelectPopup airportSelectPopup = new AirportSelectPopup(primaryStage, dataBase.getCountries().values());
    	
    	flightsList.setOnMouseClicked(event -> {
			Flight selectedFlight = flightsList.getSelectionModel().getSelectedItem();


			   ObservableList<String> content = FXCollections.observableArrayList();
			   selectedFlight.fillInfoList(content);
			   infoList.setItems(content);


	   });


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


		earth3D.resetPlanesGroup();
		earth3D.resetAirportsGroup();
		earth3D.displayFlightList(flights);

		ObservableList<Flight> content = FXCollections.observableArrayList();
		for (Flight flight : flights) {
			content.add(flight);
		}
		flightsList.setItems(content);


		
		// TODO : afficher dans la flightsList
	}



}
