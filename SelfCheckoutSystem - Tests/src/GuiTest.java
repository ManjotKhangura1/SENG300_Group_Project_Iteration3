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

import stationGUI.AddItemPanel;
import stationGUI.MainFrame;

public class GuiTest {
	
	boolean checkActionPerformed;
	
	@Before
	public void setUp() throws Exception {
		
		checkActionPerformed=false;
	}
		
	@Test
	public void testAddItemPanel_actionPerformed1() {
		
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
		
		aButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				boolean check= true;			
			}
		});
		
			String expected= "0";
			String actual= addItemPanel.itemScanned;
			
			assertEquals("0",actual);
	
		}
		
		
		
	
		
}
		
	
		
		
	
