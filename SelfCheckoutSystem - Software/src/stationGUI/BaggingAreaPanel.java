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
import com.jgoodies.forms.factories.DefaultComponentFactory;

public class BaggingAreaPanel extends JPanel {

	private MainFrame mainFrame;
	private JLabel lblStationStatus;
	private String price;
	private String weight;
	public JLabel totalWeight;

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
		btnBack.setBounds(494, 410, 203, 33);
		btnBack.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.addItemPanel.setVisible(true);
				mainFrame.baggingAreaPanel.setVisible(false);
				mainFrame.attendantLoginPanel.setVisible(false);
				totalWeight.setText("");
			}
		});

		//Creating the view cart button
		JButton btnViewCart = new JButton("View Cart");
		btnViewCart.setBounds(494, 530, 115, 33);
		btnViewCart.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainFrame.baggingAreaPanel.setVisible(false);
				mainFrame.scanningPanel.setVisible(true);
			}
		});
		btnViewCart.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnViewCart);
		
	}

	/**
	 * Initializes the different components of the panel
	 */
	private void initComponents() {
		setBounds(0, 0, 1280, 720);
		setVisible(false);

		initLabels();
		initButtons();
	}

	/**
	 * Initializes the labels
	 */
	private void initLabels() {
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(7, 7, 483, 706);
		add(scrollPane);

		//Creating bagging area label
		JLabel lblBaggingArea = new JLabel("Bagging Area: ");
		lblBaggingArea.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblBaggingArea.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblBaggingArea);

		//Creating total weight label
		JLabel lblTotalWeight = new JLabel("Total Weight (g): ");
		lblTotalWeight.setBounds(494, 174, 214, 24);
		lblTotalWeight.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalWeight.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblTotalWeight);
		
		//Creating station status label
		lblStationStatus = new JLabel("");
		lblStationStatus.setBounds(494, 305, 0, 0);
		/*if(mainFrame.maintenance.isStationOn() == true) {
			lblStationStatus.setText("Station Status: ON");
		}else {
			lblStationStatus.setText("Station Status: OFF");
		}*/
		lblStationStatus.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblStationStatus);

	}
	
	public void refreshWeight() {
		
		weight = String.valueOf(mainFrame.baggingArea.getWeightBaggingArea());
		totalWeight = new JLabel(weight);
		totalWeight.setFont(new Font("Tahoma", Font.BOLD, 12));
		totalWeight.setBounds(733, 181, 92, 14);

	}
	
	
	public JLabel getLblStationStatus() {
		return lblStationStatus;
	}

}
