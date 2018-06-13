package fxflights.parsers;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.Response;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import fxflights.model.Airport;
import fxflights.model.Flight;

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
	
	public Flight toFlight(HashMap<String, Airport> airports) {
//		Airport from = airports.get(this.From.split(" ")[0]);
//		Airport to = airports.get(this.To.split(" ")[0]);
		return new Flight(Icao, Op, null, null, Lat, Long, Gnd, Mdl);
	}
}







// PARSER CLASS

public class FlightRetriever {
	AsyncHttpClient client;
	HashMap<String, Airport> airports;
	FlightList parsingResults;
	Map<String,Flight> flights;
	
	
	
	public FlightRetriever(HashMap<String, Airport> airports) {
		this.airports = airports;
		
		DefaultAsyncHttpClientConfig.Builder clientBuilder = Dsl.config()
				  .setConnectTimeout(500)
				  .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36")
				  .setKeepAlive(false);
		client = Dsl.asyncHttpClient(clientBuilder);
		Logger.getLogger("io.netty").setLevel(Level.WARNING);
	}
	
	
	
	
	String generateURL() {
		return "https://public-api.adsbexchange.com/VirtualRadar/AircraftList.json?fOpQ=Air%20France";
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
		    	return flights;
		    }
		});
	}
	

	
	void updateFlights() {
		flights = Arrays.asList(parsingResults.acList).stream()
			.map(e->e.toFlight(airports))
			.collect(Collectors.toMap(Flight::getIcao, x->x));
		Iterator<String> i = flights.keySet().iterator();
		flights.get(i.next()).displayFlight();
	}
}
