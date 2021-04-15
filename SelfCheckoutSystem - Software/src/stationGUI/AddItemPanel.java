package stationGUI;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import javax.swing.SwingConstants;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.external.ProductDatabases;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JTextField;


public class AddItemPanel extends JPanel{
	
	public MainFrame mainFrame;
	public String itemScanned = "0";
	public BigDecimal price = new BigDecimal("0.00");
	public BigDecimal weight = new BigDecimal("0.00");
	public String name;
	public Barcode barcode;
	public ArrayList<String> baggedItems = new ArrayList<>();
	public JButton btnNewButton_2;
	public JButton btnNewButton_3;
	public JButton btnNewButton_3_1;
	public JButton btnNewButton;
	public JButton btnNewButton_5;
	public JButton btnNewButton_7;
	public JButton btnNewButton_1;
	public JButton btnNewButton_4;
	public JButton btnNewButton_6;
	

	
	public AddItemPanel(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		
		initComponents();
		initLabels();

	}

	public void initComponents(){
		
		setBounds(mainFrame.frame.getBounds());
		setVisible(false);
		setLayout(null);
	
		
		TextField textField = new TextField(25); // bags
		textField.setBounds(56, 136, 174, 22);
		add(textField);
		
		TextField textField_3 = new TextField(25); // brought own bags
		textField_3.setBounds(298, 136, 174, 22);
		add(textField_3);
		
		TextField textField_1 = new TextField(25); // plu 
		textField_1.setBounds(56, 605, 174, 22);
		add(textField_1);
		
		TextField textField_2 = new TextField(25); // weight of PLU Item Purchased
		textField_2.setBounds(313, 605, 174, 22);
		add(textField_2);

		btnNewButton_2 = new JButton("      Eggs      ");
		btnNewButton_2.setBounds(56, 362, 142, 68);
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemScanned = "4";
                JOptionPane.showMessageDialog(null, "Eggs scanned! Please add to bagging area. ");
			}
		});
		
		btnNewButton_3 = new JButton("Black Beans");
		btnNewButton_3.setBounds(269, 362, 142, 68);
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(btnNewButton_3);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemScanned = "5";
                JOptionPane.showMessageDialog(null, "Black beans scanned! Please add to bagging area. ");
			}
		});
		
		btnNewButton_3_1 = new JButton("   Crackers   ");
		btnNewButton_3_1.setBounds(454, 362, 127, 68);
		btnNewButton_3_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemScanned = "6";
                JOptionPane.showMessageDialog(null, "Crackers scanned! Please add to bagging area. ");
			}
		});
		btnNewButton_3_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(btnNewButton_3_1);
		
		btnNewButton = new JButton("      Milk      ");
		btnNewButton.setBounds(56, 294, 142, 57);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemScanned = "1";
                JOptionPane.showMessageDialog(null, "Milk scanned! Please add to bagging area. ");
			}
		});
		add(btnNewButton);
		
		btnNewButton_1 = new JButton("  Soy Milk  ");
		btnNewButton_1.setBounds(269, 294, 142, 57);
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemScanned = "2";
                JOptionPane.showMessageDialog(null, "Soy milk scanned! Please add to bagging area. ");
			}
		});
		add(btnNewButton_1);
		
		btnNewButton_4 = new JButton("    Bread    ");
		btnNewButton_4.setBounds(454, 294, 127, 57);
		btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemScanned = "3";
                JOptionPane.showMessageDialog(null, "Bread scanned! Please add to bagging area. ");
			}
		});
		add(btnNewButton_4);
		
		btnNewButton_6 = new JButton("Add to Bagging Area");
		btnNewButton_6.setBounds(757, 440, 194, 102);
		btnNewButton_6.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(btnNewButton_6);
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					// customer buys bags
					if (textField.getText() != "" && textField.getText().length() > 0) {

						price = BigDecimal.valueOf(Integer.parseInt(textField.getText()) * 0.1);
						name = textField.getText() + " Store Bags, $" + price.setScale(2,RoundingMode.HALF_UP).toString();
						int bagsWeight = Integer.parseInt(textField.getText()) * 5; // Each bag is 5g
						weight = BigDecimal.valueOf(bagsWeight);
						mainFrame.finishesAddingItems.updateTotals(name, price, weight);
						baggedItems.add(name);
					}
					
					// customer adds own bags
					else if (textField_3.getText() != "" && textField_3.getText().length() > 0) {
						// SINCE THERE IS NO ACTUAL SCALE, WE NEED TO ASSUME A WEIGHT FOR THE BAGS THAT ARE ADDED AND I WILL USE 10g PER 
						int bagsWeight = Integer.parseInt(textField_3.getText()) * 10; 
						weight = BigDecimal.valueOf(bagsWeight);
						name = Integer.parseInt(textField_3.getText()) + " Personal Bags";
						mainFrame.finishesAddingItems.updateTotals(name, new BigDecimal("0.00"), weight);
						baggedItems.add(name);
					}
					
					//PLU Code 
					else if (textField_1.getText() != "" && textField_2.getText() != "" && textField_1.getText().length() > 0 && textField_2.getText().length() > 0) {
						String PLU = textField_1.getText();
						int weightPLUPurchased = Integer.parseInt(textField_2.getText());			
						PriceLookupCode plu = new PriceLookupCode(PLU);
						price = ProductDatabases.PLU_PRODUCT_DATABASE.get(plu).getPrice();
						price = price.multiply(new BigDecimal(weightPLUPurchased)).multiply(new BigDecimal(0.001)).setScale(2, RoundingMode.HALF_UP);
						name = textField_2.getText() + " g " + ProductDatabases.PLU_PRODUCT_DATABASE.get(plu).getDescription() + ", $" + price;
						mainFrame.finishesAddingItems.updateTotals(name, price, new BigDecimal(weightPLUPurchased));
						baggedItems.add(name);
					}	
					
					//scanning
					else if(itemScanned != "0") {
						barcode = new Barcode(itemScanned);
						double scanWeight = mainFrame.BarcodedItems.get(barcode).getWeight();
						weight = new BigDecimal(scanWeight);

						price = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode).getPrice();
						name = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode).getDescription() + ", $" + price.setScale(2, RoundingMode.HALF_UP);
						//mainFrame.scanItem.scanFromMain(mainFrame.BarcodedItems.get(barcode));
						mainFrame.finishesAddingItems.updateTotals(name, price, weight);
						baggedItems.add(name);
					}
					
					else {
		                JOptionPane.showMessageDialog(null, "No item was added to the bagging area.");
					}
					
					textField.setText("");
					textField_3.setText("");
					textField_1.setText("");
					textField_2.setText("");
					
					System.out.println(mainFrame.finishesAddingItems.getPrice());
					System.out.println(mainFrame.finishesAddingItems.getList());
					System.out.println(mainFrame.finishesAddingItems.getWeight()  + " g");
					

					
				}
				catch(Exception exception) {
					exception.printStackTrace();
				}	
				
				mainFrame.addItemPanel.setVisible(false);
				mainFrame.baggingAreaPanel.setVisible(true);
				mainFrame.baggingAreaPanel.refreshWeight();
				mainFrame.scanningPanel.refreshTotal();
				
			}
		});
		
		// Get attendant to approve weight discrepancy
		btnNewButton_5 = new JButton("Skip Bagging This");
		btnNewButton_5.setBounds(757, 579, 194, 68);
		btnNewButton_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					// customer buys bags
					if (textField.getText() != "" && textField.getText().length() > 0) {
						price = BigDecimal.valueOf(Integer.parseInt(textField.getText()) * 0.1);
						int bagsWeight = Integer.parseInt(textField.getText()) * 5; // Each bag is 5g
						weight = BigDecimal.valueOf(bagsWeight);
						name = textField.getText() + " Store Bags, $" + price.setScale(2, RoundingMode.HALF_UP);
						mainFrame.finishesAddingItems.updateTotals(name, price, weight);
					}
					
					// customer adds own bags
					else if (textField_3.getText() != "" && textField_3.getText().length() > 0) {
		                JOptionPane.showMessageDialog(null, "You must place your own bags on the scale.");
					}
					
					//PLU Code 
					else if (textField_1.getText() != "" && textField_2.getText() != "" && textField_1.getText().length() > 0 && textField_2.getText().length() > 0) {

						String PLU = textField_1.getText();
						int weightPLUPurchased = Integer.parseInt(textField_2.getText());			
						PriceLookupCode plu = new PriceLookupCode(PLU);
						price = ProductDatabases.PLU_PRODUCT_DATABASE.get(plu).getPrice();
						price = price.multiply(new BigDecimal(weightPLUPurchased)).multiply(new BigDecimal(0.001));
						name = textField_2.getText() + "g " + ProductDatabases.PLU_PRODUCT_DATABASE.get(plu).getDescription() + ", $" + price.setScale(2, RoundingMode.HALF_UP);
						mainFrame.finishesAddingItems.updateTotals(name, price, new BigDecimal(weightPLUPurchased));
					}	
					
					//scanning
					else if(itemScanned != "0") {
						barcode = new Barcode(itemScanned);
						double scanWeight = mainFrame.BarcodedItems.get(barcode).getWeight();
						weight = new BigDecimal(scanWeight);
						price = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode).getPrice();
						name = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode).getDescription() + ", $" + price.setScale(2, RoundingMode.HALF_UP);
						mainFrame.finishesAddingItems.updateTotals(name, price, weight);

					}
					
					else {
		                JOptionPane.showMessageDialog(null, "No item was added to the bagging area.");
					}
					
					textField.setText("");
					textField_3.setText("");
					textField_1.setText("");
					textField_2.setText("");
					
					System.out.println(mainFrame.finishesAddingItems.getPrice());
					System.out.println(mainFrame.finishesAddingItems.getList());
					System.out.println(mainFrame.finishesAddingItems.getWeight()  + "g");
					mainFrame.addItemPanel.setVisible(false);
					mainFrame.attendantPanel.setVisibilityBtnApprove(true);
					mainFrame.attendantLoginPanel.setVisible(true);
	                JOptionPane.showMessageDialog(null, "Please wait for an attendant. ");
					mainFrame.baggingAreaPanel.refreshWeight();
					mainFrame.scanningPanel.refreshTotal();
					
				}
				catch(Exception exception) {
					exception.printStackTrace();
				}	
				

			}
		});
		add(btnNewButton_5);
		
		btnNewButton_7 = new JButton("View Cart");
		btnNewButton_7.setBounds(1021, 579, 117, 68);
		btnNewButton_7.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(btnNewButton_7);
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.addItemPanel.setVisible(false);
				mainFrame.scanningPanel.setVisible(true);
				System.out.println(mainFrame.finishesAddingItems.getPrice());
				System.out.println(mainFrame.finishesAddingItems.getList());
				System.out.println(mainFrame.finishesAddingItems.getWeight());
				textField.setText("");
				textField_3.setText("");
				textField_1.setText("");
				textField_2.setText("");
			}
		});
		
	}
	
	public ArrayList<String> getList(){
		return baggedItems;
	}
	
		private void initLabels() {
			JLabel lblNewJgoodiesLabel = new JLabel("Add Bags");
			lblNewJgoodiesLabel.setBounds(56, 53, 95, 25);
			lblNewJgoodiesLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
			add(lblNewJgoodiesLabel);
			
			JLabel lblNewJgoodiesLabel_15 = new JLabel("PLU Code Guide");
			lblNewJgoodiesLabel_15.setBounds(857, 97, 160, 25);
			lblNewJgoodiesLabel_15.setFont(new Font("Tahoma", Font.BOLD, 20));
			add(lblNewJgoodiesLabel_15);
			
			JLabel lblNewJgoodiesLabel_14 = new JLabel("Enter number of bags: ");
			lblNewJgoodiesLabel_14.setBounds(56, 103, 142, 15);
			lblNewJgoodiesLabel_14.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_14);

			
			JLabel lblNewJgoodiesLabel_2 = new JLabel("Item: ");
			lblNewJgoodiesLabel_2.setBounds(857, 149, 37, 15);
			lblNewJgoodiesLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_2);
			
			JLabel lblNewJgoodiesLabel_13 = new JLabel("Code:");
			lblNewJgoodiesLabel_13.setBounds(1021, 149, 35, 15);
			lblNewJgoodiesLabel_13.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_13);
			
			JLabel lblNewJgoodiesLabel_3 = new JLabel("Scan Item:");
			lblNewJgoodiesLabel_3.setBounds(56, 241, 111, 25);
			lblNewJgoodiesLabel_3.setFont(new Font("Tahoma", Font.BOLD, 20));
			add(lblNewJgoodiesLabel_3);
			
			JLabel lblNewJgoodiesLabel_4 = new JLabel("Onions");
			lblNewJgoodiesLabel_4.setBounds(857, 201, 42, 15);
			lblNewJgoodiesLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_4);
			
			JLabel lblNewJgoodiesLabel_8 = new JLabel("14040");
			lblNewJgoodiesLabel_8.setBounds(1021, 201, 40, 15);
			lblNewJgoodiesLabel_8.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_8);
			
			JLabel lblNewJgoodiesLabel_5 = new JLabel("Potatoes");
			lblNewJgoodiesLabel_5.setBounds(857, 249, 56, 15);
			lblNewJgoodiesLabel_5.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_5);
			
			JLabel lblNewJgoodiesLabel_9 = new JLabel("97310");
			lblNewJgoodiesLabel_9.setBounds(1021, 249, 40, 15);
			lblNewJgoodiesLabel_9.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewJgoodiesLabel_9.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_9);
			
			JLabel lblNewJgoodiesLabel_6 = new JLabel("Apples");
			lblNewJgoodiesLabel_6.setBounds(857, 295, 41, 15);
			lblNewJgoodiesLabel_6.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_6);
			
			JLabel lblNewJgoodiesLabel_10 = new JLabel("55589");
			lblNewJgoodiesLabel_10.setBounds(1021, 295, 40, 15);
			lblNewJgoodiesLabel_10.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_10);
			
			JLabel lblNewJgoodiesLabel_7 = new JLabel("Oranges");
			lblNewJgoodiesLabel_7.setBounds(857, 336, 50, 15);
			lblNewJgoodiesLabel_7.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_7);
			
			JLabel lblNewJgoodiesLabel_11 = new JLabel("30897");
			lblNewJgoodiesLabel_11.setBounds(1021, 336, 40, 15);
			lblNewJgoodiesLabel_11.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_11);
			
			JLabel lblNewJgoodiesLabel_12 = new JLabel("Add PLU Item:");
			lblNewJgoodiesLabel_12.setBounds(56, 499, 147, 25);
			lblNewJgoodiesLabel_12.setFont(new Font("Tahoma", Font.BOLD, 20));
			add(lblNewJgoodiesLabel_12);
					
			JLabel lblNewJgoodiesLabel_1 = new JLabel("Enter PLU Code:");
			lblNewJgoodiesLabel_1.setBounds(56, 553, 99, 15);
			lblNewJgoodiesLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_1);
			
			JLabel lblNewJgoodiesLabel_16 = new JLabel("Enter Amount (g):");
			lblNewJgoodiesLabel_16.setBounds(320, 553, 185, 15);
			lblNewJgoodiesLabel_16.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_16);
			
			JLabel lblNewJgoodiesLabel_17 = new JLabel("Brought Your Own Bags? Enter How Many:");
			lblNewJgoodiesLabel_17.setBounds(291, 103, 265, 15);
			lblNewJgoodiesLabel_17.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_17);
					
		}

	}
