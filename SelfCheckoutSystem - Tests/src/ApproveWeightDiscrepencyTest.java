import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;

import Software.ApproveWeightDiscrepency;
import Software.BaggingArea;

public class ApproveWeightDiscrepencyTest {
	SelfCheckoutStation station;
	BaggingArea bagging;

	@Before
	public void setUp() throws Exception {
		//Creates a self checkout station to be passed as an argument
		Currency currency = Currency.getInstance("CAD");
		int[] noteDenominations = {5,10,20,50,100};
		BigDecimal[] coinDenomonations = {BigDecimal.valueOf(0.05) , BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.25), 
		BigDecimal.valueOf(1.0), BigDecimal.valueOf(2.0)};
		int maxWeight = 23000;
		int scaleSensitivity = 10;
		station = new SelfCheckoutStation(currency, noteDenominations, coinDenomonations, maxWeight, scaleSensitivity);
		
		bagging = new BaggingArea(station);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testApproveWeightDiscrepency() {
		ApproveWeightDiscrepency test = new ApproveWeightDiscrepency(station, bagging);

		try {
			ApproveWeightDiscrepency test1 = new ApproveWeightDiscrepency(null, bagging);
		}catch(Exception e) {
			assertTrue(e instanceof SimulationException);
		}
		
		try {
			ApproveWeightDiscrepency test2 = new ApproveWeightDiscrepency(station, null);
		}catch(Exception e) {
			assertTrue(e instanceof SimulationException);
		}
	}

	@Test
	public void testApprove() {

		try {
			ApproveWeightDiscrepency test = new ApproveWeightDiscrepency(station, bagging);
			test.approve();
		} catch (Exception e) {
			fail();
		}
		
		try {
			station.baggingArea.add(new BarcodedItem(new Barcode("12345"), 23001));
			ApproveWeightDiscrepency test = new ApproveWeightDiscrepency(station, bagging);
			test.approve();
			
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof OverloadException);
		}
	}

}
