package stationGUI;

import javax.swing.JScrollPane;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.awt.event.ActionEvent;
import java.awt.Font;
import com.jgoodies.forms.factories.DefaultComponentFactory;

public class ScanningPanel extends JPanel {

	private MainFrame mainFrame;
	private JFrame frame;
	private JLabel lblStationStatus = new JLabel("");
	public JLabel lblNewJgoodiesLabel = new JLabel("");
	public JLabel lblNewJgoodiesLabel_1 = new JLabel("");
	private JPanel panel_2;
	private JPanel panel_3;
	private String subtotal;
	private String totaltax;
	private BigDecimal bdtotal = new BigDecimal("0.00");
	private BigDecimal tax = new BigDecimal("0.00");
	private String total;
	private JPanel panel_1;
	private JPanel panel_2_2;
	private JLabel lblNewJgoodiesLabel_2 = new JLabel("");

	public ScanningPanel(MainFrame mainFrame) {

		this.mainFrame = mainFrame;

		initComponents();

	}

	private void initComponents() {
		setBounds(0, 0, 1280, 720);
		setVisible(false);
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(7, 7, 490, 706);
		add(scrollPane);

		panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);

		JLabel lblNewLabel = new JLabel("Item Cart:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Sub Total ($) :");
		lblNewLabel_1.setBounds(563, 45, 160, 24);
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel_1);

		panel_2 = new JPanel();
		panel_2.setBounds(857, 45, 173, 33);
		add(panel_2);
		
		JLabel lblNewLabel_2 = new JLabel("Total ($):");
		lblNewLabel_2.setBounds(569, 213, 109, 24);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblNewLabel_2);

		panel_3 = new JPanel();
		panel_3.setBounds(857, 199, 173, 49);
		add(panel_3);
		


		// Creating station status label
		lblStationStatus = new JLabel("Station Status: ON");
		lblStationStatus.setBounds(581, 305, 160, 24);
		//lblStationStatus.setText("Station Status: ON");
		//System.out.print(mainFrame.attendantPanel.getLblStationStatus());
		//lblStationStatus.setText(mainFrame.attendantPanel.getLblStationStatus());
		/*if (mainFrame.maintenance.isStationOn() == true) {
			System.out.print(mainFrame.maintenance.isStationOn());
			lblStationStatus.setText("Station Status: ON");
		} else {
			System.out.print(mainFrame.maintenance.isStationOn());
			lblStationStatus.setText("Station Status: OFF");
		}*/
		lblStationStatus.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblStationStatus);
		
		

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(857, 286, 173, 119);
		add(panel_4);

		JButton btnNewButton = new JButton("Add Item");
		btnNewButton.setBounds(581, 451, 199, 33);
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.addItemPanel.setVisible(true);
				mainFrame.scanningPanel.setVisible(false);
			}
		});
		
		JButton btnNewButton_3 = new JButton("Remove Item");
		btnNewButton_3.setBounds(914, 451, 256, 33);
		btnNewButton_3.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		add(btnNewButton_3);

		JButton btnNewButton_2 = new JButton("Help");
		btnNewButton_2.setBounds(581, 544, 199, 33);
		btnNewButton_2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.scanningPanel.setVisible(false);
				mainFrame.attendantLoginPanel.setVisible(true);
			}
		});
		add(btnNewButton_2);

		JButton btnNewButton_1 = new JButton("Proceed to Pay");
		btnNewButton_1.setBounds(581, 644, 215, 33);
		btnNewButton_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnNewButton_1);
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mainFrame.scanningPanel.setVisible(false);
				mainFrame.paymentPanel.setVisible(true);
			}
		});
		btnNewButton_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JButton attendantLoginB = new JButton("Attendant Login");
		attendantLoginB.setBounds(914, 644, 256, 33);
		attendantLoginB.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainFrame.scanningPanel.setVisible(false);
				mainFrame.attendantLoginPanel.setVisible(true);
			}
		});
		attendantLoginB.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(attendantLoginB);
		
		JLabel lblNewLabel_1_1 = new JLabel("Tax ($) :");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_1_1.setBounds(540, 130, 160, 24);
		add(lblNewLabel_1_1);
		
		panel_2_2 = new JPanel();
		panel_2_2.setBounds(857, 130, 173, 33);
		add(panel_2_2);
		

		

		/*
		 * if (!attendantLogin.isVisible() && !attendantLogin.isVisible()) {
		 * setVisible(true); }
		 */
	}
	
	public void refreshTotal() {
		
		subtotal = String.valueOf(mainFrame.finishesAddingItems.getPrice());
		lblNewJgoodiesLabel = new JLabel(subtotal);
		lblNewJgoodiesLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewJgoodiesLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_2.removeAll();
		panel_2.add(lblNewJgoodiesLabel);
		
		tax = new BigDecimal(mainFrame.finishesAddingItems.getPrice() * 0.05);
		tax = tax.setScale(2, RoundingMode.HALF_UP);
		totaltax = tax.toString();
		lblNewJgoodiesLabel_2 = new JLabel (totaltax);
		lblNewJgoodiesLabel_2.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewJgoodiesLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_2_2.removeAll();
		panel_2_2.add(lblNewJgoodiesLabel_2);


		bdtotal = new BigDecimal(mainFrame.finishesAddingItems.getPrice() * 1.05);
		bdtotal = bdtotal.setScale(2, RoundingMode.HALF_UP);
		total = bdtotal.toString();
		lblNewJgoodiesLabel_1 = new JLabel (total);
		lblNewJgoodiesLabel_1.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewJgoodiesLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_3.removeAll();
		panel_3.add(lblNewJgoodiesLabel_1);
		
		
		
		
	}

	public JLabel getLblStationStatus() {
		return lblStationStatus;
	}
}
