package fxflights.parsers;

import fxflights.gui3D.Earth3D;
import fxflights.model.Flight;
import javafx.scene.paint.Color;

import java.util.List;

public interface FlightsListener {
        void onFlightsUpdate(List<Flight> flights);
}
