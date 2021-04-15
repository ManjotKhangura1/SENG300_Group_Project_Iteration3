package stationGUI;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class HelpPanel extends JPanel {
	
	private MainFrame mainFrame;
	
	/**
	 *Constructor for panel
	 * @param mainFrame - Frame which shows panel
	 */
	public HelpPanel(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		
		initComponents();
	}

	private void initComponents()
	{
		setBounds(0,0,1280,720);
		setLayout(new MigLayout("","[]","[]"));
		setVisible(false);
	}

}
