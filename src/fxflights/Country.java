package fxflights;

import java.util.HashMap;

public class Country {
	String name;
	HashMap<String, City> cities;
	
	public Country(String name, HashMap<String, City> cities) {
		super();
		this.name = name;
		this.cities = cities;
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
	
	
	
}