package fxflights;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
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
		this.airports = new HashMap<String, Airport>(); //keys = icao of airports
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
	
	/**
	 * Core of the application
	 * @param args
	 */
	public static void main(String [] args)
	{
		FlightLive dataBase = new FlightLive();
		
	}
	
}