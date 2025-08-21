package kentekencheck;

import com.google.gson.JsonObject;

public class VehicleClass {
    JsonObject data;
    String kenteken;
    String merk;
    String model;
    String vervaldatum;
    
    VehicleClass( JsonObject data ) {
        this.data = data;
    }
    
	public String getValue( String key )
	{
		try {
			return this.data.get( key ).getAsString();
		}
		catch ( NullPointerException e ) {
			return new String("");
		}
	}  
}
