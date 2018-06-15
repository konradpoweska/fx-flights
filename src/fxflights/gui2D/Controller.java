package fxflights.gui2D;

import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;

public class Controller {

	@FXML SubScene earth3D;
	@FXML ChoiceBox fromChoiceBox;
	@FXML ChoiceBox toChoiceBox;
	@FXML ListView flightsList;
	@FXML Button searchButton;

    public Controller() {}

    @FXML
    public void initialize() {

       

    }
}
