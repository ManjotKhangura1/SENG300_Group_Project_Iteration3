package stationGUI;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import net.miginfocom.swing.MigLayout;

public class MainFrame {
	
	AddItemPanel addItemPanel = new AddItemPanel();
	AttendantLoginPanel attendantLoginPanel = new AttendantLoginPanel();
	AttendantPanel attendantPanel = new AttendantPanel();
	HelpPanel helpPanel = new HelpPanel();
	ReceiptPanel receiptPanel = new ReceiptPanel();
	ScanningPanel scanningPanel = new ScanningPanel();
	
	
	JFrame frame = new JFrame(); //touchScreen.getFrame();
	
	public MainFrame()
	{
		initComponents();
	}
	
	/**
	 * Initializes basic components of the frame
	 */
	private void initComponents()
	{
		frame.setBounds(0,0,1280,720);
		frame.setVisible(true);
		frame.setResizable(true);
		frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                System.exit(0);
            }
        });
		
		frame.add(addItemPanel);
		frame.add(attendantLoginPanel);
		frame.add(attendantPanel);
		frame.add(helpPanel);
		frame.add(receiptPanel);
		frame.add(scanningPanel);
	}

}
