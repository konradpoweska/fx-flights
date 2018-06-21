package fxflights.gui2D;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import fxflights.gui3D.Earth3D;
import fxflights.model.Airport;
import fxflights.model.Flight;
import fxflights.model.FlightLive;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import fxflights.parsers.FlightsListener;

public class Controller implements Initializable, FlightsListener {
    Stage primaryStage;
	@FXML Pane pane3D;
	@FXML ChoiceBox fromChoiceBox;
	@FXML ChoiceBox toChoiceBox;
	@FXML ListView flightsList;
	@FXML Button searchButton;
	FlightLive dataBase;
	Earth3D earth3D;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	earth3D = new Earth3D(pane3D);
		dataBase = new FlightLive();
    	dataBase.flightRetriever.addFlightListener(this);

    	//Display every airports
    	for (Airport airport : dataBase.getAirports().values()) {
    		earth3D.displayAirport(airport.getName(), airport.getLatitude(), airport.getLongitude(), false );
    	}



    	searchButton.setOnMouseClicked(event->{
    		searchButton.setDisable(true);
			dataBase.flightRetriever.fetchFlights();
		});
    }

    void bindPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

	public void onFlightsUpdate(List<Flight> flights) {
		searchButton.setDisable(false);
		// TODO : afficher dans la flightsList
	}


}
