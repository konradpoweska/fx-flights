package fxflights.parsers;

import fxflights.model.Flight;
import java.util.List;

public interface FlightsListener {
        void onFlightsUpdate(List<Flight> flights);
}
