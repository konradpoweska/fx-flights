package fxflights;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import parsers.*;

public class FlightLive {
	HashMap<String, Country> countries;
	HashMap<String, City> cities;
	HashMap<String, Airport> airports;
	ArrayList<Flight> flights;
	
	public FlightLive() {
		
		this.countries = new HashMap<String, Country>();
		this.cities = new HashMap<String, City>();
		this.airports = new HashMap<String, Airport>();
		parserCSV.parseAirportCSV(new File("airports.csv"), this.countries, this.cities, this.airports );
		this.flights = new ArrayList<Flight>();
		
		
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
	public ArrayList<Flight> getFlights() {
		return flights;
	}
	
	
	
}