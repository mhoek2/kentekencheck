package kentekencheck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class application {
	final static RDWApi api = new RDWApi();
	//final static OverheidApi api = new OverheidApi();

	static protected JPanel 	cards = null;
	static protected CardLayout cardslayout = null;
	
	static protected  MainPanelClass mainPanel = null;
	static protected VehiclePanelClass vehiclePanel = null;
	
	static int num_panels = 0;

	static class GUIPanel
	{
		JPanel 		panel;
		String		identifier;
	}

	// static or use instance of the application ( not viable for now )
	static class MainPanelClass extends GUIPanel{
		JLabel		status;
	}

	// static or use instance of the application ( not viable for now )
	static class VehiclePanelClass extends GUIPanel {
		JLabel		title;
		JLabel		APK_status;
	}
	
	//
	// Vehicle data panel
	//
	public static void openVehicleData( VehicleClass vehicle )
	{
		vehiclePanel.title.setText( String.format("%s %s", vehicle.merk, vehicle.model) );
		
		if ( vehicle.vervaldatum.length() > 0 ) 
		{
			int days = api.getDaysUntilAPK( vehicle.vervaldatum );
			vehiclePanel.APK_status.setText( String.format("Apk verloop in %d dagen", days) );
		}
		else
			vehiclePanel.APK_status.setText( "Voertuig heeft geen vervaldatum" );
		
		// terminal debug
		System.out.println( vehicle.kenteken );
		System.out.println( vehicle.merk + " " + vehicle.model );
		System.out.println( vehicle.vervaldatum );

		for ( String key : api.getJsonKeys() )
		{
			System.out.println( String.format("%s: %s", key, vehicle.getValue(key)) );
		}
		
		cardslayout.show( cards, vehiclePanel.identifier );
	}
	
	public static JPanel createResultCard()
	{
		vehiclePanel = new VehiclePanelClass(); 
		vehiclePanel.panel = new JPanel(new BorderLayout(10, 10));
		vehiclePanel.identifier = Integer.toString( num_panels++ );
		
		// reset button
	    JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    JButton button = new JButton("Terug");
        button.addActionListener(e -> cardslayout.show(cards, mainPanel.identifier ) );
        header.add(button);
	    vehiclePanel.panel.add(header, BorderLayout.NORTH);
		
	    JPanel centerPanel = new JPanel();
	    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
	    
        // Title ( brand + model )
        vehiclePanel.title = new JLabel("");
        vehiclePanel.title.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(Box.createVerticalStrut(20)); // Margin from top
        centerPanel.add(vehiclePanel.title);
        
        // APK status
        vehiclePanel.APK_status = new JLabel("", SwingConstants.CENTER);
        vehiclePanel.APK_status.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(Box.createVerticalStrut(10)); // Margin between title and status
        centerPanel.add(vehiclePanel.APK_status);

        vehiclePanel.panel.add(centerPanel, BorderLayout.CENTER);
  
        return vehiclePanel.panel;
	}

	//
	// Main panel
	//
	public static void parseLicense( String license ) 
	{
        VehicleClass vehicle = api.getVehicle( license );
        
        if ( vehicle != null )
        {
        	openVehicleData( vehicle );
        	return;
        }

    	mainPanel.status.setText( 
    			String.format("Geen informatie gevonden kenteken: %s.", license) 
    	);
	}
	
	public static JPanel createMainCard()
	{
		mainPanel = new MainPanelClass();  
		mainPanel.panel = new JPanel();     
		mainPanel.panel.setLayout(new GridLayout(10, 1));
        mainPanel.identifier = Integer.toString( num_panels++ );

        // title
        JLabel title = new JLabel("Voer kenteken in:");
        mainPanel.panel.add(title);
        
        // license input field
        JTextField licenseField = new JTextField();
        mainPanel.panel.add(licenseField);
        
        // submit button
        JButton button = new JButton("Check APK status");
        mainPanel.panel.add(button);
        
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String license = licenseField.getText().trim().toUpperCase();
                parseLicense( license );
            }
        });
        
        // status label
        mainPanel.status = new JLabel("-");
        mainPanel.panel.add( mainPanel.status );
        
        return mainPanel.panel;
	}

	public static void main( String[] args)
	{		
        JFrame frame = new JFrame("APK Check");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 450);
  
        cardslayout = new CardLayout();
        cards = new JPanel(cardslayout);
        
        createMainCard();
        createResultCard();
        
        cards.add( mainPanel.panel, mainPanel.identifier );
        cards.add( vehiclePanel.panel, vehiclePanel.identifier);
        
        frame.add(cards);
        frame.setVisible(true);    
	}
}