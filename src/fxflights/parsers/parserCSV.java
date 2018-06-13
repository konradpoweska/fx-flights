package fxflights.parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import fxflights.model.Airport;
import fxflights.model.City;
import fxflights.model.Country;

public class parserCSV {

	
	public static void parseAirportCSV(File file, HashMap<String, Country> countries, HashMap<String, City> cities, HashMap<String, Airport> airports) {
		
		Scanner scanner = null;
		try{
			scanner = new Scanner(file);
	
		}catch(FileNotFoundException e) {
			System.err.println(e.toString());
		}
		scanner.useDelimiter(",");
		
		while(scanner.hasNextLine()) {

			String airportName, cityName, countryName, icaoAirport;
			double latitude, longitude;
			countryName = "unknownCountry";
			cityName = "unknownCity";
			airportName = "unknownAirport";
			icaoAirport = "unknownIcao";
			latitude = 0;
			longitude = 0; // lat 0 and long 0 lands in water so it's invalid coordinates

			String line = scanner.nextLine();
		    String[] fields = line.split(",");
		    if (fields.length >= 5) { // At least one address specified.
		    	for(int i = 0; i<= 5; i++) {
					switch(i) {
					case 0:
						airportName = fields[i];
//						System.out.print(airportName);
						break;

					case 1:
						cityName = fields[i];
//						System.out.print(cityName);
						break;

					case 2:
						countryName = fields[i];
//						System.out.println(countryName);
						break;

					case 3:
						icaoAirport = fields[i];
						break;

					case 4:
						latitude = Double.parseDouble(fields[i]);
						break;

					case 5:
						longitude = Double.parseDouble(fields[i]);
						break;

					default:
						break;

					}
		    	}
		    }
		    else
		    {
		    	System.err.println("Invalid record: " + line);
		    }

			//TODO what if there is a field still containing unknown ?
			if(!countries.containsKey(countryName)) {
//				System.out.print(countryName);
//				System.out.println(" est un nouveau pays.");
				Country theNewCountry = new Country(countryName);
				countries.put(countryName, theNewCountry);
				
				if(!cities.containsKey(cityName)) {
					HashMap<String, Airport> airportsOfCity = new HashMap<String, Airport>();
					City theNewCity = new City(theNewCountry, cityName, airportsOfCity);
					Airport theNewAirport = new Airport(theNewCity, airportName, icaoAirport, latitude, longitude);
					theNewCity.getAirports().put(icaoAirport, theNewAirport);
					cities.put(cityName, theNewCity);
					airports.put(icaoAirport, theNewAirport);
					theNewCountry.getCities().put(cityName,theNewCity);
				}
					
			}
			else{
				Country theCountry = countries.get(countryName);
				if(!cities.containsKey(cityName)) {
					HashMap<String, Airport> airportsOfCity = new HashMap<String, Airport>();
					City theNewCity = new City(theCountry, cityName, airportsOfCity);
					Airport theNewAirport = new Airport(theNewCity, airportName, icaoAirport, latitude, longitude);
					theNewCity.getAirports().put(icaoAirport, theNewAirport);
					cities.put(cityName, theNewCity);
					airports.put(icaoAirport, theNewAirport);
					theCountry.getCities().put(cityName,theNewCity);
				}
				else {
					City theCity = cities.get(cityName);
					Airport theNewAirport = new Airport(theCity, airportName, icaoAirport, latitude, longitude);
					theCity.getAirports().put(icaoAirport, theNewAirport);
					airports.put(icaoAirport, theNewAirport);
				}
			
			}

		}
		System.out.println("Bilan du parsingCSV :");
		System.out.println("Nombre de pays : " + countries.size());
		System.out.println("Nombre de villes : " + cities.size());
		System.out.println("Nombre d'aeroports : " + airports.size());
	}
}


