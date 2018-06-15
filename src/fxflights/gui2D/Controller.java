package fxflights.gui2D;

import java.net.URL;
import java.util.ResourceBundle;

import fxflights.gui3D.Earth3D;
import fxflights.model.Airport;
import fxflights.model.FlightLive;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;


public class Controller implements Initializable {

	@FXML Pane pane3D;
	@FXML ChoiceBox fromChoiceBox;
	@FXML ChoiceBox toChoiceBox;
	@FXML ListView flightsList;
	@FXML Button searchButton;

    public Controller() {}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	Earth3D earth3D = new Earth3D(pane3D);
    	FlightLive dataBase = new FlightLive();
    	
    	//Display every airports
    	for (Airport airport : dataBase.getAirports().values()) {
    		earth3D.displayAirport(airport.getName(), airport.getLatitude(), airport.getLongitude(), false );
    	}
    	
       

    }
}
