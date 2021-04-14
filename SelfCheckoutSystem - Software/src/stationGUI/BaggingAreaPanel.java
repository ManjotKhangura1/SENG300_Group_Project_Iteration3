package stationGUI;

import javax.swing.JScrollPane;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class BaggingAreaPanel extends JPanel {

	private MainFrame mainFrame;
	private JLabel lblStationStatus;

	/**
	 * Bagging area panel constructor
	 * @param mainFrame The main frame the panel is on
	 */
	public BaggingAreaPanel(MainFrame mainFrame) {

		this.mainFrame = mainFrame;

		initComponents();
	}

	/**
	 * Initializes the buttons
	 */
	private void initButtons() {

		//Creating the back to adding items button
		JButton btnBack = new JButton("Back to Adding Items");
		btnBack.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnBack, "cell 1 3");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.addItemPanel.setVisible(true);
				mainFrame.baggingAreaPanel.setVisible(false);
				mainFrame.attendantLoginPanel.setVisible(false);
			}
		});

		//Creating the view cart button
		JButton btnViewCart = new JButton("View Cart");
		btnViewCart.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainFrame.baggingAreaPanel.setVisible(false);
				mainFrame.scanningPanel.setVisible(true);
			}
		});
		btnViewCart.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnViewCart, "cell 1 4");

	}

	/**
	 * Initializes the different components of the panel
	 */
	private void initComponents() {
		setBounds(0, 0, 1280, 720);
		setLayout(new MigLayout("",
				"[517.00][94.00][200.00,grow][17.00,grow][180.00][204.00,grow][17.00][][][][][][][][][][][][][][][][38.00][36.00,grow]",
				"[139.00,grow][129.00,grow][138.00,grow][134.00,grow][135.00,grow][124.00,grow]"));
		setVisible(false);

		initLabels();
		initButtons();
	}

	/**
	 * Initializes the labels
	 */
	private void initLabels() {

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 0 1 6,grow");

		//Creating bagging area label
		JLabel lblBaggingArea = new JLabel("Bagging Area: ");
		lblBaggingArea.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblBaggingArea.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblBaggingArea);

		//Creating item weight label
		JLabel lblItemWeight = new JLabel("Weight of Item Added: ");
		lblItemWeight.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblItemWeight.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblItemWeight, "cell 1 0");
		
		//Creating total weight label
		JLabel lblTotalWeight = new JLabel("Total Weight:");
		lblTotalWeight.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalWeight.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblTotalWeight, "cell 1 1");

		//Creating station status label
		lblStationStatus = new JLabel("");
		/*if(mainFrame.maintenance.isStationOn() == true) {
			lblStationStatus.setText("Station Status: ON");
		}else {
			lblStationStatus.setText("Station Status: OFF");
		}*/
		lblStationStatus.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblStationStatus, "cell 1 2");

	}
	
	public JLabel getLblStationStatus() {
		return lblStationStatus;
	}

}
