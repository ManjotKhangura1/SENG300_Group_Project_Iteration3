import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.devices.*;
import org.lsmr.selfcheckout.devices.listeners.AbstractDeviceListener;
import org.lsmr.selfcheckout.devices.listeners.BarcodeScannerListener;
import org.lsmr.selfcheckout.products.BarcodedProduct;

public class ScanItemTest {
	SelfCheckoutStation station;
	BarcodedItem barcodedItem;
	Map<Barcode, BarcodedProduct> database;

	@Before
	public void setUp() throws Exception {
		//Creates a self checkout station and the components necessary to create it
		Currency currency = Currency.getInstance("CAD");
		int[] noteDenominations = {5,10,20,50,100};
		BigDecimal[] coinDenomonations = {BigDecimal.valueOf(0.05) , BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.25), 
				BigDecimal.valueOf(1.0), BigDecimal.valueOf(2.0)};
		int maxWeight = 23000;
		int scaleSensitivity = 10;
		station = new SelfCheckoutStation(currency, noteDenominations, coinDenomonations, maxWeight,scaleSensitivity);
		
		//Creates a barcoded item
		Barcode barcode = new Barcode("1");
		barcodedItem = new BarcodedItem(barcode, 50);
		BarcodedProduct product = new BarcodedProduct(barcode, "the only item we sell", BigDecimal.valueOf(10.50));
		
		database = new HashMap<>();
		database.put(barcode, product);
	}

	@After
	public void tearDown() throws Exception {
		station = null;
		barcodedItem = null;
		database = null;
	}

	@Test //test to make sure the constructor works as intended
	public void testScanItem() {
		
		//this test should pass as station is valid argument
		try {
			ScanItem scanner = new ScanItem(station, database);
		} catch(Exception e) {
			fail();
		}
		
		//the constructor should throw an exception as the selfCheckoutStation is invalid
		try {
			ScanItem scanner = new ScanItem(null, null);
			fail();
		} catch(Exception e) {
			assertTrue(e instanceof SimulationException);
		}
	}

	@Test //test to make sure scanning from main is working as intended
	public void testScanFromMain() {
		ScanItem scanner = new ScanItem(station, database);
		
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("1");
		
		for(int i = 0; i < 100; i++)
			scanner.scanFromMain(barcodedItem);
		
		assertTrue(scanner.getTotalWeight() > (barcodedItem.getWeight()*80));

		assertTrue(scanner.getTotalList().size() > 80);
		
		assertTrue(scanner.getTotalList().contains("1"));
		
		assertTrue(scanner.getTotalPrice() > (10.50 * 80));
		
		try {
			scanner.scanFromMain(null);
			fail();
		}catch (Exception e) {
			assertTrue(e instanceof SimulationException);
		}
		
		
	}
	
	@Test //test to make sure scanning from handheld is working as intended
	public void testScanFromHandheld() {
		ScanItem scanner = new ScanItem(station, database);
		
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("1");
		
		for(int i = 0; i < 100; i++)
			scanner.scanFromHandheld(barcodedItem);
		
		assertTrue(scanner.getTotalWeight() > (barcodedItem.getWeight()*80));

		assertTrue(scanner.getTotalList().size() > 80);
		
		assertTrue(scanner.getTotalList().contains("1"));
		
		assertTrue(scanner.getTotalPrice() > (10.50 * 80));
		
		try {
			scanner.scanFromHandheld(null);
			fail();
		}catch (Exception e) {
			assertTrue(e instanceof SimulationException);
		}
		
		
	}
	
	@Test
	public void testListeners() {
		ScanItem scanner = new ScanItem(station, database);
		station.mainScanner.enable();
		assertTrue(scanner.getIsEnabled());
		
		station.mainScanner.disable();
		assertFalse(scanner.getIsEnabled());
		
		station.handheldScanner.enable();
		assertTrue(scanner.getIsEnabled());
		
		station.handheldScanner.disable();
		assertFalse(scanner.getIsEnabled());
	}
	
}
