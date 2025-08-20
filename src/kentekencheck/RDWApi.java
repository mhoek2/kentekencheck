package kentekencheck;

import java.io.IOException;

import com.google.gson.*;

public class RDWApi extends APIClass  {
	protected String API_URL 		= "https://opendata.rdw.nl/resource/m9d7-ebf2.json";
	protected String API_PARAMS 	= "?$where=kenteken='";
			
	public StringBuffer buildUri( String data_table, String license )
	{
		StringBuffer output = new StringBuffer( data_table );
		output.append( API_PARAMS + license + "'" );
		
		return output;
	}
	
	public JsonObject parseJSONObject( String json )
	{
		//Vehicle data = new Gson().fromJson(response, Vehicle.class);	// class needs match format
		// more dynamic approach:
		System.out.println( json );

		JsonArray array = JsonParser.parseString(json).getAsJsonArray();
		
		if ( array.size() == 0 )
			throw new IllegalStateException("empty");

        return array.get(0).getAsJsonObject();
	}
	
    public VehicleClass parseJSONVehicle( JsonObject object ) 
    {
        String kenteken 	= super.getJsonValue( object, "kenteken" );
        String merk 		= super.getJsonValue( object, "merk" );
        String vervaldatum 	= super.getJsonValue( object, "vervaldatum_apk" );

        return new VehicleClass( 
        		kenteken,
        		merk,
        		vervaldatum
        );
	}
	
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
