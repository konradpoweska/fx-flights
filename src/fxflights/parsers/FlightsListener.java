package fxflights.parsers;

import fxflights.model.Flight;

import java.util.ArrayList;

public interface FlightsListener {
        void onFlightsUpdate(ArrayList<Flight> flights);
}
