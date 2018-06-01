package fxflights;
public class Flight {
	Airport from;
	Airport to;
	
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
	
	
	
}