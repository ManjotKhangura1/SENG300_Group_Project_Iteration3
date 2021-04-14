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
import org.lsmr.selfcheckout.products.BarcodedProduct;

import Software.BaggingArea;
import Software.FinishesAddingItems;
import Software.ScanItem;

public class ScanItemTest {
	SelfCheckoutStation station;
	BarcodedItem barcodedItem;
	Map<Barcode, BarcodedProduct> database;
	FinishesAddingItems done;
	BaggingArea bags;
	
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
		
		//create BaggingArea
		bags = new BaggingArea(station);
		
		//create controler FinishesAddingItems
		done = new FinishesAddingItems(station, bags);
		
		//Creates a barcoded item
		Barcode barcode = new Barcode("1");
		barcodedItem = new BarcodedItem(barcode, 50);
		BarcodedProduct product = new BarcodedProduct(barcode, "1", BigDecimal.valueOf(10.50));
		
		database = new HashMap<>();
		database.put(barcode, product);
		
	}

	@After
	public void tearDown() throws Exception {
		station = null;
		barcodedItem = null;
		database = null;
		done = null;
		bags = null;
	}

	@Test //test to make sure the constructor works as intended
	public void testScanItem() {
		
		//this test should pass as station is valid argument
		try {
			ScanItem scanner = new ScanItem(station, database, done, bags);
		} catch(Exception e) {
			fail();
		}
		
		//the constructor should throw an exception as the selfCheckoutStation is invalid
		try {
			ScanItem scanner = new ScanItem(null, null, null, null);
			fail();
		} catch(Exception e) {
			assertTrue(e instanceof SimulationException);
		}
	}

	@Test //test to make sure scanning from main is working as intended
	public void testScanFromMain() {
		ScanItem scanner = new ScanItem(station, database, done, bags);
		
		for(int i = 0; i < 100; i++)
			scanner.scanFromMain(barcodedItem);
		
		System.out.println(done.getWeight());
		assertTrue(done.getWeight() > (barcodedItem.getWeight()*70)); //test weight

		assertTrue(done.getList().size() > 70); //test length
		
		assertTrue(done.getList().contains("1")); //test item names
		
		assertTrue(done.getPrice() > (10.50 * 80)); //test price
		
		try {
			scanner.scanFromMain(null); 
			fail();
		}catch (Exception e) {
			assertTrue(e instanceof SimulationException);
		}
		
		
	}
	
	@Test //test to make sure scanning from handheld is working as intended
	public void testScanFromHandheld() {
		ScanItem scanner = new ScanItem(station, database, done, bags);
		
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("1");
		
		for(int i = 0; i < 100; i++)
			scanner.scanFromHandheld(barcodedItem);
		
		assertTrue(done.getWeight() > (barcodedItem.getWeight()*80)); //test weight

		assertTrue(done.getList().size() > 80); //test length
		
		assertTrue(done.getList().contains("1")); //test item names
		
		assertTrue(done.getPrice() > (10.50 * 80)); //test price
		
		try {
			scanner.scanFromHandheld(null);
			fail();
		}catch (Exception e) {
			assertTrue(e instanceof SimulationException);
		}
		
		
	}
	
	@Test
	public void testListeners() {
		ScanItem scanner = new ScanItem(station, database, done, bags);
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
