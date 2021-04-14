package stationGUI;

import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;

import net.miginfocom.swing.MigLayout;

//import BaggingArea;

public class MainFrame {
	
	AddItemPanel addItemPanel;
	AttendantLoginPanel attendantLoginPanel;
	AttendantPanel attendantPanel;
	HelpPanel helpPanel;
	ReceiptPanel receiptPanel;
	ScanningPanel scanningPanel;

	BaggingAreaPanel baggingAreaPanel;

	SelfCheckoutStation station;
	
	//BaggingArea baggingArea;
	
	
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
		addItemPanel = new AddItemPanel(this);
		attendantLoginPanel = new AttendantLoginPanel(this);
		attendantPanel = new AttendantPanel(this);
		helpPanel = new HelpPanel(this);
		receiptPanel = new ReceiptPanel(this);
		scanningPanel = new ScanningPanel(this);
		baggingAreaPanel = new BaggingAreaPanel(this);


		
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
		
		scanningPanel.setVisible(true);
		
		frame.getContentPane().add(scanningPanel);
		frame.getContentPane().add(addItemPanel);
		frame.getContentPane().add(attendantLoginPanel);
		frame.getContentPane().add(attendantPanel);
		frame.getContentPane().add(helpPanel);
		frame.getContentPane().add(receiptPanel);
		frame.getContentPane().add(baggingAreaPanel);

	}

}
