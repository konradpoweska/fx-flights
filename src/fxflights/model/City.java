package fxflights.model;

import java.util.HashMap;

public class City implements Place {
	Country country;
	String name;
	HashMap<String, Airport> airports;
	
	public City(Country country, String name, HashMap<String, Airport> airports) {
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

	public void displayCity() {
		System.out.println(name + ", " + country + ".");
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
	
	
	
}