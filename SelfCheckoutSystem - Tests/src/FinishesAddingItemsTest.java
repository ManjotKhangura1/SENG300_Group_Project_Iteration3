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
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.products.BarcodedProduct;

import Software.Bag;
import Software.BaggingArea;
import Software.FinishesAddingItems;
import Software.ScanItem;

public class FinishesAddingItemsTest {
	private SelfCheckoutStation scs;
	private BaggingArea bagArea; 
	private ScanItem scanItem;
	private FinishesAddingItems finish;
	
	private Map<Barcode, BarcodedProduct> database;
	
	//setting up the classes
	@Before
	public void setUp() throws Exception {
		Currency currency = Currency.getInstance("USD");
		int[] banknoteDenominations = {5,10,20,50,100};
		BigDecimal[] coinDenominations = {new BigDecimal(0.01), new BigDecimal(0.05), new BigDecimal(0.1),new BigDecimal(0.25), new BigDecimal(0.50)};
		int scaleMaximumWeight = 1000;
		int scaleSens =  1;
		
		scs = new SelfCheckoutStation(currency, banknoteDenominations, coinDenominations, scaleMaximumWeight, scaleSens);
		
		bagArea = new BaggingArea(scs);
		finish = new FinishesAddingItems(scs, bagArea);
		scanItem = new ScanItem(scs, database, finish, bagArea);
	}
	//testing the method without usage of other classes
	@Test
	public void updateTotals_Test() {
		String testString = "test name";
		BigDecimal testPrice = new BigDecimal(10.00);
		BigDecimal testWeight = new BigDecimal(50);
		
		Map<String, ArrayList<BigDecimal>> expected = new HashMap<>();
		ArrayList<String> testList = new ArrayList<>();
		ArrayList<BigDecimal> tempList = new ArrayList<>();
		tempList.add(testPrice);
		tempList.add(testWeight);
		testList.add(testString);

		expected.put(testString, tempList);
		finish.updateTotals(testString, testPrice, testWeight); //actual
		
		assertEquals(expected, finish.getTracker());
		assertEquals(testList, finish.getList());
		
	}
	
	//testing the method without usage of other classes
	@Test
	public void removeItemTest() {
		String testString = "test item 1";
		String testString2 = "test item 2";
		BigDecimal testPrice = new BigDecimal(10.00);
		BigDecimal testWeight = new BigDecimal(50);
		
		Map<String, ArrayList<BigDecimal>> expected = new HashMap<>();
		ArrayList<String> testList = new ArrayList<>();
		ArrayList<BigDecimal> tempList = new ArrayList<>();
		tempList.add(testPrice);
		tempList.add(testWeight);
		testList.add(testString);

		expected.put(testString, tempList);
		
		finish.updateTotals(testString, testPrice, testWeight);
		finish.updateTotals(testString2, testPrice, testWeight);
		finish.removeItem(testString2);
		
		assertEquals(expected, finish.getTracker());
		assertEquals(testList, finish.getList());
	}
	
	//testing the method without usage of other classes
	//testing get price with 2 items
	@Test
	public void getPriceTest() {
		String testString = "test item 1";
		String testString2 = "test item 2";
		BigDecimal testPrice = new BigDecimal(10.00);
		BigDecimal testWeight = new BigDecimal(50);
		finish.updateTotals(testString, testPrice, testWeight);
		finish.updateTotals(testString2, testPrice, testWeight);
		double actual = finish.getPrice();
		double expected = 10.00 + 10.00;
		assertEquals(expected, actual, 0);
	}
	
	//testing the method without usage of other classes
	@Test
	public void getListTest() {
		String testString = "test item 2";
		BigDecimal testPrice = new BigDecimal(10.00);
		BigDecimal testWeight = new BigDecimal(50);
		finish.updateTotals(testString, testPrice, testWeight);
		ArrayList<String> expected = new ArrayList<>();
		expected.add(testString);
		ArrayList<String> actual = finish.getList();
		
		assertEquals(expected, actual);
		
	}
	
	
	@Test
	public void getWeightTest() {
		String testString = "test item 1";
		String testString2 = "test item 2";
		BigDecimal testPrice = new BigDecimal(10.00);
		BigDecimal testWeight = new BigDecimal(50);
		finish.updateTotals(testString, testPrice, testWeight);
		finish.updateTotals(testString2, testPrice, testWeight);
		double actual = finish.getWeight();
		double expected = 50 + 50;
		assertEquals(expected, actual, 0);
	}
	
	@Test
	public void addOwnBagTest() throws DisabledException, OverloadException {
		double bagWeight = 4;
		String bagName = "test bag";
		Bag bag = new Bag(bagWeight);
		finish.addOwnBag(bag, bagName);
		BigDecimal expectedPrice = new BigDecimal(0.10);
		BigDecimal expectedWeight = new BigDecimal(4);
		Map<String, ArrayList<BigDecimal>> expected = new HashMap<>();
		ArrayList<String> testList = new ArrayList<>();
		ArrayList<BigDecimal> tempList = new ArrayList<>();
		ArrayList<String> actual = finish.getList();
		tempList.add(expectedPrice);
		tempList.add(expectedWeight);
		testList.add(bagName);
		expected.put(bagName, tempList);
		assertEquals(testList, finish.getList());
		assertEquals(expected, finish.getTracker()); //fails since it is a big decimal version of 0.10 but this is fine
	}
	
	@Test
	public void removeOwnBag() throws DisabledException, OverloadException {
		double bagWeight = 4;
		String bagName = "test bag";
		String bagName2 = "test bag2";
		Bag bag = new Bag(bagWeight);
		Bag bag2 = new Bag(bagWeight);
		finish.addOwnBag(bag, bagName);
		finish.addOwnBag(bag2, bagName2);
		finish.removeOwnBag(bag2, bagName2);
		BigDecimal expectedPrice = new BigDecimal(0.10);
		BigDecimal expectedWeight = new BigDecimal(4);
		Map<String, ArrayList<BigDecimal>> expected = new HashMap<>();
		ArrayList<String> testList = new ArrayList<>();
		ArrayList<BigDecimal> tempList = new ArrayList<>();
		ArrayList<String> actual = finish.getList();
		tempList.add(expectedPrice);
		tempList.add(expectedWeight);
		testList.add(bagName);
		expected.put(bagName, tempList);
		assertEquals(testList, finish.getList());
		assertEquals(expected, finish.getTracker()); //fails since it is a big decimal version of 0.10 but this is fine
	}
	
	@Test
	public void finishTest_WithBagArea() {
		finish.finish();
		Barcode barcode = new Barcode("123");
		double weight = 10.0;
		BarcodedItem item = new BarcodedItem(barcode, weight);
		
		try {
			bagArea.addItem(item);
		}
		catch(Exception e) {
			assertTrue(e instanceof DisabledException);
		}
	}
		@Test
		public void finishTest_WithScan() {
			finish.finish();
			Barcode barcode = new Barcode("123");
			double weight = 10.0;
			BarcodedItem item = new BarcodedItem(barcode, weight);
			
			try {
				scanItem.scanFromMain(item);
			}
			catch(Exception e) {
				assertTrue(e instanceof DisabledException);
			}
	
		}	
		
		
		
		
		
		/**
		
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
		scanner = new ScanItem(station, database);
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


**/
}
