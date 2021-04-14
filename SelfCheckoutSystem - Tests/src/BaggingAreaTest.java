import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;

import Software.BaggingArea;

public class BaggingAreaTest {
	
	SelfCheckoutStation station;

	//Setting up the station to be used
	@Before
	public void setUp() throws Exception {
		//Creates a self checkout station to be passed as an argument
			Currency currency = Currency.getInstance("CAD");
			int[] noteDenominations = {5,10,20,50,100};
			BigDecimal[] coinDenomonations = {BigDecimal.valueOf(0.05) , BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.25), 
			BigDecimal.valueOf(1.0), BigDecimal.valueOf(2.0)};
			int maxWeight = 23000;
			int scaleSensitivity = 10;
			station = new SelfCheckoutStation(currency, noteDenominations, coinDenomonations, maxWeight,scaleSensitivity);
	}  

	@After
	public void tearDown() throws Exception {
		station = null;
	}

	//testing to see if the program accepts a null self checkout station
	@Test (expected = SimulationException.class)
	public void testBaggingAreaSelfCheckout() throws OverloadException {
		SelfCheckoutStation selfCheckout = null;
		BaggingArea b = new BaggingArea(selfCheckout);
	}
	
	//testing to see if the program accepts a valid self checkout station
	@Test 
	public void testBaggingAreaSelfCheckout2() throws OverloadException {
		BaggingArea b = new BaggingArea(station);
	}
	
	
	//Testing to see if the program accepts a null electronic scale
	@Test (expected = SimulationException.class)
	public void testBaggingAreaElectronicScale() {
		ElectronicScale e = null;
		BaggingArea b = new BaggingArea(e);
	}
	
	//Testing to see if the program accepts a valid self checkout station
	@Test 
	public void testBaggingAreaElectronicScale2(){
		ElectronicScale e = new ElectronicScale(23000, 10);
		BaggingArea b = new BaggingArea(e);
	}	
	
	//Testing to see if the program throws a simulation exception if an item is added which
	// has a higher weight than the items scanned
	@Test (expected = SimulationException.class)
	public void testaddItem() throws OverloadException, DisabledException {
		BaggingArea b = new BaggingArea(station);
		b.setWeightScanned(BigDecimal.valueOf(500));
		itemStub i = new itemStub(1000);
		b.addItem(i);
	}	
	//Testing to see if simulation exception is thrown when a null item is added
	@Test (expected = SimulationException.class)
	public void testaddItem2() throws OverloadException, DisabledException {
		BaggingArea b = new BaggingArea(station);
		itemStub i = null;
		b.addItem(i);
	}	
	
	//Testing to see if a valid item stub can be added
	@Test 
	public void testaddItem3() throws OverloadException, DisabledException {
		BaggingArea b = new BaggingArea(station);
		b.setWeightScanned(BigDecimal.valueOf(1000));
		itemStub i = new itemStub (500);
		b.addItem(i);

	}
	
	//Checking if the program will throw a disabled exception if the bagging area is disabled
	@Test (expected = DisabledException.class)
	public void testaddItem4() throws OverloadException, DisabledException {
		station.baggingArea.disable();
		BaggingArea b = new BaggingArea(station);
		b.setWeightScanned(BigDecimal.valueOf(500));
		itemStub i = new itemStub(1000);
		b.addItem(i);

	}
	
	//Checking to see if the program will throw a simulation exception if an item is removed 
	//when there is nothing on the scale
	@Test (expected = SimulationException.class)
	public void testremoveItem() throws OverloadException, DisabledException {
		BaggingArea b = new BaggingArea(station);
		b.setWeightScanned(BigDecimal.valueOf(500));
		itemStub i = new itemStub(1000);
		b.removeItem(i);

	}	
	
	//Testing to see if a simulation exception is thrown if a null item is removed
	@Test (expected = SimulationException.class)
	public void testremoveItem2() throws OverloadException, DisabledException {
		BaggingArea b = new BaggingArea(station);
		itemStub i = null;
		b.removeItem(i);

	}	
	//Testing to see if a valid item can be removed
	@Test 
	public void testremoveItem3() throws OverloadException, DisabledException {
		BaggingArea b = new BaggingArea(station);
		b.setWeightScanned(BigDecimal.valueOf(1000));
		itemStub i = new itemStub (500);
		b.addItem(i);
		b.removeItem(i);

	}
	
	//Test to see if a disabled exception is thrown when removing an item when the bagging area is disabled
	@Test (expected = DisabledException.class)
	public void testremoveItem4() throws OverloadException, DisabledException {
		station.baggingArea.disable();
		BaggingArea b = new BaggingArea(station);
		b.setWeightScanned(BigDecimal.valueOf(500));
		itemStub i = new itemStub(1000);
		b.removeItem(i);

	}
	
	//Test to see if an exception is thrown with a negative weight
	@Test  (expected = SimulationException.class)
	public void testsetWeightBaggingArea() throws OverloadException {
		BaggingArea b = new BaggingArea(station);
		b.setWeightBaggingArea(BigDecimal.valueOf(-1));

	}
	
	//Test to see if the setter works with a valid weight
	@Test 
	public void testsetWeightBaggingArea2() throws OverloadException{
		BaggingArea b = new BaggingArea(station);
		b.setWeightBaggingArea(BigDecimal.valueOf(500));
	}
	
	//Test to see if the getter is accurate for the bagging area weight
	@Test 
	public void testgetWeightBaggingArea() throws OverloadException{
		BaggingArea b = new BaggingArea(station);
		b.setWeightBaggingArea(BigDecimal.valueOf(1234));
		assertTrue(b.getWeightBaggingArea().doubleValue() == 1234);
		
	}
	
	//Test to see if a simulation exception will be thrown if the weight scanned is set to a smaller value
	//Then the total weight in the bagging area
	@Test  (expected = SimulationException.class)
	public void testsetWeightScanned() throws OverloadException {
		BaggingArea b = new BaggingArea(station);
		b.setWeightBaggingArea(BigDecimal.valueOf(21300));
		b.setWeightScanned(BigDecimal.valueOf(2000));

	}
	//Test to see if a simulation exception will be thrown with an invalid weight
	@Test (expected = SimulationException.class)
	public void testsetWeightScanned2() throws OverloadException{
		BaggingArea b = new BaggingArea(station);
		b.setWeightScanned(BigDecimal.valueOf(-50));
	}
	
	//Test to see if the setter for the set weight scanned works with a valid weight
	@Test
	public void testsetWeightScanned3() throws OverloadException{
		BaggingArea b = new BaggingArea(station);
		b.setWeightScanned(BigDecimal.valueOf(550));
	}
	
	//Testing to see if the getter for total items scanned is accurate
	@Test 
	public void testgetWeightScanned() throws OverloadException{
		BaggingArea b = new BaggingArea(station);
		b.setWeightScanned(BigDecimal.valueOf(1234.0));
		assertTrue(b.getWeightScanned().compareTo(BigDecimal.valueOf(1234.0)) == 0);
		
	}
	
	
	//Testing to see if the bagging area returned is correct
	@Test 
	public void testgetBaggingArea() throws OverloadException{
		ElectronicScale baggingArea = new ElectronicScale(50000,25);
		BaggingArea b = new BaggingArea(baggingArea);
		assertTrue(b.getBaggingArea().equals(baggingArea));
	}
	
	//Testing to see if the bagging area returned is incorrect
	@Test 
	public void testgetBaggingArea2() throws OverloadException{
		ElectronicScale baggingArea = new ElectronicScale(50000,25);
		ElectronicScale baggingArea2 = new ElectronicScale(5000,5);
		BaggingArea b = new BaggingArea(baggingArea);
		assertFalse(b.getBaggingArea().equals(baggingArea2));
	}
	
	//test to see if set weight changes the variable correctly
	@Test 
	public void testsetweightChanged() throws OverloadException{
		BaggingArea b = new BaggingArea(station);
		b.setweightChanged(true);
		assertTrue(b.getweightChanged() == true);
	}
	
	//test to see if the set overload changes the variable correctly 
	@Test 
	public void testsetoverload() throws OverloadException{
		BaggingArea b = new BaggingArea(station);
		b.setoverload(true);
		assertTrue(b.getoverload() == true);
		
	}
	
	//Test to check if the listener overload/weightchanged methods work
	@Test (expected = SimulationException.class)
	public void teststartListener() throws OverloadException, DisabledException{
		BaggingArea b = new BaggingArea(station);
		b.setWeightScanned(BigDecimal.valueOf(30000));
		itemStub i = new itemStub (23000);
		itemStub j = new itemStub(5000);
		b.addItem(i);
		b.addItem(j);
	}
	
	//Test to check if the the disable method updates the boolean within the listener
	@Test 
	public void teststartListener2() throws OverloadException, DisabledException{
		BaggingArea b = new BaggingArea(station);
		station.baggingArea.disable();

		assertTrue(b.getIsEnabled() == false);
	}
	
	//Test to check if the enable method updates the boolean within the listener
	@Test 
	public void teststartListener3() throws OverloadException, DisabledException{
		BaggingArea b = new BaggingArea(station);
		station.baggingArea.enable();
		
		assertTrue(b.getIsEnabled() == true);
	}
	
	//Test to see if the outOfOverload method works within the listener
	@Test
	public void teststartListener4() throws OverloadException, DisabledException{
		itemStub i = new itemStub(50000);
		
		BaggingArea b = new BaggingArea(station);
		station.baggingArea.add(i);
		station.baggingArea.remove(i);
		assertTrue(b.getoverload() == false);
	}
	
	//test to see if the weightChanged boolean is updated with an invalid weight
	@Test (expected = SimulationException.class)
	public void testaddItem5() throws OverloadException, DisabledException{
		itemStub j = new itemStub(5);
		BaggingArea b = new BaggingArea(station);
		b.setWeightScanned(BigDecimal.valueOf(30000));
		b.addItem(j);
	}
	
	@Test
	public void testRemoveItems() {
		try {
			BaggingArea b = new BaggingArea(station);
			b.setWeightScanned(BigDecimal.valueOf(1000));
			b.removeWeightScanned(BigDecimal.valueOf(500));
			assertTrue(b.getWeightScanned().compareTo(BigDecimal.valueOf(500)) == 0);
		}catch(Exception e) {
			fail();
		}
		
		try {
			BaggingArea b = new BaggingArea(station);
			b.setWeightScanned(BigDecimal.valueOf(1000));
			b.removeWeightScanned(BigDecimal.valueOf(-10));
		}catch(Exception e) {
			assertTrue(e instanceof SimulationException);
		}
	}
	
	//Creating a stub for the item class
	class itemStub extends Item {
		protected itemStub(double weight) {
			super(weight);
		}
	}
	

	
}
