import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.PLUCodedItem;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;

public class AddPLUItemTest {
	
	private SelfCheckoutStation station;
	private BarcodedItem barcodedItem;
	private DeclineBagPrompt prompt;
	private PLUCodedItem PLUItem;
	private PLUCodedProduct pluProduct;
	private AddPLUItem api;
	private PriceLookupCode plu;
	private double price = 10.50;
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
		station = new SelfCheckoutStation(currency, noteDenominations, coinDenomonations, maxWeight,scaleSensitivity);
		
		prompt = new DeclineBagPrompt();
		
		//PriceLookupCode plu = new PriceLookupCode("12345");
		//PLUItem = new PLUCodedItem(plu, 30);
		//PLUCodedProduct pluProduct = new PLUCodedProduct(plu, "apples", BigDecimal.valueOf(price));
	}
	
	@Test
	public void testNullConstructor() {
		//the constructor should throw an exception as the selfCheckoutStation is invalid
		try {
			api = new AddPLUItem(null, database);
			
		} catch(Exception e) {
			assertTrue(e instanceof SimulationException);
		}
	}
	
	@Test
	public void testAddNullItem() {
		api = new AddPLUItem(station, database);
		
		try {
			plu = new PriceLookupCode(null);
			PLUItem = new PLUCodedItem(plu, 0);
		}catch(Exception e) {
			assertTrue(e instanceof SimulationException);
		}
		
		try {
			api.addItem(PLUItem);
		} catch(Exception e) {
			assertTrue(e instanceof SimulationException);
		}
	}
	
	@Test
	public void testAddPLUItem() {
		api = new AddPLUItem(station, database);
		
		plu = new PriceLookupCode("14040");
		PLUItem = new PLUCodedItem(plu, 5.00);
		pluProduct = new PLUCodedProduct(plu, "apples", BigDecimal.valueOf(price));
		database = new HashMap<>();
		database.put(plu, pluProduct);
		
		try {
			api.addItem(PLUItem);
		}catch(Exception e) {
		}
	}

}
