package fxflights.model;

import javafx.collections.ObservableList;

public class Flight {
	Airport from, to;
	double latitude, longitude, angle;
	boolean flying;
	String icaoFlight, aircraftMdl, company;
	
	

	/**
	 * @param icaoFlight
	 * @param from
	 * @param to
	 * @param latitude
	 * @param longitude
	 * @param flying
	 * @param aircraftMdl
	 * @param angle
	 */
	public Flight(String icaoFlight, String company, Airport from, Airport to, double latitude, double longitude, boolean flying,
			String aircraftMdl, double angle) {
		this.icaoFlight = icaoFlight;
		this.company = company;
		this.from = from;
		this.to = to;
		this.latitude = latitude;
		this.longitude = longitude;
		this.flying = flying;
		this.aircraftMdl = aircraftMdl;
		this.angle = angle;
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
	public String getIcao() {
		return icaoFlight;
	}

	/**
	 * @return the aircraftMdl
	 */
	public String getAircraftMdl() {
		return aircraftMdl;
	}

	/**
	 * @return the arrival
	 */
	public String getCompany() {
		return company;
	}
	
	/**
	 * @return the angle
	 */
	public Double getAngle() {
		return angle;
	}
	
	/**
	 * Method that display in console useful informations about a flight
	 */
	public void displayFlight() {
		System.out.println(toString());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return icaoFlight + ", " + company + " from: " + from + " to: " + to + " .";

	}
	
	public void fillInfoList(ObservableList<String> content) {
		content.add("Icao : " + icaoFlight);
		content.add("Company : " + company);
		content.add("Model : " + aircraftMdl);
		content.add("From : " + from);
		content.add("To : " + to);
		content.add("Latitude : " + latitude);
		content.add("Longitude : " + longitude);
		
	}
	
	
}