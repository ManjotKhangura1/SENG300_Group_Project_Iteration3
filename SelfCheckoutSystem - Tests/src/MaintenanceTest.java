import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;

import Software.Maintenance;

public class MaintenanceTest {
	SelfCheckoutStation station;
	int[] noteDenominations = { 5, 10, 20, 50, 100 };
	BigDecimal[] coinDenominations = { BigDecimal.valueOf(0.05), BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.25),
			BigDecimal.valueOf(1.0), BigDecimal.valueOf(2.0) };
	Currency currency; 
	
	@Before
	public void setUp() throws Exception {
		// Creates a self checkout station
		currency = Currency.getInstance("CAD");
		int[] noteDenominations = { 5, 10, 20, 50, 100 };
		BigDecimal[] coinDenominations = { BigDecimal.valueOf(0.05), BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.25),
				BigDecimal.valueOf(1.0), BigDecimal.valueOf(2.0) };
		int maxWeight = 23000;
		int scaleSensitivity = 10;
		station = new SelfCheckoutStation(currency, noteDenominations, coinDenominations, maxWeight, scaleSensitivity);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test //Tests if the proper exception is thrown when constructor is called with a null station
	public void testConstructorNullStation() {
		try {
			Maintenance maintenance = new Maintenance(null);
			fail("Should have thrown exception"); //fail because an exception should have been thrown and thus should not have reached this line
		} catch (Exception e) {
			assertTrue("Simulation Exception thrown because null station provided", e instanceof SimulationException);
		}
	}
	
	@Test //Tests the normal function of the constructor
	public void testConstructor() {
		try {
			Maintenance maintenance = new Maintenance(station);
			
			assertNotNull(maintenance);
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}

	@Test //Tests that program does try to refill if an invalid coin denomination is provided
	public void testRefillCoinInvalidDenom() {
		try {
			int refillAmount = 1;
			Maintenance maintenance = new Maintenance(station);
			maintenance.refillCoin(BigDecimal.valueOf(.02),refillAmount);
			
			boolean expected = false;
			
			assertEquals(expected, maintenance.isRefillSuccess());
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}
	
	@Test //Tests that program does not try to refill if negative refillAmount provided
	public void testRefillCoinInvalidRefillAmountNeg() {
		try {
			int refillAmount = -2;
			Maintenance maintenance = new Maintenance(station);
			maintenance.refillCoin(coinDenominations[0],refillAmount);
			
			boolean expected = false;
			
			assertEquals(expected, maintenance.isRefillSuccess());
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}
	
	@Test //Tests that program does not try to refill if refillAmount of 0 provided
	public void testRefillCoinInvalidRefillAmount0() {
		try {
			int refillAmount = 0;
			Maintenance maintenance = new Maintenance(station);
			maintenance.refillCoin(coinDenominations[0],refillAmount);
			
			boolean expected = false;
			
			assertEquals(expected, maintenance.isRefillSuccess());
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}
	
	@Test //Tests that system refilled proper amount when refillAmount won't overload system
	public void testRefillCoin() {
		try {
			int refillAmount = 1;
			Maintenance maintenance = new Maintenance(station);
			maintenance.refillCoin(coinDenominations[0],refillAmount);
			
			int expected = refillAmount;
			
			assertEquals(expected, maintenance.getActualRefillAmount());
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}
	
	@Test //Tests that system refilled proper amount when refillAmount more than capacity
	public void testRefillCoinTooMuch() {
		try {
			int refillAmount = station.coinDispensers.get(coinDenominations[0]).getCapacity() + 1;
			Maintenance maintenance = new Maintenance(station);
			maintenance.refillCoin(coinDenominations[0],refillAmount);
			
			int expected = station.coinDispensers.get(coinDenominations[0]).getCapacity();
			
			assertEquals(expected, maintenance.getActualRefillAmount());
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}
	
	@Test //Tests that program does try to refill if an invalid banknote denomination is provided
	public void testRefillBanknoteInvalidDenom() {
		try {
			int refillAmount = 1;
			Maintenance maintenance = new Maintenance(station);
			maintenance.refillBanknote(15,refillAmount);
			
			boolean expected = false;
			
			assertEquals(expected, maintenance.isRefillSuccess());
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}
	
	@Test //Tests that program does not try to refill if negative refillAmount provided
	public void testRefillBanknoteInvalidRefillAmountNeg() {
		try {
			int refillAmount = -2;
			Maintenance maintenance = new Maintenance(station);
			maintenance.refillBanknote(noteDenominations[0],refillAmount);
			
			boolean expected = false;
			
			assertEquals(expected, maintenance.isRefillSuccess());
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}
	
	@Test //Tests that program does not try to refill if refillAmount of 0 provided
	public void testRefillBanknoteInvalidRefillAmount0() {
		try {
			int refillAmount = 0;
			Maintenance maintenance = new Maintenance(station);
			maintenance.refillBanknote(noteDenominations[0],refillAmount);
			
			boolean expected = false;
			
			assertEquals(expected, maintenance.isRefillSuccess());
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}

	@Test  //Tests that system refilled proper amount when refillAmount won't overload system
	public void testRefillBanknote() {
		try {
			int refillAmount = 1;
			Maintenance maintenance = new Maintenance(station);
			maintenance.refillBanknote(noteDenominations[0],refillAmount);
			
			int expected = refillAmount;
			
			assertEquals(expected, maintenance.getActualRefillAmount());
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}
	
	@Test //Tests that system refilled proper amount when refillAmount more than capacity
	public void testRefillBanknoteTooMuch() {
		try {
			int refillAmount = station.banknoteDispensers.get(noteDenominations[0]).getCapacity() + 1;
			Maintenance maintenance = new Maintenance(station);
			maintenance.refillBanknote(noteDenominations[0],refillAmount);
			
			int expected = station.banknoteDispensers.get(noteDenominations[0]).getCapacity();
			
			assertEquals(expected, maintenance.getActualRefillAmount());
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}

	@Test //Test emptyCoinStorageUnit when given null storage unit
	public void testEmptyCoinStorageUnitNull() {
		try {
			Maintenance maintenance = new Maintenance(station);
			maintenance.emptyCoinStorageUnit(null);
			
			boolean expected = false;
			
			assertEquals(expected, maintenance.isEmptied());
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}
	
	@Test //Test emptyCoinStorageUnit when given storage unit that is already empty
	public void testEmptyCoinStorageUnitEmpty() {
		try {
			Maintenance maintenance = new Maintenance(station);
			maintenance.emptyCoinStorageUnit(station.coinStorage);
			
			boolean expected = false;
			
			assertEquals(expected, maintenance.isEmptied());
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}
	
	@Test //Testing normal usage
	public void testEmptyCoinStorageUnit() {
		try {
			station.coinStorage.load(new Coin(coinDenominations[0], currency));
			Maintenance maintenance = new Maintenance(station);
			maintenance.emptyCoinStorageUnit(station.coinStorage);
			
			boolean expected = true;
			
			assertEquals(expected, maintenance.isEmptied());
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}
	
	@Test //Test emptybanknoteStorageUnit when given null storage unit
	public void testEmptyBanknoteStorageUnitNull() {
		try {
			Maintenance maintenance = new Maintenance(station);
			maintenance.emptyBanknoteStorageUnit(null);
			
			boolean expected = false;
			
			assertEquals(expected, maintenance.isEmptied());
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}
	
	@Test //Test emptyBanknoteStorageUnit when given storage unit that is already empty
	public void testEmptyBanknoteStorageUnitEmpty() {
		try {
			Maintenance maintenance = new Maintenance(station);
			maintenance.emptyBanknoteStorageUnit(station.banknoteStorage);
			
			boolean expected = false;
			
			assertEquals(expected, maintenance.isEmptied());
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}

	@Test //Testing normal usage
	public void testEmptyBanknoteStorageUnit() {
		try {
			station.banknoteStorage.load(new Banknote(noteDenominations[0], currency));
			Maintenance maintenance = new Maintenance(station);
			maintenance.emptyBanknoteStorageUnit(station.banknoteStorage);
			
			boolean expected = true;
			
			assertEquals(expected, maintenance.isEmptied());
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}

	@Test //Testing normal usage
	public void testStartUp() {
		Maintenance maintenance = new Maintenance(station);
		maintenance.startUp();
		
		boolean expected = true;
		
		assertEquals(expected, maintenance.isStationOn());
	}

	@Test //Testing normal usage
	public void testShutDown() {
		Maintenance maintenance = new Maintenance(station);
		maintenance.shutDown();
		
		boolean expected = false;
		
		assertEquals(expected, maintenance.isStationOn());
	}

	@Test //Test that null return when given invalid units
	public void testChangePaperInvaludUnits() {
		try {
			Maintenance maintenance = new Maintenance(station);
			assertNull(maintenance.changePaper(0));
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}

	@Test //Testing normal usage
	public void testChangePaper() {
		try {
			Maintenance maintenance = new Maintenance(station);
			assertNotNull(maintenance.changePaper(1));
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}
	
	@Test //Test that null return when given invalid Quantity
	public void testChangePaperInvaludQuantity() {
		try {
			Maintenance maintenance = new Maintenance(station);
			assertNull(maintenance.changeInk(0));
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}
	
	@Test //Testing normal usage
	public void testChangeInk() {
		try {
			Maintenance maintenance = new Maintenance(station);
			assertNotNull(maintenance.changeInk(1));
		} catch (Exception e) {
			fail("Exception thrown for valid code");
		}
	}

}
