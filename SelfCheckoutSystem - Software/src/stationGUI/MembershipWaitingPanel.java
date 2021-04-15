package stationGUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

public class MembershipWaitingPanel extends JPanel {
	
	private MainFrame mainFrame;
	private JDialog processingDialog;
	private JProgressBar processingProgressBar;
	private JLabel approvedLabel;
	private JLabel declinedLabel;
	
	public MembershipWaitingPanel(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		initComponents();
	}

	private void initComponents()
	{
		setBounds(0,0,1280,720);
		setLayout(new GridLayout(2, 2));
		setVisible(false);
		
		JLabel instruction = new JLabel("Please swipe your membership card or enter the number");
		instruction.setBackground(Color.WHITE);
		ImageIcon instructionIcon = new ImageIcon(getClass().getResource("/Icons/Swipe_Gift Card.png"));
		instruction.setIcon(new ImageIcon(instructionIcon.getImage().getScaledInstance(500, 300, Image.SCALE_SMOOTH)));
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
				mainFrame.giftCardWaitingPanel.setVisible(false);
				mainFrame.welcomePanel.setVisible(true);
			}
		});
		add(cancel);
	}
}