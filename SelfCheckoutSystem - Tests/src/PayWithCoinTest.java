import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;

import Software.PayWithCoin;

public class PayWithCoinTest {
	
	SelfCheckoutStation station;
	Coin coin;

	@Before
	public void setUp() throws Exception {
		//Creates a self checkout station to be passed as an argument
		Currency currency = Currency.getInstance("CAD");
		int[] noteDenominations = {5,10,20,50,100};
		BigDecimal[] coinDenomonations = {BigDecimal.valueOf(0.05) , BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.25), 
		BigDecimal.valueOf(1.0), BigDecimal.valueOf(2.0)};
		int maxWeight = 23000;
		int scaleSensitivity = 10;
		
		//Initializing the station and the coin
		station = new SelfCheckoutStation(currency, noteDenominations, coinDenomonations, maxWeight,scaleSensitivity);
		coin = new Coin(coinDenomonations[4], currency);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test //Tests if the proper exception is thrown when PayWithcoin is called with a null station
	public void testPayWithcoinNull() {
		try {
			PayWithCoin payWithCoin = new PayWithCoin(null);
			fail("Should have thrown exception"); //fail because an exception should have been thrown and thus should not have reached this line
		} catch (Exception e) {
			assertTrue("Simulation Exception thrown because null station provided", e instanceof SimulationException);
		}	
	} 
	
	@Test //Tests if PayWithcoin is created properly when given proper station
	public void testPayWithcoin() {
		try {
			PayWithCoin payWithCoin = new PayWithCoin(station);
			assertNotNull(payWithCoin);
		} catch (Exception e) {
			//fails if exception thrown because this is a valid station
			fail("Exception thrown for valid code");
		}	
	}

	@Test //Tests if proper exception is thrown when pay is called with a null coin
	public void testPayNullcoin() {
		try {
			PayWithCoin payWithCoin = new PayWithCoin(station);
			payWithCoin.setEnabled(true);
			payWithCoin.pay(null);
			fail("Should have thrown exception"); //fail because an exception should have been thrown and thus should not have reached this line
		} catch (Exception e) {
			assertTrue("Simulation Exception thrown because null coin provided", e instanceof SimulationException);
		}
	}
	
	@Test //Tests if proper exception is thrown when disabled exception is caught
	public void testPayDisabledException() {
		try {
			PayWithCoin payWithCoin = new PayWithCoin(station);
			station.coinValidator.disable(); //disables coin slot so the hardware throws a disabled exception for the software to catch
			payWithCoin.setEnabled(true);
			payWithCoin.pay(coin);
			fail("Should have thrown exception"); //fail because an exception should have been thrown and thus should not have reached this line
		} catch (Exception e) {
			assertTrue("Simulation Exception thrown because Disabled Exception caught", e instanceof SimulationException);
		}
	}

	@Test //Tests if coinsDeposited list is updated when valid coin is given
	public void testPay() {
		try {
			PayWithCoin payWithCoin = new PayWithCoin(station);
			payWithCoin.setEnabled(true);
			payWithCoin.pay(coin);
			
			ArrayList<Coin> expected = new ArrayList<Coin>();
			
			//We know if update was executed properly if the list is no longer empty
			assertNotEquals(expected,payWithCoin.getCoinsDepositied());
			
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}
	
	@Test //Tests if coinsDeposited list is updated when coin with unmatching currency is given
	public void testPayInvalid() {
		try {
			PayWithCoin payWithCoin = new PayWithCoin(station);
			Currency currency = Currency.getInstance("USD");
			BigDecimal value = BigDecimal.valueOf(2.0);
			Coin coin2 = new Coin(value, currency);
			payWithCoin.setEnabled(true);
			payWithCoin.pay(coin2);
			
			boolean expected = false;
			
			assertEquals(expected,payWithCoin.getAccepted());
			//This should not throw an exception but the test fails due to the design of the bidirectional channel 
			//I don't believe this should throw an exception every time so I let this fail
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}
	
	@Test //Tests if coinsDeposited list is updated when coin with unmatching currency is given
	public void testPayInvalid2() throws DisabledException, OverloadException {
			PayWithCoin payWithCoin = new PayWithCoin(station);
			Currency currency = Currency.getInstance("USD");
			BigDecimal value = BigDecimal.valueOf(2.0);
			Coin coin2 = new Coin(value, currency);
			payWithCoin.setEnabled(false);
			payWithCoin.pay(coin2);
	}
	
	@Test //Tests if coinsDeposited list is updated when valid coin is given
	public void testPayDisabled() {
		try {
			PayWithCoin payWithCoin = new PayWithCoin(station);
			//making sure the system is disabled
			station.coinValidator.disable();
			
			boolean expected = false;

			assertEquals(expected,payWithCoin.getEnabled());
			
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}

	@Test //Test if getCoinsPaid gets the correct total 
	public void testGetTotalPaid() throws DisabledException, OverloadException {
		PayWithCoin payWithCoin = new PayWithCoin(station);
		payWithCoin.setEnabled(true);
		payWithCoin.pay(coin);
		
		double expected = coin.getValue().doubleValue();
		
		assertEquals(expected,payWithCoin.getcoinsPaid(),0);
	}

	@Test //Test if getStation gets the correct station
	public void testGetStation() {
		PayWithCoin payWithCoin = new PayWithCoin(station);
		
		assertEquals(station, payWithCoin.getStation());
	}

	@Test //Tests if getNotesDeposited gets the correct arrayList
	public void testGetNotesDeposited() throws DisabledException, OverloadException {
		PayWithCoin payWithCoin = new PayWithCoin(station);
		payWithCoin.setEnabled(true);
		payWithCoin.pay(coin);
		
		ArrayList<Coin> expected = new ArrayList<Coin>();
		expected.add(coin);
		
		assertEquals(expected,payWithCoin.getCoinsDepositied());
	}
	
	@Test //Tests if getAccepted gets the correct truth value
	public void testGetAcceptedFalse() {
		PayWithCoin payWithCoin = new PayWithCoin(station);
		boolean expected = false;
		
		assertEquals(expected, payWithCoin.getAccepted());
	}
	
	@Test //Tests if getAccepted gets the correct truth value
	public void testGetAcceptedTrue() throws DisabledException, OverloadException {
		PayWithCoin payWithCoin = new PayWithCoin(station);
		payWithCoin.setEnabled(true);
		payWithCoin.pay(coin);
		boolean expected = true;
		
		
		assertEquals(expected, payWithCoin.getAccepted());
	}
	
	@Test //Tests if getEnabled gets the proper value when the validator is enabled
	public void testGetEnabled() {
		try {
			PayWithCoin payWithCoin = new PayWithCoin(station);
			payWithCoin.setEnabled(true);
			payWithCoin.pay(coin);
			
			for(int i = 0; i < 100; i++) {
				//We know if update was executed properly if the total is no longer the default of 0
				assertNotEquals(0,payWithCoin.getCoinsDepositied());
			}
			
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}

	@Test //Tests if getEnabled gets the proper value when the validator is disabled
	public void testGetEnabledDisabled() {
		try {
			PayWithCoin payWithCoin = new PayWithCoin(station);
			//making sure the system is enabled
			station.coinValidator.enable();
			
			boolean expected = true;

			assertEquals(expected,payWithCoin.getEnabled());
			
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}

}
}
