package stationGUI;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ScanningPanel extends JPanel {
	
	public ScanningPanel() {
		
	}
	
	private void initComponents()
	{
		setBounds(0,0,1280,720);
		setLayout(new MigLayout("","[]","[]"));
		setVisible(true);
	}
	
}
