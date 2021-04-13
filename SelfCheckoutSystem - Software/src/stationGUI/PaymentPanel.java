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
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class PaymentPanel extends JPanel {
	
	private MainFrame mainFrame;
	
	public PaymentPanel(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		initComponents();
	}

	private void initComponents()
	{
		setBounds(0,0,1280,720);
		setLayout(new GridLayout(2, 3));
		setVisible(false);
		
		JButton creditCard = new JButton("CREDIT CARD"); 
		creditCard.setBackground(Color.WHITE);
		ImageIcon creditCardIcon = new ImageIcon(getClass().getResource("/Icons/Credit Card.png"));
		creditCard.setIcon(new ImageIcon(creditCardIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH)));
		creditCard.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		creditCard.setVerticalTextPosition(SwingConstants.BOTTOM);
		creditCard.setHorizontalTextPosition(SwingConstants.CENTER);
		creditCard.setCursor(new Cursor(Cursor.HAND_CURSOR));
		creditCard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				creditCard.setBackground(Color.GREEN);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				creditCard.setBackground(Color.WHITE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				ActionListener listener1 = new ActionListener() {
                	public void actionPerformed (ActionEvent ee) {
                		mainFrame.creditDebitWaitingPanel.setVisible(false);
                		mainFrame.acknowledgementPanel.setVisible(true);
                		ActionListener listener2 = new ActionListener() {
                        	public void actionPerformed (ActionEvent ee) {
                        		mainFrame.acknowledgementPanel.setVisible(false);
                        		mainFrame.welcomePanel.setVisible(true);
                        	}
                        };
                        Timer timer2 = new Timer(10000, listener2);
                        timer2.start();
                	}
                };
                Timer timer1 = new Timer(10000, listener1);
                mainFrame.paymentPanel.setVisible(false);
                mainFrame.creditDebitWaitingPanel.setVisible(true);
                timer1.start();
			}
		});
		add(creditCard);
		
		JButton debitCard = new JButton("DEBIT CARD");
		debitCard.setBackground(Color.WHITE);
		ImageIcon debitCardIcon = new ImageIcon(getClass().getResource("/Icons/Debit Card.png"));
		debitCard.setIcon(new ImageIcon(debitCardIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH)));
		debitCard.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		debitCard.setVerticalTextPosition(SwingConstants.BOTTOM);
		debitCard.setHorizontalTextPosition(SwingConstants.CENTER);
		debitCard.setCursor(new Cursor(Cursor.HAND_CURSOR));
		debitCard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				debitCard.setBackground(Color.GREEN);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				debitCard.setBackground(Color.WHITE);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				ActionListener listener1 = new ActionListener() {
                	public void actionPerformed (ActionEvent ee) {
                		mainFrame.creditDebitWaitingPanel.setVisible(false);
                		mainFrame.acknowledgementPanel.setVisible(true);
                		ActionListener listener2 = new ActionListener() {
                        	public void actionPerformed (ActionEvent ee) {
                        		mainFrame.acknowledgementPanel.setVisible(false);
                        		mainFrame.welcomePanel.setVisible(true);
                        	}
                        };
                        Timer timer2 = new Timer(10000, listener2);
                        timer2.start();
                	}
                };
                Timer timer1 = new Timer(10000, listener1);
                mainFrame.paymentPanel.setVisible(false);
                mainFrame.creditDebitWaitingPanel.setVisible(true);
                timer1.start();
			}
		});
		add(debitCard);
		
		JButton cash = new JButton("CASH");
		cash.setBackground(Color.WHITE);
		ImageIcon cashIcon = new ImageIcon(getClass().getResource("/Icons/Cash.png"));
		cash.setIcon(new ImageIcon(cashIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH)));
		cash.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		cash.setVerticalTextPosition(SwingConstants.BOTTOM);
		cash.setHorizontalTextPosition(SwingConstants.CENTER);
		cash.setCursor(new Cursor(Cursor.HAND_CURSOR));
		cash.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				cash.setBackground(Color.GREEN);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				cash.setBackground(Color.WHITE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				ActionListener listener1 = new ActionListener() {
                	public void actionPerformed (ActionEvent ee) {
                		mainFrame.cashWaitingPanel.setVisible(false);
                		mainFrame.acknowledgementPanel.setVisible(true);
                		ActionListener listener2 = new ActionListener() {
                        	public void actionPerformed (ActionEvent ee) {
                        		mainFrame.acknowledgementPanel.setVisible(false);
                        		mainFrame.welcomePanel.setVisible(true);
                        	}
                        };
                        Timer timer2 = new Timer(10000, listener2);
                        timer2.start();
                	}
                };
                Timer timer1 = new Timer(10000, listener1);
                mainFrame.paymentPanel.setVisible(false);
                mainFrame.cashWaitingPanel.setVisible(true);
                timer1.start();
			}
		});
		add(cash);
		
		JButton giftCard = new JButton("GIFT CARD");
		giftCard.setBackground(Color.WHITE);
		ImageIcon giftCardIcon = new ImageIcon(getClass().getResource("/Icons/Gift Card.png"));
		giftCard.setIcon(new ImageIcon(giftCardIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH)));
		giftCard.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		giftCard.setVerticalTextPosition(SwingConstants.BOTTOM);
		giftCard.setHorizontalTextPosition(SwingConstants.CENTER);
		giftCard.setCursor(new Cursor(Cursor.HAND_CURSOR));
		giftCard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				giftCard.setBackground(Color.GREEN);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				giftCard.setBackground(Color.WHITE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				ActionListener listener1 = new ActionListener() {
                	public void actionPerformed (ActionEvent ee) {
                		mainFrame.giftCardWaitingPanel.setVisible(false);
                		mainFrame.acknowledgementPanel.setVisible(true);
                		ActionListener listener2 = new ActionListener() {
                        	public void actionPerformed (ActionEvent ee) {
                        		mainFrame.acknowledgementPanel.setVisible(false);
                        		mainFrame.welcomePanel.setVisible(true);
                        	}
                        };
                        Timer timer2 = new Timer(10000, listener2);
                        timer2.start();
                	}
                };
                Timer timer1 = new Timer(10000, listener1);
                mainFrame.paymentPanel.setVisible(false);
                mainFrame.giftCardWaitingPanel.setVisible(true);
                timer1.start();
			}
		});
		add(giftCard);
		
		JButton help = new JButton("Help");
		help.setBackground(Color.WHITE);
		ImageIcon helpIcon = new ImageIcon(getClass().getResource("/Icons/Help.png"));
		help.setIcon(new ImageIcon(helpIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH)));
		help.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		help.setVerticalTextPosition(SwingConstants.BOTTOM);
		help.setHorizontalTextPosition(SwingConstants.CENTER);
		help.setCursor(new Cursor(Cursor.HAND_CURSOR));
		help.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				help.setBackground(Color.CYAN);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				help.setBackground(Color.WHITE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		add(help);
		
		JButton cancel = new JButton("Cancel");
		cancel.setBackground(Color.WHITE);
		ImageIcon cancelIcon = new ImageIcon(getClass().getResource("/Icons/Cancel.png"));
		cancel.setIcon(new ImageIcon(cancelIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH)));
		cancel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		cancel.setVerticalTextPosition(SwingConstants.BOTTOM);
		cancel.setHorizontalTextPosition(SwingConstants.CENTER);
		cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				cancel.setBackground(Color.GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				cancel.setBackground(Color.WHITE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				mainFrame.paymentPanel.setVisible(false);
				mainFrame.welcomePanel.setVisible(true);
			}
		});
		add(cancel);
	}
}