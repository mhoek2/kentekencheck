package kentekencheck;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


import com.google.gson.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Vehicle {
    String kenteken;
    String merk;
    String vervaldatum;

    Vehicle( String kenteken, String merk, String vervaldatum ) {
        this.kenteken = kenteken;
        this.merk = merk;
        this.vervaldatum = vervaldatum;
    }
}

public class Api {
	private String API_URL = "https://opendata.rdw.nl/resource/m9d7-ebf2.json";

	public static String getJSON( String uri ) throws IOException, InterruptedException
	{
		HttpRequest request = HttpRequest.newBuilder().GET().uri( URI.create( uri ) ).build();
		HttpClient client = HttpClient.newHttpClient();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	
		return response.body();
	}

	public StringBuffer buildUri( String data_table, String license )
	{
		StringBuffer output = new StringBuffer( data_table );
		output.append("?$where=kenteken='" + license + "'");
		
		return output;
	}
	
	
	public String getJsonValue( JsonObject data, String key )
	{
		try {
			return data.get( key ).getAsString();
		}
		catch ( NullPointerException e ) {
			return new String("");
		}
	}
	
	public Vehicle jsonToObject( String json )
	{
		//Vehicle data = new Gson().fromJson(response, Vehicle.class);	// class needs match format
		// more dynamic approach:
		System.out.println( json );

		JsonArray array = JsonParser.parseString(json).getAsJsonArray();
		if ( array.size() == 0 ) {
			throw new IllegalStateException("empty");
		}

        JsonObject data = array.get(0).getAsJsonObject();
        
        String kenteken 	= this.getJsonValue( data, "kenteken" );
        String merk 		= this.getJsonValue( data, "merk" );
        String vervaldatum 	= this.getJsonValue( data, "vervaldatum_apk" );

        return new Vehicle( 
        		kenteken,
        		merk,
        		vervaldatum
        );  
	}
	
	public LocalDate getDate( String date_str, String pattern )
	{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( pattern );
        return LocalDate.parse( date_str, formatter );
	}
	
	public void getData( String license )
	{
        try {
        	StringBuffer uri = this.buildUri( API_URL, license );
        	String json = Api.getJSON( uri.toString() );

        	try {
        		Vehicle vehicle = this.jsonToObject( json );
        		
        		System.out.println( vehicle.kenteken );
        		System.out.println( vehicle.merk );
        		System.out.println( vehicle.vervaldatum );
        		
        		LocalDate date = this.getDate( vehicle.vervaldatum, "yyyyMMdd" );
        		System.out.print( date.toString() );
        	}
        	catch ( IllegalStateException e) {
        		System.out.println( e.getMessage() );
        	}
        } 
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
	}
}
