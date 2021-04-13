package stationGUI;

import javax.swing.JPanel;

import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JList;

public class DispenserPanel extends JPanel{

	private MainFrame mainFrame;

	/**
	 * Create the Refill Options window
	 */
	public DispenserPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
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
		JLabel lblTitle = DefaultComponentFactory.getInstance().createTitle("Dispenser Options");
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 1;
		gbc_lblTitle.gridy = 0;
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblTitle, gbc_lblTitle);
		
		String cDenominations[] = new String[mainFrame.station.coinDenominations.size()];
	
		for(int i = 0; i < mainFrame.station.coinDenominations.size(); i++) {
			cDenominations[i] = mainFrame.station.coinDenominations.get(i).toString();
		}

		JList lisCDispensers = new JList(cDenominations);
		GridBagConstraints gbc_lisCDispensers = new GridBagConstraints();
		gbc_lisCDispensers.insets = new Insets(0, 0, 5, 5);
		gbc_lisCDispensers.fill = GridBagConstraints.BOTH;
		gbc_lisCDispensers.gridx = 1;
		gbc_lisCDispensers.gridy = 3;
		add(lisCDispensers, gbc_lisCDispensers);
		lisCDispensers.setVisible(false);
		
		String bDenominations[]= { "$5.00", "$10.00", "$20.00",
		        "$50.00", "$100.00"};
		
		JList lisBDispensers = new JList(bDenominations);
		GridBagConstraints gbc_lisBDispensers = new GridBagConstraints();
		gbc_lisBDispensers.insets = new Insets(0, 0, 5, 5);
		gbc_lisBDispensers.fill = GridBagConstraints.BOTH;
		gbc_lisBDispensers.gridx = 1;
		gbc_lisBDispensers.gridy = 3;
		add(lisBDispensers, gbc_lisBDispensers);
		lisBDispensers.setVisible(false);
		
		//Creating coins option
		JRadioButton rdbtnCoins = new JRadioButton("Coins");
		GridBagConstraints gbc_rdbtnCoins = new GridBagConstraints();
		gbc_rdbtnCoins.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnCoins.gridx = 1;
		gbc_rdbtnCoins.gridy = 1;
		rdbtnCoins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lisBDispensers.setVisible(false);
				lisCDispensers.setVisible(true);	
			}
		});
		rdbtnCoins.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(rdbtnCoins, gbc_rdbtnCoins);
		
		//creating banknote option
		JRadioButton rdbtnBanknote = new JRadioButton("Banknote");
		GridBagConstraints gbc_rdbtnBanknote = new GridBagConstraints();
		gbc_rdbtnBanknote.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnBanknote.gridx = 1;
		gbc_rdbtnBanknote.gridy = 2;
		rdbtnBanknote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lisCDispensers.setVisible(false);
				lisBDispensers.setVisible(true);
			}
			
		});
		rdbtnBanknote.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(rdbtnBanknote, gbc_rdbtnBanknote);
		
		//Grouping radio buttons so only one can be selected at a time
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnCoins);
		group.add(rdbtnBanknote);
		
		setVisible(true);
	}
}
