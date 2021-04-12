package stationGUI;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingConstants;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JTextField;

public class AddItemPanel extends JPanel{
	
	private MainFrame mainFrame;

	private JTextField txtScanned;


	
	public AddItemPanel(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		
		initComponents();
		initLabels();
		initButtons();
		initTextFields();

	}
	


	private void initTextFields() {
		
		TextField textField_1 = new TextField(22);
		add(textField_1, "cell 1 14");
		
		TextField textField_2 = new TextField(22);
		add(textField_2, "cell 2 14");

	}

	private void initButtons() {

		JButton btnNewButton_2 = new JButton("      Eggs      ");
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(btnNewButton_2, "cell 1 8");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
                JOptionPane.showMessageDialog(null, "Eggs scanned! Please add to bagging area. ");
			}
		});
		
		JButton btnNewButton_3 = new JButton("Black Beans");
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(btnNewButton_3, "cell 2 8");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Black beans scanned! Please add to bagging area. ");
			}
		});
		
		JButton btnNewButton_3_1 = new JButton("   Crackers   ");
		btnNewButton_3_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Crackers scanned! Please add to bagging area. ");
			}
		});
		btnNewButton_3_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(btnNewButton_3_1, "cell 3 8");
		
		JButton btnNewButton = new JButton("      Milk      ");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Crackers scanned! Please add to bagging area. ");
			}
		});
		add(btnNewButton, "cell 1 6");
		
		JButton btnNewButton_1 = new JButton("  Soy Milk  ");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Soy Milk scanned! Please add to bagging area. ");
			}
		});
		add(btnNewButton_1, "cell 2 6");
		
		JButton btnNewButton_4 = new JButton("    Bread    ");
		btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Bread scanned! Please add to bagging area. ");
			}
		});
		add(btnNewButton_4, "cell 3 6");
		
		JButton btnNewButton_6 = new JButton("Add to Bagging Area");
		btnNewButton_6.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(btnNewButton_6, "cell 5 14");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.addItemPanel.setVisible(false);
				mainFrame.baggingAreaPanel.setVisible(true);
				
			}
		});
		
		JButton btnNewButton_7 = new JButton("View Cart");
		btnNewButton_7.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(btnNewButton_7, "cell 6 14");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.addItemPanel.setVisible(false);
				mainFrame.scanningPanel.setVisible(true);
			}
		});
	
		
	}

	private void initLabels() {
		
		JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("Add Bags");
		lblNewJgoodiesLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		add(lblNewJgoodiesLabel, "cell 1 1");
		
		JLabel lblNewJgoodiesLabel_15 = DefaultComponentFactory.getInstance().createLabel("PLU Code Guide");
		lblNewJgoodiesLabel_15.setFont(new Font("Tahoma", Font.BOLD, 20));
		add(lblNewJgoodiesLabel_15, "cell 5 1");
		
		JLabel lblNewJgoodiesLabel_14 = DefaultComponentFactory.getInstance().createLabel("Enter number of bags: ");
		lblNewJgoodiesLabel_14.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(lblNewJgoodiesLabel_14, "cell 1 2");
		TextField textField = new TextField(22);
		add(textField, "cell 1 3");
		
		JLabel lblNewJgoodiesLabel_2 = DefaultComponentFactory.getInstance().createLabel("Item: ");
		lblNewJgoodiesLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(lblNewJgoodiesLabel_2, "cell 5 3");
		
		JLabel lblNewJgoodiesLabel_13 = DefaultComponentFactory.getInstance().createLabel("Code:");
		lblNewJgoodiesLabel_13.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(lblNewJgoodiesLabel_13, "cell 6 3");
		
		JLabel lblNewJgoodiesLabel_3 = DefaultComponentFactory.getInstance().createLabel("Scan Item:");
		lblNewJgoodiesLabel_3.setFont(new Font("Tahoma", Font.BOLD, 20));
		add(lblNewJgoodiesLabel_3, "cell 1 5");
		
		JLabel lblNewJgoodiesLabel_4 = DefaultComponentFactory.getInstance().createLabel("Onions");
		lblNewJgoodiesLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(lblNewJgoodiesLabel_4, "cell 5 5");
		
		JLabel lblNewJgoodiesLabel_8 = DefaultComponentFactory.getInstance().createLabel("14040");
		lblNewJgoodiesLabel_8.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(lblNewJgoodiesLabel_8, "cell 6 5");
		
		JLabel lblNewJgoodiesLabel_5 = DefaultComponentFactory.getInstance().createLabel("Potatoes");
		lblNewJgoodiesLabel_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(lblNewJgoodiesLabel_5, "cell 5 7");
		
		JLabel lblNewJgoodiesLabel_9 = DefaultComponentFactory.getInstance().createLabel("97310");
		lblNewJgoodiesLabel_9.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewJgoodiesLabel_9.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(lblNewJgoodiesLabel_9, "cell 6 7");
		
		JLabel lblNewJgoodiesLabel_6 = DefaultComponentFactory.getInstance().createLabel("Apples");
		lblNewJgoodiesLabel_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(lblNewJgoodiesLabel_6, "cell 5 9");
		
		JLabel lblNewJgoodiesLabel_10 = DefaultComponentFactory.getInstance().createLabel("55589");
		lblNewJgoodiesLabel_10.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(lblNewJgoodiesLabel_10, "cell 6 9");
		
		JLabel lblNewJgoodiesLabel_7 = DefaultComponentFactory.getInstance().createLabel("Oranges");
		lblNewJgoodiesLabel_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(lblNewJgoodiesLabel_7, "cell 5 11");
		
		JLabel lblNewJgoodiesLabel_11 = DefaultComponentFactory.getInstance().createLabel("30897");
		lblNewJgoodiesLabel_11.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(lblNewJgoodiesLabel_11, "cell 6 11");
		
		JLabel lblNewJgoodiesLabel_12 = DefaultComponentFactory.getInstance().createLabel("Add PLU Item:");
		lblNewJgoodiesLabel_12.setFont(new Font("Tahoma", Font.BOLD, 20));
		add(lblNewJgoodiesLabel_12, "cell 1 12");
				
		JLabel lblNewJgoodiesLabel_1 = DefaultComponentFactory.getInstance().createLabel("Enter PLU Code:");
		lblNewJgoodiesLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(lblNewJgoodiesLabel_1, "cell 1 13");
		
		JLabel lblNewJgoodiesLabel_16 = DefaultComponentFactory.getInstance().createLabel("Enter Amount(g):");
		lblNewJgoodiesLabel_16.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(lblNewJgoodiesLabel_16, "cell 2 13");
				
	}

	private void initComponents()
	{
		setBounds(0,0,1280,720);
		setLayout(new MigLayout("", "[][][][][grow][][][grow]", "[][][][][][][][][][][][][][][][][][][grow][][][][][][][][][][][][]"));
		setVisible(false);
	
	}


}
