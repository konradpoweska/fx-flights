package fxflights.model;

import java.util.ArrayList;

public class Airport {
	City city;
	String name, icaoId;
	Double latitude, longitude;
	ArrayList<Flight> departures;
	ArrayList<Flight> arrivals;
	
	public Airport(City city, String name, String icaoId, Double latitude, Double longitude) {
	
		this.city = city;
		this.name = name;
		this.icaoId = icaoId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.departures = new ArrayList<Flight>();
		this.arrivals = new ArrayList<Flight>();
		
	}

	/**
	 * @return the city
	 */
	public City getCity() {
		return city;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the icaoId
	 */
	public String getIcaoId() {
		return icaoId;
	}

	/**
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * @return the departures
	 */
	public ArrayList<Flight> getDepartures() {
		return departures;
	}

	/**
	 * @return the arrivals
	 */
	public ArrayList<Flight> getArrivals() {
		return arrivals;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Airport [city=" + city + ", name=" + name + ", icaoId=" + icaoId + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", departures=" + departures + ", arrivals=" + arrivals + "]";
	}
	
	
	
}