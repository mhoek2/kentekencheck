/*
 * This file is part of Kentekencheck.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

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

/**
 * Abstract base class for API implementations that fetches vehicle data.
 * <p>
 * Subclasses must provide API-specific details such as the endpoint URL,
 * parameters, JSON parsing, and date formatting.
 * </p>
 */
public abstract class APIClass {

    /**
     * Returns the base URL of the API. (e.g., https://opendata.rdw.nl/resource/m9d7-ebf2.json)
     *
     * @return the API base URL as a {@link String}
     */
    public abstract String getApiUrl();

    /**
     * Returns the query parameters used by the API. (e.g., ?$where=kenteken=)
     *
     * @return a {@link String} containing the API query parameters
     */
    public abstract String getApiParams();

    /**
     * Returns the date format pattern used in the API's date fields.
     *
     * @return a {@link String} representing the date format pattern (e.g., "yyyyMMdd")
     */
    public abstract String getDatePattern();

    /**
     * Builds a full URI to request vehicle data from a given data table and license.
     *
     * @param data_table The name of the data table or endpoint
     * @param license    The vehicle license plate number
     * @return a {@link StringBuffer} containing the full URI
     */
    public abstract StringBuffer buildUri(String data_table, String license);

    /**
     * Returns the expected JSON keys from the API response.
     *
     * @return an array of {@link String} keys
     */
    public abstract String[] getJsonKeys();

    /**
     * Parses a raw JSON string into a {@link JsonObject}.
     *
     * @param json the raw JSON string
     * @return a parsed {@link JsonObject}
     */
    public abstract JsonObject parseJSONObject(String json);

    /**
     * Converts a {@link JsonObject} into a {@link VehicleClass} instance.
     *
     * @param object the JSON object containing vehicle data
     * @return a {@link VehicleClass} populated with data from the JSON
     */
    public abstract VehicleClass parseJSONVehicle(JsonObject object);

    /**
     * Fetches and returns a {@link VehicleClass} based on the provided license plate.
     *
     * @param license the license plate number
     * @return a {@link VehicleClass} object containing vehicle data
     */
    public abstract VehicleClass getVehicle(String license);

    /**
     * Parses a date string using the API's date pattern.
     *
     * @param date_str the date string to parse
     * @return a {@link LocalDate} object representing the date
     */ 
	public LocalDate getDate( String date_str ) 
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern( getDatePattern() );
		return LocalDate.parse( date_str, formatter );
	}

    /**
     * Returns the date string in ISO-8601 format (yyyy-MM-dd).
     *
     * @param date_str the original date string
     * @return formatted date as a {@link String}
     */
	public String getDateStr( String date_str )
	{
        return getDate(date_str).toString();
	}

    /**
     * Calculates the number of days remaining until the given APK (vehicle inspection) date.
     *
     * @param date_str the expiration date of the APK
     * @return number of days left until the date, or 0 if the date has passed
     */
	public int getDaysUntilAPK( String date_str )
	{
		LocalDate today = LocalDate.now();
		LocalDate apk_date = getDate( date_str );
		long days_left = ChronoUnit.DAYS.between( today, apk_date );

		return (int) Math.max( 0, days_left );	
	}

    /**
     * Performs an HTTP GET request to the specified URI and returns the response body.
     *
     * @param uri the URL to request
     * @return the response body as a {@link String}
     * @throws IOException          if an I/O error occurs when sending or receiving
     * @throws InterruptedException if the operation is interrupted
     */
	public String getJSON( String uri ) throws IOException, InterruptedException
	{
		HttpRequest request = HttpRequest.newBuilder().GET().uri( URI.create( uri ) ).build();
		HttpClient client = HttpClient.newHttpClient();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	
		return response.body();
	}
}
