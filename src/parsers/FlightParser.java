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

// temporary classes used for json deserializing

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
	   public int Id;
	   public String Icao;
	   public float Spd;
	   public float Trak;
	   public String Type;
}




// parser class

public class FlightParser {
	ArrayList<Flight> flights;
	
	public void convertFlights(HashMap<String,Airport> airports) {
	}
	
	public ArrayList<Flight> getFlights() {
		return flights;
	}
	
	static String generateDBURL() {
		return "https://public-api.adsbexchange.com/VirtualRadar/AircraftList.json?fOpQ=Air%20France";
	}
	
	static void getFlightJSON(String URL, AsyncCompletionHandler<Object> onComplete) {
		// Configurer le client http
		DefaultAsyncHttpClientConfig.Builder clientBuilder = Dsl.config()
						  .setConnectTimeout(500)
						  .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36")
						  .setKeepAlive(false);
		AsyncHttpClient client = Dsl.asyncHttpClient(clientBuilder);

		// Créer
		BoundRequestBuilder getRequest = client.prepareGet(URL);


		// Éxecuter la requête et appeler le callback
		getRequest.execute(onComplete);

	}

	
	
	
	static public void main(String[] args) {
		getFlightJSON(generateDBURL(), new AsyncCompletionHandler<Object>() {
		    @Override
		    public Object onCompleted(Response response) throws Exception {
		    	String json = response.getResponseBody(); 
		    	ObjectMapper mapper = new ObjectMapper();
		    	mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); //Ignorer les champs inutiles
		    	FlightList flights = mapper.readValue(json, FlightList.class); //CrÃ©er l'objet de plus haut niveau dans le dictionnaire json
		    	return json;
		    }
		});
		
	}


}
