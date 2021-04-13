package stationGUI;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AttendantPanel extends JPanel {

	private MainFrame mainFrame;
	JFrame frame;

	/**
	 * Creates the Attendent Panel
	 * 
	 * @param mainFrame The main frame used in the checkout system
	 */
	public AttendantPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		initComponents();
	}

	private void initComponents() {
		setBounds(0, 0, 1280, 720);
		setLayout(new MigLayout("",
				"[517.00][252.00][136.00,grow][grow][101.00][131.00,grow][17.00][10.00][][][][][][][][][][][][][][][38.00][36.00,grow]",
				"[139.00,grow][129.00,grow][138.00,grow][134.00,grow][135.00,grow][124.00,grow]"));
		setVisible(false);

		// Creating new frame for option panels
		frame = new JFrame("Option Frame");
		frame.setBounds(0, 0, 250, 220);
		frame.setResizable(false);

		// frame.getContentPane().setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Sub Total:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel_1, "cell 1 0");

		JPanel panel_2 = new JPanel();
		add(panel_2, "cell 2 0,grow");

		JLabel lblNewLabel_7 = new JLabel("Printer Status:");
		lblNewLabel_7.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblNewLabel_7, "cell 4 0");

		JPanel panel_7 = new JPanel();
		add(panel_7, "cell 5 0,grow");

		JLabel lblNewLabel_2 = new JLabel("Total:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblNewLabel_2, "cell 1 1");

		JPanel panel_3 = new JPanel();
		add(panel_3, "cell 2 1,grow");

		JLabel lblNewLabel_3 = new JLabel("Station Status:");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblNewLabel_3, "cell 1 2");

		JPanel panel_4 = new JPanel();
		add(panel_4, "cell 2 2,grow");

		JLabel lblNewLabel_4 = new JLabel("Payment Method:");
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblNewLabel_4, "cell 2 3");

		JPanel panel = new JPanel();
		add(panel, "cell 4 3,grow");

		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblStatus, "cell 2 4");

		JPanel panel_5 = new JPanel();
		add(panel_5, "cell 4 4,grow");

		JLabel lblChange = new JLabel("Change:");
		lblChange.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblChange, "cell 2 5");

		JPanel panel_6 = new JPanel();
		add(panel_6, "cell 4 5,grow");

		createButtons();

	}

	private void createButtons() {
		// Block Station button
		JButton btnBlock = new JButton("Block Station");
		btnBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.station.baggingArea.disable();
				mainFrame.station.mainScanner.disable();
				mainFrame.station.handheldScanner.disable();
			}
		});
		btnBlock.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnBlock, "cell 5 3");

		// Unlock Station button
		JButton btnUnblock = new JButton("Unblock Station");
		btnUnblock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mainFrame.station.baggingArea.isDisabled() && mainFrame.station.mainScanner.isDisabled()
						&& mainFrame.station.handheldScanner.isDisabled()) {
					mainFrame.station.baggingArea.enable();
					mainFrame.station.mainScanner.enable();
					mainFrame.station.handheldScanner.enable();
				}
			}
		});
		btnUnblock.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnUnblock, "cell 5 4");

		// Refill button
		JButton btnRefill = new JButton("Refill");
		btnRefill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Creating new Refill Panel
				DispenserPanel dispenserPanel = new DispenserPanel(mainFrame);
				frame.getContentPane().add(dispenserPanel);
				frame.pack();
				frame.setVisible(true);
			}
		});
		btnRefill.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnRefill, "cell 0 0");

		// Empty Dispenser
		JButton btnEmpty = new JButton("Empty Storage");
		btnEmpty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Creating new Refill Panel
				DispenserPanel panel = new DispenserPanel(mainFrame);
				frame.getContentPane().add(panel);
				frame.pack();
				frame.setVisible(true);
			}
		});
		btnEmpty.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnEmpty, "cell 0 1");

		// Logout button
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.attendantPanel.setVisible(false);
				mainFrame.scanningPanel.setVisible(true);
			}
		});
		btnLogout.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnLogout, "cell 5 5");
	}

}
