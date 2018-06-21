package fxflights.gui2D;

import fxflights.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;


interface ChoiceCallback {
    void onPlaceChosen(Place selectedPlace);
}


public class AirportSelectPopup {
    Stage primaryStage;
    Stage dialog;
    Scene dialogScene;

    @FXML TreeView<Place> choiceTree;
    @FXML Button okButton;
    @FXML Button cancelButton;

    ChoiceCallback callback;

    public AirportSelectPopup(Stage primaryStage, Collection<Country> countries) {
        this.primaryStage = primaryStage;


        // Scene init

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("airport-select-popup.fxml"));
        fxmlLoader.setController(this);
        Parent parent;
        try {
            parent = fxmlLoader.load();
            }
        catch(IOException exception) {
            exception.printStackTrace();
            return;
        }
        dialogScene = new Scene(parent, 320, 480);

        // buttons
        cancelButton.setOnAction(event->dialog.close());
        okButton.setOnAction(event->{
            callback.onPlaceChosen(choiceTree.getSelectionModel().getSelectedItem().getValue());
            dialog.close();
            dialog = null;
        });

        TreeItem<Place> rootItem = new TreeItem<>();

        countries.stream()
                .sorted(Comparator.comparing(Country::getName))
                .forEach(country->{

                    TreeItem<Place> countryItem = new TreeItem<>(country);

                    country.getCities().values().stream()
                            .sorted(Comparator.comparing(City::getName))
                            .forEach(city->{

                                TreeItem<Place> cityItem = new TreeItem<>(city);
                                city.getAirports().values().forEach(airport->{
                                    cityItem.getChildren().add(new TreeItem<>(airport));
                                });

                                countryItem.getChildren().add(cityItem);
                            });
                    rootItem.getChildren().add(countryItem);

                });

        choiceTree.setRoot(rootItem);
        choiceTree.setShowRoot(false);


    }

    public void show(Place currentValue, ChoiceCallback callback) {
        dialog = new Stage();
        dialog.initOwner(primaryStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(dialogScene);
        dialog.show();
        this.callback = callback;
    }
}

