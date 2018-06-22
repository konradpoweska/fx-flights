package fxflights.parsers;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fxflights.model.*;
import javafx.application.Platform;
import org.asynchttpclient.*;

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
	public float Trak;


	public Flight toFlight(HashMap<String, Airport> airports) {

	    Airport from = airports.get(this.From.split(" ")[0]);
		Airport to   = airports.get(this.To  .split(" ")[0]);

		// if(from==null || to==null) throw new AirportNotRecognized();

		return new Flight(Icao, Op, from, to, Lat, Long, Gnd, Mdl, Trak);
	}
}







// PARSER CLASS

public class FlightRetriever {
	AsyncHttpClient client;
	HashMap<String, Airport> airports;
	FlightList parsingResults;
	List<Flight> flights;
	Place from, to;
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

	public boolean addFlightsListener(FlightsListener fl) {
		return flightsListeners.add(fl);
	}
	public boolean removeFlightsListener(FlightsListener fl) {
		return flightsListeners.remove(fl);
	}


    public Place getFrom() {
        return from;
    }
    public void setFrom(Place from) {
        this.from = from;
    }
    public Place getTo() {
        return to;
    }
    public void setTo(Place to) {
        this.to = to;
    }

    String generateURL() {
	    StringBuilder result = new StringBuilder("https://public-api.adsbexchange.com/VirtualRadar/AircraftList.json?");
//        result.append("fOpQ=Air%20France");
        String airportICAO = null;
        if(from instanceof Airport) airportICAO = ((Airport)from).getIcaoId();
        else if(to instanceof Airport) airportICAO = ((Airport)to).getIcaoId();

        if(airportICAO!=null) result.append("fAirQ="+airportICAO);

        System.out.println("Request sent: "+result);
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
                .filter(f->(placeMatch(from, f.getFrom()) && placeMatch(to, f.getTo())))
                .collect(Collectors.toList());


        Platform.runLater(()->{
            for(FlightsListener listener : flightsListeners) {
                listener.onFlightsUpdate(flights);
            }
        });
	}

	static boolean placeMatch(Place p, Airport a) {
	    if(p==null || a==null) return false;
        if(p instanceof Airport) return a.equals(p);
        if(p instanceof City) return a.getCity().equals(p);
        if(p instanceof Country) return a.getCity().getCountry().equals(p);
	    return false;
    }

}
