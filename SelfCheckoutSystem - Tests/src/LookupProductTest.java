import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Test;
import org.lsmr.selfcheckout.PLUCodedItem;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;


public class LookupProductTest {

	private BigDecimal nickel = new BigDecimal(0.05);
    private BigDecimal dime = new BigDecimal(0.1);
    private BigDecimal quarter = new BigDecimal(0.25);
    private BigDecimal loonie = new BigDecimal(1.00);
    private BigDecimal toonie = new BigDecimal(2.00);
    private BigDecimal[] coinDenominations = {nickel, dime, quarter, loonie, toonie};
	private int[] banknoteDenominations = {5, 10 , 20 , 50, 100};
    private int scaleMaximumWeight = 23000;
    private int scaleSensitivity = 10;
    private Currency CAD = Currency.getInstance("CAD");
	private SelfCheckoutStation station = new SelfCheckoutStation(CAD, banknoteDenominations, coinDenominations, scaleMaximumWeight, scaleSensitivity);

	

	@Test
	public void testLookupInDatabase() throws OverloadException{
			LookupProduct lookup = new LookupProduct(station, "14040");
			PriceLookupCode plu = new PriceLookupCode("14040");
			PLUCodedItem onions = new PLUCodedItem(plu, 1000); // 1000g = 1kg of onions
			station.scale.add(onions);
			lookup.Lookup();
			assertEquals(lookup.getPrice(), 2.00, 0.00);

	}
	
	@Test
	public void testBigDecimalLookup() throws OverloadException{
			LookupProduct lookup = new LookupProduct(station, "14040");
			PriceLookupCode plu = new PriceLookupCode("14040");
			PLUCodedItem onions = new PLUCodedItem(plu, 1000); // 1000g = 1kg of onions
			station.scale.add(onions);
			lookup.Lookup();
			assertEquals(lookup.getBigDecimalPrice(), new BigDecimal(2.00));

	}
	
	@Test(expected=SimulationException.class)
	public void testLookupNotInDatabase() throws OverloadException {
		try {
			PriceLookupCode plu = new PriceLookupCode("1404");
			LookupProduct lookup = new LookupProduct(station, "1404");
			PLUCodedItem nonexistent = new PLUCodedItem(plu, 1000); // 1000g = 1kg of an unknown item
			station.scale.add(nonexistent);
			lookup.Lookup();
			assertEquals(lookup.getPrice(), LookupProduct.PLUPRODUCTS.get("1404"));
		}
		catch(Exception e) {
			throw new SimulationException(new NullPointerException("Error"));
		}
	}
	
	@Test(expected=SimulationException.class)
	public void testNullPLU() throws OverloadException {
		try {
			LookupProduct lookup = new LookupProduct(station, null);
			PLUCodedItem nonexistent = new PLUCodedItem(new PriceLookupCode(null), 1000); // 1000g = 1kg of an unknown item
			station.scale.add(nonexistent);
			lookup.Lookup();
			assertEquals(lookup.getPrice(), LookupProduct.PLUPRODUCTS.get(null));
		}
		catch(Exception e) {
			throw new SimulationException(new NullPointerException("Error"));
		}
	}
	

	@Test
	public void testGetDescription() throws OverloadException{
		try {
			PriceLookupCode plu = new PriceLookupCode("14040");
			LookupProduct lookup = new LookupProduct(station, "14040");
			PLUCodedItem onions = new PLUCodedItem(plu, 1000); // 1000g = 1kg of onions
			station.scale.add(onions);
			lookup.Lookup();
			assertEquals(lookup.getDescription(), "These are delicious onions imported from California!");
		}
		catch(Exception e) {
			throw new SimulationException(new NullPointerException("Error"));
		}
	}

}
