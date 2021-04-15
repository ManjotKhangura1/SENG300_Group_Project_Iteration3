import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

import javax.swing.JButton;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;

import stationGUI.*;

public class GuiTest {
	
	boolean checkActionPerformed;
	boolean check=true;
	
	@Before
	public void setUp() throws Exception {
		
	}
		
	@Test
	public void testAddItemPanel_actionPerformed2() {
		
		
		JButton aButton= new  JButton("      Eggs      ");
		MainFrame mainFrame = new MainFrame();
		AddItemPanel addItemPanel= new AddItemPanel(mainFrame);
		
		//textField.getText() != "" && textField.getText().length() > 
		
		addItemPanel.btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkActionPerformed= true;
				
			}	
		});
		addItemPanel.itemScanned="4";
		addItemPanel.initComponents();
		
		aButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				 check = true;			
			}
			
		});
			String expected= "4";
			String actual= addItemPanel.itemScanned;
			
			assertEquals("4",actual);
			assertEquals(check,true);

		}

	@Test
	public void testAddItemPanel_actionPerformed3() {
		
		JButton aButton= new  JButton("     Black Beans      ");
		MainFrame mainFrame = new MainFrame();
		AddItemPanel addItemPanel= new AddItemPanel(mainFrame);
		
		//textField.getText() != "" && textField.getText().length() > 
		
		addItemPanel.btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkActionPerformed= true;
			}	
		});
		addItemPanel.itemScanned="5";
		aButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				boolean check= true;			
			}	
			
		});
		
			String expected= "5";
			String actual= addItemPanel.itemScanned;
			
			assertEquals("5",actual);

		}
	
	@Test
	public void testAttendantLoginPanel_actionPerformed() {
		
		JButton login= new  JButton("INVALID LOGIN");
		MainFrame mainFrame = new MainFrame();
		AttendantLoginPanel attendantLoginPanel = new AttendantLoginPanel(mainFrame);
		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkActionPerformed=true;		
			}
		});
		
		boolean expected = false;
		boolean actual= checkActionPerformed;
		assertEquals(expected, actual);
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
		
	
		
		
	
