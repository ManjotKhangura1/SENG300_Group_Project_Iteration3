package stationGUI;

import javax.swing.JScrollPane;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class BaggingAreaPanel extends JPanel {
	
	
	private MainFrame mainFrame;
	
	public BaggingAreaPanel(MainFrame mainFrame) {
		
		this.mainFrame = mainFrame;
		
		initComponents();
		initLabels();
		initButtons();
		
	}
	


	private void initButtons() {
		
		JButton btnNewButton = new JButton("Back to Adding Items");
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnNewButton, "cell 1 3");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.addItemPanel.setVisible(true);
				mainFrame.baggingAreaPanel.setVisible(false);
				mainFrame.attendantLoginPanel.setVisible(false);
			}
		});
		
		
		JButton btnNewButton_2 = new JButton("View Cart");
		btnNewButton_2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				mainFrame.baggingAreaPanel.setVisible(false);
				mainFrame.scanningPanel.setVisible(true);
			}
		});
		
		btnNewButton_2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnNewButton_2, "cell 1 4");
		
	}



	private void initComponents()
	{
		setBounds(0,0,1280,720);
		setLayout(new MigLayout("", "[517.00][94.00][200.00,grow][17.00,grow][180.00][204.00,grow][17.00][][][][][][][][][][][][][][][][38.00][36.00,grow]", "[139.00,grow][129.00,grow][138.00,grow][134.00,grow][135.00,grow][124.00,grow]"));
		setVisible(false);
		

		
	}
	
	private void initLabels() {
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 0 1 6,grow");
		
		JPanel panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		
		JLabel lblNewLabel = new JLabel("Bagging Area: ");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Weight of Item Added: ");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel_1, "cell 1 0");
		
		JPanel panel_2 = new JPanel();
		add(panel_2, "cell 2 0,grow");
		
		JLabel lblNewLabel_2 = new JLabel("Total Weight:");
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
		
		
		
	}
	
}