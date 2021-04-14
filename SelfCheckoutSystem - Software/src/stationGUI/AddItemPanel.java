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

import javax.swing.SwingConstants;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.external.ProductDatabases;


import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JTextField;

public class AddItemPanel extends JPanel{
	
	private MainFrame mainFrame;
	private String itemScanned = "0";
	private BigDecimal price = new BigDecimal("0.00");
	private BigDecimal weight = new BigDecimal("0.00");
	private String name;
	private Barcode barcode;

	
	public AddItemPanel(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		
		initComponents();
		initLabels();

	}

	private void initComponents(){
		
		setBounds(0,0,1280,720);
		setLayout(new MigLayout("", "[][][][][grow][][][grow]", "[][][][][][][][][][][][][][][][][][][grow][][][][][][][][][][][][]"));
		setVisible(false);
	
		
		TextField textField = new TextField(25); // bags
		add(textField, "cell 1 3");
		
		TextField textField_3 = new TextField(25); // brought own bags
		add(textField_3, "cell 2 3");
		
		TextField textField_1 = new TextField(25); // plu 
		add(textField_1, "cell 1 14");
		
		TextField textField_2 = new TextField(25); // weight of PLU Item Purchased
		add(textField_2, "cell 2 14");

		JButton btnNewButton_2 = new JButton("      Eggs      ");
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(btnNewButton_2, "cell 1 8");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemScanned = "4";
                JOptionPane.showMessageDialog(null, "Eggs scanned! Please add to bagging area. ");
			}
		});
		
		JButton btnNewButton_3 = new JButton("Black Beans");
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(btnNewButton_3, "cell 2 8");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemScanned = "5";
                JOptionPane.showMessageDialog(null, "Black beans scanned! Please add to bagging area. ");
			}
		});
		
		JButton btnNewButton_3_1 = new JButton("   Crackers   ");
		btnNewButton_3_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemScanned = "6";
                JOptionPane.showMessageDialog(null, "Crackers scanned! Please add to bagging area. ");
			}
		});
		btnNewButton_3_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(btnNewButton_3_1, "cell 3 8");
		
		JButton btnNewButton = new JButton("      Milk      ");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemScanned = "2";
                JOptionPane.showMessageDialog(null, "Milk scanned! Please add to bagging area. ");
			}
		});
		add(btnNewButton, "cell 1 6");
		
		JButton btnNewButton_1 = new JButton("  Soy Milk  ");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemScanned = "2";
                JOptionPane.showMessageDialog(null, "Soy milk scanned! Please add to bagging area. ");
			}
		});
		add(btnNewButton_1, "cell 2 6");
		
		JButton btnNewButton_4 = new JButton("    Bread    ");
		btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemScanned = "3";
                JOptionPane.showMessageDialog(null, "Bread scanned! Please add to bagging area. ");
			}
		});
		add(btnNewButton_4, "cell 3 6");
		
		JButton btnNewButton_6 = new JButton("Add to Bagging Area");
		btnNewButton_6.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(btnNewButton_6, "cell 5 14");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					// customer buys bags
					if (textField.getText() != "" && textField.getText().length() > 0) {
						name = textField.getText() + " Store Bags";
						price = BigDecimal.valueOf(Integer.parseInt(textField.getText()) * 0.1);
						int bagsWeight = Integer.parseInt(textField.getText()) * 5; // Each bag is 5g
						weight = BigDecimal.valueOf(bagsWeight);
						mainFrame.finishesAddingItems.updateTotals(name, price, weight);
					}
					
					// customer adds own bags
					else if (textField_3.getText() != "" && textField_3.getText().length() > 0) {
						// SINCE THERE IS NO ACTUAL SCALE, WE NEED TO ASSUME A WEIGHT FOR THE BAGS THAT ARE ADDED AND I WILL USE 10g PER 
						int bagsWeight = Integer.parseInt(textField_3.getText()) * 10; 
						weight = BigDecimal.valueOf(bagsWeight);
						name = Integer.parseInt(textField_3.getText()) + " Personal Bags";
						mainFrame.finishesAddingItems.updateTotals(name, new BigDecimal("0.00"), weight);
		                JOptionPane.showMessageDialog(null, "Place your bags on the scale. ");
					}
					
					//PLU Code 
					else if (textField_1.getText() != "" && textField_2.getText() != "" && textField_1.getText().length() > 0 && textField_2.getText().length() > 0) {

						String PLU = textField_1.getText();
						int weightPLUPurchased = Integer.parseInt(textField_2.getText());			
						PriceLookupCode plu = new PriceLookupCode(PLU);
						name = textField_2.getText() + " g " + ProductDatabases.PLU_PRODUCT_DATABASE.get(plu).getDescription();
						price = ProductDatabases.PLU_PRODUCT_DATABASE.get(plu).getPrice();
						price = price.multiply(new BigDecimal(weightPLUPurchased)).multiply(new BigDecimal(0.001));
						mainFrame.finishesAddingItems.updateTotals(null, price, new BigDecimal(weightPLUPurchased));
					}	
					
					//scanning
					else if(itemScanned != "0") {
						barcode = new Barcode(itemScanned);
						double scanWeight = mainFrame.BarcodedItems.get(barcode).getWeight();
						weight = new BigDecimal(scanWeight);
						name = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode).getDescription();
						price = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode).getPrice();
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
		JButton btnNewButton_5 = new JButton("Skip Bagging This");
		btnNewButton_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				try {
					// customer buys bags
					if (textField.getText() != "" && textField.getText().length() > 0) {
						price = BigDecimal.valueOf(Integer.parseInt(textField.getText()) * 0.1);
						int bagsWeight = Integer.parseInt(textField.getText()) * 5; // Each bag is 5g
						weight = BigDecimal.valueOf(bagsWeight);
						mainFrame.finishesAddingItems.updateTotals(null, price, weight);
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
						mainFrame.finishesAddingItems.updateTotals(null, price, new BigDecimal(weightPLUPurchased));
					}	
					
					//scanning
					else if(itemScanned != "0") {
						barcode = new Barcode(itemScanned);
						double scanWeight = mainFrame.BarcodedItems.get(barcode).getWeight();
						weight = new BigDecimal(scanWeight);
						price = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode).getPrice();
						mainFrame.finishesAddingItems.updateTotals(null, price, weight);

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
					
				}
				catch(Exception exception) {
					exception.printStackTrace();
				}	
				
				mainFrame.addItemPanel.setVisible(false);
				mainFrame.attendantLoginPanel.setVisible(true);
                JOptionPane.showMessageDialog(null, "Please wait for an attendant. ");
				mainFrame.baggingAreaPanel.refreshWeight();
				mainFrame.scanningPanel.refreshTotal();
				
			}
		});
		add(btnNewButton_5, "cell 5 15");
		
		JButton btnNewButton_7 = new JButton("View Cart");
		btnNewButton_7.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(btnNewButton_7, "cell 6 14");
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
	
		private void initLabels() {
			JLabel lblNewJgoodiesLabel = new JLabel("Add Bags");
			lblNewJgoodiesLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
			add(lblNewJgoodiesLabel, "cell 1 1");
			
			JLabel lblNewJgoodiesLabel_15 = new JLabel("PLU Code Guide");
			lblNewJgoodiesLabel_15.setFont(new Font("Tahoma", Font.BOLD, 20));
			add(lblNewJgoodiesLabel_15, "cell 5 1");
			
			JLabel lblNewJgoodiesLabel_14 = new JLabel("Enter number of bags: ");
			lblNewJgoodiesLabel_14.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_14, "cell 1 2");

			
			JLabel lblNewJgoodiesLabel_2 = new JLabel("Item: ");
			lblNewJgoodiesLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_2, "cell 5 3");
			
			JLabel lblNewJgoodiesLabel_13 = new JLabel("Code:");
			lblNewJgoodiesLabel_13.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_13, "cell 6 3");
			
			JLabel lblNewJgoodiesLabel_3 = new JLabel("Scan Item:");
			lblNewJgoodiesLabel_3.setFont(new Font("Tahoma", Font.BOLD, 20));
			add(lblNewJgoodiesLabel_3, "cell 1 5");
			
			JLabel lblNewJgoodiesLabel_4 = new JLabel("Onions");
			lblNewJgoodiesLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_4, "cell 5 5");
			
			JLabel lblNewJgoodiesLabel_8 = new JLabel("14040");
			lblNewJgoodiesLabel_8.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_8, "cell 6 5");
			
			JLabel lblNewJgoodiesLabel_5 = new JLabel("Potatoes");
			lblNewJgoodiesLabel_5.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_5, "cell 5 7");
			
			JLabel lblNewJgoodiesLabel_9 = new JLabel("97310");
			lblNewJgoodiesLabel_9.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewJgoodiesLabel_9.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_9, "cell 6 7");
			
			JLabel lblNewJgoodiesLabel_6 = new JLabel("Apples");
			lblNewJgoodiesLabel_6.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_6, "cell 5 9");
			
			JLabel lblNewJgoodiesLabel_10 = new JLabel("55589");
			lblNewJgoodiesLabel_10.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_10, "cell 6 9");
			
			JLabel lblNewJgoodiesLabel_7 = new JLabel("Oranges");
			lblNewJgoodiesLabel_7.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_7, "cell 5 11");
			
			JLabel lblNewJgoodiesLabel_11 = new JLabel("30897");
			lblNewJgoodiesLabel_11.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_11, "cell 6 11");
			
			JLabel lblNewJgoodiesLabel_12 = new JLabel("Add PLU Item:");
			lblNewJgoodiesLabel_12.setFont(new Font("Tahoma", Font.BOLD, 20));
			add(lblNewJgoodiesLabel_12, "cell 1 12");
					
			JLabel lblNewJgoodiesLabel_1 = new JLabel("Enter PLU Code:");
			lblNewJgoodiesLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_1, "cell 1 13");
			
			JLabel lblNewJgoodiesLabel_16 = new JLabel("Enter Amount(g):");
			lblNewJgoodiesLabel_16.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_16, "cell 2 13");
			
			JLabel lblNewJgoodiesLabel_17 = new JLabel("Brought Your Own Bags? Enter How Many:");
			lblNewJgoodiesLabel_17.setFont(new Font("Tahoma", Font.BOLD, 12));
			add(lblNewJgoodiesLabel_17, "cell 2 2");
					
		}

	}
