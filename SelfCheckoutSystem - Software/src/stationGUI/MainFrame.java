package stationGUI;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;

import net.miginfocom.swing.MigLayout;

public class MainFrame {
	
	AcknowledgementPanel acknowledgementPanel;
	AddItemPanel addItemPanel;
	AttendantLoginPanel attendantLoginPanel;
	AttendantPanel attendantPanel;
	HelpPanel helpPanel;
	ReceiptPanel receiptPanel;
	PaymentPanel paymentPanel;
	ScanningPanel scanningPanel;
	WelcomePanel welcomePanel;
	
	SelfCheckoutStation station;
	
	
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
		acknowledgementPanel = new AcknowledgementPanel(this);
		addItemPanel = new AddItemPanel(this);
		attendantLoginPanel = new AttendantLoginPanel(this);
		attendantPanel = new AttendantPanel(this);
		helpPanel = new HelpPanel(this);
		receiptPanel = new ReceiptPanel(this);
		paymentPanel = new PaymentPanel(this);
		scanningPanel = new ScanningPanel(this);
		welcomePanel = new WelcomePanel(this);
		
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
		
		welcomePanel.setVisible(true);
		
		frame.getContentPane().add(scanningPanel);
		frame.getContentPane().add(acknowledgementPanel);
		frame.getContentPane().add(addItemPanel);
		frame.getContentPane().add(attendantLoginPanel);
		frame.getContentPane().add(attendantPanel);
		frame.getContentPane().add(helpPanel);
		frame.getContentPane().add(receiptPanel);
		frame.getContentPane().add(paymentPanel);
		frame.getContentPane().add(welcomePanel);
	}

}
