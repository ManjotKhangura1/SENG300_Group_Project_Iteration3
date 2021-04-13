package stationGUI;

import javax.swing.JPanel;

import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JList;

public class RefillPanel extends JPanel{

	private MainFrame mainFrame;

	/**
	 * Create the Refill Options window
	 */
	public RefillPanel() {
		initComponents();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initComponents() {
		setBounds(0, 0, 250, 220);
		
		//Setting layout information
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{36, 178, 36};
		gridBagLayout.rowHeights = new int[]{40, 37, 37, 106, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		//Creating title
		JLabel lblTitle = DefaultComponentFactory.getInstance().createTitle("Refill Options");
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 1;
		gbc_lblTitle.gridy = 0;
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblTitle, gbc_lblTitle);
		
		//Creating refill coins option
		JRadioButton rdbtnRefillCoins = new JRadioButton("Refill Coins");
		GridBagConstraints gbc_rdbtnRefillCoins = new GridBagConstraints();
		gbc_rdbtnRefillCoins.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnRefillCoins.gridx = 1;
		gbc_rdbtnRefillCoins.gridy = 1;
		rdbtnRefillCoins.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(rdbtnRefillCoins, gbc_rdbtnRefillCoins);
		
		//creating refill banknote option
		JRadioButton rdbtnRefillBanknote = new JRadioButton("Refill Banknote");
		GridBagConstraints gbc_rdbtnRefillBanknote = new GridBagConstraints();
		gbc_rdbtnRefillBanknote.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnRefillBanknote.gridx = 1;
		gbc_rdbtnRefillBanknote.gridy = 2;
		rdbtnRefillBanknote.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(rdbtnRefillBanknote, gbc_rdbtnRefillBanknote);
		
		//Grouping radio buttons so only one can be selected at a time
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnRefillCoins);
		group.add(rdbtnRefillBanknote);
		
		JList lisCDispensers = new JList();
		GridBagConstraints gbc_lisCDispensers = new GridBagConstraints();
		gbc_lisCDispensers.insets = new Insets(0, 0, 5, 5);
		gbc_lisCDispensers.fill = GridBagConstraints.BOTH;
		gbc_lisCDispensers.gridx = 1;
		gbc_lisCDispensers.gridy = 3;
		add(lisCDispensers, gbc_lisCDispensers);
		lisCDispensers.setVisible(false);
		
		JList lisBDispensers = new JList();
		GridBagConstraints gbc_lisBDispensers = new GridBagConstraints();
		gbc_lisBDispensers.insets = new Insets(0, 0, 5, 5);
		gbc_lisBDispensers.fill = GridBagConstraints.BOTH;
		gbc_lisBDispensers.gridx = 1;
		gbc_lisBDispensers.gridy = 3;
		add(lisBDispensers, gbc_lisBDispensers);
		lisBDispensers.setVisible(false);
		
		setVisible(true);
	}
}
