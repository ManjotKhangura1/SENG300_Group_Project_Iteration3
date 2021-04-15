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
import java.util.Arrays;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;

public class RefillPanel extends JPanel {

	private MainFrame mainFrame;
	private JFrame currentFrame;
	private int indexSelected;
	private JTextField txtfRefillAmount;
	public JRadioButton rdbtnCoins;
	public JRadioButton rdbtnBanknote;

	/**
	 * Create the Refill Options window
	 * 
	 * @param mainFrame The main frame of the interface
	 */
	public RefillPanel(MainFrame mainFrame, JFrame currentFrame) {
		this.mainFrame = mainFrame;
		this.currentFrame = currentFrame;
		initComponents();
	}

	/**
	 * Initialize the components of the panel
	 */
	private void initComponents() {
		setBounds(0, 0, 250, 370);
		currentFrame.pack();

		// Setting layout information
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 36, 178, 36 };
		gridBagLayout.rowHeights = new int[] { 40, 37, 37, 106, 25, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		// Creating title
		JLabel lblTitle = DefaultComponentFactory.getInstance().createTitle("Dispenser Options");
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 1;
		gbc_lblTitle.gridy = 0;
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblTitle, gbc_lblTitle);

		// Creating the list of coin denominations as a string
		String cDenominations[] = new String[mainFrame.station.coinDenominations.size()];
		for (int i = 0; i < mainFrame.station.coinDenominations.size(); i++) {
			cDenominations[i] = mainFrame.station.coinDenominations.get(i).toString();
		}

		// Creating the list of banknote denominations as a string
		String bDenominations[] = new String[mainFrame.station.banknoteDenominations.length];
		for (int i = 0; i < mainFrame.station.banknoteDenominations.length; i++) {
			bDenominations[i] = Integer.toString(mainFrame.station.banknoteDenominations[i]);
		}

		// Creating a scroll pane for coin denomination display
		JScrollPane scrollPaneCoin = new JScrollPane();
		GridBagConstraints gbc_scrollPaneCoin = new GridBagConstraints();
		gbc_scrollPaneCoin.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneCoin.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneCoin.gridx = 1;
		gbc_scrollPaneCoin.gridy = 3;
		add(scrollPaneCoin, gbc_scrollPaneCoin);

		// Creating a J list of the cDenominations
		JList lisCDispensers = new JList(cDenominations);
		lisCDispensers.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				indexSelected = Arrays.asList(cDenominations).indexOf(lisCDispensers.getSelectedValue());
			}
		});
		scrollPaneCoin.setViewportView(lisCDispensers);
		lisCDispensers.setVisible(false);

		// Creating a scroll pane for banknote denomination display
		JScrollPane scrollPaneBanknote = new JScrollPane();
		GridBagConstraints gbc_scrollPaneBanknote = new GridBagConstraints();
		gbc_scrollPaneBanknote.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneBanknote.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneBanknote.gridx = 1;
		gbc_scrollPaneBanknote.gridy = 3;
		add(scrollPaneBanknote, gbc_scrollPaneBanknote);

		// Creating a J list of the bDenominations
		JList lisBDispensers = new JList(bDenominations);
		lisBDispensers.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				indexSelected = Arrays.asList(bDenominations).indexOf(lisBDispensers.getSelectedValue());
			}
		});
		scrollPaneBanknote.setViewportView(lisBDispensers);
		lisBDispensers.setVisible(false);

		// Creating coins option
		rdbtnCoins = new JRadioButton("Coins");
		GridBagConstraints gbc_rdbtnCoins = new GridBagConstraints();
		gbc_rdbtnCoins.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnCoins.gridx = 1;
		gbc_rdbtnCoins.gridy = 1;
		rdbtnCoins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrollPaneBanknote.setVisible(false);
				lisBDispensers.setVisible(false);
				scrollPaneCoin.setVisible(true);
				lisCDispensers.setVisible(true);
			}
		});
		rdbtnCoins.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(rdbtnCoins, gbc_rdbtnCoins);

		// creating banknote option
		rdbtnBanknote = new JRadioButton("Banknote");
		GridBagConstraints gbc_rdbtnBanknote = new GridBagConstraints();
		gbc_rdbtnBanknote.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnBanknote.gridx = 1;
		gbc_rdbtnBanknote.gridy = 2;
		rdbtnBanknote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrollPaneCoin.setVisible(false);
				lisCDispensers.setVisible(false);
				scrollPaneBanknote.setVisible(true);
				lisBDispensers.setVisible(true);
			}

		});
		rdbtnBanknote.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(rdbtnBanknote, gbc_rdbtnBanknote);

		// Grouping radio buttons so only one can be selected at a time
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnCoins);
		group.add(rdbtnBanknote);

		// Creating label
		JLabel lblRefillAmount = new JLabel("Amount Refilling With:");
		GridBagConstraints gbc_lblRefillAmount = new GridBagConstraints();
		gbc_lblRefillAmount.insets = new Insets(0, 0, 5, 5);
		gbc_lblRefillAmount.gridx = 1;
		gbc_lblRefillAmount.gridy = 4;
		add(lblRefillAmount, gbc_lblRefillAmount);

		// Creating text field to choose amount to refill selected denomination with
		JTextField txtRefillAmount = new JTextField();
		GridBagConstraints gbc_txtfRefillAmount = new GridBagConstraints();
		gbc_txtfRefillAmount.insets = new Insets(0, 0, 5, 5);
		gbc_txtfRefillAmount.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtfRefillAmount.gridx = 1;
		gbc_txtfRefillAmount.gridy = 5;
		add(txtRefillAmount, gbc_txtfRefillAmount);
		txtRefillAmount.setColumns(10);

		//Creating the refill button that calls the logic
		JButton btnRefill = new JButton("Refill");
		GridBagConstraints gbc_btnRefill = new GridBagConstraints();
		gbc_btnRefill.insets = new Insets(0, 0, 5, 5);
		gbc_btnRefill.gridx = 1;
		gbc_btnRefill.gridy = 7;
		btnRefill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int amountToRefill;
				boolean refillSuccess = false;
				try {
					amountToRefill = Integer.parseInt(txtRefillAmount.getText());
					
					if (rdbtnCoins.isSelected() && indexSelected >= 0) {
						mainFrame.maintenance.refillCoin(mainFrame.station.coinDenominations.get(indexSelected),
								amountToRefill);
						refillSuccess = mainFrame.maintenance.isRefillSuccess();

					} else if (rdbtnBanknote.isSelected() && indexSelected >= 0) {
						mainFrame.maintenance.refillBanknote(mainFrame.station.banknoteDenominations[indexSelected],
								amountToRefill);
						refillSuccess = mainFrame.maintenance.isRefillSuccess();
					}

					if(refillSuccess) {
						JOptionPane.showMessageDialog(null, "Refill Successfull");
						currentFrame.dispose();
					}else {
						JOptionPane.showMessageDialog(null, "Refill Unsuccessfull. Please make sure a denomination was selected and non negative value was entered");
					}
				} catch (NumberFormatException exception) {
					JOptionPane.showMessageDialog(null, "Please make sure amount is entered and an integer");
				}

			}
		});
		add(btnRefill, gbc_btnRefill);

		setVisible(true);
	}
}
