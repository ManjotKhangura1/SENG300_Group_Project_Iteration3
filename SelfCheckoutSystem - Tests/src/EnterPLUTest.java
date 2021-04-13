import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.PLUCodedItem;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;

import Software.EnterPLU;

public class EnterPLUTest {
	SelfCheckoutStation station;
	Map<PriceLookupCode, PLUCodedProduct> database;

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
			
		//Creates a barcoded item
			PriceLookupCode plu = new PriceLookupCode("1234");
			PLUCodedProduct product = new PLUCodedProduct(plu, "Red Apples", BigDecimal.valueOf(1.50));
			
			PriceLookupCode plu1 = new PriceLookupCode("2222");
			PLUCodedProduct product1 = new PLUCodedProduct(plu1, "Green Grapes", BigDecimal.valueOf(1.50));
			
			database = new HashMap<>();
			database.put(plu, product);
			database.put(plu1, product1);
		
	}

	@After
	public void tearDown() throws Exception {
		station = null;
		database = null;
	}

	@Test
	public void testEnterPLU() {
		
	}

	@Test
	public void testItemLookup() {
		EnterPLU test = new EnterPLU(station, database);
		try {
		station.scale.add(new PLUCodedItem(new PriceLookupCode("1234"),5.0)); //item 1234 weighs 5kg
		test.itemLookup("1234");
		station.scale.add(new PLUCodedItem(new PriceLookupCode("2222"),5.0)); //item 2222 weighs 5kg
		test.itemLookup("2222");
		}catch(Exception e) {
			fail();
		} 
		
		//looking up an invalid plu
		try {
			station.scale.add(new PLUCodedItem(new PriceLookupCode("1234"),5.0)); //item 1234 weighs 5kg
			test.itemLookup("1111");
			fail();
		}catch(Exception e) {
			assertTrue(e instanceof SimulationException);
		}
		
		//over the weight limit
		try {
			station.scale.add(new PLUCodedItem(new PriceLookupCode("1234"),23001)); 
			test.itemLookup("1234");
			fail();
		}catch(Exception e) {
			assertTrue(e instanceof OverloadException); 
		}
		
		//looking up a null plu
		try {
			test.itemLookup(null);
			fail();
		}catch(Exception e) {
			assertTrue(e instanceof SimulationException);
		}
	}

	@Test
	public void testGetPrice() {
		//PLUproduct has plu = 1234 and price of 1.5
		EnterPLU test = new EnterPLU(station, database);
		try {
		station.scale.add(new PLUCodedItem(new PriceLookupCode("1234"),5.0)); //item 1234 weighs 5kg
		test.itemLookup("1234");
		
		assertEquals(test.getPrice(), BigDecimal.valueOf(station.scale.getCurrentWeight()).multiply(BigDecimal.valueOf(1.5))); //we must mulitply price by weight
		}catch(Exception e) {
			fail();
		}
	}

	@Test
	public void testGetDescription() {
		//PLUproduct has plu = 1234 and price of 1.5
		EnterPLU test = new EnterPLU(station, database);
		try {
		station.scale.add(new PLUCodedItem(new PriceLookupCode("1234"),5.0)); //item 1234 weighs 5kg
		test.itemLookup("1234");
		
		assertEquals(test.getDescription(), "Red Apples"); 
		}catch(Exception e) {
			fail();
		}
	}

}
