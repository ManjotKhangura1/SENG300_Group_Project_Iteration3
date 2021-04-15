package stationGUI;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class AttendantPanel extends JPanel {

	private MainFrame mainFrame;
	private JFrame frame;
	private JLabel lblStationStatus;
	private JLabel lblPrinterPaperStatus;
	private JLabel lblPrinterInkStatus;
	public JButton btnRefill;
	public JButton btnEmpty;
	public JButton btnChangeInk;
	public JButton btnChangePaper;
	public JButton btnBlock;
	public JButton btnUnblock;
	public JButton btnStartUp;
	public JButton btnShutDown;
	public JButton btnLogout;
	private JButton btnApprove;
	private int paper = 0;
	private int ink = 0;
	int maxPaper = 0;
	int maxInk = 0;

	/**
	 * Creates the Attendent Panel
	 * 
	 * @param mainFrame The main frame used in the checkout system
	 */
	public AttendantPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		initComponents();
	}

	/**
	 * Creates the different components of the Attendant Panel
	 */
	private void initComponents() {
		setBounds(mainFrame.frame.getBounds());
		setLayout(new MigLayout("",
				"[517.00][252.00][136.00,grow][grow][101.00][131.00,grow][17.00][10.00][][][][][][][][][][][][][][][38.00][36.00,grow]",
				"[139.00,grow][129.00,grow][138.00,grow][134.00,grow][135.00,grow][124.00,grow]"));
		setVisible(false);

		JLabel lblSubTotal = new JLabel("Sub Total:");
		lblSubTotal.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblSubTotal.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblSubTotal, "cell 1 0");

		lblPrinterPaperStatus = new JLabel("Printer Paper Status: " + paper + " pages remaining");
		lblPrinterPaperStatus.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblPrinterPaperStatus, "cell 4 0");

		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotal.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblTotal, "cell 1 1");

		lblPrinterInkStatus = new JLabel("Printer Ink Status: " + ink + " units of ink remaining");
		lblPrinterInkStatus.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblPrinterInkStatus, "cell 4 1");

		// Creates the label to display the station status
		lblStationStatus = new JLabel("Station Status: ON");
		lblStationStatus.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblStationStatus, "cell 1 2");

		JLabel lblPaymentMethod = new JLabel("Payment Method:");
		lblPaymentMethod.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblPaymentMethod, "cell 2 3");

		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblStatus, "cell 2 4");

		JLabel lblChange = new JLabel("Change:");
		lblChange.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblChange, "cell 2 5");

		createButtons();

	}

	/**
	 * Creates all the buttons needed in the attendant station
	 */
	private void createButtons() {
		// Refill button
		btnRefill = new JButton("Refill");
		btnRefill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createFrame();
				// Creating new Refill Panel
				RefillPanel dispenserPanel = new RefillPanel(mainFrame, frame);
				frame.getContentPane().add(dispenserPanel);
				frame.pack();
				frame.setVisible(true);
			}
		});
		btnRefill.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnRefill, "cell 0 0");
		btnRefill.setVisible(false);

		// Empty Dispenser
		btnEmpty = new JButton("Empty Storage");
		btnEmpty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createFrame();
				// Creating new Refill Panel
				EmptyPanel panel = new EmptyPanel(mainFrame, frame);
				frame.getContentPane().add(panel);
				frame.pack();
				frame.setVisible(true);
			}
		});
		btnEmpty.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnEmpty, "cell 0 1");
		btnEmpty.setVisible(false);

		// Change ink button
		btnChangeInk = new JButton("Change Ink");
		btnChangeInk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				maxInk = mainFrame.station.printer.MAXIMUM_INK;
				if (ink == 0) {
					mainFrame.maintenance.changeInk(maxInk);
					lblPrinterInkStatus.setText("Printer Ink Status: " + maxInk + " units of ink remaining");
				}
				if (ink > 0 && ink < maxInk) {
					mainFrame.maintenance.changeInk(maxInk - ink);
					lblPrinterInkStatus.setText("Printer Ink Status: " + maxInk + " units of ink remaining");
				}
				ink = maxInk;
			}
		});
		btnChangeInk.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnChangeInk, "cell 0 3");
		btnChangeInk.setVisible(false);

		// Change paper button
		btnChangePaper = new JButton("Change Paper");
		btnChangePaper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				maxPaper = mainFrame.station.printer.MAXIMUM_PAPER;
				if (paper == 0) {
					mainFrame.maintenance.changePaper(maxPaper);
					lblPrinterPaperStatus.setText("Printer Paper Status: " + maxPaper + " pages remaining");
				}
				if (paper > 0 && paper < maxPaper) {
					mainFrame.maintenance.changePaper(maxPaper - paper);
					lblPrinterPaperStatus.setText("Printer Paper Status: " + maxPaper + " pages remaining");
				}
				paper = maxPaper;
			}
		});
		btnChangePaper.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnChangePaper, "flowx,cell 0 3");
		btnChangePaper.setVisible(false);
		
		// Approve weight discrepancy button
		btnApprove = new JButton("Approve Weight Discrepancy");
		btnApprove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String name;
					BigDecimal price = new BigDecimal("0.00");
					BigDecimal weight = new BigDecimal("0.00");
					mainFrame.approveWeightDiscrepancy.approve();
					mainFrame.finishesAddingItems.updateTotals(null, price, weight);
				} catch (Exception e1) {
				}
				mainFrame.attendantPanel.setVisible(false);
				mainFrame.scanningPanel.setVisible(true);
				btnApprove.setVisible(false);
			}
		});
		add(btnApprove, "cell 0 4");
		btnApprove.setVisible(false);

		// Block Station button
		btnBlock = new JButton("Block Station");
		// Unlock Station button
		btnUnblock = new JButton("Unblock Station");

		// Adding in the action listener for block and formatting it
		btnBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.station.baggingArea.disable();
				mainFrame.station.mainScanner.disable();
				mainFrame.station.handheldScanner.disable();
				// switching visible buttons
				btnBlock.setVisible(false);
				btnUnblock.setVisible(true);
				btnRefill.setVisible(true);
				btnEmpty.setVisible(true);
				btnChangeInk.setVisible(true);
				btnChangePaper.setVisible(true);
			}
		});
		btnBlock.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnBlock, "cell 5 3");
		btnBlock.setVisible(true);

		// Adding in the action listener for unblock and formatting it
		btnUnblock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mainFrame.station.baggingArea.isDisabled() && mainFrame.station.mainScanner.isDisabled()
						&& mainFrame.station.handheldScanner.isDisabled()) {
					mainFrame.station.baggingArea.enable();
					mainFrame.station.mainScanner.enable();
					mainFrame.station.handheldScanner.enable();
					// switching visible buttons
					btnBlock.setVisible(true);
					btnUnblock.setVisible(false);
					btnRefill.setVisible(false);
					btnEmpty.setVisible(false);
					btnChangeInk.setVisible(false);
					btnChangePaper.setVisible(false);
				}
			}
		});
		btnUnblock.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnUnblock, "cell 5 4");
		btnUnblock.setVisible(false);

		// Creating the start up button
		btnStartUp = new JButton("Start Up");

		// Creating the shut down button
		btnShutDown = new JButton("Shut Down");

		// Creating an action listener for start up button and formatting
		btnStartUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.maintenance.startUp();
				//Changing buttons visible
				btnStartUp.setVisible(false);
				btnShutDown.setVisible(true);
				btnBlock.setVisible(true);
				lblStationStatus.setText("Station Status: ON");
				mainFrame.scanningPanel.getLblStationStatus().setText("Station Status: ON");
				mainFrame.baggingAreaPanel.getLblStationStatus().setText("Station Status: ON");
			}
		});
		btnStartUp.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnStartUp, "cell 0 2");

		// Creating an action listener for shut down button and formatting
		btnShutDown.setVisible(true);
		btnShutDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.maintenance.shutDown();
				//changing buttons visible
				btnBlock.setVisible(false);
				btnUnblock.setVisible(false);
				btnShutDown.setVisible(false);
				btnStartUp.setVisible(true);
				btnRefill.setVisible(false);
				btnEmpty.setVisible(false);
				btnChangeInk.setVisible(false);
				btnChangePaper.setVisible(false);
				lblStationStatus.setText("Station Status: OFF");
				mainFrame.scanningPanel.getLblStationStatus().setText("Station Status: OFF");
				mainFrame.baggingAreaPanel.getLblStationStatus().setText("Station Status: OFF");
			}
		});
		btnShutDown.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnShutDown, "cell 0 2");
		btnStartUp.setVisible(false);

		// Logout button
		btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.attendantPanel.setVisible(false);
				mainFrame.scanningPanel.setVisible(true);
			}
		});
		btnLogout.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnLogout, "cell 5 5");
	}

	/**
	 * Creates a new JFrame
	 */
	private void createFrame() {
		// Creating new frame for option panels
		frame = new JFrame("Option Frame");
		frame.setBounds(0, 0, 250, 370);
		frame.setResizable(false);
	}

	/**
	 * Gets the button to approve weight discrepencies
	 * @return btnApprove the button
	 */
	public JButton getBtnApprove() {
		return btnApprove;
	}

	/**
	 * Sets the visibility of the btnApprove
	 * @param visibility the boolean representing if the button should be visible or not
	 */
	public void setVisibilityBtnApprove(boolean visibility) {
		btnApprove.setVisible(visibility);
	}
	
	

}
