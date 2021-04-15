package stationGUI;

import javax.swing.JScrollPane;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class BaggingAreaPanel extends JPanel {

	private MainFrame mainFrame;
	//Label for status of station (if it is on or off)
	private JLabel lblStationStatus;
	//Price of items (or bags)
	public String price;
	//Weight of items (or bags)
	public String weight;
	//Item names
	public String items = "";
	//Cart with items
	private JLabel itemCart;
	//Total weight in electronic scale
	public JLabel totalWeight;
	//Scroll panel
	public JScrollPane scrollPane;
	

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
				totalWeight.setText("");
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
		setBounds(mainFrame.frame.getBounds());
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

		scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 0 1 6,grow");

		//Creating bagging area label
		JLabel lblBaggingArea = new JLabel("Bagging Area: ");
		lblBaggingArea.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblBaggingArea.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblBaggingArea);
		
		JList list = new JList();
		scrollPane.setViewportView(list);

		//Creating total weight label
		JLabel lblTotalWeight = new JLabel("Total Weight (g): ");
		lblTotalWeight.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalWeight.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblTotalWeight, "flowx,cell 1 1");

		totalWeight = new JLabel("");
		totalWeight.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(totalWeight, "cell 1 1");

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
	
	/**
	 * Refreshes total price, total weight, and item cart as items are scanned. Also updates payment panels
	 */
	public void refreshWeight() {
		
		weight = String.valueOf(mainFrame.finishesAddingItems.getWeight());
		totalWeight.setText(weight);
		
		for (int i = 0; i < mainFrame.addItemPanel.getList().size(); i++) {
			if (mainFrame.addItemPanel.getList().get(i) != null) {
			items.concat(mainFrame.addItemPanel.getList().get(i));
			}
		}

		itemCart = new JLabel(items);
		String items[] = new String[mainFrame.finishesAddingItems.getList().size()];

		for (int i = 0; i < mainFrame.addItemPanel.getList().size(); i++) {
			if (mainFrame.addItemPanel.getList().get(i) != null) {
			items[i] = mainFrame.addItemPanel.getList().get(i).toString();
			}
		}

		JList list = new JList(items);
		scrollPane.setViewportView(list);
		

	}
	
	
	public JLabel getLblStationStatus() {
		return lblStationStatus;
	}
}
