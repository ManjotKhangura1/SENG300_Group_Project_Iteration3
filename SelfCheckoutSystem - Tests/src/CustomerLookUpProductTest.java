import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.PLUCodedItem;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.products.PLUCodedProduct;

import Software.BaggingArea;
import Software.CustomerLookUpProduct;
import Software.EnterPLU;
import Software.FinishesAddingItems;
import Software.ScanItem;

public class CustomerLookUpProductTest {
	
	SelfCheckoutStation station;
	Map<String, PLUCodedProduct> database;
	FinishesAddingItems finishAddingItems;
	BaggingArea baggingArea;
	ScanItem scanItem;
	
	@Before
	public void setUp() throws Exception {
		
		//Creates a self checkout station and the components necessary to create it
			Currency currency = Currency.getInstance("CAD");
			int[] noteDenominations = {5,10,20,50,100};
			BigDecimal[] coinDenomonations = {BigDecimal.valueOf(0.05) , BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.25), 
					BigDecimal.valueOf(1.0), BigDecimal.valueOf(2.0)};
			int maxWeight = 23000;
			int scaleSensitivity = 10;
			station = new SelfCheckoutStation(currency, noteDenominations, coinDenomonations, maxWeight, scaleSensitivity);
			//scanItem = new ScanItem(station, Map<Barcode, BarcodedProduct> d, );
			baggingArea = new BaggingArea(station);
			
			
		//Creates a barcoded item
			PriceLookupCode plu = new PriceLookupCode("1234");
			PLUCodedProduct product = new PLUCodedProduct(plu, "apples", BigDecimal.valueOf(2.50));
			
			PriceLookupCode plu1 = new PriceLookupCode("2222");
			PLUCodedProduct product1 = new PLUCodedProduct(plu1, "grapes", BigDecimal.valueOf(1.50));
			
			PriceLookupCode plu2 = new PriceLookupCode("1111");
			PLUCodedProduct product2 = new PLUCodedProduct(plu, "bananas", BigDecimal.valueOf(1.00));
			
			PriceLookupCode plu3 = new PriceLookupCode("4321");
			PLUCodedProduct product3 = new PLUCodedProduct(plu1, "oranges", BigDecimal.valueOf(2.00));
			
			database = new HashMap<>();
			database.put("apples", product);
			database.put("grapes", product1);
			database.put("bananas", product2);
			database.put("oranges", product3);
			
			finishAddingItems = new FinishesAddingItems(station, baggingArea);
		
	}
	
	@After
	public void tearDown() throws Exception {
		station = null;
		database = null;
	}
	
	@Test
	public void testLookUpConstructor() {
		
		try {
			CustomerLookUpProduct test = new CustomerLookUpProduct(station, database, finishAddingItems, baggingArea);
		} catch(Exception e) {
			fail();
		}
		
		//the constructor should throw an exception as the selfCheckoutStation is invalid
		try {
			CustomerLookUpProduct test = new CustomerLookUpProduct(null, null, null, null);
			fail();
		} catch(Exception e) {
			assertTrue(e instanceof SimulationException);
		}
		
	}
	
	@Test
	public void testNullInput() {
		
		CustomerLookUpProduct test = new CustomerLookUpProduct(station, database, finishAddingItems, baggingArea);
		
		try {
			test.Lookup(null);
		}catch(Exception e) {
			assertTrue(e instanceof SimulationException);
		}
		
	}
	
	@Test
	public void testLookupInput() {
		
		CustomerLookUpProduct test = new CustomerLookUpProduct(station, database, finishAddingItems, baggingArea);
		
		try {
			station.scale.add(new PLUCodedItem(new PriceLookupCode("1234"),5.0)); //item 1234 weighs 5kg
			test.Lookup("apples");
			station.scale.add(new PLUCodedItem(new PriceLookupCode("2222"),2.0)); //item 2222 weighs 2kg
			test.Lookup("grapes");
			station.scale.add(new PLUCodedItem(new PriceLookupCode("1111"),3.0)); //item 1111 weighs 3kg
			test.Lookup("bananas");
			station.scale.add(new PLUCodedItem(new PriceLookupCode("4321"),1.0)); //item 4321 weighs 1kg
			test.Lookup("oranges");
		}catch(Exception e) {
			fail();
		}
		
	}
	
	@Test
	public void testItemWeightOverload() {
		
		CustomerLookUpProduct test = new CustomerLookUpProduct(station, database, finishAddingItems, baggingArea);
		
		try {
			station.scale.add(new PLUCodedItem(new PriceLookupCode("2222"), 23001.0)); //item 1234 weighs 23001kg
			test.Lookup("grapes");
		}catch(Exception e) {
			assertTrue(e instanceof OverloadException);
		}
		
	}
	
	@Test
	public void testIncorrectPLU() {
		
		CustomerLookUpProduct test = new CustomerLookUpProduct(station, database, finishAddingItems, baggingArea);
		
		try {
			station.scale.add(new PLUCodedItem(new PriceLookupCode("1234"),4.0)); //item 1234 weighs 4kg
			test.Lookup("kiwi");
		}catch(Exception e) {
			assertTrue(e instanceof SimulationException);
		}
		
	}
}
