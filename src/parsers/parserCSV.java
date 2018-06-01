package parsers;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import fxflights.*;

public class parserCSV {

	
	public static void parseAirportCSV(File file, HashMap<String, Country> countries, HashMap<String, City> cities, HashMap<String, Airport> airports) {
		
		scanner.useDelimiter(",");

		String airportName, cityName, countryName, icaoAirport;
		double latitude, longitude;
		for(int i = 1; i<= 6; i++) {
			String element = scanner.next();
			switch(i) {
			case 1:
				airportName = element;
				break;

			case 2:
				cityName = element;
				break;

			case 3:
				countryName = element;
				break;

			case 4:
				icaoAirport = element;
				break;

			case 5:
				latitude = Double.parseDouble(element);
				break;

			case 6:
				longitude = Double.parseDouble(element);
				break;

			default:
				break;


			}
			
		}

	
		// TODO gerer country name
	}
}


