import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;

import Software.PayWithBanknote;

public class PayWithBanknoteTest {
	SelfCheckoutStation station;
	Banknote banknote;

	@Before
	public void setUp() throws Exception {
		//Creates a self checkout station
		Currency currency = Currency.getInstance("CAD");
		int[] noteDenominations = {5,10,20,50,100};
		BigDecimal[] coinDenomonations = {BigDecimal.valueOf(0.05) , BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.25), 
				BigDecimal.valueOf(1.0), BigDecimal.valueOf(2.0)};
		int maxWeight = 23000;
		int scaleSensitivity = 10;
		station = new SelfCheckoutStation(currency, noteDenominations, coinDenomonations, maxWeight,scaleSensitivity);

		//creates a banknote
		int value = 5;
		banknote = new Banknote(value, currency);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test //Tests if the proper exception is thrown when PayWithBanknote is called with a null station
	public void testPayWithBanknoteNull() {
		try {
			PayWithBanknote payWithBanknote = new PayWithBanknote(null);
			fail("Should have thrown exception"); //fail because an exception should have been thrown and thus should not have reached this line
		} catch (Exception e) {
			assertTrue("Simulation Exception thrown because null station provided", e instanceof SimulationException);
		}	
	} 
	
	@Test //Tests if PayWithBanknote is created properly when given proper station
	public void testPayWithBanknote() {
		try {
			PayWithBanknote payWithBanknote = new PayWithBanknote(station);
			assertNotNull(payWithBanknote);
		} catch (Exception e) {
			//fails if exception thrown because this is a valid station
			fail("Exception thrown for valid code");
		}	
	}

	@Test //Tests if proper exception is thrown when pay is called with a null banknote
	public void testPayNullBanknote() {
		try {
			PayWithBanknote payWithBanknote = new PayWithBanknote(station);
			payWithBanknote.pay(null);
			fail("Should have thrown exception"); //fail because an exception should have been thrown and thus should not have reached this line
		} catch (Exception e) {
			assertTrue("Simulation Exception thrown because null banknote provided", e instanceof SimulationException);
		}
	}
	
	@Test //Tests if proper exception is thrown when disabled exception is caught
	public void testPayDisabledException() {
		try {
			PayWithBanknote payWithBanknote = new PayWithBanknote(station);
			station.banknoteInput.disable(); //disables banknote slot so the hardware throws a disabled exception for the software to catch
			payWithBanknote.pay(banknote);
			fail("Should have thrown exception"); //fail because an exception should have been thrown and thus should not have reached this line
		} catch (Exception e) {
			assertTrue("Simulation Exception thrown because Disabled Exception caught", e instanceof SimulationException);
		}
	}

	
	@Test //Tests if proper exception is thrown when overload exception is caught
	public void testPayOverloadException() {
		try {
			PayWithBanknote payWithBanknote = new PayWithBanknote(station);
			station.banknoteInput.emit(banknote); //emits a banknote so there is a dangling banknote so the hardware throws an overload exception for the software to catch
			payWithBanknote.pay(banknote);
			fail("Should have thrown exception"); //fail because an exception should have been thrown and thus should not have reached this line
		} catch (Exception e) {
			assertTrue("Simulation Exception thrown because OverloadException Exception caught", e instanceof SimulationException);
		}
	}
	
	@Test //Tests if notesDeposited list is updated when valid banknote is given
	public void testPay() {
		try {
			PayWithBanknote payWithBanknote = new PayWithBanknote(station);
			payWithBanknote.pay(banknote);
			
			ArrayList<Banknote> expected = new ArrayList<Banknote>();
			
			//We know if update was executed properly if the list is no longer empty
			assertNotEquals(expected,payWithBanknote.getNotesDeposited());
			
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}
	
	@Test //Tests if notesDeposited list is updated when banknote with unmatching currency is given
	public void testPayInvalid() {
		try {
			PayWithBanknote payWithBanknote = new PayWithBanknote(station);
			Currency currency = Currency.getInstance("USD");
			int value = 5;
			banknote = new Banknote(value, currency);
			payWithBanknote.pay(banknote);
			
			boolean expected = false;
			
			assertEquals(expected,payWithBanknote.getAccepted());
			//This should not throw an exception but the test fails due to the design of the bidirectional channel 
			//I don't believe this should throw an exception every time so I let this fail
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}
	
	@Test //Tests if notesDeposited list is updated when valid banknote is given
	public void testPayDisabled() {
		try {
			PayWithBanknote payWithBanknote = new PayWithBanknote(station);
			//making sure the system is disabled
			station.banknoteValidator.disable();
			
			boolean expected = false;

			assertEquals(expected,payWithBanknote.getEnabled());
			
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}

	@Test //Test if getTotalPaid gets the correct total 
	public void testGetTotalPaid() {
		PayWithBanknote payWithBanknote = new PayWithBanknote(station);
		payWithBanknote.pay(banknote);
		
		int expected = banknote.getValue();
		
		assertEquals(expected,payWithBanknote.getTotalPaid());
	}

	@Test //Test if getStation gets the correct station
	public void testGetStation() {
		PayWithBanknote payWithBanknote = new PayWithBanknote(station);
		
		assertEquals(station, payWithBanknote.getStation());
	}

	@Test //Tests if getNotesDeposited gets the correct arrayList
	public void testGetNotesDeposited() {
		PayWithBanknote payWithBanknote = new PayWithBanknote(station);
		payWithBanknote.pay(banknote);
		
		ArrayList<Banknote> expected = new ArrayList<Banknote>();
		expected.add(banknote);
		
		assertEquals(expected,payWithBanknote.getNotesDeposited());
	}
	
	@Test //Tests if getAccepted gets the correct truth value
	public void testGetAcceptedFalse() {
		PayWithBanknote payWithBanknote = new PayWithBanknote(station);
		boolean expected = false;
		
		for (int i = 0; i < 100; i++) {
			assertEquals(expected, payWithBanknote.getAccepted());
		}
	}
	
	@Test //Tests if getAccepted gets the correct truth value
	public void testGetAcceptedTrue() {
		PayWithBanknote payWithBanknote = new PayWithBanknote(station);
		payWithBanknote.pay(banknote);
		boolean expected = true;
		
		assertEquals(expected, payWithBanknote.getAccepted());
	}
	
	@Test //Tests if getEnabled gets the proper value when the validator is enabled
	public void testGetEnabled() {
		try {
			PayWithBanknote payWithBanknote = new PayWithBanknote(station);
			payWithBanknote.pay(banknote);
			
			//We know if update was executed properly if the total is no longer the default of 0
			assertNotEquals(0,payWithBanknote.getTotalPaid());
			
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}

	@Test //Tests if getEnabled gets the proper value when the validator is disabled
	public void testGetEnabledDisabled() {
		try {
			PayWithBanknote payWithBanknote = new PayWithBanknote(station);
			//making sure the system is enabled
			station.banknoteValidator.enable();
			
			boolean expected = true;

			assertEquals(expected,payWithBanknote.getEnabled());
			
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}

}
