import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;

public class ReturnsToAddingItemsTest {
	
	//parameters for instance of valid SelfCheckoutStation
	Currency currency= Currency.getInstance("CAD");
	int[] bankNoteDenominations= {5,10,20,50,100};
	BigDecimal[] coinDenominations= {new BigDecimal(0.01), new BigDecimal(0.05),
			 new BigDecimal(0.1),new BigDecimal(0.25), new BigDecimal(0.50)};
	int scaleMaximumWeight= 1000;
	int scaleSensitivity=1;
	
	SelfCheckoutStation validStation = new SelfCheckoutStation(currency, bankNoteDenominations,
			coinDenominations, scaleMaximumWeight, scaleSensitivity);
	
	ReturnsToAddingItems return1 = new ReturnsToAddingItems(validStation);
	
	@Test 
	public void testMainScanner() {
		
		return1.enableRequiredHardware();
		
		boolean expectedMainScanner=false;
		boolean actualMainScanner= validStation.mainScanner.isDisabled();
		
		assertEquals(expectedMainScanner, actualMainScanner);
	}
	
	
	
	@Test
	public void testHandheldScanner() {
		
		return1.enableRequiredHardware();
		
		boolean expectedMainScanner=false;
		boolean actualMainScanner= validStation.handheldScanner.isDisabled();
		
		assertEquals(expectedMainScanner, actualMainScanner);
		
	}
	
	@Test
	public void testScale_WithValidStation() {
		
		return1.enableRequiredHardware();
		
		boolean expectedScale=false;
		boolean actualScale= validStation.baggingArea.isDisabled();
		
		assertEquals(expectedScale, actualScale);
		
	}
	
	@Test 
	public void testMainScanner_WithInValidStation() {
		
		return1.enableRequiredHardware();
		
		boolean expectedMainScanner=true;
		boolean actualMainScanner= validStation.mainScanner.isDisabled();
		
		assertEquals(expectedMainScanner, actualMainScanner);
	}
	
	
}
