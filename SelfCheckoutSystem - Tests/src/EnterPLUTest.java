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
import Software.BaggingArea;
import Software.ScanItem;
import Software.FinishesAddingItems;

public class EnterPLUTest {
	SelfCheckoutStation station;
	Map<PriceLookupCode, PLUCodedProduct> database;
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
			PLUCodedProduct product = new PLUCodedProduct(plu, "Red Apples", BigDecimal.valueOf(1.50));
			
			PriceLookupCode plu1 = new PriceLookupCode("2222");
			PLUCodedProduct product1 = new PLUCodedProduct(plu1, "Green Grapes", BigDecimal.valueOf(1.50));
			
			database = new HashMap<>();
			database.put(plu, product);
			database.put(plu1, product1);
			
			finishAddingItems = new FinishesAddingItems(station, baggingArea);
		
	}

	@After
	public void tearDown() throws Exception {
		station = null;
		database = null;
	}

	@Test
	public void testPLUConstructor() {
		
		try {
			EnterPLU test = new EnterPLU(station, database, finishAddingItems, baggingArea);
		} catch(Exception e) {
			fail();
		}
		
		//the constructor should throw an exception as the selfCheckoutStation is invalid
		try {
			EnterPLU test = new EnterPLU(null, null, null, null);
			fail();
		} catch(Exception e) {
			assertTrue(e instanceof SimulationException);
		}
		
	}
	
	@Test
	public void testNullInput() {
		
		EnterPLU test = new EnterPLU(station, database, finishAddingItems, baggingArea);
		
		try {
			station.scale.add(new PLUCodedItem(new PriceLookupCode("1234"),5.0)); //item 1234 weighs 5kg
			test.itemLookup(null);
		}catch(Exception e) {
			assertTrue(e instanceof SimulationException);
		}
		
	}
	
	@Test
	public void testPLUInput() {
		
		EnterPLU test = new EnterPLU(station, database, finishAddingItems, baggingArea);
		
		try {
			station.scale.add(new PLUCodedItem(new PriceLookupCode("1234"),5.0)); //item 1234 weighs 5kg
			test.itemLookup("1234");
			station.scale.add(new PLUCodedItem(new PriceLookupCode("2222"),3.0)); //item 2222 weighs 3kg
			test.itemLookup("2222");
		}catch(Exception e) {
			fail();
		}
		
	}
	
	@Test
	public void testItemWeightOverload() {
		
		EnterPLU test = new EnterPLU(station, database, finishAddingItems, baggingArea);
		
		try {
			station.scale.add(new PLUCodedItem(new PriceLookupCode("1234"),23001.0)); //item 1234 weighs 23001kg
			test.itemLookup("1234");
		}catch(Exception e) {
			assertTrue(e instanceof OverloadException);
		}
		
	}
	
	@Test
	public void testIncorrectPLU() {
		
		EnterPLU test = new EnterPLU(station, database, finishAddingItems, baggingArea);
		
		try {
			station.scale.add(new PLUCodedItem(new PriceLookupCode("1234"),51.0)); //item 1234 weighs 51kg
			test.itemLookup("5555");
		}catch(Exception e) {
			assertTrue(e instanceof SimulationException);
		}
		
	}
	

}
