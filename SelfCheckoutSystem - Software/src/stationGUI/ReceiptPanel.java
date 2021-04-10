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
		setBounds(0,0,1280,720);
		setLayout(new MigLayout("","[]","[]"));
		setVisible(false);
	}

}
