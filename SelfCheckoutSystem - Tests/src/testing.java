import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.products.BarcodedProduct;

public class testing {
//testing for scanning and bagging
	private SelfCheckoutStation scs;
	private int scaleSensitivity;
	private double price = 10.50;
	private double bagPrice = 0.10;
	
	private BaggingArea baggingArea;
	private ScanItem scanItem;
	private DeclineBagPrompt dbp;
	private EntersPlasticBagsUsed epbu;
	private FailBagging failBagging;
	private RemoveItem removeItem;
	private FinishesAddingItems finishAddingItems;
	private CustomerLookUpProduct clp;
	private AddOwnBag aob;
	
	private BaggingAreaWeightIncorrect bawi;

	private BarcodedItem barcodedItem;
	Map<Barcode, BarcodedProduct> database;
	
	
	@Before
	public void setup() throws OverloadException {
	Currency currency = Currency.getInstance("USD");
	int[] banknoteDenominations = {5,10,20,50,100};
	BigDecimal[] coinDenominations = {new BigDecimal(0.01), new BigDecimal(0.05), new BigDecimal(0.1),new BigDecimal(0.25), new BigDecimal(0.50)};
	int scaleMaximumWeight = 1000;
	this.scaleSensitivity = 1;
	
	
	//Creates a barcoded item
	Barcode barcode = new Barcode("1");
	barcodedItem = new BarcodedItem(barcode, 50);
	BarcodedProduct product = new BarcodedProduct(barcode, "the only item we sell", BigDecimal.valueOf(price));
	
	database = new HashMap<>();
	database.put(barcode, product);

	dbp = new DeclineBagPrompt();
	
	scs = new SelfCheckoutStation(currency, banknoteDenominations, coinDenominations, scaleMaximumWeight, scaleSensitivity);
	baggingArea = new BaggingArea(scs);
	scanItem = new ScanItem(scs, database, dbp);
	
	bawi = new BaggingAreaWeightIncorrect(scs, scanItem);
	
	epbu = new EntersPlasticBagsUsed();
	aob = new AddOwnBag(scs);
	
	}
	
	@Test
	public void testing1() throws DisabledException, OverloadException {
		scanItem.scanFromMain(barcodedItem, dbp.getPrompt());
		baggingArea.setWeightScanned(barcodedItem.getWeight());
		baggingArea.addItem(barcodedItem);
		bawi.calculate();
		epbu.setTotalPlasticBagsUsed(1);
		this.finishAddingItems = new FinishesAddingItems(scs, scanItem, baggingArea);
		double actual = finishAddingItems.getPrice() + epbu.calculateTotalPlasticBagsUsedPrice();
		double expected = price + bagPrice;
		
		assertEquals(expected, actual, 0);
	}

	@Test
	public void testing2() throws DisabledException, OverloadException {
		scanItem.scanFromMain(barcodedItem, dbp.getPrompt());
		baggingArea.setWeightScanned(barcodedItem.getWeight());
		baggingArea.addItem(barcodedItem);
		bawi.calculate();
		epbu.setTotalPlasticBagsUsed(1);
		this.finishAddingItems = new FinishesAddingItems(scs, scanItem, baggingArea);
		ArrayList<String> actual = finishAddingItems.getList();
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("1");
		assertEquals(expected, actual);
	}
	
	@Test
	public void testing3() throws DisabledException, OverloadException {
		Bag bag = new Bag(10);
		scanItem.scanFromMain(barcodedItem, dbp.getPrompt());
		baggingArea.setWeightScanned(barcodedItem.getWeight());
		baggingArea.addItem(barcodedItem);
		bawi.calculate();
		epbu.setTotalPlasticBagsUsed(0);
		aob.addBag(bag);
		bawi.calculate();
		this.finishAddingItems = new FinishesAddingItems(scs, scanItem, baggingArea);
		double actual = finishAddingItems.getPrice();
		double expected = price;
		
		assertEquals(expected, actual, 0);
	}
	
	
}
