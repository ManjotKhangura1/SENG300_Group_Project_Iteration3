package stationGUI;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

public class CashWaitingPanel extends JPanel {
	
	private MainFrame mainFrame;
	public JLabel calculation;
	
	/**
	 * getter to get the total and paid values in payment panel
	 * @return - calculation: JLabel which shows total and paid amount for payments
	 */
	public JLabel getCalculation() {
		return calculation;
	}

	/**
	 * Constructor for this panel
	 * @param mainFrame - Frame which shows panels
	 */
	public CashWaitingPanel(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		initComponents();
	}

	/**
	 * Initializes the components of panel
	 */
	private void initComponents()
	{
		setBounds(mainFrame.frame.getBounds());
		setLayout(new GridLayout(2, 2));
		setVisible(false);
		
		//Label for adding banknote or coins
		JLabel instruction = new JLabel("Please insert banknote and/or coins");
		instruction.setBackground(Color.WHITE);
		ImageIcon instructionIcon = new ImageIcon(getClass().getResource("/Icons/Insert_Banknote_Coin.png"));
		instruction.setIcon(new ImageIcon(instructionIcon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH)));
		instruction.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		instruction.setHorizontalAlignment(SwingConstants.CENTER);
		instruction.setVerticalTextPosition(SwingConstants.BOTTOM);
		instruction.setHorizontalTextPosition(SwingConstants.CENTER);
		add(instruction);
		
		calculation = new JLabel("<html>TOTAL: <br>PAID: </html>");
		calculation.setBackground(Color.WHITE);
		calculation.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 48));
		calculation.setHorizontalAlignment(SwingConstants.CENTER);
		calculation.setVerticalAlignment(SwingConstants.CENTER);
		add(calculation);
		
		//Help button
		JButton help = new JButton("Help");
		help.setBackground(Color.WHITE);
		ImageIcon helpIcon = new ImageIcon(getClass().getResource("/Icons/Help.png"));
		help.setIcon(new ImageIcon(helpIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH)));
		help.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		help.setVerticalTextPosition(SwingConstants.BOTTOM);
		help.setHorizontalTextPosition(SwingConstants.CENTER);
		help.setCursor(new Cursor(Cursor.HAND_CURSOR));
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		add(help);
		
		//Cancel button
		JButton cancel = new JButton("Cancel");
		cancel.setBackground(Color.WHITE);
		ImageIcon cancelIcon = new ImageIcon(getClass().getResource("/Icons/Cancel.png"));
		cancel.setIcon(new ImageIcon(cancelIcon.getImage().getScaledInstance(300, 150, Image.SCALE_SMOOTH)));
		cancel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		cancel.setVerticalTextPosition(SwingConstants.BOTTOM);
		cancel.setHorizontalTextPosition(SwingConstants.CENTER);
		cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.cashWaitingPanel.setVisible(false);
				mainFrame.welcomePanel.setVisible(true);
			}
		});
		add(cancel);
	}
}