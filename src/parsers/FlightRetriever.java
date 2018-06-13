package parsers;
import fxflights.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.Response;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// temporary classes for json deserializing

class FlightList {
	private ParsedFlight[] acList; //List of planes
	private  String lastDv; //Timestamp for further query
	public ParsedFlight[] getAcList() {
		return acList;
	}
	public void setAcList(ParsedFlight[] acList) {
		this.acList = acList;
	}
	public String getLastDv() {
		return lastDv;
	}
	public void setLastDv(String lastDv) {
		this.lastDv = lastDv;
	}
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
		Airport from = airports.get(this.From);
		Airport to = airports.get(this.To);
		return new Flight(Icao, from, to, Lat, Long, Gnd, Mdl);
	}
}




// parser class

public class FlightRetriever {
	FlightList flights;
	AsyncHttpClient client;
	
	FlightRetriever() {
		DefaultAsyncHttpClientConfig.Builder clientBuilder = Dsl.config()
				  .setConnectTimeout(500)
				  .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36")
				  .setKeepAlive(false);
		client = Dsl.asyncHttpClient(clientBuilder);
	}
	
	String generateURL() {
		return "https://public-api.adsbexchange.com/VirtualRadar/AircraftList.json?fOpQ=Air%20France";
	}
	
	
	void getFlightJSON(String URL, AsyncCompletionHandler<Object> onComplete) {
		BoundRequestBuilder getRequest = client.prepareGet(URL);
		getRequest.execute(onComplete);

	}
	
	
	
	static public void main(String[] args) {
		FlightRetriever fr = new FlightRetriever();
		fr.getFlightJSON(fr.generateURL(), new AsyncCompletionHandler<Object>() {
		    @Override
		    public Object onCompleted(Response response) throws Exception {
		    	String json = response.getResponseBody(); 
		    	ObjectMapper mapper = new ObjectMapper();
		    	mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		    	FlightList flights = mapper.readValue(json, FlightList.class);
//		    	System.out.println(flights.getAcList()[0].toFlight(airports).toString());
		    	return json;
		    }
		});
		
	}


}
