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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.*;

public final class OverheidApi extends APIClass  {
	//Overheid.io voertuiggegevens	REST	https://api.overheid.io/voertuiggegevens/4TFL24	

	protected final String API_URL 		= "https://api.overheid.io/voertuiggegevens/";
	protected final String API_PARAMS 	= "";
	protected final String DATE_PATTERN = "yyyyMMdd";
	protected final String API_KEY		= "";
	
    @Override
    public String getApiUrl() { return API_URL; }

    @Override
    public String getApiParams() { return API_PARAMS; }

    @Override
    public String getDatePattern() { return DATE_PATTERN; }
    
    @Override
    public String[] getJsonKeys() {
    	return new String[] {};
    }
    
    @Override
	public StringBuffer buildUri( String data_table, String license )
	{
		StringBuffer output = new StringBuffer( data_table );
		output.append( API_PARAMS + license + "?ovio-api-key=" + API_KEY );
		
		return output;
	}
	
	@Override
	public JsonObject parseJSONObject( String json )
	{
		System.out.println( json );

		JsonArray array = JsonParser.parseString(json).getAsJsonArray();
		
		if ( array.size() == 0 )
			throw new IllegalStateException("empty");

        return array.get(0).getAsJsonObject();
	}
	
	@Override
    public VehicleClass parseJSONVehicle( JsonObject object ) 
    {
    	return null;
	}
	
    @Override
	public VehicleClass getVehicle( String license )
	{
        try {
        	StringBuffer uri = this.buildUri( API_URL, license );
        	String json_str = super.getJSON( uri.toString() );

        	try {
        		JsonObject json_object = this.parseJSONObject( json_str );
        		
        		return this.parseJSONVehicle( json_object );
        	}
        	catch ( IllegalStateException e) {
        		System.out.println( e.getMessage() );
        	}
        } 
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        
        return null;
	}
}
