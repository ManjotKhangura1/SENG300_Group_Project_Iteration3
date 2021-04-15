package stationGUI;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class AcknowledgementPanel extends JPanel {
	
	private MainFrame mainFrame;
	
	/**
	 * Constructor for AcknowledgementPanel
	 * @param mainFrame - the frame which holds all the panels of the self checkout system
	 */
	public AcknowledgementPanel(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		initComponents();
	}

	/**
	 * Initializes the components for this panel
	 */
	private void initComponents()
	{
		setBounds(mainFrame.frame.getBounds());
		setLayout(new GridLayout(2, 1));
		setVisible(false);
		
		//Labels when customer done payment
		JLabel acknowledgementLabel1 = new JLabel("Thank you for shopping with us today!", SwingConstants.CENTER);
		acknowledgementLabel1.setForeground(Color.MAGENTA);
		acknowledgementLabel1.setBackground(Color.WHITE);
		acknowledgementLabel1.setFont(new Font("Brush Script MT", Font.PLAIN, 72));
		acknowledgementLabel1.setVerticalAlignment(SwingConstants.BOTTOM);
		add(acknowledgementLabel1);
		JLabel acknowledgementLabel2 = new JLabel("Have a nice day!", SwingConstants.CENTER);
		acknowledgementLabel2.setForeground(Color.MAGENTA);
		acknowledgementLabel2.setBackground(Color.WHITE);
		acknowledgementLabel2.setFont(new Font("Brush Script MT", Font.PLAIN, 72));
		acknowledgementLabel2.setVerticalAlignment(SwingConstants.TOP);
		add(acknowledgementLabel2);
	}
}