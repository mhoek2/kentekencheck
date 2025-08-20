package kentekencheck;

public class application {
	public static void main( String[] args)
	{	
		//RDW Open Data (resource)	REST	.../resource/m9d7-ebf2.json?$where=kenteken='63BKP7'
		//Overheid.io voertuiggegevens	REST	https://api.overheid.io/voertuiggegevens/4TFL24	
		
		RDWApi api = new RDWApi();
		//OverheidApi api = new OverheidApi();
		
		String kenteken = "VPZ42X";
		
		VehicleClass vehicle = api.getVehicle( kenteken );
		
		if ( vehicle == null ) {
			System.out.printf("Geen informatie gevonden voor voertuig met kenteken: %s.", 
					kenteken
			);
			return;
		}
		
		System.out.println( vehicle.kenteken );
		System.out.println( vehicle.merk + " " + vehicle.model );
		System.out.println( vehicle.vervaldatum );
		
		int days = api.getDaysUntilAPK( vehicle.vervaldatum );
		
		System.out.printf("Apk verloop int %d dagen", days );
	}
}