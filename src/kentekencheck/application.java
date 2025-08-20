package kentekencheck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class application {
	
	public static void parseLicense( ActionEvent e, JLabel label, String license ) 
	{
		StringBuffer display_text = new StringBuffer(); // alloc 16
		
		RDWApi api = new RDWApi();
		//OverheidApi api = new OverheidApi();
		
		VehicleClass vehicle = api.getVehicle( license );
		
		if ( vehicle == null ) {
			display_text.insert( 0, 
					String.format("Geen informatie gevonden kenteken: %s.", 
					license
			) );
			
			System.out.print(display_text);
		}
	
		else if ( vehicle.vervaldatum.length() > 0 )
		{
			int days = api.getDaysUntilAPK( vehicle.vervaldatum );
		
			display_text.insert( 0, String.format("Apk verloop in %d dagen", days) );
			
			// terminal debug
			System.out.println( vehicle.kenteken );
			System.out.println( vehicle.merk + " " + vehicle.model );
			System.out.println( vehicle.vervaldatum );
			System.out.printf( display_text.toString() );	
		}
	
		else {
			display_text.insert( 0, "Voertuig heeft geen vervaldatum" );
		}
		
		// GUI
		label.setText( display_text.toString() );
	}
	
	public static void main( String[] args)
	{		
        JFrame frame = new JFrame("APK Check");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 450);
        frame.setLayout(new GridLayout(10, 1));
        
        JTextField licenseField = new JTextField();
        frame.add(new JLabel("Voer kenteken in:"));
        frame.add(licenseField);

        JLabel result = new JLabel("Voer kenteken in");
        
        JButton button = new JButton("Check APK");
        button.setHorizontalAlignment(SwingConstants.CENTER);
        
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String license = licenseField.getText().trim().toUpperCase();
                parseLicense( e, result, license );    
            }
        });

        frame.add(button);
        frame.add(result);

        frame.setVisible(true);    
	}
}