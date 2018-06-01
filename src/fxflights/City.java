package fxflights;

import java.util.HashMap;

public class City {
	Country country;
	String name;
	HashMap<String, Airport> airports;
	
	public City(Country country, String name, HashMap<String, Airport> airports) {
		super();
		this.country = country;
		this.name = name;
		this.airports = airports;
	}

	/**
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the airports
	 */
	public HashMap<String, Airport> getAirports() {
		return airports;
	}
	
	
	
}