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
	Currency invalidCurrency= Currency.getInstance("USD");
	
	int[] bankNoteDenominations= {5,10,20,50,100};
	BigDecimal[] coinDenominations= {new BigDecimal(0.01), new BigDecimal(0.05),
			 new BigDecimal(0.1),new BigDecimal(0.25), new BigDecimal(0.50)};
	int scaleMaximumWeight= 1000;
	int scaleSensitivity=1;
	
	SelfCheckoutStation validStation = new SelfCheckoutStation(currency, bankNoteDenominations,
			coinDenominations, scaleMaximumWeight, scaleSensitivity);
	
	ReturnsToAddingItems return1 = new ReturnsToAddingItems(validStation);
	
	SelfCheckoutStation invalidStation = new SelfCheckoutStation(invalidCurrency, bankNoteDenominations,
			coinDenominations, scaleMaximumWeight, scaleSensitivity);
	
	ReturnsToAddingItems return2 = new ReturnsToAddingItems(invalidStation);
	
	
	@Test 
	public void testMainScanner_WithValidStation() {
		
		return1.enableRequiredHardware(validStation);
		
		boolean expectedMainScannerDisabled=false;
		boolean actualMainScannerDisabled= validStation.mainScanner.isDisabled();
		
		assertEquals(expectedMainScannerDisabled, actualMainScannerDisabled);
	}
		
	@Test
	public void testHandheldScanner_WithValidStation() {
		
		return1.enableRequiredHardware(validStation);
		
		boolean expectedHandheldScannerDisabled=false;
		boolean actualHandheldScannerDisabled= validStation.handheldScanner.isDisabled();
		
		assertEquals(expectedHandheldScannerDisabled, actualHandheldScannerDisabled);
		
	}
	
	@Test
	public void testBaggingArea_WithValidStation() {
		
		return1.enableRequiredHardware(validStation);
		
		boolean expectedBaggingAreaDisabled=false;
		boolean actualBaggingAreaDisabled= validStation.baggingArea.isDisabled();
		
		assertEquals(expectedBaggingAreaDisabled, actualBaggingAreaDisabled);
		
	}
	
	@Test
	public void testScale_WithValidStation() {
		
		return1.enableRequiredHardware(validStation);
		
		boolean expectedScaleDisabled=false;
		boolean actualScaleDisabled= validStation.baggingArea.isDisabled();
		
		assertEquals(expectedScaleDisabled, actualScaleDisabled);
		
	}
	
	@Test 
	public void testMainScanner_WithNullStation() {
		
		return1.enableRequiredHardware(null);
		
		boolean expectedMainScannerEnabled=false;
		boolean actualMainScannerEnabled= return2.isEnabled;
		
		assertEquals(expectedMainScannerEnabled, actualMainScannerEnabled);
	}
	
	
}
