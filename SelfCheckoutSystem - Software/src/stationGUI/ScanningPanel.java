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

public class ScanningPanel extends JPanel {
	
	JPanel panel = new JPanel();
	
	public ScanningPanel() {
		initComponents();
		
	}
	
	private void initComponents()
	{
		setBounds(0,0,1280,720);
		setLayout(new MigLayout("", "[464.00][][][][][][][][][][][][][][][][][][][][][][][162.00,grow]", "[94.00][122.00,grow][87.00][87.00][104.00][152.00]"));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 0 1 6,grow");
		
		JPanel panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		
		JLabel lblNewLabel = new JLabel("Item Cart:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Sub Total:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel_1, "cell 23 0");
		setVisible(true);
	}
	
}
