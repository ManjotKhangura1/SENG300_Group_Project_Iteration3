package stationGUI;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.external.CardIssuer;

import net.miginfocom.swing.MigLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

public class GiftCardWaitingPanel extends JPanel {
	
	private MainFrame mainFrame;
	public JDialog processingDialog;
	private JProgressBar processingProgressBar;
	private JLabel approvedLabel;
	private JLabel declinedLabel;
	public CardIssuer testIssuer;
	public Card testCard;
	private boolean isApproved = false;
	
	/**
	 * Constructor for panel
	 * @param mainFrame - Frame which shows panel
	 */
	public GiftCardWaitingPanel(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		initComponents();
		initDatabase();
	}

	/**
	 * Initializes components
	 */
	private void initComponents()
	{
		setBounds(mainFrame.frame.getBounds());
		setLayout(new GridLayout(2, 2));
		setVisible(false);
		
		//Asks to swipe gift card
		JLabel instruction = new JLabel("Please swipe your gift card");
		instruction.setBackground(Color.WHITE);
		ImageIcon instructionIcon = new ImageIcon(getClass().getResource("/Icons/Swipe_Gift Card.png"));
		instruction.setIcon(new ImageIcon(instructionIcon.getImage().getScaledInstance(500, 300, Image.SCALE_SMOOTH)));
		instruction.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		instruction.setHorizontalAlignment(SwingConstants.CENTER);
		instruction.setVerticalTextPosition(SwingConstants.BOTTOM);
		instruction.setHorizontalTextPosition(SwingConstants.CENTER);
		add(instruction);
		
		//Makes keypad for card
		KeypadWithDisplay keypadWithDisplay = new KeypadWithDisplay();
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
		//Help with gift card listener
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processingDialog.setVisible(true);
				if(keypadWithDisplay.data.equals("111111")) {
					Card validCard = new Card("Membership", "1234567", "A Name", null, null, false, false);
					BufferedImage aSignature = new BufferedImage(1,2,3);
					mainFrame.payWithGiftCard.SwipeGiftCard(validCard, aSignature, testIssuer, mainFrame.scanningPanel.getBDTotal());
					isApproved = true;
					processing();
				}
				else {
					isApproved = false;
					processing();
				}
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
				mainFrame.giftCardWaitingPanel.setVisible(false);
				mainFrame.welcomePanel.setVisible(true);
			}
		});
		add(cancel);
	}
	
	/**
	 * Processes the gift card
	 */
	public void processing() {
		if(isApproved) {
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
	 * Initializes the gift card database
	 */
	private void initDatabase() {
		testIssuer = new CardIssuer("testIssuer");
		Calendar testCalendar =  Calendar.getInstance();
		testCalendar.set(Calendar.YEAR, 2030);
		testIssuer.addCardData("111111", "Customer", testCalendar, "111", BigDecimal.valueOf(5.0));
	};
}