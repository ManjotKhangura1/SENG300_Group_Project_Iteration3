package stationGUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class PaymentPanel extends JPanel {
	
	private MainFrame mainFrame;
	public JButton creditCard;
	public JButton debitCard;
	public JButton cash;
	public JButton giftCard;
	public JButton membership;
	public JButton help;
	public JButton cancel;
	public JLabel calculation;
	
	/**
	 * Constructor for panel
	 * @param mainFrame - Frame to show panel
	 */
	public PaymentPanel(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		initComponents();
	}

	/**
	 * Initialize components
	 */
	private void initComponents()
	{
		setBounds(mainFrame.frame.getBounds());
		setLayout(new GridLayout(2, 4));
		setVisible(false);
		
		//Credit card button
		creditCard = new JButton("CREDIT CARD"); 
		creditCard.setBackground(Color.WHITE);
		ImageIcon creditCardIcon = new ImageIcon(getClass().getResource("/Icons/Credit Card.png"));
		creditCard.setIcon(new ImageIcon(creditCardIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH)));
		creditCard.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		creditCard.setVerticalTextPosition(SwingConstants.BOTTOM);
		creditCard.setHorizontalTextPosition(SwingConstants.CENTER);
		creditCard.setCursor(new Cursor(Cursor.HAND_CURSOR));
		creditCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                mainFrame.paymentPanel.setVisible(false);
                mainFrame.creditDebitWaitingPanel.setVisible(true);
			}
		});
		add(creditCard);
		
		//Debit card button
		debitCard = new JButton("DEBIT CARD");
		debitCard.setBackground(Color.WHITE);
		ImageIcon debitCardIcon = new ImageIcon(getClass().getResource("/Icons/Debit Card.png"));
		debitCard.setIcon(new ImageIcon(debitCardIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH)));
		debitCard.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		debitCard.setVerticalTextPosition(SwingConstants.BOTTOM);
		debitCard.setHorizontalTextPosition(SwingConstants.CENTER);
		debitCard.setCursor(new Cursor(Cursor.HAND_CURSOR));
		debitCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                mainFrame.paymentPanel.setVisible(false);
                mainFrame.creditDebitWaitingPanel.setVisible(true);
			}
		});
		add(debitCard);
		
		//Cash button
		cash = new JButton("CASH");
		cash.setBackground(Color.WHITE);
		ImageIcon cashIcon = new ImageIcon(getClass().getResource("/Icons/Cash.png"));
		cash.setIcon(new ImageIcon(cashIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH)));
		cash.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		cash.setVerticalTextPosition(SwingConstants.BOTTOM);
		cash.setHorizontalTextPosition(SwingConstants.CENTER);
		cash.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		cash.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                mainFrame.paymentPanel.setVisible(false);
                mainFrame.cashWaitingPanel.setVisible(true);
			}
		});
		add(cash);
		
		//Gift card button
		JButton giftCard = new JButton("GIFT CARD");
		giftCard.setBackground(Color.WHITE);
		ImageIcon giftCardIcon = new ImageIcon(getClass().getResource("/Icons/Gift Card.png"));
		giftCard.setIcon(new ImageIcon(giftCardIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH)));
		giftCard.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		giftCard.setVerticalTextPosition(SwingConstants.BOTTOM);
		giftCard.setHorizontalTextPosition(SwingConstants.CENTER);
		giftCard.setCursor(new Cursor(Cursor.HAND_CURSOR));
		giftCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                mainFrame.paymentPanel.setVisible(false);
                mainFrame.giftCardWaitingPanel.setVisible(true);
			}
		});
		add(giftCard);
		
		//Membership button
		JButton membership = new JButton("MEMBERSHIP");
		membership.setBackground(Color.WHITE);
		ImageIcon membershipIcon = new ImageIcon(getClass().getResource("/Icons/MEMBERSHIP.png"));
		membership.setIcon(new ImageIcon(membershipIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH)));
		membership.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		membership.setVerticalTextPosition(SwingConstants.BOTTOM);
		membership.setHorizontalTextPosition(SwingConstants.CENTER);
		membership.setCursor(new Cursor(Cursor.HAND_CURSOR));
		membership.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                mainFrame.paymentPanel.setVisible(false);
                mainFrame.membershipWaitingPanel.setVisible(true);
			}
		});
		add(membership);
		
		//Help button
		JButton help = new JButton("Help");
		help.setBackground(Color.WHITE);
		ImageIcon helpIcon = new ImageIcon(getClass().getResource("/Icons/Help.png"));
		help.setIcon(new ImageIcon(helpIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH)));
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
		cancel.setIcon(new ImageIcon(cancelIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH)));
		cancel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		cancel.setVerticalTextPosition(SwingConstants.BOTTOM);
		cancel.setHorizontalTextPosition(SwingConstants.CENTER);
		cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.paymentPanel.setVisible(false);
				mainFrame.welcomePanel.setVisible(true);
			}
		});
		add(cancel);
		
		//Shows total and paid
		calculation = new JLabel("<html>TOTAL: <br>PAID: </html>");
		calculation.setBackground(Color.WHITE);
		calculation.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		calculation.setHorizontalAlignment(SwingConstants.CENTER);
		calculation.setVerticalAlignment(SwingConstants.CENTER);
		add(calculation);
	}

	/**
	 * Getter to get total and paid string to change when items are added
	 * @return - Total and paid label
	 */
	public JLabel getCalculation() {
		return calculation;
	}
}