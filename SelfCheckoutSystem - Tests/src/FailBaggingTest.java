import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;

import Software.BaggingArea;
import Software.FailBagging;


public class FailBaggingTest {
	
	SelfCheckoutStation station;			//Instantiate self checkout station
	
	//Make a couple barcodes to test on
	Barcode code = new Barcode("42");
	Barcode code2 = new Barcode("43");
	
	/*
	 * Sets up the self checkout station
	 */
	@Before
	public void setUp() throws Exception 
	{
		//Make currencies, coins, and banknotes for the station
		Currency canadianDollars = Currency.getInstance("CAD");
	    BigDecimal nickel = new BigDecimal(0.05);
	    BigDecimal dime = new BigDecimal(.1);
	    BigDecimal quarter = new BigDecimal(.25);
	    BigDecimal loonie = new BigDecimal(1.00);
	    BigDecimal toonie = new BigDecimal(2.00);
	    BigDecimal[] coinDenominations = {nickel, dime, quarter, loonie, toonie};
	    int[] bankNoteDenominations = {5, 10, 20, 50, 100};
	    
	    //Set max weight and sensitivities
	    int scaleMaximumWeight = 100;
	    int scaleSensitivity = 1;
	    station = new SelfCheckoutStation(canadianDollars, bankNoteDenominations, coinDenominations, scaleMaximumWeight, scaleSensitivity);
	}
	
	class Items extends Item
    {
        /**
         * Constructs an item with the indicated weight.
         *
         * @param weightInGrams The weight of the item.
         * @throws SimulationException If the weight is &le;0.
         */
        protected Items(double weightInGrams) {
            super(weightInGrams);
        }
    }
	
	/**
	 * Checks if scanner does lock when a new item is added
	 * @throws OverloadException
	 */
	@Test
	public void testLock() throws OverloadException
	{
		BaggingArea b = new BaggingArea(station);
		FailBagging fail = new FailBagging(station);
		Item item = new Items(2.34);
		
		fail.lockScan(item);
		
		assertTrue(fail.isLock() == true);
	}
	
	/**
	 * Tests if the weight changes if a barcoded item is added
	 * @throws OverloadException
	 * @throws DisabledException
	 */
	@Test
	public void testWeightChanged() throws OverloadException, DisabledException
	{
		FailBagging fail = new FailBagging(station);
		BarcodedItem bItem = new BarcodedItem(code, 2.34);
		
		station.mainScanner.scan(bItem);
		
		fail.lockScan(bItem);
		
		station.baggingArea.add(bItem);
		
		assertEquals(fail.isLock(), false);
	}
	
	/**
	 * Tests if a different item was added to the bagging area than then one we were trying to add
	 * @throws OverloadException
	 * @throws DisabledException
	 */
	@Test
	public void testWeightChangedFalse() throws OverloadException, DisabledException
	{
		FailBagging fail = new FailBagging(station);
		BarcodedItem bItem = new BarcodedItem(code, 10.22);
		BarcodedItem bItem2 = new BarcodedItem(code2, 2.34);
		
		station.mainScanner.scan(bItem);
		
		fail.lockScan(bItem);
		
		station.baggingArea.add(bItem2);
		
		assertEquals(fail.isLock(), true);
	}
	
	/**
	 * Tests if a scanner that was locked gets unlocked unexpectedly
	 * @throws OverloadException
	 * @throws DisabledException
	 */
	@Test
	public void testUnlockedScan() throws OverloadException, DisabledException
	{
		FailBagging fail = new FailBagging(station);
		BarcodedItem bItem = new BarcodedItem(code, 10.22);
		
		station.mainScanner.scan(bItem);
		
		fail.lockScan(bItem);
		
		fail.setLock(false);
		
		station.baggingArea.add(bItem);
		
		assertEquals(fail.isLock(), false);
	}
	
	/**
	 * Tests if admin override overrides a locked scanner
	 * @throws OverloadException
	 * @throws DisabledException
	 */
	@Test
	public void testAdminOverride() throws OverloadException, DisabledException
	{

		FailBagging fail = new FailBagging(station);
		BarcodedItem bItem = new BarcodedItem(code, 10.22);
		
		station.mainScanner.scan(bItem);
		
		fail.lockScan(bItem);
		
		fail.adminOverride();
		
		assertEquals(fail.isLock(), false);
	}
	
	/**
	 * Tests if admin override fails
	 * @throws OverloadException
	 * @throws DisabledException
	 */
	@Test
	public void testAdminOverrideFailed() throws OverloadException, DisabledException
	{

		FailBagging fail = new FailBagging(station);
		BarcodedItem bItem = new BarcodedItem(code, 10.22);
		
		station.mainScanner.scan(bItem);
		
		
		fail.lockScan(bItem);
		
		fail.setLock(false);
		
		fail.adminOverride();
		
		
		assertEquals(fail.isLock(), false);
	}
	
	/**
	 * Tests out of overload for listener
	 */
	@Test
	public void testOutOfOverload()
	{
		FailBagging fail = new FailBagging(station);
		BarcodedItem bItem = new BarcodedItem(code, 10000.22);
		station.mainScanner.scan(bItem);
        station.baggingArea.add(bItem);
        station.baggingArea.remove(bItem);
	}
	
	/**
	 * Tests enable and disable from listener
	 */
	@Test
	public void TestEnableDisableScanner()
	{
		FailBagging fail = new FailBagging(station);
		station.baggingArea.enable();
        station.baggingArea.disable();
	}

	
	@After
	public void tearDown() throws Exception 
	{
		
	}
	
}
