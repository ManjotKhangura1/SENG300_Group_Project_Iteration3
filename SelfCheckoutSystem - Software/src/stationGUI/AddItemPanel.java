package stationGUI;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class AddItemPanel extends JPanel{
	
	private MainFrame mainFrame;
	
	public AddItemPanel(MainFrame mainFrame)
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
