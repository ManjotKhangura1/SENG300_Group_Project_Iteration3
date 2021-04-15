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
		setBounds(mainFrame.frame.getBounds());
		setLayout(new MigLayout("","[]","[]"));
		setVisible(false);
	}

}
