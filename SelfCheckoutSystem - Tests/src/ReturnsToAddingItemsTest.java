import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;

import Software.ReturnsToAddingItems;

public class ReturnsToAddingItemsTest {
	
	/*
	 * Parameters for instance of valid SelfCheckoutStation
	 */
	Currency currency= Currency.getInstance("CAD");
	Currency invalidCurrency= Currency.getInstance("USD");
	
	int[] bankNoteDenominations= {5,10,20,50,100};
	BigDecimal[] coinDenominations= {new BigDecimal(0.01), new BigDecimal(0.05),
			 new BigDecimal(0.1),new BigDecimal(0.25), new BigDecimal(0.50)};
	int scaleMaximumWeight= 1000;
	int scaleSensitivity=1;
	
	/*
	 * Instances of valid and invalid stations and of the class we are testing
	 */
	SelfCheckoutStation validStation = new SelfCheckoutStation(currency, bankNoteDenominations,
			coinDenominations, scaleMaximumWeight, scaleSensitivity);
	
	ReturnsToAddingItems return1 = new ReturnsToAddingItems(validStation);
	
	SelfCheckoutStation invalidStation = new SelfCheckoutStation(invalidCurrency, bankNoteDenominations,
			coinDenominations, scaleMaximumWeight, scaleSensitivity);
	
	ReturnsToAddingItems return2 = new ReturnsToAddingItems(invalidStation);
	
	
	/*
	 * Testing whether the main scanner gets enabled when it it required or not
	 */
	
	@Test 
	public void testMainScanner_WithValidStation() {
		
		return1.enableRequiredHardware();
		
		boolean expectedMainScannerDisabled=false;
		boolean actualMainScannerDisabled= validStation.mainScanner.isDisabled();
		
		assertEquals(expectedMainScannerDisabled, actualMainScannerDisabled);
	}
		
	/*
	 * Testing whether the hand-held scanner gets enabled when it it required or not
	 */
	
	@Test
	public void testHandheldScanner_WithValidStation() {
		
		return1.enableRequiredHardware();
		
		boolean expectedHandheldScannerDisabled=false;
		boolean actualHandheldScannerDisabled= validStation.handheldScanner.isDisabled();
		
		assertEquals(expectedHandheldScannerDisabled, actualHandheldScannerDisabled);
		
	}
	
	
	/*
	 * Testing whether the bagging area gets enabled when it it required or not
	 */
	
	@Test
	public void testBaggingArea_WithValidStation() {
		
		return1.enableRequiredHardware();
		
		boolean expectedBaggingAreaDisabled=false;
		boolean actualBaggingAreaDisabled= validStation.baggingArea.isDisabled();
		
		assertEquals(expectedBaggingAreaDisabled, actualBaggingAreaDisabled);
		
	}
	
	
	/*
	 * Testing whether the scale gets enabled when it it required or not
	 */
	
	@Test
	public void testScale_WithValidStation() {
		
		return1.enableRequiredHardware();
		
		boolean expectedScaleDisabled=false;
		boolean actualScaleDisabled= validStation.baggingArea.isDisabled();
		
		assertEquals(expectedScaleDisabled, actualScaleDisabled);
		
	}
	
	
	/*
	 * The hardware should not get enabled if an invalid station or null station is passed
	 */
	
	@Test 
	public void testEnabledRequiredHardware_WithNullStation() {
		
		return1.enableRequiredHardware();
		
		boolean expectedMainScannerEnabled=false;
		boolean actualMainScannerEnabled= return2.isEnabled;
		
		assertEquals(expectedMainScannerEnabled, actualMainScannerEnabled);
	}
	
	/*
	 * Testing whether an item gets scanned properly after customer decides to
	 * return to adding items
	 */
	@Test
	public void testItemGetsAddedProperly() {
		
		Barcode testBarcode = new Barcode("0001");
		BarcodedItem testItem = new BarcodedItem(testBarcode, 5);
		
		validStation.mainScanner.scan(testItem);
		
		boolean expectedisScanned=true;
		boolean actualisScanned= return1.isScanned;
		
		assertEquals(expectedisScanned, actualisScanned);
		
	}
	
	/*
	 * test throwing an exception if station is null
	 */
	@Test
	public void testNullStation() {
		try {
			
			return1 = new ReturnsToAddingItems(null);
		}catch(Exception e) {
			assertTrue(e instanceof SimulationException);
		}
	}
	
}
