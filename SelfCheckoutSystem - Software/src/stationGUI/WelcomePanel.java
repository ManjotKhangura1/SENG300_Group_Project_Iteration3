package stationGUI;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WelcomePanel extends JPanel {
	
	private MainFrame mainFrame;
	
	/**
	 * Constructor for panel
	 * @param mainFrame - Frame which shows panel
	 */
	public WelcomePanel(MainFrame mainFrame)
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
		setLayout(new GridLayout(2, 1));
		setVisible(false);
		
		//Welcome page
		JLabel welcomeLabel1 = new JLabel("WELCOME!", SwingConstants.CENTER);
		welcomeLabel1.setForeground(Color.GREEN);
		welcomeLabel1.setBackground(Color.WHITE);
		welcomeLabel1.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 72));
		welcomeLabel1.setVerticalAlignment(SwingConstants.BOTTOM);
		welcomeLabel1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		welcomeLabel1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mainFrame.welcomePanel.setVisible(false);
				mainFrame.scanningPanel.setVisible(true);
			}
		});
		add(welcomeLabel1);
		
		//More of welcome page
		JLabel welcomeLabel2 = new JLabel("TOUCH SCREEN TO START...", SwingConstants.CENTER);
		welcomeLabel2.setForeground(Color.GREEN);
		welcomeLabel2.setBackground(Color.WHITE);
		welcomeLabel2.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 72));
		welcomeLabel2.setVerticalAlignment(SwingConstants.TOP);
		welcomeLabel2.setCursor(new Cursor(Cursor.HAND_CURSOR));
		welcomeLabel2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mainFrame.welcomePanel.setVisible(false);
				mainFrame.scanningPanel.setVisible(true);
			}
		});
		add(welcomeLabel2);
	}
}