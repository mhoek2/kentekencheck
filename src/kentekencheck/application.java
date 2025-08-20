package kentekencheck;

public class application {
	public static void main( String[] args)
	{	
		//RDW Open Data (resource)	REST	.../resource/m9d7-ebf2.json?$where=kenteken='63BKP7'
		//Overheid.io voertuiggegevens	REST	https://api.overheid.io/voertuiggegevens/4TFL24	
		
		Api api = new Api();
		
		api.getData("VPZ42X");
	}
}