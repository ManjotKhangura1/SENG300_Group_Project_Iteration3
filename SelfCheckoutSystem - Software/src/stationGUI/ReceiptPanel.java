package stationGUI;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class ReceiptPanel extends JPanel {
	
	private MainFrame mainFrame;
	
	/**
	 * Constructor for panel
	 * @param mainFrame - Frame which shows panel
	 */
	public ReceiptPanel(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		
		initComponents();
	}

	/**
	 * Initializes components
	 */
	private void initComponents()
	{
		setBounds(mainFrame.frame.getBounds());
		setLayout(new MigLayout("","[]","[]"));
		setVisible(false);
	}

}
