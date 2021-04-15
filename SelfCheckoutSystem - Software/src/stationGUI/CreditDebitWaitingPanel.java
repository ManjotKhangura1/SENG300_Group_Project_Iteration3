package stationGUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.external.CardIssuer;

public class CreditDebitWaitingPanel extends JPanel {
	
	private MainFrame mainFrame;
	public JDialog processingDialog;
	private JProgressBar processingProgressBar;
	private JLabel approvedLabel;
	private JLabel declinedLabel;
	public Card testCard;
	
	/**
	 * constructor for panel
	 * @param mainFrame - Frame which shows panel
	 */
	public CreditDebitWaitingPanel(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		initComponents();
		initDatabase();
	}

	/**
	 * Initialize components
	 */
	private void initComponents()
	{
		setBounds(mainFrame.frame.getBounds());
		setLayout(new GridLayout(2, 2));
		setVisible(false);
		
		//Credit/debit label asking for number
		JLabel instruction = new JLabel("Please insert/swipe/tap your card");
		instruction.setBackground(Color.WHITE);
		ImageIcon instructionIcon = new ImageIcon(getClass().getResource("/Icons/Insert_Swipe_Tap.png"));
		instruction.setIcon(new ImageIcon(instructionIcon.getImage().getScaledInstance(600, 200, Image.SCALE_SMOOTH)));
		instruction.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		instruction.setHorizontalAlignment(SwingConstants.CENTER);
		instruction.setVerticalTextPosition(SwingConstants.BOTTOM);
		instruction.setHorizontalTextPosition(SwingConstants.CENTER);
		add(instruction);
		
		//Makes keypad for card
		KeypadWithDisplay keypadWithDisplay = new KeypadWithDisplay();
		keypadWithDisplay.keypad.enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processingDialog.setVisible(true);
				if(keypadWithDisplay.data.equals("111111")) {
					testCard = new Card("credit", "111111", "Person One", "111", "1234", true, true);
					mainFrame.payWithCreditCard.payWithTap(testCard, mainFrame.scanningPanel.getBDTotal());
					processing();
				}
				else if(keypadWithDisplay.data.equals("222222")) {
					testCard = new Card("credit", "222222", "Person One", "111", "1234", true, true);
					mainFrame.payWithCreditCard.payWithTap(testCard, mainFrame.scanningPanel.getBDTotal());
					processing();
				}
				else if(keypadWithDisplay.data.equals("333333")) {
					testCard = new Card("credit", "333333", "Person One", "111", "1234", true, true);
					mainFrame.payWithCreditCard.payWithTap(testCard, mainFrame.scanningPanel.getBDTotal());
					processing();
				}
				else {
					mainFrame.payWithCreditCard.isApproved = false;
					processing();
				}
			}
		});
		add(keypadWithDisplay);
		
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
				mainFrame.creditDebitWaitingPanel.setVisible(false);
				mainFrame.welcomePanel.setVisible(true);
			}
		});
		add(cancel);
		
		//Makes dialog box
		processingDialog = new JDialog(mainFrame.frame);
		processingDialog.setSize(400, 80);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		processingDialog.setLocation((dimension.width - 400)/2, (dimension.height - 80)/2);
		processingDialog.setLayout(new GridLayout(1,1));
		
		//Makes bar showing progress in processing card
		processingProgressBar = new JProgressBar();
		processingProgressBar.setBorderPainted(true);
		processingProgressBar.setIndeterminate(true);
		processingProgressBar.setOrientation(SwingConstants.HORIZONTAL);
		processingProgressBar.setString("Processing ...");
		processingProgressBar.setStringPainted(true);
		
		//Approved label
		approvedLabel = new JLabel();
		approvedLabel.setText("Approved!");
		approvedLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
		approvedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		approvedLabel.setVerticalAlignment(SwingConstants.CENTER);
		
		//Declined label
		declinedLabel = new JLabel();
		declinedLabel.setText("Declined! Please select another payment type.");
		declinedLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
		declinedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		declinedLabel.setVerticalAlignment(SwingConstants.CENTER);
		
		processingDialog.add(processingProgressBar);
		processingDialog.setVisible(false);
	}
	
	/**
	 * Processes card to approve or decline it
	 */
	public void processing() {
		if(mainFrame.payWithCreditCard.isApproved) {
			processingDialog.setVisible(false);
			processingDialog.remove(processingProgressBar);
			processingDialog.add(approvedLabel);
			processingDialog.setVisible(true);
		}
		else {
			processingDialog.setVisible(false);
			processingDialog.remove(processingProgressBar);
			processingDialog.add(declinedLabel);
			processingDialog.setVisible(true);
		}
	}
	
	/**
	 * Initializes the database for cards
	 */
	private void initDatabase() {
		CardIssuer testIssuer = new CardIssuer("testIssuer");
		Calendar testCalendar =  Calendar.getInstance();
		testCalendar.set(Calendar.YEAR, 2030);
		testIssuer.addCardData("111111", "Person One", testCalendar, "111", new BigDecimal("1000"));
		testIssuer.addCardData("222222", "Person Two", testCalendar, "222", new BigDecimal("5"));
		testIssuer.addCardData("333333", "Person Three", testCalendar, "333", new BigDecimal("10"));
	};
	
}