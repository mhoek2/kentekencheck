package kentekencheck;

public class application {
	public static void main( String[] args)
	{	
		//RDW Open Data (resource)	REST	.../resource/m9d7-ebf2.json?$where=kenteken='63BKP7'
		//Overheid.io voertuiggegevens	REST	https://api.overheid.io/voertuiggegevens/4TFL24	
		
		RDWApi api = new RDWApi();
		
		VehicleClass vehicle = api.getVehicle("VPZ42X");
		
		if ( vehicle == null ) {
			System.out.println("Vehicle not found or error occurred.");
			return;
		}
		
		System.out.println( vehicle.kenteken );
		System.out.println( vehicle.merk );
		System.out.println( vehicle.vervaldatum );
		
		String date = api.getDateStr( vehicle.vervaldatum, "yyyyMMdd" );
		System.out.print( date );
	}
}