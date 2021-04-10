package stationGUI;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AttendantPanel extends JPanel {
	
	private MainFrame mainFrame;
	
	public AttendantPanel(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		initComponents();
	}

	private void initComponents()
	{
		setBounds(0,0,1280,720);
		setLayout(new MigLayout("", "[517.00][252.00][136.00,grow][grow][101.00][131.00,grow][17.00][10.00][][][][][][][][][][][][][][][38.00][36.00,grow]", "[139.00,grow][129.00,grow][138.00,grow][134.00,grow][135.00,grow][124.00,grow]"));
		setVisible(false);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 0 1 6,grow");
		
		JPanel panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		
		JLabel lblNewLabel = new JLabel("Item Cart:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Sub Total:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel_1, "cell 1 0");
		
		JPanel panel_2 = new JPanel();
		add(panel_2, "cell 2 0,grow");
		
		JLabel lblNewLabel_7 = new JLabel("Printer Status:");
		lblNewLabel_7.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblNewLabel_7, "cell 4 0");
		
		JPanel panel_7 = new JPanel();
		add(panel_7, "cell 5 0,grow");
		
		JLabel lblNewLabel_2 = new JLabel("Total:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblNewLabel_2, "cell 1 1");
		
		JPanel panel_3 = new JPanel();
		add(panel_3, "cell 2 1,grow");
		
		JLabel lblNewLabel_3 = new JLabel("Station Status:");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblNewLabel_3, "cell 1 2");
		
		JPanel panel_4 = new JPanel();
		add(panel_4, "cell 2 2,grow");
		
		JLabel lblNewLabel_4 = new JLabel("Payment Method:");
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblNewLabel_4, "cell 2 3");
		
		JPanel panel = new JPanel();
		add(panel, "cell 4 3,grow");
		
		JButton btnNewButton = new JButton("Block Station");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.station.baggingArea.disable();
				mainFrame.station.mainScanner.disable();
				mainFrame.station.handheldScanner.disable();
			}
		});
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnNewButton, "cell 5 3");
		
		JLabel lblNewLabel_5 = new JLabel("Status:");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblNewLabel_5, "cell 2 4");
		
		JPanel panel_5 = new JPanel();
		add(panel_5, "cell 4 4,grow");
		
		JButton btnNewButton_1 = new JButton("Unblock Station");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mainFrame.station.baggingArea.isDisabled() && mainFrame.station.mainScanner.isDisabled()
						&& mainFrame.station.handheldScanner.isDisabled())
				{
					mainFrame.station.baggingArea.enable();
					mainFrame.station.mainScanner.enable();
					mainFrame.station.handheldScanner.enable();
				}
			}
		});
		btnNewButton_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnNewButton_1, "cell 5 4");
		
		JLabel lblNewLabel_6 = new JLabel("Change:");
		lblNewLabel_6.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblNewLabel_6, "cell 2 5");
		
		JPanel panel_6 = new JPanel();
		add(panel_6, "cell 4 5,grow");
		
		JButton btnNewButton_2 = new JButton("Logout");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.attendantPanel.setVisible(false);
				mainFrame.scanningPanel.setVisible(true);
			}
		});
		btnNewButton_2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnNewButton_2, "cell 5 5");
		
		JButton addItem = new JButton("Add Item");
		addItem.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(addItem, "cell 1 3");
	}

}
