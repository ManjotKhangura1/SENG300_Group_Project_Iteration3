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
	public JDialog processingDialog;
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
		
		KeypadWithDisplay keypadWithDisplay = new KeypadWithDisplay();
		add(keypadWithDisplay);
		
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
		processingDialog.setVisible(true);
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
}