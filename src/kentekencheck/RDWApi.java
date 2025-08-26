package kentekencheck;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public final class RDWApi extends APIClass  {
	//RDW Open Data (resource)	REST	.../resource/m9d7-ebf2.json?$where=kenteken='63BKP7'

	protected final String API_URL 		= "https://opendata.rdw.nl/resource/m9d7-ebf2.json";
	protected final String API_PARAMS 	= "?$where=kenteken='";
	protected final String DATE_PATTERN 	= "yyyyMMdd";
	
    @Override
    public String getApiUrl() { return API_URL; }

    @Override
    public String getApiParams() { return API_PARAMS; }

    @Override
    public String getDatePattern() { return DATE_PATTERN; }
	
    @Override
    public String[] getJsonKeys() {
    	return new String[] {
    	    "datum_tenaamstelling",
    	    "bruto_bpm",
    	    "inrichting",
    	    "aantal_zitplaatsen",
    	    "eerste_kleur",
    	    "tweede_kleur",
    	    "aantal_cilinders",
    	    "cilinderinhoud",
    	    "massa_ledig_voertuig",
    	    "toegestane_maximum_massa_voertuig",
    	    "massa_rijklaar",
    	    "maximum_massa_trekken_ongeremd",
    	    "maximum_trekken_massa_geremd",
    	    "datum_eerste_toelating",
    	    "datum_eerste_tenaamstelling_in_nederland",
    	    "wacht_op_keuren",
    	    "wam_verzekerd",
    	    "aantal_deuren",
    	    "aantal_wielen",
    	    "lengte",
    	    "europese_voertuigcategorie",
    	    "plaats_chassisnummer",
    	    "technische_max_massa_voertuig",
    	    "typegoedkeuringsnummer",
    	    "variant",
    	    "uitvoering",
    	    "volgnummer_wijziging_eu_typegoedkeuring",
    	    "vermogen_massarijklaar",
    	    "wielbasis",
    	    "export_indicator",
    	    "openstaande_terugroepactie_indicator",
    	    "taxi_indicator",
    	    "maximum_massa_samenstelling",
    	    "jaar_laatste_registratie_tellerstand",
    	    "tellerstandoordeel",
    	    "code_toelichting_tellerstandoordeel",
    	    "tenaamstellen_mogelijk",
    	    "vervaldatum_apk_dt",
    	    "datum_tenaamstelling_dt",
    	    "datum_eerste_toelating_dt",
    	    "datum_eerste_tenaamstelling_in_nederland_dt",
    	    "zuinigheidsclassificatie"
    	}; 	
    }

    @Override
	public StringBuffer buildUri( String data_table, String license )
	{
		StringBuffer output = new StringBuffer( data_table );
		output.append( API_PARAMS + license + "'" );
		
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
        VehicleClass vehicle = new VehicleClass( object );

        vehicle.kenteken 	= vehicle.getValue( "kenteken" );
        vehicle.merk 		= vehicle.getValue( "merk" );
        vehicle.model 		= vehicle.getValue( "handelsbenaming" );
        vehicle.vervaldatum = vehicle.getValue( "vervaldatum_apk" );

        return vehicle;
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
