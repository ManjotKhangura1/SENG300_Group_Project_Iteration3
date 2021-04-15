package stationGUI;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class ReceiptPanel extends JPanel {
	
	private MainFrame mainFrame;
	
	public ReceiptPanel(MainFrame mainFrame)
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
