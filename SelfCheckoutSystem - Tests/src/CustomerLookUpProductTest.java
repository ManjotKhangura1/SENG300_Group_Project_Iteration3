import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.products.PLUCodedProduct;

import Software.CustomerLookUpProduct;

public class CustomerLookUpProductTest {

	public static Map<PriceLookupCode, PLUCodedProduct> database = new HashMap<>();

	@Test
	public void testCustomerLookUpProduct() {
		
		PriceLookupCode plu1 = new PriceLookupCode("14040");
		PriceLookupCode plu2 = new PriceLookupCode("97310");
		PriceLookupCode plu3 = new PriceLookupCode("55589");
		PriceLookupCode plu4 = new PriceLookupCode("30897");
		
		
		PLUCodedProduct apples = new PLUCodedProduct(plu1, "Fresh organic apples!", new BigDecimal("3.50"));
		PLUCodedProduct grapes = new PLUCodedProduct(plu2, "Sweet Grapes that taste like cotton candy!", new BigDecimal("7.85"));
		PLUCodedProduct kobe = new PLUCodedProduct(plu3, "Kobe beef from Japan!", new BigDecimal("54.77"));
		PLUCodedProduct fries = new PLUCodedProduct(plu4, "Fries that taste like MCD!", new BigDecimal("7.50"));
		
		database.put(plu1, apples); 
		database.put(plu2, grapes); 
		database.put(plu3, kobe);
		database.put(plu4, fries);
		
		CustomerLookUpProduct look = new CustomerLookUpProduct(database);
		PLUCodedProduct applesLookup = look.searchProductInPLUDatabase(plu1);
		
		assertEquals(applesLookup, apples); 
		
	}
	
	@Test
	public void testCustomerLookUpProductException() {
		try {
			PriceLookupCode plu1 = new PriceLookupCode("14040");
			CustomerLookUpProduct look = new CustomerLookUpProduct(database);
			PLUCodedProduct nonExistentLookup = look.searchProductInPLUDatabase(plu1);
		
		}catch(Exception e) {
			assertEquals(e.getMessage(),"There is no product with this PLU code" );
			
		}
	}
	
	@Test
	public void testGetPriceOfProduct() {
		PriceLookupCode plu1 = new PriceLookupCode("14040");
		PLUCodedProduct apples = new PLUCodedProduct(plu1, "Fresh organic apples!", new BigDecimal("3.50"));
		database.put(plu1, apples); 
		CustomerLookUpProduct look = new CustomerLookUpProduct(database);
		BigDecimal applesPrice = look.getPriceOfProduct(plu1); 
		
		assertEquals(applesPrice, new BigDecimal("3.50")); 
	}
	
	@Test(expected=SimulationException.class)
	public void testGetPriceOfProductException() {
		try {
			PriceLookupCode plu1 = new PriceLookupCode("99999"); 
			CustomerLookUpProduct look = new CustomerLookUpProduct(database);
			BigDecimal nonExistentLookup = look.getPriceOfProduct(plu1); 
		
		}catch(Exception e) {
			throw new SimulationException(new NullPointerException("Error"));
		}
	}
	@Test
	public void testGetDescriptionOfProduct() {
		PriceLookupCode plu1 = new PriceLookupCode("14040");
		PLUCodedProduct apples = new PLUCodedProduct(plu1, "Fresh organic apples!", new BigDecimal("3.50"));
		database.put(plu1, apples); 
		CustomerLookUpProduct look = new CustomerLookUpProduct(database);
		String applesPrice = look.getDescriptionOfProduct(plu1); 
		
		assertEquals(applesPrice, "Fresh organic apples!"); 
	}
	
	@Test(expected=SimulationException.class)
	public void testGetDescriptionOfProductException() {
		try {
			PriceLookupCode plu1 = new PriceLookupCode("99999"); 
			CustomerLookUpProduct look = new CustomerLookUpProduct(database);
			String nonExistentLookup = look.getDescriptionOfProduct(plu1);
			
	}catch(Exception e) {
		throw new SimulationException(new NullPointerException("Error"));
		}
	}
}
