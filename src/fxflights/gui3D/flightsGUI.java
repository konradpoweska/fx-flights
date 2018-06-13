package fxflights.gui3D;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.*;
import javafx.scene.*;
import java.io.*;


public class flightsGUI extends Application {

	public flightsGUI() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		try{
			Parent content = FXMLLoader.load(getClass().getResource("gui2D.fxml"));
			primaryStage.setTitle("Flight Live 3D");
			primaryStage.setScene(new Scene(content));
			primaryStage.show();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
