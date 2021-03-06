package fxflights.model;

import java.util.HashMap;

public class Country implements Place {
	String name;
	HashMap<String, City> cities;
	
	public Country(String name) {
		this.name = name;
		this.cities = new HashMap<String, City>();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the cities
	 */
	public HashMap<String, City> getCities() {
		return cities;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
	
	
	
}