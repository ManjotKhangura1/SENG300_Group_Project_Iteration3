package stationGUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PaymentPanel extends JPanel {
	
	private MainFrame mainFrame;
	
	public PaymentPanel(MainFrame mainFrame)
	{
		setBackground(new Color(240, 240, 240));
		this.mainFrame = mainFrame;
		setLayout(new GridLayout(2, 2, 0, 0));
		
		JButton creditCard = new JButton("CREIDT CARD");
		creditCard.setIcon(new ImageIcon(getClass().getResource("/Icons/Credit Card.png")));
		creditCard.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		creditCard.setVerticalAlignment(SwingConstants.BOTTOM);
		creditCard.setCursor(new Cursor(Cursor.HAND_CURSOR));
		creditCard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				mainFrame.paymentPanel.setVisible(false);
				mainFrame.scanningPanel.setVisible(true);
			}
		});
		add(creditCard);
		
		JButton debitCard = new JButton("DEBIT CARD");
		debitCard.setIcon(new ImageIcon(getClass().getResource("/Icons/Debit Card.png")));
		debitCard.setVerticalAlignment(SwingConstants.BOTTOM);
		debitCard.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		debitCard.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(debitCard);
		
		JButton cash = new JButton("CASH");
		cash.setIcon(new ImageIcon(getClass().getResource("/Icons/Cash.png")));
		cash.setVerticalAlignment(SwingConstants.BOTTOM);
		cash.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		cash.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(cash);
		
		JButton giftCard = new JButton("GIFT CARD");
		giftCard.setIcon(new ImageIcon(getClass().getResource("/Icons/Gift Card.png")));
		giftCard.setVerticalAlignment(SwingConstants.BOTTOM);
		giftCard.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		giftCard.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(giftCard);
		
		initComponents();
	}

	private void initComponents()
	{
		setBounds(0,0,1280,720);
		setVisible(false);
	}

}