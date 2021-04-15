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

public class EmptyPanel extends JPanel {

	private MainFrame mainFrame;
	private int indexSelected;
	private JFrame currentFrame;
	public JRadioButton rdbtnCoins;
	public JRadioButton rdbtnBanknote;
	public JButton btnEmpty;

	/**
	 * Create the Refill Options window
	 * 
	 * @param mainFrame The main frame of the interface
	 */
	public EmptyPanel(MainFrame mainFrame, JFrame currentFrame) {
		this.mainFrame = mainFrame;
		this.currentFrame = currentFrame;
		initComponents();
	}

	/**
	 * Initialize the components of the panel
	 */
	private void initComponents() {
		setBounds(0, 0, 250, 158);
		currentFrame.pack();

		// Setting layout information
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 36, 178, 36 };
		gridBagLayout.rowHeights = new int[] { 40, 37, 37, 44, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		// Creating title
		JLabel lblTitle = DefaultComponentFactory.getInstance().createTitle("Emptying Options");
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 1;
		gbc_lblTitle.gridy = 0;
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblTitle, gbc_lblTitle);

		// Creating coins option
		rdbtnCoins = new JRadioButton("Coins");
		GridBagConstraints gbc_rdbtnCoins = new GridBagConstraints();
		gbc_rdbtnCoins.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnCoins.gridx = 1;
		gbc_rdbtnCoins.gridy = 1;
		rdbtnCoins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
			}

		});
		rdbtnBanknote.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(rdbtnBanknote, gbc_rdbtnBanknote);

		// Grouping radio buttons so only one can be selected at a time
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnCoins);
		group.add(rdbtnBanknote);

		// Creating the refill button that calls the logic
		btnEmpty = new JButton("Empty");
		GridBagConstraints gbc_btnEmpty = new GridBagConstraints();
		gbc_btnEmpty.insets = new Insets(0, 0, 0, 5);
		gbc_btnEmpty.gridx = 1;
		gbc_btnEmpty.gridy = 3;
		btnEmpty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean emptied = false;
				if (rdbtnCoins.isSelected()) {
					mainFrame.maintenance.emptyCoinStorageUnit(mainFrame.station.coinStorage);
					emptied = mainFrame.maintenance.isEmptied();

				} else if (rdbtnBanknote.isSelected() && indexSelected >= 0) {
					mainFrame.maintenance.emptyBanknoteStorageUnit(mainFrame.station.banknoteStorage);
					emptied = mainFrame.maintenance.isEmptied();
				}

				if (emptied) {
					JOptionPane.showMessageDialog(null, "Emptied Successfully");
					currentFrame.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Emptying Unsuccessfull. Please make sure a type was selected and that storage is not already empty");
				}
			}
		});
		add(btnEmpty, gbc_btnEmpty);

		setVisible(true);
	}
}
