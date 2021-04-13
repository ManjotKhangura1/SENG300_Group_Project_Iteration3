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
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.products.BarcodedProduct;

import Software.BaggingArea;
import Software.DeclineBagPrompt;
import Software.FinishesAddingItems;
import Software.ScanItem;

public class FinishesAddingItemsTest {
	public SelfCheckoutStation station;
	public BarcodedItem barcodedItem;
	public Map<Barcode, BarcodedProduct> database;
	public ScanItem scanner;
	public BaggingArea bags;
	public DeclineBagPrompt bagPrompt;

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
		
		//create database
		database = new HashMap<>();
		database.put(barcode, product);
		
		//create scanner
		bagPrompt = new DeclineBagPrompt();
		scanner = new ScanItem(station, database, bagPrompt);
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("1");
		
		for(int i = 0; i < 100; i++)
			scanner.scanFromMain(barcodedItem, false); //should have an 80% pass rate

		//create bagging area
		bags = new BaggingArea(station);
	}

	@After
	public void tearDown() throws Exception {
		station = null;
		barcodedItem = null;
		database = null;
	}

	@Test //test the constructor
	public void testFinishesAddingItems() {
		//the constructor should disable the scanner and bagging area
		FinishesAddingItems finished = new FinishesAddingItems(station, scanner, bags);
		
		assertFalse(scanner.getIsEnabled());
		assertFalse(bags.getIsEnabled());
		
		try { //station should not be null
			FinishesAddingItems finished1 = new FinishesAddingItems(null, scanner, bags);
			fail();
		}catch(Exception e) {
			assertTrue(e instanceof SimulationException);
		}
		
		try { //scanner should not be null
			FinishesAddingItems finished2 = new FinishesAddingItems(station, null, bags);
			fail();
		}catch(Exception e) {
			assertTrue(e instanceof SimulationException);
		}
		
		try { //bagging area should not be null
			FinishesAddingItems finished3 = new FinishesAddingItems(station, scanner, null);
			fail();
		}catch(Exception e) {
			assertTrue(e instanceof SimulationException);
		}
	}

	@Test
	public void testGetPrice() {
		FinishesAddingItems finished = new FinishesAddingItems(station, scanner, bags);
		
		assertTrue(finished.getPrice() >= (10.50 * 80));
		assertFalse(finished.getPrice() < (10.50 * 80)); // scanner should have an 80% success rate and the item price is 10.50
	}

	@Test
	public void testGetList() {
		FinishesAddingItems finished = new FinishesAddingItems(station, scanner, bags);
		
		assertTrue(finished.getList().contains("1")); //item barcode name is "1"
		assertTrue(finished.getList().size() >= 80); //there should be more then 80 items
		assertFalse(finished.getList().size() < 80); //there should not be less them 80 items
	}

}
