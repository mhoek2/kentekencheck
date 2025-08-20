package kentekencheck;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public abstract class APIClass {
    public abstract String getApiUrl();
    public abstract String getApiParams();
    public abstract String getDatePattern();
	
	public LocalDate getDate( String date_str ) 
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern( getDatePattern() );
		return LocalDate.parse( date_str, formatter );
	}	
	
	public String getDateStr( String date_str )
	{
        return getDate(date_str).toString();
	}
	
	public int getDaysUntilAPK( String date_str )
	{
		LocalDate today = LocalDate.now();
		LocalDate apk_date = getDate( date_str );
		long days_left = ChronoUnit.DAYS.between( today, apk_date );

		return (int) Math.max( 0, days_left );	
	}
	
	public String getJSON( String uri ) throws IOException, InterruptedException
	{
		HttpRequest request = HttpRequest.newBuilder().GET().uri( URI.create( uri ) ).build();
		HttpClient client = HttpClient.newHttpClient();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	
		return response.body();
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
	
	public abstract StringBuffer buildUri( String data_table, String license );
	public abstract JsonObject parseJSONObject( String json );
	public abstract VehicleClass parseJSONVehicle( JsonObject object );
	public abstract VehicleClass getVehicle( String license );
}
