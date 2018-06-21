package fxflights.model;
import fxflights.parsers.FlightsListener;
import fxflights.gui3D.Aircraft;
import fxflights.gui3D.Earth3D;
import fxflights.parsers.FlightRetriever;
import fxflights.parsers.parserCSV;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class FlightLive implements FlightsListener {
	HashMap<String, Country> countries;
	HashMap<String, City> cities;
	HashMap<String, Airport> airports;
	List<Flight> flights;
	public final FlightRetriever flightRetriever;
	
	public FlightLive() {

		this.countries = new HashMap<String, Country>();
		this.cities = new HashMap<String, City>();
		this.airports = new HashMap<String, Airport>(); //keys = icao of airports
		parserCSV.parseAirportCSV(new File("airports.csv"), this.countries, this.cities, this.airports );
		flightRetriever = new FlightRetriever(airports);
		flightRetriever.addFlightListener(this);
	}

	/**
	 * @return the countries
	 */
	public HashMap<String, Country> getCountries() {
		return countries;
	}

	/**
	 * @return the cities
	 */
	public HashMap<String, City> getCities() {
		return cities;
	}

	/**
	 * @return the airports
	 */
	public HashMap<String, Airport> getAirports() {
		return airports;
	}

	/**
	 * @return the flights
	 */
	public List<Flight> getFlights() {
		return flights;
	}
	
	/**
	 * Give the list of cities for a given country name
	 * @param countryName
	 * @return
	 */
	public Collection<City> getCitiesOfCountry(String countryName) {
		Country theCountry = getCountries().get(countryName);
		HashMap<String, City> cityList = theCountry.getCities();
		return cityList.values();
	}
	
	/**
	 * Give the list of airports for a given city name
	 * @param cityName
	 * @return
	 */
	public Collection<Airport> getAirportsOfCity(String cityName) {
		City theCity = getCities().get(cityName);
		HashMap<String, Airport> airportList = theCity.getAirports();
		return airportList.values();
	}
	
	public void displayFlights(Collection<Flight> listOfFlight) {
		for (Flight flight : listOfFlight) {
			flight.displayFlight();
		}
	}
	
//	public void displayCitiesOfCountry(String countryName) {
//		
//		Country theCountry = getCountries().get(countryName);
//		HashMap<String, City> cityList = theCountry.getCities();
//		for(City city : cityList.values()) {
//			city.displayCity();
//		}
//	}



	public void onFlightsUpdate(List<Flight> flights, Earth3D root) {
		this.flights = flights;
		System.out.println("Nombre de vols : " + flights.size());
		displayFlights(flights); // To display it in console
		root.displayFlightList(flights);

//		for (Flight flight : flights) {
//			Aircraft testAircraft = new Aircraft(Color.GREEN);
//			testAircraft.displayAircraft(flight, earth3D.getRoot3D());
//		}
		
	}
}