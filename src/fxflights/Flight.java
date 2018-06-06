package fxflights;

import java.util.Date;

public class Flight {
	Airport from;
	Airport to;
	double latitude, longitude;
	boolean flying;
	String icaoFlight, aircraftMdl;
	Date departure, arrival;
	
	
	public Flight(Airport from, Airport to) {
		this.from = from;
		this.to = to;
	}

	/**
	 * @return the from
	 */
	public Airport getFrom() {
		return from;
	}

	/**
	 * @return the to
	 */
	public Airport getTo() {
		return to;
	}
	
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @return the flying
	 */
	public boolean isFlying() {
		return flying;
	}

	/**
	 * @return the icaoFlight
	 */
	public String getIcaoFlight() {
		return icaoFlight;
	}

	/**
	 * @return the aircraftMdl
	 */
	public String getAircraftMdl() {
		return aircraftMdl;
	}

	/**
	 * @return the departure
	 */
	public Date getDeparture() {
		return departure;
	}

	/**
	 * @return the arrival
	 */
	public Date getArrival() {
		return arrival;
	}
	
	/**
	 * Method that display in console useful informations about a flight
	 */
	public void displayFlight() {
		String flightInfo = icaoFlight + ", " + aircraftMdl + " from: " + from + " to: " + to + ". Currently at lat: " + latitude + ", long:" + longitude + ".";
		System.out.println(flightInfo);
	}
	
	
}