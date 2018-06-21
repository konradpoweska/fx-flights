package fxflights.parsers;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fxflights.model.Airport;
import fxflights.model.City;
import fxflights.model.Country;
import fxflights.model.Flight;
import javafx.application.Platform;
import org.asynchttpclient.*;

import java.io.File;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

// INTERMEDIATE CLASSES for json deserializing


class FlightList {
	public ParsedFlight[] acList; //List of planes
	public String lastDv; //Timestamp for further query
}


class ParsedFlight {

	public String Icao;
	public String From;
	public String To;
	public float Lat;
	public float Long;
	public boolean Gnd;
	public float Spd;
	public float Alt;
	public String Op;
	public String Mdl;
	public String Type;
	public float Ang;


	public Flight toFlight(HashMap<String, Airport> airports) {

	    Airport from = airports.get(this.From.split(" ")[0]);
		Airport to   = airports.get(this.To  .split(" ")[0]);

		// if(from==null || to==null) throw new AirportNotRecognized();

		return new Flight(Icao, Op, from, to, Lat, Long, Gnd, Mdl, Ang);
	}
}







// PARSER CLASS

public class FlightRetriever {
	AsyncHttpClient client;
	HashMap<String, Airport> airports;
	FlightList parsingResults;
	List<Flight> flights;
	Object from, to;
	ArrayList<FlightsListener> flightsListeners;
	
	
	public FlightRetriever(HashMap<String, Airport> airports) {
		this.airports = airports;
		this.flightsListeners = new ArrayList<>();

		DefaultAsyncHttpClientConfig.Builder clientBuilder = Dsl.config()
				  .setConnectTimeout(500)
				  .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36")
				  .setKeepAlive(false);
		client = Dsl.asyncHttpClient(clientBuilder);
		Logger.getLogger("io.netty").setLevel(Level.WARNING);
	}

	public boolean addFlightListener(FlightsListener fl) {
		return flightsListeners.add(fl);
	}
	public boolean removeFlightListener(FlightsListener fl) {
		return flightsListeners.remove(fl);
	}
	
	public void setFromTo(Object from, Object to) {
		this.from = from;
		this.to = to;
	}




	String generateURL() {
		StringBuilder result = new StringBuilder("https://public-api.adsbexchange.com/VirtualRadar/AircraftList.json");
		result.append("?fOpQ=Air%20France");
		return result.toString();
	}
	
	
	
	
	void fetchJSON(String URL, AsyncCompletionHandler<Object> completionHandler) {
		BoundRequestBuilder getRequest = client.prepareGet(URL);
		getRequest.execute(completionHandler);
	}
	
	
	
	
	public void fetchFlights() {
		fetchJSON(generateURL(), new AsyncCompletionHandler<Object>() {
			@Override
			public Object onCompleted(Response response) throws Exception {
				String json = response.getResponseBody();
				ObjectMapper mapper = new ObjectMapper();
				mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
				parsingResults = mapper.readValue(json, FlightList.class);
				updateFlights();
				return parsingResults;
			}
		});
	}
	

	
	void updateFlights() {

        flights = Arrays.asList(parsingResults.acList).stream()
                .filter(pf->(pf.From != null && pf.To != null))
                .map(e->e.toFlight(airports))
                .collect(Collectors.toList());

		for(FlightsListener listener : flightsListeners) {
            Platform.runLater(()->{listener.onFlightsUpdate(flights);});
		}
	}

}
