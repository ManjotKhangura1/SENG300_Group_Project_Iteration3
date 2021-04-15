package stationGUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

public class CreditDebitWaitingPanel extends JPanel {
	
	private MainFrame mainFrame;
	private JDialog processingDialog;
	private JProgressBar processingProgressBar;
	private JLabel approvedLabel;
	private JLabel declinedLabel;
	
	public CreditDebitWaitingPanel(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		initComponents();
	}

	private void initComponents()
	{
		setBounds(0,0,1280,720);
		setLayout(new GridLayout(2, 2));
		setVisible(false);
		
		JLabel instruction = new JLabel("Please insert/swipe/tap your card");
		instruction.setBackground(Color.WHITE);
		ImageIcon instructionIcon = new ImageIcon(getClass().getResource("/Icons/Insert_Swipe_Tap.png"));
		instruction.setIcon(new ImageIcon(instructionIcon.getImage().getScaledInstance(600, 200, Image.SCALE_SMOOTH)));
		instruction.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		instruction.setHorizontalAlignment(SwingConstants.CENTER);
		instruction.setVerticalTextPosition(SwingConstants.BOTTOM);
		instruction.setHorizontalTextPosition(SwingConstants.CENTER);
		add(instruction);
		
		JLabel calculation = new JLabel("<html>SUBTOTAL<br>TAX<br>TOTAL<br>PAID</html>");
		calculation.setBackground(Color.WHITE);
		calculation.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 48));
		calculation.setHorizontalAlignment(SwingConstants.CENTER);
		calculation.setVerticalAlignment(SwingConstants.CENTER);
		add(calculation);
		
		JButton help = new JButton("Help");
		help.setBackground(Color.WHITE);
		ImageIcon helpIcon = new ImageIcon(getClass().getResource("/Icons/Help.png"));
		help.setIcon(new ImageIcon(helpIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH)));
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
		cancel.setIcon(new ImageIcon(cancelIcon.getImage().getScaledInstance(300, 150, Image.SCALE_SMOOTH)));
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
				mainFrame.creditDebitWaitingPanel.setVisible(false);
				mainFrame.welcomePanel.setVisible(true);
			}
		});
		add(cancel);
		
		processingDialog = new JDialog(mainFrame.frame);
		processingDialog.setSize(400, 80);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		processingDialog.setLocation((dimension.width - 400)/2, (dimension.height - 80)/2);
		processingDialog.setLayout(new GridLayout(1,1));
		
		processingProgressBar = new JProgressBar();
		processingProgressBar.setBorderPainted(true);
		processingProgressBar.setIndeterminate(true);
		processingProgressBar.setOrientation(SwingConstants.HORIZONTAL);
		processingProgressBar.setString("Processing ...");
		processingProgressBar.setStringPainted(true);
		
		approvedLabel = new JLabel();
		approvedLabel.setText("Approved!");
		approvedLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
		approvedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		approvedLabel.setVerticalAlignment(SwingConstants.CENTER);
		
		declinedLabel = new JLabel();
		declinedLabel.setText("Declined! Please select another payment type.");
		declinedLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
		declinedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		declinedLabel.setVerticalAlignment(SwingConstants.CENTER);
		
		processingDialog.add(processingProgressBar);
		processingDialog.setVisible(false);
	}
	
	public void processing() {
		while(!mainFrame.payWithCreditCard.getInProgress()) {}
		do {
			processingDialog.setVisible(true);
		} while(!mainFrame.payWithCreditCard.isCompleted);
		processingDialog.setVisible(false);
		if(mainFrame.payWithCreditCard.isApproved) {
			processingDialog.remove(processingProgressBar);
			processingDialog.add(approvedLabel);
			processingDialog.setVisible(true);
			processingDialog.setVisible(false);
			mainFrame.creditDebitWaitingPanel.setVisible(false);
			mainFrame.acknowledgementPanel.setVisible(true);
		}
		else {
			processingDialog.remove(processingProgressBar);
			processingDialog.add(declinedLabel);
			processingDialog.setVisible(true);
			processingDialog.setVisible(false);
			mainFrame.creditDebitWaitingPanel.setVisible(false);
			mainFrame.paymentPanel.setVisible(true);
		}
	}
}