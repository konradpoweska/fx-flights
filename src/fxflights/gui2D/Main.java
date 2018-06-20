package fxflights.gui2D;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
        Parent root = loader.load();

        Controller controller = loader.getController();
        controller.bindPrimaryStage(primaryStage);

        primaryStage.setTitle("Flight Live");
        primaryStage.setScene(new Scene(root, 860, 600));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
