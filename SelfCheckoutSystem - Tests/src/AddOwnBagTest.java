import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;

import Software.AddOwnBag;
import Software.Bag;
import Software.BaggingArea;

public class AddOwnBagTest {
	private SelfCheckoutStation scs;
	private AddOwnBag addOwnBag;
	private int scaleSensitivity;
	private BaggingArea bagArea;
	
	//setting up the constructor for the SelfCheckoutStation
	@Before
		public void setup() throws OverloadException {
		Currency currency = Currency.getInstance("USD");
		int[] banknoteDenominations = {5,10,20,50,100};
		BigDecimal[] coinDenominations = {new BigDecimal(0.01), new BigDecimal(0.05), new BigDecimal(0.1),new BigDecimal(0.25), new BigDecimal(0.50)};
		int scaleMaximumWeight = 1000;
		this.scaleSensitivity = 1;
	
		scs = new SelfCheckoutStation(currency, banknoteDenominations, coinDenominations, scaleMaximumWeight, scaleSensitivity);
		addOwnBag = new AddOwnBag(scs);
		bagArea = new BaggingArea(scs);
	}
	
	//testing when SelfCheckoutStation in the parameter of the constructor is null
	@Test
	public void testConstructor_WithNull() {
		try {
			AddOwnBag aob = new AddOwnBag(null);
		}
		catch(Exception e) {
			assertTrue("Null constructor", e instanceof SimulationException);
		}
	}
	
	//testing with a bag with a weight that surpasses the limit, testing overload
	@Test
	public void testAddBag_WithOverload(){
		double illegalWeight = 50000.0;
		Bag bag = new Bag(illegalWeight);
		try {
			addOwnBag.addBag(bag);
			bagArea.updateWeight();
		}
		catch(Exception e) {
			assertTrue("Bag weight exceeds the limit of the scale", e instanceof OverloadException);
		}
	}
	
	//testing bag with weight less than the limit and greater than the sensitivity
	@Test
	public void testAddBag_With5GramBag() throws DisabledException, OverloadException{
		double expectedWeight = 5.0;
		Bag bag = new Bag(expectedWeight);
		addOwnBag.addBag(bag);
		bagArea.updateWeight();
		double actualWeight = bagArea.getWeightBaggingArea().doubleValue();
		assertEquals(expectedWeight,actualWeight, scaleSensitivity);
	}
	
	//testing bag with weight less than the limit and less than the sensitivity
	@Test
	public void testAddBag_WithHalfGramBag() throws DisabledException, OverloadException{
		double expectedWeight = 0.5;
		Bag bag = new Bag(expectedWeight);
		addOwnBag.addBag(bag);
		bagArea.updateWeight();
		double actualWeight = bagArea.getWeightBaggingArea().doubleValue();
		assertEquals(expectedWeight,actualWeight, scaleSensitivity);
	}
	
	//testing bag with negative weight
	@Test
	public void testAddBag_WithNegativeWeight(){
		double expectedWeight = -1.0;
		try {
			Bag bag = new Bag (expectedWeight);
			addOwnBag.addBag(bag);
			bagArea.updateWeight();
			double actualWeight = bagArea.getWeightBaggingArea().doubleValue();
		}
		catch(Exception e) {
			assertTrue("Bag weight cannot be zero or less", e instanceof SimulationException);
		}
	}
	
	//testing when trying to add a null bag
	@Test
	public void testAddBag_WithNull() {
		try {
			addOwnBag.addBag(null);
		}
		catch(Exception e) {
			assertTrue("Null bag is being added", e instanceof SimulationException);
		}
	}
	
	//testing with a disabled scale
	@Test
	public void testAddBag_WithDisabledScale() {
		scs.baggingArea.disable();
		double testWeight = 5.0;
		Bag bag = new Bag(testWeight);
		try {
			addOwnBag.addBag(bag);
		}
		catch(Exception e) {
			assertTrue("Scale is disabled.", e instanceof DisabledException);
		}
	}
	
	//testing with an enabled scale
	@Test
	public void testAddBag_WithEnabledScale() throws DisabledException, OverloadException {
		scs.baggingArea.enable();
		double expectedWeight = 5.0;
		Bag bag = new Bag(expectedWeight);
		addOwnBag.addBag(bag);
		bagArea.updateWeight();
		double actualWeight = bagArea.getWeightBaggingArea().doubleValue();
		assertEquals(expectedWeight,actualWeight, scaleSensitivity);
	}
	
	//testing with adding the same bag more than once
	@Test
	public void testAddBag_AddingSameBagTwice() throws DisabledException {
		double testWeight = 5.0;
		Bag bag = new Bag(testWeight);
		addOwnBag.addBag(bag);
		try {
			addOwnBag.addBag(bag);
		}
		catch(Exception e) {
			assertTrue("Cannot add same bag more than once", e instanceof SimulationException);
		}
	}
	
	//test remove bag with disabled scale
	@Test
	public void testRemoveBag_WithDisabledScale() throws DisabledException{
		double testWeight = 5.0;
		Bag bag = new Bag(testWeight);
		addOwnBag.addBag(bag);
		scs.baggingArea.disable();
		try {
			addOwnBag.removeBag(bag);
		}
		catch(Exception e) {
			assertTrue("Scale is disabled.", e instanceof DisabledException);
		}
	}
	
	//test remove bag with enabled scale
	@Test
	public void testRemoveBag_WithEnabledScale() throws DisabledException, OverloadException {
		double testWeight = 5.0;
		double expectedWeight = testWeight - testWeight;
		scs.baggingArea.enable();
		Bag bag = new Bag(testWeight);
		addOwnBag.addBag(bag);
		bagArea.updateWeight();
		addOwnBag.removeBag(bag);
		bagArea.updateWeight();
		double actualWeight = bagArea.getWeightBaggingArea().doubleValue();
		assertEquals(expectedWeight,actualWeight, scaleSensitivity);
	}
	
	//test remove bag with null bag
	@Test
	public void testRemoveBag_WithNull() {
		try {
			addOwnBag.removeBag(null);
		}
		catch(Exception e) {
			assertTrue("Null bag is being removed.", e instanceof SimulationException);
		}
	}
	
	//test to remove a bag when bags on the scale are empty
	@Test
	public void testRemoveBag_RemovingWhenEmpty() {
		double testWeight = 5.0;
		Bag bag = new Bag(testWeight);
		try {
		addOwnBag.removeBag(bag);
		}
		catch (Exception e) {
			assertTrue("Removing a bag when there is nothing on the scale", e instanceof SimulationException);
		}
	}
	
	//test removing one bag out of two
	@Test
	public void testRemoveBag_RemovingOneBag() throws DisabledException, OverloadException {
		double bag0Weight = 10.0;
		double bag1Weight = 5.0;
		double expectedWeight = bag0Weight + bag1Weight - bag1Weight;
		Bag bag0 = new Bag(bag0Weight);
		Bag bag1 = new Bag(bag1Weight);
		addOwnBag.addBag(bag0);
		bagArea.updateWeight();
		addOwnBag.addBag(bag1);
		bagArea.updateWeight();
		addOwnBag.removeBag(bag1);
		bagArea.updateWeight();
		double actualWeight = bagArea.getWeightBaggingArea().doubleValue();
		assertEquals(expectedWeight,actualWeight, scaleSensitivity);
	}
	
	//test outOfOverload with two bags 
	@Test
	public void testAddOwnBag_OutOfOverload() throws DisabledException, OverloadException {
		double bag0Weight = 10.0;
		double bag1Weight = 1000.0;
		double expectedWeight = bag0Weight + bag1Weight - bag1Weight;
		Bag bag0 = new Bag(bag0Weight);
		Bag bag1 = new Bag(bag1Weight);
		addOwnBag.addBag(bag0);
		bagArea.updateWeight();
		try {
			addOwnBag.addBag(bag1);
			bagArea.updateWeight();
		}
		catch(Exception e) {
			addOwnBag.removeBag(bag1);
			bagArea.updateWeight();
			double actualWeight = bagArea.getWeightBaggingArea().doubleValue();
			assertEquals(expectedWeight,actualWeight, scaleSensitivity);
		}
	}
	
	
	//test outofOverload with one bag
	@Test	
	public void testAddOwnBag_OutOverloadWithOneBag() throws DisabledException, OverloadException {
		double testWeight = 4000.0;
		double expectedWeight = testWeight - testWeight;
		Bag bag = new Bag(testWeight);
		try {
			addOwnBag.addBag(bag);
			bagArea.updateWeight();
		}
		catch(Exception e) {
			addOwnBag.removeBag(bag);
			bagArea.updateWeight();
			double actualWeight = bagArea.getWeightBaggingArea().doubleValue();
			assertEquals(expectedWeight,actualWeight, scaleSensitivity);
		}
	}
	
	//test to remove bag when weight is less than sensitivity
	@Test
	public void testRemoveBag_RemovingHalfGramBag() throws DisabledException, OverloadException {
		double bagWeight = 0.5;
		double expectedWeight = bagWeight - bagWeight;
		Bag bag = new Bag(bagWeight);
		addOwnBag.addBag(bag);
		bagArea.updateWeight();
		addOwnBag.removeBag(bag);
		bagArea.updateWeight();
		double actualWeight = bagArea.getWeightBaggingArea().doubleValue();;
		assertEquals(expectedWeight,actualWeight, scaleSensitivity);
	}
	
	//test to remove a bag that has not yet been added
	@Test
	public void testRemoveBag_RemovingWhenNotAdded() throws DisabledException {
		double bag0Weight = 5.0;
		double bag1Weight = 3.0;
		Bag bag0 = new Bag(bag0Weight);
		Bag bag1 = new Bag(bag0Weight);
		addOwnBag.addBag(bag0);
		try {
		addOwnBag.removeBag(bag1);
		}
		catch (Exception e) {
			assertTrue("Removing a bag that has not been added yet", e instanceof SimulationException);
		}
	}
	
}
